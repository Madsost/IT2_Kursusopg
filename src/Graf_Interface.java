import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Graf_Interface extends JPanel{
    
        Timer timer;
        Database datb;        
	
	// Metode til at tegne akserne
	abstract void drawAxis(Graphics2D g);
	
	/*
	 *  Metode til at tegne selve grafen
	 */
	abstract void drawGraph(Graphics2D g);
	
    
    abstract void begin();
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    abstract void stop();

	//void setData(ArrayList<Object> liste);
}
