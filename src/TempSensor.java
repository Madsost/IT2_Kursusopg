
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;

public class TempSensor extends Sensor implements Runnable {
	private SerialPort port;
	private String inputBuffer;
	private ArrayList<String> outputBuffer = new ArrayList<>();

	/*
	 * Temp-specifikke attributter
	 */

	public TempSensor(String portname) {
		this.port = super.openPort(portname);
		this.running = true;
		this.type = "Temperatur";
	}

	@Override
	public String measure() {
		try {
			if (port.getInputBufferBytesCount() > 0) {
				inputBuffer = port.readString();
			}
		} catch (SerialPortException ex) {
		}
		return inputBuffer;
	}

	@Override
	public void run() {
		while (running) {
			try {
				if (measure().isEmpty())
					continue;
				else{
					String test = measure();
					outputBuffer.add(test);
					System.out.println(test);
				}
					
					
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<String> getData(){
		ArrayList<String> kopi = outputBuffer;
		outputBuffer = new ArrayList<>();
		return kopi;
	}
}
