import java.sql.*;
import java.util.*;

import com.mysql.jdbc.CommunicationsException;

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
	
	public Database(boolean test){
		
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
			// conn.setAutoCommit(false);

			// opret forbindelsesobjekt
			System.out.println("Der er oprettet forbindelse til databasen");
			initTable("Pulsslag");
			initTable("Temperatur");

		} catch (CommunicationsException ex) {
			ex.printStackTrace();
			System.out.println();
			System.exit(0);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e2) {
			e2.printStackTrace();
		} catch (IllegalAccessException e3) {
			e3.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database-test: " + e.getMessage());
			System.out.println("Programmet afsluttes");
			// udskriv fejlmeddelelse
			e.printStackTrace(); // udskriv stacktrace
			System.exit(0);
		}
		// afslut program paent
	}

	public void initTable(String name) {

		try {
			// Slet tabel fra forrige session
			dropTable(name);

			// Tabellen eksisterer ikke
			System.out.println("Opretter tabel " + name + "...");
			stmt = conn.createStatement();
			String message1 = "CREATE TABLE " + name;
			stmt.executeUpdate(
					message1 + "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + name + " DOUBLE NOT NULL DEFAULT 0)");
			// conn.commit();
		} catch (Exception e) {
			System.out.println("Init table: " + e.getMessage());
			// udskriv fejlmeddelelse
			e.printStackTrace();
		}
	}

	public void dropTable(String tablename) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE " + tablename);
			// conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Det lykkedes ikke at slette tabellen");
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
			// conn.commit();
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
			// System.out.println("Henter målinger...");
			// udfoer forespoergsel - modtag målingerne aftagende, så den
			// seneste måling kommer på plads 0 i listen.
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM " + user + "." + tablename + " ORDER BY id DESC");
			// conn.commit();
			while (rset.next()) {
				outputData.add(rset.getDouble(2));
				// System.out.println("test af løkke");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputData;
	}
}