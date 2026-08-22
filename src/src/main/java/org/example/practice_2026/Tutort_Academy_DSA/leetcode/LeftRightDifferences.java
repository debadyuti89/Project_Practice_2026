package Tutort_Academy_DSA.leetcode;

import java.util.Arrays;

/*
https://leetcode.com/problems/left-and-right-sum-differences/description/
You are given a 0-indexed integer array nums of size n.

Define two arrays leftSum and rightSum where:

leftSum[i] is the sum of elements to the left of the index i in the array nums. If there is no such element, leftSum[i] = 0.
rightSum[i] is the sum of elements to the right of the index i in the array nums. If there is no such element, rightSum[i] = 0.
Return an integer array answer of size n where answer[i] = |leftSum[i] - rightSum[i]|.

Example 1:
Input: nums = [10,4,8,3]
Output: [15,1,11,22]
Explanation: The array leftSum is [0,10,14,22] and the array rightSum is [15,11,3,0].
The array answer is [|0 - 15|,|10 - 11|,|14 - 3|,|22 - 0|] = [15,1,11,22].

Example 2:
Input: nums = [1]
Output: [0]
Explanation: The array leftSum is [0] and the array rightSum is [0].
The array answer is [|0 - 0|] = [0].

Constraints:
=============
1 <= nums.length <= 1000
1 <= nums[i] <= 105
*/
public class LeftRightDifferences {
    public static int[] leftRightDifference(int[] nums) {
        int n = nums.length;
        int[] resultArr = new int[n];
        int leftSum = 0;
        // Calculate the initial total sum to use as the starting right sum
        int rightSum = Arrays.stream(nums).sum();
        /*
        int rightSum = 0;
        for(int i = 0; i < n; i++) {
            rightSum = rightSum + nums[i];
        }
        */


        for (int i = 0; i < n; i++) {
            rightSum = rightSum - nums[i]; // Subtract the current element from rightSum as it's not on the right side
            resultArr[i] = Math.abs(leftSum - rightSum); // Calculate the absolute difference
            leftSum = leftSum + nums[i]; // Add the current element to leftSum for the next iteration
        }
        return resultArr;
    }

    public static void main(String[] args) {
        int[] nums1 = {10, 4, 8, 3};
        int[] result1 = leftRightDifference(nums1);
        System.out.println("Input: " + Arrays.toString(nums1));
        System.out.println("Output: " + Arrays.toString(result1)); // Expected: [15, 1, 11, 22]

        int[] nums2 = {1};
        int[] result2 = leftRightDifference(nums2);
        System.out.println("Input: " + Arrays.toString(nums2));
        System.out.println("Output: " + Arrays.toString(result2)); // Expected: [0]
    }
}
