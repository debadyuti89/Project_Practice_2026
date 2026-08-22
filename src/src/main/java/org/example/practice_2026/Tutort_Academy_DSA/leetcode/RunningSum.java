package Tutort_Academy_DSA.leetcode;

/*
https://leetcode.com/problems/running-sum-of-1d-array/description/
Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]…nums[i]).

Return the running sum of nums.



Example 1:

Input: nums = [1,2,3,4]
Output: [1,3,6,10]
Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].
Example 2:

Input: nums = [1,1,1,1,1]
Output: [1,2,3,4,5]
Explanation: Running sum is obtained as follows: [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1].
Example 3:

Input: nums = [3,1,2,10,1]
Output: [3,4,6,16,17]
 */
public class RunningSum {
    public static void main(String[] args) {
        int[] nums = {3, 1, 2, 10, 1};
        int[] result = runningSum(nums);
        for (int i : result) {
            System.out.print(result[i] + ", ");
        }
    }

    private static int[] runningSum(int[] nums) {
        /* Brut force approach
        int n=nums.length;
       int[] ans = new int[n];
       for(int i=0;i<n;i++) {
           //i=3
           for(int j=0;j<=i;j++) {//j=3
               ans[i]=ans[i]+nums[j];
               //ans[3]=0+3=3
               //ans[3]=3+1=4
               //ans[3]=4+2=6
               //ans[3]=6+10=16
           }
       }

         */

        if (nums.length == 0) {
            return nums;
        }

        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i - 1] + nums[i];
        }
        return nums;
    }
}
