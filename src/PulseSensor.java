
public class PulseSensor extends Sensor implements Runnable{
	/*
	 * Puls-specifikke attributter
	 * */
	private String type = "pulse";
	
	public PulseSensor(){
		super.init(/* Måske sensortype? */); // måske
		
		// Noget med opsætning af tråden (eller sker det i Main?)
	}
	
	@Override
	public void init(){
		/*
		 * Skal vi bruge denne her metode? 
		 */
	}
	
	@Override
	public int measure(){
		/*
		 * Puls-specifik metode for at hente måling. 
		 */
		return 0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
