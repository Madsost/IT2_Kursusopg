
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

    Database dtb;
    String puls = "puls";
    String temp = "temp";
    Boolean brugTemp = false;
    double xMax, yMax;
    ArrayList<Integer> inddataPuls = new ArrayList<>();
    ArrayList<Double> inddataTemp = new ArrayList<>();

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
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                repaint();
            }

        }, 0, updateRate);
    }

    public void tegnPunkt(Graphics g) {
        double punkt = 0;
        if (brugTemp) {
            punkt = (int) dtb.getTemp() * 10;
            inddataTemp.add(punkt);
            if (inddataTemp.size() >= 2) {
                for (int j = 1; j < inddataTemp.size() - 1; j++) {
                    //g.drawLine(j - 1, (int) (inddataTemp.get(j - 1)*10), j, (int) (inddataTemp.get(j)*10));
                    g.fillOval(j, (int) (inddataTemp.get(j) * 10), 10, 10);
                }
            }
        } else {
            int punkt2 = dtb.getPuls();
            inddataPuls.add(punkt2);
            if (inddataPuls.size() >= 2) {
                for (int j = 1; j < inddataPuls.size() - 1; j++) {

                    g.drawLine(j - 1, (int) (inddataPuls.get(j - 1)), j, (int) (inddataPuls.get(j)));
                }
            }
        }
        if (inddataPuls.size() > xMax) {
            inddataPuls = new ArrayList<>();
        }
        if (inddataTemp.size() > xMax) {
            inddataTemp = new ArrayList<>();
        } else {

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform af = new AffineTransform();

        double h = getHeight() - 2;
        double b = getWidth() - 2;

        int step = 0;
        if (brugTemp) {
            xMax = 60;
            yMax = 5000;
            step = 1;

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

        for (int i = step; i <= xMax; i += step) {
            g2.drawLine(i, -5, i, 10);
        }
        /*for (int i = step; i <= yMax; i += step) {
            g.drawLine(-5, i, 5, i);
        }*/
        tegnPunkt(g);

    }

}
