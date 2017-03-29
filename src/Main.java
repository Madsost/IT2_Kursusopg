
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

	// HEJ aline

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
				count ++;
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
