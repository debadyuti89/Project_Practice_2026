package Tutort_Academy_DSA.patterns;

/*
Pattern 13
******
 *****
  ****
   ***
    **
     *
*/

import java.util.Scanner;

public class Pattern13 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = n; row >= 1; row--) {
            for (col = 1; col <= n - row; col++) {
                System.out.print(" ");
            }
            for (col = 1; col <= row; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
