package org.example.Mphasis;

import java.util.HashMap;
import java.util.Map;

public class SubArraySumEqual_K {

	public static void main(String[] args) {
		int input[] = {2, 3, 1, 2, 4, 3};/* { 1, 1, 1 }; */ /* { 3, 4, 7, 2, -3, 1, 4, 2 }; [0,0,0,0,0,0,0,0,0,0]*/
		int k = 7;
		System.out.println("No. of sub arrays "+subArraySum(input, k));
	}

	private static int subArraySum(int[] nums, int k) {
		int count = 0;		
		if(nums.length == 0)
			return count;
		
		Map<Integer, Integer> map = new HashMap<>(); // key = prefix sum & value = count of prefix sum
		int currentSum = 0;
		
		for(int i = 0; i < nums.length; i++) {
			currentSum += nums[i];
			
			if(currentSum == k) {
				count++;
			}
			
			if(map.containsKey(currentSum - k))
				count += map.get(currentSum - k);
			
			map.put(currentSum, map.getOrDefault(currentSum, 0) + 1);
		}	
		return count;
	}

}
