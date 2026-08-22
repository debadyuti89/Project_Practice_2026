package Tutort_Academy_DSA.patterns;

import java.util.Scanner;

/*
Pattern 3

     ******
    ******
   ******
  ******
 ******
******

*/

public class Pattern3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= n - row; col++) {
                System.out.print(" ");
            }
            for (col = 1; col <= n; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
