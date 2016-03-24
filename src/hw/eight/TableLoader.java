package hw.eight;

import hw.six.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class TableLoader {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/data_engineering";
		String user = "root";
		String password = "";
		
		int total = 1000000;
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			
			Random random = new Random();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO TestData (pk, ht, tt, ot, filler) VALUES (?, ?, ?, ?, ?)");
			for (int i = 0; i < total; i++) {
				statement.setInt(1, i);
				statement.setInt(2, random.nextInt(99999));
				statement.setInt(3, random.nextInt(9999));
				statement.setInt(4, random.nextInt(999));
				statement.setString(5, Util.generateRandomString(247));
				statement.addBatch();
				if ( i > 0 && i % 100 == 0 ) {
					statement.executeBatch();
					statement.clearBatch();
				}
			}
			statement.executeBatch();
			statement.clearBatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if ( conn != null ) {
				try { conn.close(); } catch (Exception e) {}
			}
		}
	}
}
