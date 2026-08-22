package Tutort_Academy_DSA.patterns;

/*
Pattern 20
* * * * * *
 *       *
  *     *
   *   *
    * *
     *
    * *
   *   *
  *     *
 *       *
* * * * * *
 */

import java.util.Scanner;

public class Pattern20 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        // top
        for (row = 1; row <= n; row++) {
            // loop for left space
            for (col = 1; col <= row - 1; col++) {
                System.out.print(" ");
            }
            for (col = row; col <= n; col++) {
                if (col == row || col == n || row == 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");  // Double space
                }
            }
            System.out.println();
        }
        // down
        for (row = n - 1; row >= 1; row--) {
            // loop for left space
            for (col = 1; col <= row - 1; col++) {
                System.out.print(" ");
            }
            for (col = row; col <= n; col++) {
                if (col == row || col == n || row == 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("  "); // Double space
                }
            }
            System.out.println();
        }
    }
}