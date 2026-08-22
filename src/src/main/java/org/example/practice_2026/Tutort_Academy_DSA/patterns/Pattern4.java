package Tutort_Academy_DSA.patterns;

/*
Pattern 4

1 2 3 4 5 6
1 2 3 4 5
1 2 3 4
1 2 3
1 2
1

*/

import java.util.Scanner;

public class Pattern4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col;
        for (row = n; row >= 1; row--) {
            for (col = 1; col <= row; col++) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }
}