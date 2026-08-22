import java.util.LinkedHashMap;
import java.util.Map;

/*1.  (Mandatory) Given a string. Write a function to find the first non-repeating character in it. If there is no non-repeating character, return 0; 
e.g. 
Input (string) Output (char) 

—– —— 
Input		  Output
-------------------------
aabbccd         d 
abbccpddee    	a 
iijjkkllmm      0 
abbcddea        c 
*/
public class NonRepeatatingCharacter {
	
	public static int firstUniqChar(String s) {
		if(s == null || s.length() < 1) return 0;
		int length = s.length();
		int[] charCount = new int[26];
		
		for(int i = 0; i < length; i++) // store frequency of all characters
			charCount[s.charAt(i) - 'a'] += 1;
		
		for(int i = 0; i < length; i++)
			if(charCount[s.charAt(i) - 'a'] == 1)
				return i;		
		return -1;
	}
	
	public static int firstUniqChar_Map(String s) {
		if(s == null || s.length() < 1) return 0;
		int length = s.length();
		LinkedHashMap<Character, Integer> charFreq = new LinkedHashMap<>(); // 
		
		for(int i = 0; i < length; i++) // store frequency of all characters
			charFreq.put(s.charAt(i), charFreq.getOrDefault(s.charAt(i), 0) + 1);   //[s.charAt(i) - 'a'] += 1;
		
		for(int i = 0; i < length; i++)
			if(charFreq.get(s.charAt(i)) == 1)
				return i;		
		return -1;
	}

	static char firstNonRepeatatingCharacter(String str) {
		boolean flag = false;
		char returnChar = '0';

		for (char ch : str.toCharArray()) {
			if (str.indexOf(ch) == str.lastIndexOf(ch)) {
				returnChar = ch;
				flag = true;
				break;
			}

		}
		if (flag == false) {
			return returnChar;
		} else {
			return returnChar;
		}
	}

	public static void main(String[] args) {
		String str = "abbccxddeeay";
		str = str.replaceAll("\\W", "");
		System.out.println("firstNonRepeatatingCharacter = " + firstNonRepeatatingCharacter(str));
		System.out.println("firstNonRepeatatingCharacter = " + firstUniqChar(str));
		System.out.println("firstNonRepeatatingCharacter = " + firstUniqChar_Map(str));
	}
}
