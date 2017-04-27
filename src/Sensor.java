
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

// Forældreklasse til Sensorne
public class Sensor {

	protected boolean running = false;
	protected SerialPort serialPort;
	protected String inputBuffer = "";
	protected ArrayList<String> outputBuffer = new ArrayList<>();

	public Sensor() {
	}

	/**
	 * Test forbindelsen. Retunerer sand hvis forbindelsen er puls-måler, falsk
	 * hvis det er temperatur.
	 */
	public boolean testType(SerialPort port) {
		System.out.println("Undersøger sensor-type...");
		boolean tester = false;
		String input = "";
		try {
			// test forbindelsen fx 3 gange med 1 sekund delay
			for (int i = 0; i < 15; i++) {
				System.out.print(".");
				if (port.getInputBufferBytesCount() > 0) {
					input += port.readString();
					//System.out.println(input);
					System.out.println("\nSensor-type bestemt.");
					break;
				} else
					Thread.sleep(1000);
				// System.out.println("Vi modtog ikke noget");
			}
			// test tegn i målingen - led efter et '!'
			CharSequence s = "!";
			tester = input.contains(s);

		} catch (SerialPortException e) {
			System.out.println("Porten var ikke initialiseret");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("vi ventede ikke");
		}
		return tester;
	}

	/**
	 * Opret forbindelse til porten.
	 * 
	 * @param portname
	 * @return den åbnede SerialPort
	 */
	public SerialPort openPort(String portname) {
		// SerialPort serialPort;
		try {
			serialPort = new SerialPort(portname);
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_19200, 8, 1, 0); // sætter
																		// parametre
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			serialPort.setDTR(true);
			System.out.println("Porten blev oprettet");
			return serialPort;
		} catch (SerialPortException e) {
			System.out.println("Sensor ikke tilsluttet.");
			return null;
		}
	}

	public synchronized ArrayList<String> getData() {
		ArrayList<String> kopi = outputBuffer;
		outputBuffer = new ArrayList<>();
		return kopi;
	}
}
