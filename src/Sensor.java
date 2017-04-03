// Forældreklasse til Sensorne
public class Sensor {
	/*
	 * Nogle attributter - de der SerialPort osv.
	 */

	public void init(/* Skal der være en parameter her? */) {
		/*
		 * Her skal der være den fælles kode til initialisering af sensoren
		 */
	}

	public int measure() {
		/*
		 * Tom metode. Den skal nok laves forskelligt i de to underklasser
		 */
		return 0;
	}

	public String getType() {
		/*
		 * Hvis vi får brug for at kunne aflæse en sensors type. 
		 * */
		String type = "";
		return type;
	}
}
