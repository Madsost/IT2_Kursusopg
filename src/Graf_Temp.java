
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.Timer;

public class Graf_Temp extends Graf_Master {

	public Graf_Temp() {
		speed = 1000;
		maxX = 12;
		minX = 0;
		minY = 0;
		maxY = 50;
		trinX = 1;
		trinY = 10;

		dataToDraw = new ArrayList<Double>();
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				dataToDraw = datb.getValueSet("Temperatur");
				// System.out.println("Efter database: "+dataToDraw.toString());
				repaint();
			}
		};
		timer = new Timer(speed, taskPerformer);
		timer.setInitialDelay(0);
	}

	public void setDTB(Database dtb) {
		this.datb = dtb;
	}

	@Override
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
		double origoY = h + (minY) * deltaY + 10;

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
			String xAxis = "" + (i * 10) + " s";
			AffineTransform af = new AffineTransform();
			af.setToScale(1, -1);
			g.transform(af);
			g.setColor(Color.BLUE);
			g.drawString(xAxis, (int) (i * deltaX), 0);
			g.setColor(Color.BLACK);
			g.rotate(0);
			af.setToScale(1, -1);
			g.transform(af);
		}
		for (int i = 0; i <= maxY; i += trinY) {
			g.draw(new Line2D.Double(-(0.1 * deltaX), (i * deltaY), (0.1 * deltaX), (i * deltaY)));
			// Her kan vi skrive størrelser på...
		}
		// Tegn selve akserne
		g.draw(new Line2D.Double((minX * deltaX), 0, (maxX * deltaX), 0));
		g.draw(new Line2D.Double(0, (minY * deltaY), 0, (maxY * deltaY)));
	}
}
