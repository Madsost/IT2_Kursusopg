import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PulseSensor extends Sensor implements Runnable {

	private SerialPort port;

	public PulseSensor(String portname) {
		this.port = super.openPort(portname);
		this.running = true;
	}
	
	public PulseSensor(boolean test){
		
	}

	@Override
	public void run() {
		try {
			port.addEventListener(new SerialPortEventListener() {

				@Override
				public void serialEvent(SerialPortEvent event) {
					try {
						if (event.isRXCHAR() && event.getEventValue() > 0) {

							inputBuffer += port.readString(event.getEventValue());
							int pos = -1;
							while ((pos = inputBuffer.indexOf("!")) > -1) {
								outputBuffer.add(inputBuffer.substring(0, pos));
								inputBuffer = inputBuffer.substring(pos + 1);
							}

						}
					} catch (SerialPortException e) {
						e.printStackTrace();
					}

				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

}
