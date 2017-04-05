
public class TempSensor extends Sensor implements Runnable{
	/*
	 * Temp-specifikke attributter
	 */
	
	public TempSensor(){
		super.init(); // MÅSKE
		
		// Noget andet der skal ske når vi initialiserer sensoren...
	}
	
	@Override 
	public int measure(){
		/*
		 * Her skal der stå noget mere.
		 */
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
