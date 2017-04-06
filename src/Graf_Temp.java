
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Graf_Temp extends Graf_Interface {

    private Timer timer;
    private int speed = 1000;
    private Database datb;
    private int maxX = 12;
    private int minX = 0;
    private int minY = 0;
    private int maxY = 50;
    private double trinX = 1;
    private int trinY = 10;
    private double deltaX, deltaY;
    private ArrayList<Double> dataToDraw;

    public Graf_Temp() {
        dataToDraw = new ArrayList<Double>();
        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                /*
				 * dataToDraw = datb.getValues("puls");
                 */
                //setVisible(true); - sker i begin().
                setData(/* datb.getPulsListe() */);
                repaint();
            }
        };
        timer = new Timer(speed, taskPerformer);
        timer.setInitialDelay(0);
        //setVisible(false);
    }


    public void begin() {
        timer.start();
        //this.setVisible(true);
    }


    public void stop() {
        timer.stop();
        //this.setVisible(false);
    }

    
    public void setDTB(Database dtb) {
        this.datb = dtb;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform af = new AffineTransform();
        g2.setColor(getBackground());

        // hent bredde og højde og læg padding til
        double h = getHeight() - 20;
        double b = getWidth() - 30;
        deltaX = b / (maxX - minX);
        deltaY = h / (maxY - minY);
        double origoX = -(double) minX * deltaX + 15;
        double origoY = h + (double) (minY) * deltaY + 10;

        af.setToTranslation(origoX, origoY);
        g2.transform(af);

        // Tilret til 'bruger'-koordinatsystem
        af.setToScale(1, -1);
        g2.transform(af);

        drawAxis(g2);
        drawGraph(g2);
    }

    @Override
    public void drawAxis(Graphics2D g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= maxX; i += trinX) {
            g.draw(new Line2D.Double((i * deltaX), -(1 * deltaY), (i * deltaX), (1 * deltaY)));
            // Her kan vi skrive størrelser på
        }
        for (int i = 0; i <= maxY; i += trinY) {
            g.draw(new Line2D.Double(-(0.1 * deltaX), (i * deltaY), (0.1 * deltaX), (i * deltaY)));
            // Her kan vi skrive størrelser på...
        }
        // Tegn selve akserne 
        g.draw(new Line2D.Double((minX * deltaX), 0, (maxX * deltaX), 0));
        g.draw(new Line2D.Double(0, (minY * deltaY), 0, (maxY * deltaY)));
    }

    @Override
    public void drawGraph(Graphics2D g) {
        g.setColor(Color.red);
        //g.setStroke(new BasicStroke(3));

        // Hvis data er kortere maxX
        if (dataToDraw.size() <= maxX) {
            for (int i = 0; i < dataToDraw.size() - 1; i++) {
                g.draw(new Rectangle2D.Double((i * deltaX),
                        (dataToDraw.get(i) * deltaY), 5, 5));
            }
        } // Ellers - tegn kun maxX målinger
        else {
            dataToDraw = new ArrayList<>();
        }
    }

    public void setData(/* ArrayList<Integer> listen */) {
        dataToDraw.add(Database.genererMaaling());
    }
}
