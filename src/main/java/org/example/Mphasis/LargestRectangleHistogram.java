 package org.example.Mphasis;

import java.util.Stack;

public class LargestRectangleHistogram {

	public static void main(String[] args) {
		int[] heights = {2, 1, 5, 6, 2, 3}; /* { 6, 2, 5, 4, 5, 1, 6 }; */
		LargestRectangleHistogram lrh = new LargestRectangleHistogram();
		System.out.println(lrh.largestRectangleArea(heights));
	}

	public int largestRectangleArea(int[] heights) {
		int maxArea = 0;
		int[] ps = preSmaller(heights);
		int[] ns = nextSmaller(heights);

		for (int i = 0; i < heights.length; i++) {
			int curr = (ns[i] - ps[i] + 1) * heights[i];
			maxArea = Math.max(maxArea, curr);
		}
		return maxArea;
	}
	static int[] preSmaller(int[] heights) {
		int[] left = new int[heights.length];
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < heights.length; i++) {
			if(stack.empty()) {
				left[i] = 0;
			}else {
				while (!stack.empty() && heights[stack.peek()] >= heights[i])
					stack.pop();
				left[i] = stack.empty() ? 0 : ( stack.peek() + 1 );
			}
			stack.push(i);
		}
		return left;
	}
	static int[] nextSmaller(int[] heights) {
		int[] right = new int[heights.length];
		Stack<Integer> stack = new Stack<>();

		for (int i = (heights.length - 1); i >= 0; i--) {
			
			if (stack.empty()) {
				right[i] = heights.length - 1;
			}else {
				while (!stack.isEmpty() && heights[stack.peek()] >= heights[i])
					stack.pop();
				right[i] = stack.empty() ? (heights.length - 1) : ( stack.peek() - 1 );
			}
			stack.push(i);
		}
		return right;
	}
}
