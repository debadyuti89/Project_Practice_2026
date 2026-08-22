package org.example.Mphasis;

import java.util.Arrays;

/*
20. Product of Array
Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
*/
public class ProductElementsInArray {

	public static void main(String[] args) {
		int[] nums = {-1,1,0,-3,3}; /*{ 1, 2, 3, 4 };*/  /*{0,4,0};*/

		int output[] = productExceptSelf(nums);
		System.out.println(Arrays.toString(output));
		for (int i = 0; i < output.length; i++)
			System.out.print(output[i] + ", ");
	}

	public static int[] productExceptSelf(int nums[]) {
		int n = nums.length;
		int output[] = new int[n];
		
		if(n<1) return output;
		
		int product = 1;
		for(int i=0;i<n;i++) {
			product *= nums[i];
			output[i] = product;
		}
		// Traverse from right and update output array.
		product = 1;
		for(int i = n - 1; i > 0; i--) {
			output[i] = output[i-1] * product;
			product *= nums[i];
		}
		output[0] = product;
	return output;	
		
	/*	
		int prod = 1;
		int flag = 0;
		int arrayLength = nums.length;
		// creating a new array of size n
		int arr[] = new int[arrayLength];

		// product of all elements
		for (int i = 0; i < arrayLength; i++) {
			if (nums[i] == 0) // counting number of elements which have value 0
				flag++;
			else
				prod *= nums[i];
		}
		if(flag == arrayLength) {
			return nums;
		}

		for (int i = 0; i < arrayLength; i++) {
			if (flag > 1) {
                arr[i] = 0;
            }else if (flag == 0) {
                arr[i] = (prod / nums[i]);
            }else if (flag == 1 && nums[i] != 0) {
                arr[i] = 0;
            }        
            else
                arr[i] = prod; 
		}
		return arr;
	*/
	}
}
