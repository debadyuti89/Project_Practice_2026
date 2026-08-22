package Tutort_Academy_DSA.patterns;


/*
Pattern 6
1
0 1
1 0 1
0 1 0 1
1 0 1 0 1
0 1 0 1 0 1
*/

import java.util.Scanner;

public class Pattern6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= row; col++) {
                if ((row + col) % 2 == 0) {
                    System.out.print(1 + " ");
                }
                else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }
}
