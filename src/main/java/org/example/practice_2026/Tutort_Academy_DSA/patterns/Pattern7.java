package Tutort_Academy_DSA.patterns;

/*
Pattern 7
     *
    ***
   *****
  *******
 *********
***********
 *********
  *******
   *****
    ***
     *
*/

import java.util.Scanner;

public class Pattern7 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= n - row; col++) {
                System.out.print(" ");
            }
            for (col = 1; col <= 2 * row - 1; col++) {
                System.out.print("*");
            }
            System.out.println();
        }

        for (row = n-1; row >= 1; row--) {
            for (col = 1; col <= n - row; col++) {
                System.out.print(" ");
            }
            for (col = 1; col <= 2 * row - 1; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
