
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author super
 */
public class Database {
	/*
	 * Denne klasse skal oprette forbindelse til databasen
	 * - den skal kunne skrive målinger dertil
	 * - den skal kunne returnere målinger
	 * 
	 * Attributter her:
	 */
	private String standardSelect, standardInsert;
	private String url, password, user; // osv. 

	public Database(String hej){
		// Konstruktør 
		
		// Kald noget init af en art 
		// Skal der ske andet?
	}
	
	public void initConn(){
		// Her skal vi initialisere forbindelsen til serveren
		
		// Undersøg om databasen eksisterer
		// Hvis den ikke gør; opret.
		// Vil vi have en database for hver session?!
		
		try{ /* Noget */ } catch(Exception e){ /* Fejlhåndtering */ }
	}
	
	public void writeTo(String parameter1, String parameter2){
		// Skriv til databasen - måske er parameter1 typen og parameter2 er selve målingen
		
		try{ /* Noget */ } catch(Exception e){ /* Fejlhåndtering */ }
	}
	
	public void getValue(/* nogle parametre */){
		// Hent målinger fra databasen med nogle bestemte 
		
		try{ /* Noget */ } catch(Exception e){ /* Fejlhåndtering */ }
	}
	
	
	
	
	
	// --------- GAMMEL KODE ------------- //
	
	static double value = 168;
	private ArrayList<Double> temperaturListe;
	private ArrayList<Integer> pulsListe;
	private int tempKald = 0;
	private int pulsKald = 0;

	public Database() {
		File temperatur = new File("Temperatur.txt");
		File puls = new File("puls.txt");
		try {
			Scanner input = new Scanner(new FileReader(temperatur));
			Scanner input2 = new Scanner(new FileReader(puls));
			temperaturListe = new ArrayList<>();
			pulsListe = new ArrayList<>();
			while (input.hasNext()) {
				temperaturListe.add(Double.parseDouble(input.nextLine()));

			}
			while (input2.hasNext()) {
				pulsListe.add(Integer.parseInt(input2.nextLine()));

			}
		} catch (IOException e1) {
			e1.printStackTrace();

		}
	}

	public double getTemp() {
		if (tempKald < temperaturListe.size()) {
			double maaling = temperaturListe.get(tempKald);
			//maaling = (maaling * 4 / 50) + 24;
			tempKald++;
			return maaling;
		} else {
			tempKald = 0;
			double maaling = temperaturListe.get(tempKald);
			//maaling = (maaling * 4 / 50) + 24;
			tempKald++;
			return maaling;
		}
	}

	public int getPuls() {
		if (pulsKald < pulsListe.size()) {
			int maaling = pulsListe.get(pulsKald);
			pulsKald++;
			return maaling;
		} else {
			pulsKald = 0;
			int maaling = pulsListe.get(pulsKald);
			pulsKald++;
			return maaling;
		}
	}

	/*public static void main(String[] args) {
		Database test = new Database();
		for (int i = 0; i < 20; i++) {
			System.out.println("Puls:" + test.getPuls());
			System.out.println("Temp:" + test.getTemp());

		}
	}*/
	
	public ArrayList<Integer> getPulsListe(){
		return pulsListe;
	}

	public static double genererMaaling() {
		value += (2 * Math.random() - 1.0) * 0.25;
		if (value < 0) {
			value = 0;
		}
		if (value > 255) {
			value = 255;
		}

		return value;
	}
	
	
}
