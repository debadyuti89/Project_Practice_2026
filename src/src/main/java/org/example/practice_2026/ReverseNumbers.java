import java.util.*;
import java.util.stream.Collectors;

public class ReverseNumbers {

	public static void main(String[] args) {
		int number = 123456789;
		System.out.println("Original number = " + number);
		System.out.println("Reverse number = " + reverseNumber(number));
		
		System.out.println("-------------------------------------------------");
		
		System.out.println("Remove Nulls From Array");
		List<String> listColors = Arrays.asList("Red", null, "Green", null, "Blue", null, null, "Orange");
		System.out.println();
		listColors.stream().forEach(cl -> System.out.print(cl+", "));
		System.out.println();
		//listColors = listColors.stream().filter(c -> c != null).collect(Collectors.toList());
		listColors = listColors.stream().filter(Objects::nonNull).collect(Collectors.toList());
		listColors.stream().forEach(cl -> System.out.print(cl+", "));
	}

	private static int reverseNumber(int number) {
		int reverse = 0;

		for (int no = number; no != 0; no = no / 10) {
			int rem = no % 10;
			reverse = reverse * 10 + rem;
		}
		return reverse;
	}

}
