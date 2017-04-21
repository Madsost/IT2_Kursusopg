
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

// Forældreklasse til Sensorne
public class Sensor {

	public boolean openPortTemp = false;
	public boolean openPortPuls = false;
	public boolean openPort = false;
	protected boolean running = false;
	protected String type = "";
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
			this.openPort = true;
			return serialPort;
		} catch (SerialPortException e) {
			System.out.println("Sensor ikke tilsluttet.");
			openPortTemp = false;
			return null;
		}
	}

	public String measure() {
		/*
		 * Tom metode. Den skal nok laves forskelligt i de to underklasser
		 */
		return "";
	}

	public String getType() {
		/*
		 * Hvis vi får brug for at kunne aflæse en sensors type.
		 */
		return type;
	}

	public ArrayList<String> getData() {
		ArrayList<String> kopi = outputBuffer;
		outputBuffer = new ArrayList<>();
		return kopi;
	}

	public void stopIt() {
		running = false;
	}

	public void closeAll() {
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
		}
	}

	public static void main(String[] args) throws Throwable {
		Sensor sens = new Sensor();
		TempSensor tempSens = null;
		PulseSensor pulseSens = null;
		ArrayList<String> input = new ArrayList<>();
		Thread tråd;
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
			SerialPort testPort = sens.openPort(portNames[i]);
			boolean isPuls = sens.testType(testPort);
			testPort.closePort();
			if (isPuls) {
				System.out.println("HIP HURRA vi detekterede en pulsmåler");
				pulseSens = new PulseSensor(portNames[i]);
			} else {
				tempSens = new TempSensor(portNames[i]);
			}

			tråd = new Thread(pulseSens);
			tråd.start();
			synchronized (tråd) {
				for (int j = 0; j < 100; j++) {
					System.out.println("Hej?");
					input = pulseSens.getData();
					System.out.println("Vi kaldte en metode");
					try {
						System.out.println(input.toString() + "\nAntal målinger: " + input.size());

						Thread.sleep(3000);
					} catch (ConcurrentModificationException e) {
						System.err.println(
								"Der blev foretaget en ændring i listen samtidig med at der blev udført en handling derpå.");
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
