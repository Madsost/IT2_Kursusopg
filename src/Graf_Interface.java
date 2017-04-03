import java.awt.*;

public interface Graf_Interface{
	
	// Metode til at tegne akserne
	void drawAxis(Graphics g);
	
	/*
	 *  Metode til at tegne selve grafen
	 */
	void drawGraph(Graphics g);
	
	// 
	void setData(/* input parametre */);
}
