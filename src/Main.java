
import javax.swing.*;
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
	private boolean dangerPulse;
	private boolean dangerTemp;

	public Main() {
		this.dangerPulse = false;
		this.dangerTemp = false;
	}

	public void init() {
		// Lav 2 sensor-objekter i hver deres tråd
		temp = new TempSensor();
		puls = new PulseSensor();
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

		// Mere?
	}

	public void run() {
		/*
		 * Alt det der skal ske i Main. 1) Indhente målinger fra sensorer 2)
		 * Kontrollere data 3) Beregne puls/etwas 4) Gemme i databasen 4)
		 * Opdatere GUI (måske?)
		 */
		// Indhent målinger
		temp.measure(); // De skal returnere noget
		puls.measure(); // Og hvordan kommer det til at virke?

		validate();

	}

	public void validate() {
		// noget med at tjekke om data er OK inden det gemmes i databasen
	}

	public boolean boundsCheck() {
		// en klasse til at tjekke om grænseværdierne er overskredet
		// Der skal indhentes grænseværdier: [tl tu pl pu]
		double[] bounds = gui.getBound();
		double tl = bounds[0]; // temp nedre
		double tu = bounds[1];
		double pl = bounds[2];
		double pu = bounds[3];

		// Her kaldes så den relevante metode i GUI
		String type = "";
		gui.advarsel(type);
		return false;
	}

	public int calculatePulse() {
		// den ene eller den anden implementering af en pulsregneren
		return 0;
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
	public static void main(String[] args) {
		dtb = new Database();
		Main main = new Main();
		GUI gui = new GUI(dtb);
	}
}
