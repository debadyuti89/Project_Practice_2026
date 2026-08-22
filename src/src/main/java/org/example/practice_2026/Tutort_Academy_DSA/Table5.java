package Tutort_Academy_DSA;

import java.util.Scanner;

public class Table5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter number to create table:");
        int n = input.nextInt();
        int counter = 0;
        do {
            System.out.print(n + " * " + counter + " = ");
            System.out.print(5 * counter);
            System.out.println();
            counter++;
        } while (counter <= 10);
    }
}
