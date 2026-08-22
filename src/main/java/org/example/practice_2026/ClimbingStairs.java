public class ClimbingStairs {

	public static void main(String[] args) {

		System.out.println(climbStairs(4));
		System.out.println(climbStairs_On(4));

	}

	private static int climbStairs_On(int n) {
		if (n == 1 || n == 0)
			return 1;

		int[] arr = new int[n + 1];
		arr[0] = 1;
		arr[1] = 1;
		for (int i = 2; i <= n; i++) {
			arr[i] = arr[i - 1] + arr[i - 2];
		}
		return arr[n];
	}

	private static int climbStairs(int n) {
		int firstStep = 1;
		int secondStep = 1;
		// int thirdStep = 3;
		for (int i = 2; i <= n; i++) {
			int temp = firstStep;
			firstStep = firstStep + secondStep;
			secondStep = temp;
		}
		return firstStep;
	}

	public int climbStairs_rec(int n) {
		if (n == 1 || n == 0)
			return 1;
		if (n == 2)
			return 2;
		else {
			return climbStairs_rec(n - 1) + climbStairs_rec(n - 2);
		}
	}

}
