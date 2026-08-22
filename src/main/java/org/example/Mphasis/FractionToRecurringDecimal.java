package org.example.Mphasis;

import java.util.HashMap;

public class FractionToRecurringDecimal {

	public static void main(String[] args) {
		
		System.out.println(fractionToDecimal(0, -1));
		System.out.println(fractionToDecimal(-1, -2147483648));
		System.out.println(fractionToDecimal(4, 333));
		System.out.println(fractionToDecimal(-22, -2));
		System.out.println(fractionToDecimal(2, -11));
		System.out.println(fractionToDecimal(22, -7));
		System.out.println(fractionToDecimal(0, 0));
		System.out.println(fractionToDecimal(1, 6));
	}

	public static String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0)
			return "0";
		if (denominator == 0)
			return "Denominator can't be 0";
			
		HashMap<Long, Integer> map = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		if((numerator < 0) || (denominator < 0))
			sb.append("-");
		
		if((numerator < 0) && (denominator < 0))
			sb = new StringBuilder();
		
		numerator = Math.abs(numerator);
		denominator = Math.abs(denominator);
		
		long quotient = numerator / denominator;
		long reminder = numerator % denominator;
		
		sb.append(quotient);

		if (reminder != 0) {
			sb.append(".");
			map.put(reminder, sb.toString().length());

			while (reminder != 0) {
				reminder = reminder * 10;
				quotient = reminder / denominator;
				sb.append(quotient);
				reminder = reminder % denominator;

				if (map.containsKey(reminder)) {
					int index = map.get(reminder);
					sb.insert(index, '(');
					sb.append(')');
					return sb.toString();
				} else {
					map.put(reminder, sb.toString().length());
				}
			}
		}
		return sb.toString();
	}

}
