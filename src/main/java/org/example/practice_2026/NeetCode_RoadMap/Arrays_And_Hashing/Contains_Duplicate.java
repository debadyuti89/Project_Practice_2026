package NeetCode_RoadMap.Arrays_And_Hashing;


import java.util.*;

/**
https://neetcode.io/problems/duplicate-integer?list=neetcode150

Given an integer array nums, return true if any value appears more than once in the array, otherwise return false.

Example 1:
Input: nums = [1, 2, 3, 3]
Output: true

Example 2:
Input: nums = [1, 2, 3, 4]
Output: false

Recommended Time & Space Complexity
You should aim for a solution with O(n) time and O(n) space, where n is the size of the input array.
**/
public class Contains_Duplicate {
    public static boolean hasDuplicate(int[] nums) {
        Set<Integer> uniqueElements = new HashSet<>(); // Create an empty HashSet to store unique elements

        // Iterate through the array
        for (int number : nums) { //
            // If the element is already in the set, it's a duplicate
            if (!uniqueElements.add(number)) { //
                return true; //
            }
        }
        return false; // No duplicates found after checking all elements
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        System.out.println("Array: [1, 2, 3, 1], Contains Duplicate: " + hasDuplicate(nums1)); // Output: true

        int[] nums2 = {1, 2, 3, 4};
        System.out.println("Array: [1, 2, 3, 4], Contains Duplicate: " + hasDuplicate(nums2)); // Output: false

        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        System.out.println("Array: [1, 1, 1, 3, 3, 4, 3, 2, 4, 2], Contains Duplicate: " + hasDuplicate(nums3)); // Output: true
    }
}
