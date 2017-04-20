import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;

public class PulseSensor extends Sensor implements Runnable {

	public String dataPuls;
	private SerialPort port;
	private String inputBuffer;
	private ArrayList<String> outputBuffer = new ArrayList<>();

	public PulseSensor(String portname) {
		this.port = super.openPort(portname);
		this.running = true;
		this.type = "Puls";
	}

	@Override
	public String measure() {
		int point = 0;
		do {
			try {
				// hvis der er målinger på vej
				if (port.getInputBufferBytesCount() > 0) {
					inputBuffer += port.readString();
					int pos = -1;
					while ((pos = inputBuffer.indexOf("!")) > -1) {
						outputBuffer.add(inputBuffer.substring(0, pos));
						inputBuffer = inputBuffer.substring(pos + 1);
					}
				}
				// Venter på at input-bufferen bliver fyldt igen
				else
					try {
						Thread.sleep(75); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			} catch (SerialPortException e1) {
				e1.printStackTrace();
			}
			// Udskriver et punktum for hvert 200 måling (ca. hvert sekund)
			if (outputBuffer.size() - point >= 200) {
				System.out.print(".");
				point = outputBuffer.size();
			}

			// størrelsen kan godt være større end sampleSize!
		} while (outputBuffer.size() <= 1000);
		return "";
	}

	@Override
	public void run() {
		while(running){
			measure();
			try{Thread.sleep(250);}catch(InterruptedException e){}
		}		
	}
	
	
}
