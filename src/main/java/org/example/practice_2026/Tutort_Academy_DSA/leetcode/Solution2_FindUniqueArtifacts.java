package Tutort_Academy_DSA.leetcode;

import java.util.*;

/*
Each artifact has a magical energy signature represented as an integer. Most artifacts appear exactly three times in the chest, but there is one unique artifact that appears exactly once. Your task is to identify this unique artifact and return its magical energy signature. You must implement a solution with a linear runtime complexity and use only constant extra space.

Example 1: Input: artifacts = [8, 8, 12, 8]

Output: 12

Example 2: Input: artifacts = [0, 7, 0, 7, 0, 7, 42]

Output: 42

Sample Input 1: 4 8 8 12 8

Sample Output 1: 12

Sample Input 2: 7 0 7 0 7 0 7 42

Sample Output 2: 42

Explanation: Implement a program that takes in a list of integers artifacts representing the magical energy signatures of the artifacts, and returns the magical energy signature of the unique artifact that appears exactly once.

Input Format

The first line contains an integer n, the number of artifacts. The second line contains n integers, the magical energy signatures of the artifacts.

Constraints

1 <= artifacts.length <= 30,000 -2^31 <= artifacts[i] <= 2^31 - 1 Each artifact in artifacts appears exactly three times except for one unique artifact which appears once.

Output Format

Output a single integer, the magical energy signature of the unique artifact.
 */

//https://www.hackerrank.com/contests/level-test-1-dsa/challenges/find-the-unique-artifact/submissions/code/1403351402

public class Solution2_FindUniqueArtifacts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] artifacts = new int[n];
        for (int i = 0; i < n; i++) {
            artifacts[i] = sc.nextInt();
        }
        System.out.println(findUnique(artifacts));
    }

    public static int findUnique(int[] artifacts) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int bitSum = 0;
            int bitMask = (1 << i);
            for (int artifact : artifacts) {
                if ((artifact & bitMask) != 0) {
                    bitSum++;
                }
            }
            if (bitSum % 3 != 0) {
                result |= bitMask;
            }
        }
        return result;
    }
}
