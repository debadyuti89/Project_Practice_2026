/*9.(Mandatory) A robot can move in 4 directions up, down, left and right.
 * It's path is encoded in a string with characters U, D, L and R. 
 * If the bot starts at coordinate {0,0} then find it's final coordinate based on the input path string .
 * 
 * 
 * 
 * 
 * */ 
public class Robot {

	public static void main(String[] args) {

		String input = "uudd";
		if(!input.isEmpty()) {
			int[] arr = coordinateCalculation(input);
			System.out.print( "["+arr[0]+ ", "+arr[1]+"]");     
		}else
			System.out.println("Invalid Input");
	}

	private static int[] coordinateCalculation(String input) {
		int[] returnCor = new int[2];
		int x, y;
		
		x = y = 0;
		input = input.toUpperCase();
		
		for(int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			switch (ch) {
			case 'U':
				y++;
				break;
			case 'D':
				y--;
				break;
			case 'L':
				x--;
				break;
			case 'R':
				x++;
				break;
			default:
				break;
			}
		}
		returnCor[0] = x;
		returnCor[1] = y;
		
		return returnCor;		
	}

}
