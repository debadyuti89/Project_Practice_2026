package org.example.Mphasis;

public class TrappingWater {

	public static void main(String[] args) {
		int[] hight = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }; /* { 4, 2, 0, 3, 2, 5 }; */
		System.out.println(trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
		System.out.println(trap(new int[] { 4, 2, 0, 3, 2, 5 }));
		System.out.println(trap(new int[] { 0, 1, 3, 0, 1, 2, 0, 4, 2, 0, 3, 0 }));
		System.out.println(trap(new int[] { 1, 1, 1, 1, 1, 4 }));
		System.out.println(trap(new int[] { 4, 2, 0, 0, 0, 5 }));
		System.out.println(trap(new int[] {}));
		System.out.println(trap(null));
	}

	public static int trap(int[] height) {
		if (null == height || height.length == 0)
			return 0;

		int left_index = 0, right_index = height.length - 1;
		int ans = 0;
		int left_max = 0, right_max = 0;

		while (left_index < right_index) {
			if (height[left_index] < height[right_index]) {
				if (height[left_index] >= left_max)
					left_max = height[left_index];
				else
					ans += (left_max - height[left_index]);
				++left_index;
			} else {
				if (height[right_index] >= right_max)
					right_max = height[right_index];
				else
					ans += (right_max - height[right_index]);
				--right_index;
			}
		}
		return ans;
	}
}
