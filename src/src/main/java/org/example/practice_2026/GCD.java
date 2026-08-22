public class GCD {

	public static void main(String[] args) {
		System.out.println("GCD of two numbers = " + gcdCalculation(15, 9));
		System.out.println("GCD of two numbers = " + gcd(0, 12));
	}

	private static int gcdCalculation(int n1, int n2) {
		int gcd = 1;
		int lowestNo = 0;
		if (n1 == 0) {
			return n2;
		}
		if (n2 == 0) {
			return n1;
		}
		if(n1 == n2) {
			return n1;
		}
		if (n1 > n2) {
			lowestNo = n2;
		} else if (n2 > n1) {
			lowestNo = n1;
		}
		
		for (int i = lowestNo; i >= 2; i--) {
			if ((n1 % i) == 0 && (n2 % i) == 0 && i > gcd) {
				gcd = i;
				break;
			}
		}
		return gcd;
	}

	private static int gcd(int n1, int n2) {
		if(n1 == 0)
			return n2;		
		return gcd((n2 % n1), n1);		
	}
}
