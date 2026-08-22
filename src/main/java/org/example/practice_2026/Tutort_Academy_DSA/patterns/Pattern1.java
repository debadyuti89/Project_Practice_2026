package Tutort_Academy_DSA.patterns;

import java.util.Scanner;

/*
Pattern 1
        ******
        *    *
        *    *
        *    *
        *    *
        ******
*/
public class Pattern1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = 0; row < n; row++) {
            for (col = 0; col < n; col++) {
                if (row == 0 || col == 0 || row == n - 1
                        || col == n - 1) {
                    System.out.print("*");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}