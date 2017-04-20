
import java.util.ArrayList;
import java.util.function.Supplier;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class TempSensor extends Sensor implements Runnable {
	private SerialPort port;
	// private String inputBuffer;
	private ArrayList<String> outputBuffer = new ArrayList<>();
	private StringBuilder buff = new StringBuilder();

	/*
	 * Temp-specifikke attributter
	 */

	public TempSensor(String portname) {
		// SerialPort tempPort = super.openPort(portname);
		port = new SerialPort(portname);
		try {
			port.openPort();
			port.setParams(SerialPort.BAUDRATE_9600, 8, 1, SerialPort.PARITY_NONE);
			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			port.setDTR(true);
			/*
			 * port.addEventListener(new SerialPortEventListener() {
			 * 
			 * @Override public void serialEvent(SerialPortEvent event) { if
			 * (event.getEventValue() > 0) { try {
			 * buff.append(port.readString()); System.out.println(buff);
			 * outputBuffer.add(inputBuffer); //notifyAll(); } catch
			 * (SerialPortException e) { e.printStackTrace(); } } } });
			 */
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
		this.running = true;
		this.type = "Temperatur";

	}

	@Override
	public String measure() {
		boolean wait = true;
		while (wait) {
			try {
				if (port.getInputBufferBytesCount() > 0) {
					inputBuffer = port.readString();
					wait = false;
				} else
					Thread.sleep(1000);
			} catch (SerialPortException ex) {
			} catch (InterruptedException e) {
			}
		}
		return inputBuffer;
	}

	@Override
	public void run() {
		while (running) {
			try {
				//System.out.println(inputBuffer);
				outputBuffer.add(measure());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} /*
		 * try { if (measure().isEmpty()) continue; else { String test =
		 * measure();
		 * 
		 * System.out.println(test); }
		 * 
		 * Thread.sleep(5000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } }
		 */
}
