import java.util.HashMap;
import java.util.Map;

public class longestUniformSubstring {

	public static int[] printCharacterCount(String str) {
		Character presentChar = '\0';
		Character previousChar = '\0';
		int count = 1;
		Map<Integer, Integer> map = new HashMap<>();
		int maxArr[] = new int[2]; // 1
		boolean flag = false;
		int prevIndex = -1;
		for (int presentCharIndex = 1; presentCharIndex < str.length(); presentCharIndex++) {
			// Count occurrences of present character
			presentChar = str.charAt(presentCharIndex);
			previousChar = str.charAt(presentCharIndex - 1);

			if (presentChar.equals(previousChar)) {
				count++;
				if(!flag) {
					prevIndex = presentCharIndex - 1;
					flag = true;
				}
			} else {
				
				System.out.println("indexOf -> "+str.indexOf(previousChar)+ ",  prevIndex -> "+prevIndex);
				map.put(str.indexOf(previousChar), count); // 2
				count = 1;
				flag = false;
			}
		}
		
		map.put(str.indexOf(presentChar), count);
		//map.entrySet().stream().forEach(System.out::println);
		
		Map.Entry<Integer, Integer> maxEntry = null;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		maxArr[0] = maxEntry.getKey();
		maxArr[1] = maxEntry.getValue();

		return maxArr;
	}

	public static void main(String[] args) {
		String str = "aaabbbbxccccccdeeeeeef";
		if (!str.isEmpty()) {
			int[] arr = printCharacterCount(str);
			System.out.println( "["+arr[0]+ ", "+arr[1]+"]");
		} else
			System.out.println("Empty String");
	}

}
