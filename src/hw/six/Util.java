package hw.six;

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
}
