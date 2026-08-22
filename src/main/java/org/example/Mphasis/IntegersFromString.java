package org.example.Mphasis;

/*
23 Mandatory

Takes a string str and returns the int value represented by
       the string. For example, atoi("42") returns 42.
    More Test Cases for Second problem.
      ("3") == 3,
      ("-3") == -3,
      ("acdd") = 0,
      ("345a") == 345,
      ("34b") == 34,
       ("b23") == 0,
     ("-zcb34a") = 0,
    ("") = 0,
    ("3212bas34d5ae4") = 3212
*/
public class IntegersFromString {

	public static void main(String[] args) {
		String input = "-245a";
		System.out.println(getIntegerFromString(input));
	}

	private static int getIntegerFromString(String input) {
		int output = 0;
		char flag = '\0';
		int index = 0;
		
		if(input == null || input.length() < 1)
			return output;
		
		if(input.charAt(0) == '-') {
			flag = '-';
			index++;
		}
		if(input.charAt(0) == ' ')// " 45ab56"
			index++;
		while(index < input.length() && input.charAt(index) >= '0' && input.charAt(index) <= '9') {
			output = (output * 10) + (input.charAt(index) - '0'); // ascii value of 0-9 is 48-57
			index++;
		}
		if(flag == '-')
			output = -output;
		
		return output;
	}

}
