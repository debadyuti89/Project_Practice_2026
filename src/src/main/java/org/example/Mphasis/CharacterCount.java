package org.example.Mphasis;

public class CharacterCount {

	public static String printCharacterCount(String str) {
		Character presentChar = '\0';
		Character previousChar = '\0';
		int count = 1;
		StringBuilder resultStr = new StringBuilder();

		for (int presentCharIndex = 1; presentCharIndex < str.length(); presentCharIndex++) {
			// Count occurrences of present character
			presentChar = str.charAt(presentCharIndex);
			previousChar = str.charAt(presentCharIndex - 1);

			if (presentChar.equals(previousChar)) {
				count++;
			} else {
				if(count == 1)
					resultStr.append(previousChar);
				else {
					resultStr.append(previousChar);
					resultStr.append(count);
				}
				count = 1;
			}
		}
		// Print last character and its count
		if(count == 1)
			resultStr.append(presentChar);
		else {
			resultStr.append(presentChar);
			resultStr.append(count);
		}
		
		return resultStr.toString();
	}

	public static void main(String[] args) {
		String str = "aaabbbbxcccccd";
		if(!str.isEmpty())
			System.out.println(printCharacterCount(str));
		else
			System.out.println("Empty String");
	}

}
