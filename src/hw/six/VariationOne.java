package hw.six;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class VariationOne {
	/**
	 * Generates database load in sorted order
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/data_engineering";
		String user = "root";
		String password = "";
		
		int total = 10000;
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println("Begin: " + System.currentTimeMillis());
			doInsert(conn, total);
			System.out.println("End: " + System.currentTimeMillis());

			doDelete(conn);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if ( conn != null ) {
				try { conn.close(); } catch (Exception e) {}
			}
		}
	}
	/**
	 * Performs batch insertion in sorted order into the benchmark table. 
	 * Random values are generated for columnA, columnB, and filler.
	 * @param conn
	 * @param total
	 * @throws SQLException
	 */
	private static void doInsert(Connection conn, int total) throws SQLException {
		Random random = new Random();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO benchmark (theKey, columnA, columnB, filler) VALUES (?, ?, ?, ?)");
		for (int i = 0; i < total; i++) {
			statement.setInt(1, i);
			statement.setInt(2, random.nextInt(49999) + 1);
			statement.setInt(3, random.nextInt(49999) + 1);
			statement.setString(4, Util.generateRandomString(247));
			statement.addBatch();
			if ( i > 0 &&  i % 100 == 0 ) {
				statement.executeBatch();
				statement.clearBatch();
			}
		}
		statement.executeBatch();
		statement.closeOnCompletion();
	}
	/**
	 * Deletes all rows from benchmark table.
	 * @param conn
	 * @throws SQLException
	 */
	private static void doDelete(Connection conn) throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("DELETE FROM benchmark");
		statement.close();
	}
}
