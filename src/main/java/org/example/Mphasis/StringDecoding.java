package org.example.Mphasis;

public class StringDecoding {

	public static void main(String[] args) {
		String encode = "AAABCABCD";
		System.out.println(decodeString(encode));
	}

	private static String decodeString(String encode) {
		StringBuilder result = new StringBuilder();
		if (encode.length() == 0)
			return "Input can't be empty";
		result.append(encode.charAt(0)); // Add first element
		for (int i = 1; i < encode.length(); i++) {
			char curr = encode.charAt(i);

			if (i * 2 <= encode.length() && encode.substring(0, i).equals(encode.substring(i, i * 2))) {
				result.append("*");
				i = i * 2 - 1;
			}else {
				result.append(curr);
			}
		}

		return result.toString();
	}

}
