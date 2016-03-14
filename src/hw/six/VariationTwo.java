package hw.six;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VariationTwo {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/data_engineering";
		String user = "root";
		String password = "";
		
		int total = 5000000;
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < total; i++) {
				list.add(i);
			}
			Collections.shuffle(list);

			long begin = System.currentTimeMillis();
			System.out.println("Begin: " + begin);
			doInsert(conn, list);
			long end = System.currentTimeMillis();
			System.out.println("End: " + end);

			Util.doDelete(conn);
			
			System.out.println("Total Time = " + Util.getMinutes(begin, end) + " minutes");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if ( conn != null ) {
				try { conn.close(); } catch (Exception e) {}
			}
		}
	}
	
	private static void doInsert(Connection conn, List<Integer> list) throws SQLException {
		Random random = new Random();
		PreparedStatement statement = conn.prepareStatement("INSERT INTO benchmark (theKey, columnA, columnB, filler) VALUES (?, ?, ?, ?)");
		int count = 0;
		for (Integer key : list) {
			statement.setInt(1, key);
			statement.setInt(2, random.nextInt(49999) + 1);
			statement.setInt(3, random.nextInt(49999) + 1);
			statement.setString(4, Util.generateRandomString(247));
			statement.addBatch();
			if ( count > 0 && count % 100 == 0 ) {
				statement.executeBatch();
				statement.clearBatch();
			}
		}
		statement.executeBatch();
		statement.closeOnCompletion();
	}
}
