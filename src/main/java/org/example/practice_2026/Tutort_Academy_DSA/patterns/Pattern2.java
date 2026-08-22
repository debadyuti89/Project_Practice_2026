package Tutort_Academy_DSA.patterns;

import java.util.Scanner;

/*
Pattern 2

         1
        2 2
       3 3 3
      4 4 4 4
     5 5 5 5 5
    6 6 6 6 6 6
*/

public class Pattern2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= n - row; col++) {
                System.out.print(" ");
            }
            for (col = 1; col <= row; col++) {
                System.out.print(row + " ");
            }
            System.out.println();
        }
    }
}
