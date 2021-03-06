
import java.util.ArrayList;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class TempSensor extends Sensor implements Runnable {
	private SerialPort port;
	private String buffer = "";

	public TempSensor(String portname) {
		port = new SerialPort(portname);
		try {
			port.openPort();
			port.setParams(SerialPort.BAUDRATE_9600, 8, 1, SerialPort.PARITY_NONE);
			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			port.setDTR(true);
			this.running = true;

		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public TempSensor(boolean test){
		
	}

	@Override
	public void run() {
		try {
			port.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent event) {
					if (event.isRXCHAR() && event.getEventValue() > 0) {
						try {
							buffer += port.readString(event.getEventValue());
							int pos = -1;
							buffer = buffer.replaceAll("\\R", "");
							// System.out.println("målt: " +buffer);
							while ((pos = buffer.indexOf("C")) > -1) {
								// Udfordring: sensoren anvender et linjeskift
								// til at markere enden på en måling. "\\R" = alle
								// linjeskift (regex)
								outputBuffer.add(buffer.substring(0, pos + 1));
								System.out.println("Gemt: " + buffer);
								System.out.println("Status: " + outputBuffer.toString());

								// klip den seneste måling fra.
								buffer = buffer.substring(pos + 1);
							}

						} catch (SerialPortException e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}

	}
}
