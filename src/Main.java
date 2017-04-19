
import javax.swing.*;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.*;
import java.util.Timer;
import java.util.stream.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madso
 */
public class Main {

	private Database datb;
	private PulseSensor puls;
	private TempSensor temp;
	private GUI gui;
	private boolean dangerPulseHigh;
	private boolean dangerPulseLow;
	private boolean dangerTempLow;
	private boolean dangerTempHigh;
	private ArrayList<String> inputTempBuffer;
	private ArrayList<Double> calcTempBuffer = new ArrayList<>();
	private ArrayList<String> inputPulsBuffer;
	private double[] calcPulsBuffer = new double[1000];
	private ArrayList<Integer> calcPuls = new ArrayList<>();

	public Main() {
		this.dangerPulseHigh = false;
		this.dangerPulseLow = false;
		this.dangerTempHigh = false;
		this.dangerTempLow = false;

		init();
	}

	public void init() {
		// Opret objekter for alle de tilsluttede
		try{
		Sensor sens = new Sensor();
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
			SerialPort testPort = sens.openPort(portNames[i]);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			boolean isPuls = sens.testType(testPort);
			testPort.closePort();
			if (isPuls) {
				System.out.println("Der blev tilsluttet en pulsmåler");
				puls = new PulseSensor(portNames[i]);
			} else {
				System.out.println("Der blev tilsluttet en temperaturmåler");
				temp = new TempSensor(portNames[i]);
			}
		}
		Thread sensorT = new Thread(temp);
		Thread sensorP = new Thread(puls);

		// set sensor-trådene til daemon
		sensorT.setDaemon(true);
		sensorP.setDaemon(true);

		// start de to tråde
		sensorT.start();
		sensorP.start();

		// init databbasen
		datb = new Database();

		// Opret GUI-objekt med databasen som parameter
		gui = new GUI(datb);
		}
		catch(SerialPortException e){
			e.printStackTrace();
		}
		// Mere?
	}

	public void run() {
		/*
		 * Alt det der skal ske i Main. 1) Indhente målinger fra sensorer 2)
		 * Kontrollere data 3) Beregne puls/etwas 4) Gemme i databasen 4)
		 * Opdatere GUI (måske?)
		 */
		
		/*
		 * TJEK HVILKE SENSORER DER ER AKTIVE 
		 * Ellers stopper programmet her...
		 */
		inputTempBuffer = temp.getData(); // De skal returnere noget
		inputPulsBuffer = puls.getData();

		validate();
		calcPuls.add(calculatePulse());
		boundsCheck();

		// Skriv til databasen
		for (double x : calcTempBuffer) {
			datb.writeTo("Temperatur", x);
		}

		for (double y : calcPuls) {
			datb.writeTo("Pulsslag", y);
		}
	}

	public void validate() {
		// noget med at tjekke om data er OK inden det gemmes i databasen
		for (int i = 0; i < inputTempBuffer.size(); i++) {
			String buffer = inputTempBuffer.get(i);
			if (!buffer.isEmpty() && buffer.length() == 7) {
				try {
					calcTempBuffer.add(Double.parseDouble(buffer.substring(2, 6)));
				} catch (ArithmeticException e) {
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < inputPulsBuffer.size(); i++) {
			String buffer = inputPulsBuffer.get(i);
			if (!buffer.isEmpty() && i < 1000) {
				try {
					calcPulsBuffer[i] = Double.parseDouble(buffer);
				} catch (ArithmeticException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// en klasse til at tjekke om grænseværdierne er overskredet
	public void boundsCheck() {
		// Der skal indhentes grænseværdier: [tl tu pl pu]
		double[] bounds = gui.getBound();
		double tl = bounds[0]; // temp nedre
		double tu = bounds[1];
		double pl = bounds[2];
		double pu = bounds[3];

		// tjek min og max værdier
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		for (double x : calcTempBuffer) {
			max = (x > max) ? x : max;
			min = (x < min) ? x : min;
		}

		// Tjek grænseværdier for temp
		String type = "";
		if (max > tu || min < tl) {
			type = "Temp";
			gui.advarsel(type); // Her kaldes så den relevante metode i GUI
		}

		// Tjek min og max for puls
		max = Double.MIN_VALUE;
		min = Double.MAX_VALUE;
		for (double x : calcPuls) {
			max = (x > max) ? x : max;
			min = (x < min) ? x : min;
		}

		// tjek grænseværdier for pulsS
		if (max > pu || min < pl) {
			type = "Puls";
			gui.advarsel(type); // Her kaldes så den relevante metode i GUI
		}
	}

	public int calculatePulse() {
		// Udregner afvigelsen for 300 forskydelser
		double[] scores = new double[300];
		for (int j = 1; j < 301; j++) {
			double score = 0;
			int b = j;
			int a = 0;
			while (b < (calcPulsBuffer.length)) {
				score += (calcPulsBuffer[a] - calcPulsBuffer[b]) * (calcPulsBuffer[a] - calcPulsBuffer[b]);
				b++;
				a++;
			}
			scores[j - 1] = score;
		}

		// Finder laveste værdi for bølgedale
		boolean ned = false;
		boolean bund = false;
		double min = Double.MAX_VALUE;
		for (int i = 60; i < scores.length - 1; i++) {
			ned = (scores[i] - scores[i - 1] < 0);
			if (i == scores.length)
				break;
			else {
				bund = (scores[i + 1] - scores[i] >= 0);
				if (bund && ned) {
					min = (scores[i] < min) ? scores[i] : min;
				}
			}
		}

		// Finder første forskydning med lav nok score
		int hit = 0;
		for (int i = 60; i < scores.length - 1; i++) {
			ned = (scores[i] - scores[i - 1] < 0);
			if (i == scores.length)
				break;
			else {
				bund = (scores[i + 1] - scores[i] >= 0);
				if (bund && ned && scores[i] < (min * 1.1)) {
					hit = i;
					// System.out.println(i);
					break;
				}
			}
		}

		try {
			double pulsgaet = 60000 / (hit * 5);
			System.out.println("Bedste bud: " + pulsgaet);
			pulsListe.add((int) pulsgaet);
		} catch (ArithmeticException e1) {
			System.out.println("Divideret med 0");
			System.out.println("Bedste bud: Intet bud");
		}

		// Sikrer at vi kun gemmer de forrige 3 målinger.
		while (pulsListe.size() >= 3) {
			pulsListe.remove(0);
		}

		// Finder gennemsnittet af denne måling og de forrige 2.
		double jævnetPuls = 0;
		for (double puls : pulsListe) {
			jævnetPuls += puls;
		}

		jævnetPuls = jævnetPuls / pulsListe.size();

		return (int) jævnetPuls;
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
	}

	/*--------------- GAMMEL KODE -------------*/

	String[] dataArray; // De 1000 målinger som streng
	static Database dtb;
	static int[] saveArray; // De samme 1000 måliner konverteret til double
	double sum = 0.0; // Summen af de 1000 målinger i vores array.
	double doublePulse = 0; // Gennemsnitspulsen som double
	int finalPulse = 0; // Gennemsnitspulsen som int
	ArrayList<Integer> pulsListe = new ArrayList<>(); // bruges til at have de
														// sidste 12 målinger
	static double gennemsnit = 0;
	static int count = 0;
	static boolean overMax = false;

	public static int calcPulse() {
		gennemsnit = 0.0; // Resetter gennemsnittet ved hvert gennemløb
		ArrayList<Integer> input = dtb.getPulsListe();
		saveArray = new int[input.size()];
		for (int i = 0; i < saveArray.length; i++) {
			saveArray[i] = input.get(i);
		}
		int sum = IntStream.of(saveArray).sum(); // Vi får summen fra vores
													// array.
		gennemsnit = sum / saveArray.length * 1.1;
		count = 0;
		for (int i = 0; i < saveArray.length; i++) {
			// Hvis en måling er højere end gennemsnittet + 10%, tælles et slag
			if (saveArray[i] > gennemsnit && !overMax) {
				overMax = true;
				count++;
			}
			if (saveArray[i] < gennemsnit && overMax) {
				// Når en måling kommer under gennemsnittet + 10%, sættes
				// overMax til false
				overMax = false;
			}

		}
		int pulsMinut = count * 12; // Beregning af pulsen i minuttet ud for
									// antal

		return pulsMinut;
	}

	public int calcPulse2() {
		ArrayList<Integer> data = datb.getPulsListe();
		double[] inddata = new double[data.size()];

		// Konverterer til array af simpel type
		for (int i = 0; i < data.size(); i++) {
			inddata[i] = data.get(i);
		}

		// Udregner afvigelsen for 300 forskydelser
		double[] scores = new double[300];
		for (int j = 1; j < 301; j++) {
			double score = 0;
			int b = j;
			int a = 0;
			while (b < (inddata.length)) {
				score += (inddata[a] - inddata[b]) * (inddata[a] - inddata[b]);
				b++;
				a++;
			}
			scores[j - 1] = score;
		}

		// Finder laveste værdi for bølgedale
		boolean ned = false;
		boolean bund = false;
		double min = Double.MAX_VALUE;
		for (int i = 60; i < scores.length - 1; i++) {
			ned = (scores[i] - scores[i - 1] < 0);
			if (i == scores.length)
				break;
			else {
				bund = (scores[i + 1] - scores[i] >= 0);
				if (bund && ned) {
					min = (scores[i] < min) ? scores[i] : min;
				}
			}
		}

		// Finder første forskydning med lav nok score
		int hit = 0;
		for (int i = 60; i < scores.length - 1; i++) {
			ned = (scores[i] - scores[i - 1] < 0);
			if (i == scores.length)
				break;
			else {
				bund = (scores[i + 1] - scores[i] >= 0);
				if (bund && ned && scores[i] < (min * 1.1)) {
					hit = i;
					// System.out.println(i);
					break;
				}
			}
		}

		try {
			double pulsgaet = 60000 / (hit * 5);
			System.out.println("Bedste bud: " + pulsgaet);
			pulsListe.add((int) pulsgaet);
		} catch (ArithmeticException e1) {
			System.out.println("Divideret med 0");
			System.out.println("Bedste bud: Intet bud");
		}

		// Sikrer at vi kun gemmer de forrige 3 m�linger.
		while (pulsListe.size() >= 3) { /* 1G */
			pulsListe.remove(0);
		}

		// Finder gennemsnittet af denne m�ling og de forrige 2.
		double jævnetPuls = 0;
		for (double puls : pulsListe) { /* 1H */
			jævnetPuls += puls;
		}

		jævnetPuls = jævnetPuls / pulsListe.size();

		return (int) jævnetPuls;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	
}
