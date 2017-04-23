
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Graf_Pulse extends Graf_Master {

	public Graf_Pulse() {
		speed = 1000;
		maxX = 24;
		minX = 0;
		minY = 0;
		maxY = 220;
		trinX = 1;
		trinY = 10;

		dataToDraw = new ArrayList<>();
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				dataToDraw = datb.getValueSet("Pulsslag");
				repaint();
			}
		};
		timer = new Timer(speed, taskPerformer);
		timer.setInitialDelay(0);
		// setVisible(false);
	}

	@Override
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
			g.draw(new Line2D.Double((i * deltaX), -(1*deltaY), (i * deltaX), (1 * deltaY)));
		}
		for (int i = 0; i <= maxY; i += trinY) {
			g.draw(new Line2D.Double(-(0.1*deltaX), (i * deltaY), (0.1 * deltaX), (i * deltaY)));
		}
		g.draw(new Line2D.Double((minX * deltaX), 0, (maxX * deltaX), 0));
		g.draw(new Line2D.Double(0, (minY * deltaY), 0, (maxY * deltaY)));
	}

	public void setDTB(Database dtb) {
		this.datb = dtb;
	}
}
