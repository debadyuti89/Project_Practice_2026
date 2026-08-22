import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 4. (Mandatory)Missing characters to make a string Pangram
 * 
 *  
 * 
 * A pangram is a sentence containing every letter in the English alphabet.
 * 
 * Given a string, find all characters that are missing from the string, i.e.,
 * 
 * the characters that can make the string a Pangram. We need to print output in
 * alphabetic order.
 * 
 *  
 * 
 * Input: welcome to geeksforgeeks
 * 
 * Output : abdhijnpquvxyz
 * 
 *  
 * 
 * Input: The quick brown fox jumps
 * 
 * Output: adglvyz
 */
public class StringPangram {

	static Queue<Character> missingCharacters(String str) {
		int totalAlphabet = 26;

		boolean[] presentChar = new boolean[totalAlphabet];
		Queue<Character> charsMissingList = new LinkedList<>();

		for (int i = 0; i < str.length(); i++) {
			if ('A' <= str.charAt(i) && str.charAt(i) <= 'Z')
				presentChar[str.charAt(i) - 'A'] = true;
			else if ('a' <= str.charAt(i) && str.charAt(i) <= 'z')
				presentChar[str.charAt(i) - 'a'] = true;
		}

		for (int i = 0; i < presentChar.length; i++) {
			if (presentChar[i] == false)
				charsMissingList.offer((char) (i + 'a'));
		}
		return charsMissingList;
	}

	public static void main(String[] args) {
		String str = "The quick brown fox jumps over the lazy dog";

		Queue<Character> missingCharList = missingCharacters(str);
		System.out.println(missingCharList);
		if (missingCharList.size() >= 1) {
			System.out.println("The missing character present in the string listed below.");
			missingCharList.stream().forEach(System.out::print);
		}else {
			System.out.println("There is no missing character present in the string.");
		}
	}

}
