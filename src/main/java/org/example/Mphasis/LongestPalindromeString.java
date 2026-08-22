package org.example.Mphasis;

public class LongestPalindromeString {

	public static void main(String[] args) {
		String s = "babbabd"; /* "forgeeksskeegfor"; */
		System.out.println(longestPalindrome(s));
	}

	public static String longestPalindrome(String s) {
		int n = s.length();
		if (n < 2) {
			return s;
		}

		int startIndex = 0, maxLength = Integer.MIN_VALUE;
		int low, high;
		for (int i = 0; i < n; i++) {
			low = i - 1;
			high = i + 1;

			while (high < n && s.charAt(high) == s.charAt(i)) 
				high++;

			while (low >= 0 && s.charAt(low) == s.charAt(i)) 
				low--;

			while (low >= 0 && high < n && s.charAt(low) == s.charAt(high)) {
				low--;
				high++;
			}

			int length = high - low - 1;
			if (maxLength < length) {
				maxLength = length;
				startIndex = low + 1;
			}
			System.out.println(s.substring(startIndex, (startIndex + maxLength)));
		}
	    System.out.println(s.substring(startIndex, (startIndex + maxLength)));

		return s.substring(startIndex, (startIndex + maxLength));
	}
	
	/*
	 public String longestPalindrome(String s) {
       int start = 0;
       int end = 0;
        //bbabb
        for(int i =0; i<s.length()-1;i++){
            int max1 = findMaxAroundCenter(s,i,i);
            int max2 = findMaxAroundCenter(s,i,i+1);
            int max = Math.max(max2,max1);
            if(max > end - start){
                start = i - (max-1)/2;
                end = i + max/2;
            }
        }
        return (s.substring(start,end+1));
    }
    
    private int findMaxAroundCenter(String s,int i,int j){
        
        while(i>=0 && j<s.length() && s.charAt(i) == s.charAt(j)){
            i--;
            j++;
        }
        return j - i - 1;
    }
	 
	*/

}
