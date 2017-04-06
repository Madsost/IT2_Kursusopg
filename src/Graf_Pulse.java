
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Graf_Pulse extends Graf_Master {

    private Timer timer;
    private int speed = 3000;
    private Database datb;
    private int maxX = 600;
    private int minX = -10;
    private int minY = -10;
    private int maxY = 1024;
    private int trinX = 200;
    private int trinY = 400;
    private double deltaX, deltaY;
    private ArrayList<Integer> dataToDraw;

    public Graf_Pulse() {
        dataToDraw = new ArrayList<Integer>();
        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                /*
				 * dataToDraw = datb.getValues("puls");
                 */
                setVisible(true);
                setData(/* datb.getPulsListe() */);
                repaint();

            }
        };
        timer = new Timer(speed, taskPerformer);
        timer.setInitialDelay(0);
        //setVisible(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform af = new AffineTransform();
        g2.setColor(getBackground());

        // hent bredde og højde og læg padding til
        double h = getHeight() - 20;
        double b = getWidth() - 20;
        deltaX = b / (maxX - minX);
        deltaY = h / (maxY - minY);
        double origoX = -(double) minX * deltaX + 10;
        double origoY = h + (double) (minY) * deltaY + 10;

        af.setToTranslation(origoX, origoY);
        g2.transform(af);

        // Tilret til 'bruger'-koordinatsystem
        af.setToScale(1, -1);
        g2.transform(af);

        drawAxis(g2);
        drawGraph(g2);
    }

    public void drawAxis(Graphics2D g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= maxX; i += trinX) {
            g.draw(new Line2D.Double((i * deltaX), 0, (i * deltaX), (10 * deltaY)));
        }
        for (int i = 0; i <= maxY; i += trinY) {
            g.draw(new Line2D.Double(0, (i * deltaY), (10 * deltaX), (i * deltaY)));
        }
        g.draw(new Line2D.Double((minX * deltaX), 0, (maxX * deltaX), 0));
        g.draw(new Line2D.Double(0, (minY * deltaY), 0, (maxY * deltaY)));
    }

    public void drawGraph(Graphics2D g) {
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(3));

        // Hvis data er kortere maxX
        if (dataToDraw.size() <= maxX) {
            for (int i = 1; i < dataToDraw.size() - 1; i++) {
                g.draw(new Line2D.Double(((i - 1) * deltaX), (dataToDraw.get(i - 1) * deltaY), (i * deltaX),
                        (dataToDraw.get(i) * deltaY)));
                // g.fillRoundRect((int)(i*deltaX),
                // (int)(dataToDraw.get(i)*deltaY), 10, 10,10,10);
            }
        } // Ellers - tegn kun maxX målinger
        else {
            for (int i = 0; i < maxX; i++) {
                g.draw(new Line2D.Double(((i - 1) * deltaX), (dataToDraw.get(i - 1) * deltaY), (i * deltaX),
                        (dataToDraw.get(i) * deltaY)));
            }
        }
    }
    public void setDTB(Database dtb) {
        datb = dtb;
    }
    
    @Override
    public void begin() {
        timer.start();
        //this.setVisible(true);
    }

    @Override
    public void stop() {
        timer.stop();
        //this.setVisible(false);
    }

    public void setData(/* ArrayList<Integer> listen */) {
        dataToDraw = new ArrayList<Integer>();
        for (int i = 0; i < maxX; i++) {
            dataToDraw.add(datb.getPuls());
        }
    }
}
