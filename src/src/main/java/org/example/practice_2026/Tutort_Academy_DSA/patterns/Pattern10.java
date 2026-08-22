package Tutort_Academy_DSA.patterns;

/*

Pattern 10
*
**
***
****
*****
******

*/
import java.util.Scanner;

public class Pattern10 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of rows to print:");
        int n = input.nextInt();

        int row, col, k = 1;
        for (row = 1; row <= n; row++) {
            for (col = 1; col <= row; col++) {
                System.out.print("*");
                k++;
            }
            System.out.println();
        }
    }
}
