package Tutort_Academy_DSA.patterns;

/*
Pattern 5
1
2 3
4 5 6
7 8 9 10
11 12 13 14 15
16 17 18 19 20 21
*/

import java.util.Scanner;

public class Pattern5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col, k = 1;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= row; col++) {
                System.out.print(k + " ");
                k++;
            }
            System.out.println();
        }
    }
}
