package Tutort_Academy_DSA.leetcode;

import java.util.Scanner;

public class PowerOfTwo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number to check power of 2:");
        int n = input.nextInt();
        System.out.println(n & (n - 1));
        boolean isPowerOfTwo = (n > 0) && ((n & (n - 1)) == 0);
        System.out.println("isPowerOfTwo = " + isPowerOfTwo);
    }
}
