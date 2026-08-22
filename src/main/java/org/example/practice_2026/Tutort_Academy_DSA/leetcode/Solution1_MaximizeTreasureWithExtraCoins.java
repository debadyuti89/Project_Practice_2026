package Tutort_Academy_DSA.leetcode;

import java.util.*;

/*
Each pirate has a certain number of coins. You are given an integer array coins, where each coins[i] represents the number of coins the ith pirate has. You also have an additional number of coins extraCoins that you can distribute to a single pirate. Your task is to determine for each pirate if, after giving them all the extraCoins, they will have the maximum number of coins among all pirates. Return a boolean array result of length n, where result[i] is true if, after giving the ith pirate all the extraCoins, they will have the greatest number of coins among all the pirates, or false otherwise. Note that multiple pirates can have the greatest number of coins.

Example 1: Input: coins = [2, 3, 5, 1, 3] extraCoins = 3

Output: [true, true, true, false, true]

Explanation: If you give all extraCoins to: Pirate 1, they will have 2 + 3 = 5 coins, which is the greatest among the pirates. Pirate 2, they will have 3 + 3 = 6 coins, which is the greatest among the pirates. Pirate 3, they will have 5 + 3 = 8 coins, which is the greatest among the pirates. Pirate 4, they will have 1 + 3 = 4 coins, which is not the greatest among the pirates. Pirate 5, they will have 3 + 3 = 6 coins, which is the greatest among the pirates.

Example 2: Input: coins = [4, 2, 1, 1, 2] extraCoins = 1

Output: [true, false, false, false, false]

Explanation: There is only 1 extra coin. Pirate 1 will always have the greatest number of coins, even if a different pirate is given the extra coin. Example 3: Input: coins = [12, 1, 12] extraCoins = 10

Output: [true, false, true]

Sample Input 1: 5 2 3 5 1 3 3

Sample Output 1: true true true false true

Sample Input 2: 5 4 2 1 1 2 1

Sample Output 2: true false false false false

Sample Input 3: 3 12 1 12 10

Sample Output 3: true false true

Explanation: Implement a program that takes in a list of integers coins representing the number of coins each pirate has and an integer extraCoins, and returns a list of booleans indicating if giving the extraCoins to each pirate will make them have the greatest number of coins among all the pirates.

Input Format

The first line contains an integer n, the number of pirates. The second line contains n integers, the number of coins each pirate has. The third line contains an integer, the number of extraCoins.

Constraints

n == coins.length 2 <= n <= 100 1 <= coins[i] <= 100 1 <= extraCoins <= 50

Output Format

Output a boolean array of length n where each element indicates whether the corresponding pirate can have the greatest number of coins after receiving all the extraCoins.
 */

//https://www.hackerrank.com/contests/level-test-1-dsa/challenges/maximize-treasure-with-extra-coins/submissions/code/1403352957

public class Solution1_MaximizeTreasureWithExtraCoins {

    public static boolean[] kidsWithCandies(int[] coins, int extraCoins) {
        int maxCoins = 0;
        for (int coin : coins) {
            if (coin > maxCoins) {
                maxCoins = coin;
            }
        }
        boolean[] result = new boolean[coins.length];
        for (int i = 0; i < coins.length; i++) {
            result[i] = (coins[i] + extraCoins) >= maxCoins;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] coins = new int[n];
        for (int i = 0; i < n; i++) {
            coins[i] = sc.nextInt();
        }
        if (!sc.hasNextInt()) return;
        int extraCoins = sc.nextInt();
        System.out.println(Arrays.toString(kidsWithCandies(coins, extraCoins)));
    }
}