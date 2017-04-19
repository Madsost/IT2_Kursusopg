
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

// Forældreklasse til Sensorne
public class Sensor {

	public boolean openPortTemp = false;
	public boolean openPortPuls = false;
	public boolean openPort = false;
	protected boolean running=false;
	public String type = "";
	SerialPort serialPort;

	public Sensor() {

		/*
		 * Tjek om der er noget tilsluttet
		 * 
		 * Hvilken port er der tale om? Hvilken type er der tale om?
		 * 
		 * Initialiser forbindelsen
		 * 
		 * Begynd at måle i en daemon tråd Måske med SerialPortEvent-listener
		 * 
		 */
	}

	/**
	 * Test forbindelsen. Retunerer sand hvis forbindelsen er puls-måler, falsk
	 * hvis det er temperatur.
	 */
	public boolean testType(SerialPort port) {
		boolean tester = false;
		String input = "";
		try {
			// test forbindelsen fx 3 gange med 1 sekund delay
			for (int i = 0; i < 15; i++) {
				System.out.print(".");
				if (port.getInputBufferBytesCount() > 0) {
					input += port.readString();
				} else
					//System.out.println("Vi modtog ikke noget");
				Thread.sleep(1000);
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
	 * @param portname
	 * @return den åbnede SerialPort
	 */
	public SerialPort openPort(String portname) {
		SerialPort serialPort;
		try {
			serialPort = new SerialPort(portname);
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0); // sætter parametre
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			serialPort.setDTR(true);
			System.out.println("Porten blev oprettet");
			this.openPort = true;
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
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
		String type = "";
		return type;
	}
	
	public void stopIt(){
		running = false;
	}
	
	public void closeAll(){
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
			
		}
	}
	
	public static void main(String[] args) throws SerialPortException {
		Sensor sens = new Sensor();
		String[] portNames = SerialPortList.getPortNames();
		for (int i = 0; i < portNames.length; i++) {
			SerialPort testPort = sens.openPort(portNames[i]);
			
			boolean isPuls = sens.testType(testPort);
			testPort.closePort();
			if (isPuls) {
				System.out.println("HIP HURRA vi detekterede en pulsmåler");
				PulseSensor pulseSens = new PulseSensor(portNames[i]);
			} else {
				TempSensor tempSens = new TempSensor(portNames[i]);
				tempSens.run();
			}

		}

	}

}
