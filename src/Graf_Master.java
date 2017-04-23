import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Graf_Master extends JPanel {

	protected Timer timer;
	protected Database datb;
	protected ArrayList<Double> dataToDraw;
	protected int maxX, minX, minY, maxY, speed, trinY;
	protected double trinX, deltaX, deltaY;

	// Metode til at tegne akserne
	abstract void drawAxis(Graphics2D g);

	/*
	 * Metode til at tegne selve grafen
	 */
	public void drawGraph(Graphics2D g){
		g.setColor(Color.red);
		g.setStroke(new BasicStroke(3));
		
		int index = dataToDraw.size() - 1;
		if (index >= maxX-1) {
			int j = maxX-1;
			for (int i = 0; i <= maxX-1; i++) {
				g.draw(new Rectangle2D.Double((j * deltaX), (dataToDraw.get(i) * deltaY), 5, 5));
				j--;
			}
		}
		if (index < maxX-1) {
			int j  = 0;
			for (int i = index; i >= 0; i--) {
				g.draw(new Rectangle2D.Double((i * deltaX), (dataToDraw.get(j) * deltaY), 5, 5));
				j++;
			}
		}
	}

	public void begin(){
		timer.start();
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void stop(){
		timer.stop();
	}

	// void setData(ArrayList<Object> liste);
}
