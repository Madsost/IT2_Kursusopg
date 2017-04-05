import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

public class Graf_Pulse extends JPanel implements Graf_Interface {
	private Timer timer;
	private int speed = 3000;
	private Database datb;
	private int maxX = 600;
	private int minX = -1;
	private int minY = -1;
	private int maxY = 1024;
	private int trinX = 200;
	private int trinY = 400;
	private ArrayList<Integer> dataToDraw;

	public Graf_Pulse(Database datb) {
		//speed = 5;
		this.datb = datb;
		setData(/* datb.getPulsListe() */);
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				setData(/* datb.getPulsListe() */);
				repaint();

			}
		};
		this.timer = new Timer(speed, taskPerformer);
	}

	public void begin() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform af = new AffineTransform();
		g2.setColor(getBackground());

		// hent bredde og højde og læg padding til
		double h = getHeight() - 20;
		double b = getWidth() - 20;
		double deltaX = b / (maxX - minX);
		double deltaY = h / (maxY - minY);
		double origoX = -(double) minX * deltaX + 10;
		double origoY = h + (double) (minY) * deltaY + 10;

		af.setToTranslation(origoX, origoY);
		g2.transform(af);
		
		// Tilret til 'bruger'-koordinatsystem
		af.setToScale(deltaX, -deltaY);
		g2.transform(af);

		drawAxis(g2);
		drawGraph(g2);
	}

	@Override
	public void drawAxis(Graphics2D g) {
		g.setColor(Color.BLACK);
		for (int i = (minX / trinX) * trinX; i <= maxX; i += trinX) {
			g.drawLine(i, 0, i, 10);
		}
		for (int i = (minY / trinY) * trinY; i <= maxY; i += trinY) {
			g.drawLine(0, i, 10, i);
		}
		g.drawLine(minX, 0, maxX, 0);
		g.drawLine(0, minY, 0, maxY);
	}

	@Override
	public void drawGraph(Graphics2D g) {
		g.setColor(Color.red);
		// Hvis data er kortere maxX
		if (dataToDraw.size() <= maxX) {
			int j = 0;
			for (int i = 0; i < dataToDraw.size(); i++) {
				g.fillRect(i, dataToDraw.get(i), 2, 20);
				//j += 20;
			}
		}

		// Ellers - tegn kun maxX målinger
		else {
			int j = 0;
			for (int i = 0; i < maxX; i++) {
				g.drawRoundRect(i, dataToDraw.get(i), 2, 2, 1, 1);
				//j += 20;
			}
		}
	}

	public void setData(/* ArrayList<Integer> listen */) {
		dataToDraw = new ArrayList<Integer>();
		for (int i = 0; i < maxX; i++) {
			dataToDraw.add(datb.getPuls());
		}
	}

	public static void main(String[] args) {
		Graf_Pulse test = new Graf_Pulse(new Database());
		test.begin();
		JFrame ramme = new JFrame();
		ramme.add(test);
		ramme.setVisible(true);
		ramme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramme.setSize(540, 540);
	}

}
