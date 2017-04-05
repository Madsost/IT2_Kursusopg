import java.awt.*;
import java.util.ArrayList;

public interface Graf_Interface{
	
	// Metode til at tegne akserne
	void drawAxis(Graphics2D g);
	
	/*
	 *  Metode til at tegne selve grafen
	 */
	void drawGraph(Graphics2D g);
	
	//void setData(ArrayList<Object> liste);
}
