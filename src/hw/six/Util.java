package hw.six;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Util {
	public static String generateRandomString(int maxLength) {
		Random random = new Random();
		String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		int stringLength = random.nextInt(maxLength);
		
		StringBuilder builder = new StringBuilder();
		while ( builder.length() < stringLength ) {
			builder.append(values.charAt(random.nextInt(values.length())));
		}
		return builder.toString();
	}
	
	/**
	 * Deletes all rows from benchmark table.
	 * @param conn
	 * @throws SQLException
	 */
	public static void doDelete(Connection conn) throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("DELETE FROM benchmark");
		statement.close();
	}
	
	public static Double getMinutes(long start, long end) {
		long diff = end - start;
		return (new Long(diff).doubleValue()) / 60000.00;
	}
}
