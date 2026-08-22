public class SubArraySumMinimumEqual_K {

	public static void main(String[] args) {
		int[] input = { 2, 3, 1, 2, 4, 3 }; /*{ 3, 4, 7, 2, -3, 1, 4, 2 };*/ 
		int target = 7;
		System.out.println(minSubArrayLen(target, input));
	}

	public static int minSubArrayLen(int target, int[] nums) {
		int sum = 0;
		int minLength = Integer.MAX_VALUE;
		int windowSize = 0, left = 0, right = 0;

		if(nums == null)
			return 0;
		
		while (right < nums.length) {
			sum += nums[right]; // adding each element to the sum

			if (sum >= target) { // checking 
				while(sum >= target) {
					sum -= nums[left];
					left++;
				} 
				windowSize = ((right - left) + 1 ) + 1;
				minLength = minLength > windowSize ? windowSize : minLength;
			}
			right++;
		}
		return minLength == Integer.MAX_VALUE ? 0 : minLength;
	}
}
