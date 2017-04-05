
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
	
	public Main(){
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
		
		
	}

	public void validate() {
		/*
		 * Noget med en boolean for grænseværdierne. De skal så nok hentes heri -
		 * eller i run()...
		 */
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
