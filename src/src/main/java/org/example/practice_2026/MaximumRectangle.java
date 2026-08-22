import java.util.Stack;

public class MaximumRectangle {

	public static void main(String[] args) {
		char[][] matrix = { { '1', '0', '1', '0', '0' }, { '1', '0', '1', '1', '1' }, 
							  { '1', '1', '1', '1', '1' }, { '1', '0', '0', '1', '0' } };
		

	}

	public static int maximalRectangle(char[][] matrix) {
		int row = matrix.length;
		if(row == 0)
			return 0;
		int col = matrix[0].length;
		int maxArea = 0;
		
		for(int i=1; i<=row; i++) {
			for(int j=0; j<col;j++) {
				
			}
		}
		
		
		
		
		return 0;
        
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
