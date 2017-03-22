
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

    static double value = 0;
    ArrayList<Double> temperaturListe;
    ArrayList<Integer> pulsListe;
    int tempKald = 0;
    int pulsKald = 0;

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
            maaling = (maaling * 4 / 50) + 24;
            tempKald++;
            return maaling;
        } else {
            tempKald = 0;
            double maaling = temperaturListe.get(tempKald);
            maaling = (maaling * 4 / 50) + 24;
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

    public static void main(String[] args) {
        Database test = new Database();
        for (int i = 0; i < 20; i++) {
            System.out.println("Puls:" + test.getPuls());
            System.out.println("Temp:" + test.getTemp());

        }
    }

    public static double genererMaaling() {
        value += (2 * Math.random() - 1.0) * 0.25;
        if (value < 0) {
            value = 0;
        }
        if (value > 255) {
            value = 255;
        }

        return (double) value;

    }
}
