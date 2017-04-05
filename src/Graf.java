
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madso
 */
public class Graf extends JPanel {
	
	/*
	 * Her skal vi tage stilling til nogle ting..
	 * 
	 * Vi kan godt fortsat tegne med Graphics2D, men måske
	 * skal det ikke være med affin transformation... 
	 * 
	 * Det kunne være rart med nogle pænere akser :-) 
	 * Hvis vi fortsætter med Graphics(2D) skal vi se på 
	 * at tegne det lidt mere lavpraktisk
	 * 
	 * Desuden vil vi nok gerne bruge et interface til at 
	 * samle graftegnerne i to separate klasser. 
	 * 
	 * 
	 * 
	 * 
	 * Hvis vi gerne vil prøve med JFreeChart så skal det her laves
	 * helt om. Der er en hjemmeside med en guide til det her;
	 * 
	 * http://www.tutorialspoint.com/jfreechart/index.htm
	 * 
	 * Og så skal vi se lidt på hvordan vi sikrer os at data 
	 * er de samme igennem hele brugergrænsefladen
	 * 
	 */

	Database dtb;
	String puls = "puls";
	String temp = "temp";
	Boolean brugTemp = false;
	double xMax, yMax;
	ArrayList<Integer> inddataPuls = new ArrayList<>();
	ArrayList<Double> inddataTemp = new ArrayList<>();
	Timer timer;

	public Graf(String typo, Database dtb) {
		this.dtb = dtb;
		String buff = typo.trim().toLowerCase();
		if (buff.equals(puls)) {
			brugTemp = false;
		} else if (buff.equals(temp)) {
			brugTemp = true;
		}
		int updateRate;
		if (brugTemp) {
			updateRate = 1000;
		} else {
			updateRate = 5;
		}
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				repaint();
			}

		}, 0, updateRate);
	}

	public int getPuls() {

		return 0;
	}

	public double getTemp() {
		double temp = inddataTemp.get(inddataTemp.size() - 1);

		return temp;
	}

	public void tegnPunkt(Graphics2D g2) {
		double punkt = 0;
		if (brugTemp) {
			punkt = Database.genererMaaling();
			// System.out.println(punkt);
			inddataTemp.add(punkt);

			if (inddataTemp.size() >= 2) {
				for (int j = 1; j < inddataTemp.size() - 1; j++) {
					g2.drawLine(j - 1, (inddataTemp.get(j - 1).intValue()), j, (int) (inddataTemp.get(j).intValue()));
					// g2.drawOval(j, (inddataTemp.get(j).intValue()), 1,15);
				}
			}
		} else {
			int punkt2 = dtb.getPuls();
			inddataPuls.add(punkt2);
			if (inddataPuls.size() >= 2) {
				g2.setColor(Color.red);
				for (int i = 0; i < inddataPuls.size(); i++) {
					g2.fillRoundRect(i, inddataPuls.get(i), 10, 10, 2, 5);
				}
			}
		}
		if (inddataPuls.size() > xMax) {
			inddataPuls = new ArrayList<>();
			// inddataPuls.remove(0);
		}
		if (inddataTemp.size() > xMax) {
			// inddataTemp = new ArrayList<>();
			inddataTemp.remove(0);
		} else {

		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (GUI.isActive) {
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform af = new AffineTransform();

			double h = getHeight() - 2;
			double b = getWidth() - 2;

			int step = 0;
			if (brugTemp) {
				xMax = 60;
				yMax = 255;
				step = 20;

			} else {
				xMax = 600;
				yMax = 1050;
				step = 200;
			}

			double deltaX = b / xMax;
			double deltaY = h / yMax;
			double origoX = 2;
			double origoY = h;
			af.setToTranslation(origoX, origoY);
			g2.transform(af);
			af.setToScale(deltaX, -deltaY);
			g2.transform(af);

			g2.drawLine(0, 0, (int) xMax, 0);
			g2.drawLine(0, 0, 0, (int) yMax);

			for (int i = 0; i <= xMax; i += step) {
				g2.drawLine(i, -5, i, 10);
			}
			for (int i = step; i <= yMax; i += step) {
				g.drawLine(-5, i, 5, i);
			}
			tegnPunkt(g2);
			
		} 	
			

	}

}
