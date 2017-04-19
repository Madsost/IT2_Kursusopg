import java.sql.*;
import java.util.*;
public class Database {

	/*
	 * Denne klasse skal oprette forbindelse til databasen - den skal kunne
	 * skrive målinger dertil - den skal kunne returnere målinger
	 * 
	 * Attributter her:
	 */
	private String url, password, user; // osv.
	private Connection conn;
	private Statement stmt;
	private PreparedStatement stmt2;

	public Database() {
		initConn();
	}

	public void initConn() {
		// Her skal vi initialisere forbindelsen til serveren

		// Undersøg om databasen eksisterer
		// Hvis den ikke gør; opret.
		// Vil vi have en database for hver session?!
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// tilknyt driver

			url = "jdbc:mysql://server3.eduhost.dk/suit218";
			// URL: "JDBC:DBMS://maskinnavn/databasenavn"
			user = "suit218";
			password = "dtu165263F17"; // MySQL kodeord

			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			// opret forbindelsesobjekt
			System.out.println("Der er oprettet forbindelse til databasen");
			initTable("Pulsslag");
			initTable("Temperatur");

		} catch (Exception e) {
			System.out.println("Database-test: " + e.getMessage());
			// udskriv fejlmeddelelse
			e.printStackTrace(); // udskriv stacktrace

		}
		// afslut program paent
	}

	public void initTable(String name) {

		try {
			DatabaseMetaData dbm = conn.getMetaData();
			// check om "temp" tabellen allerede eksisterer
			ResultSet tables = dbm.getTables(null, null, name, null);
			if (tables.next()) {
				// Tabellen eksisterer
				dropTable(name);
				// System.out.println("Tabellen eksisterer allerede");
				// writeTo();
			}
			// Tabellen eksisterer ikke
			System.out.println("Tabellen eksisterer IKKE");
			stmt = conn.createStatement();
			String message1 = "CREATE TABLE " + name;
			stmt.executeUpdate(
					message1 + "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + name + " DOUBLE NOT NULL DEFAULT 0)");
			// writeTo();
			conn.commit();
		} catch (Exception e) {
			System.out.println("Tabel-test: " + e.getMessage());
			// udskriv fejlmeddelelse
			e.printStackTrace();
		}
	}

	public void dropTable(String tablename) {
		try {
			stmt.executeUpdate("DROP TABLE " + tablename);
			conn.commit();
		} catch (Exception e) {
		}
	}

	public void writeTo(String tablename, double value) {
		// Skriv til databasen - måske er parameter1 typen og parameter2 er
		// selve målingen
		try {
			stmt2 = conn.prepareStatement("INSERT INTO " + tablename + " VALUES (?,?)");
			// denne auto-incrementerer, altså skal vi ikke sætte den
			stmt2.setInt(1, 0);
			// her sætter vi value
			stmt2.setDouble(2, value);
			stmt2.executeUpdate();
			conn.commit();
			// System.out.println("test af write to");
			// getValue();
		} catch (SQLException e) {
			System.err.println("Der skete en fejl i writeTo");
			e.printStackTrace();
		}
	}

	public ArrayList<Double> getValueSet(String tablename) {
		// Hent målinger fra databasen med nogle bestemte¨
		ArrayList<Double> outputData = new ArrayList<>();
		try {
			System.out.println("Test af select...");
			// udfoer forespoergsel
			ResultSet rset = stmt.executeQuery("SELECT " + tablename + " FROM " + user + "." + tablename);
			conn.commit();
			while (rset.next()) {
				outputData.add(rset.getDouble(1));
				// System.out.println("test af løkke");
			}
		} catch (Exception e) {
		}
		return outputData;
	}

	public static void main(String[] args) {

		Database test = new Database();

		for (int i = 0; i < 20; i++) {
			test.writeTo("Temperatur", genererMaaling());
		}

		ArrayList<Double> output = new ArrayList<>();
		output = test.getValueSet("Temperatur");
		for (double ting : output) {
			System.out.println(ting);
		}

		test.dropTable("Pulsslag");
		// test.dropTable("Temperatur");
		System.exit(0);
	}

	// --------- GAMMEL KODE ------------- //

	static double value = 168;
	private ArrayList<Double> temperaturListe;
	private ArrayList<Integer> pulsListe;
	private int tempKald = 0;
	private int pulsKald = 0;

	/*
	 * public Database() { File temperatur = new File("Temperatur.txt"); File
	 * puls = new File("puls.txt"); try { Scanner input = new Scanner(new
	 * FileReader(temperatur)); Scanner input2 = new Scanner(new
	 * FileReader(puls)); temperaturListe = new ArrayList<>(); pulsListe = new
	 * ArrayList<>(); while (input.hasNext()) {
	 * temperaturListe.add(Double.parseDouble(input.nextLine()));
	 * 
	 * } while (input2.hasNext()) {
	 * pulsListe.add(Integer.parseInt(input2.nextLine()));
	 * 
	 * } } catch (IOException e1) { e1.printStackTrace();
	 * 
	 * } }
	 */

	public double getTemp() {
		if (tempKald < temperaturListe.size()) {
			double maaling = temperaturListe.get(tempKald);
			// maaling = (maaling * 4 / 50) + 24;
			tempKald++;
			return maaling;
		} else {
			tempKald = 0;
			double maaling = temperaturListe.get(tempKald);
			// maaling = (maaling * 4 / 50) + 24;
			tempKald++;
			return maaling;
		}
	}

	public int getPuls() {
		if (pulsKald < pulsListe.size()) {
			int maaling = pulsListe.get(pulsKald);
			pulsKald++;
			return maaling;
		} else {
			pulsKald = 0;
			int maaling = pulsListe.get(pulsKald);
			pulsKald++;
			return maaling;
		}
	}

	/*
	 * public static void main(String[] args) { Database test = new Database();
	 * for (int i = 0; i < 20; i++) { System.out.println("Puls:" +
	 * test.getPuls()); System.out.println("Temp:" + test.getTemp());
	 * 
	 * } }
	 */

	public ArrayList<Integer> getPulsListe() {
		return pulsListe;
	}

	public static double genererMaaling() {
		value += (2 * Math.random() - 1.0) * 0.25;
		if (value < 0) {
			value = 0;
		}
		if (value > 255) {
			value = 255;
		}

		value = (value * 4 / 50) + 24;

		return value;
	}

}