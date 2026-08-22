package Tutort_Academy_DSA;

import java.util.Scanner;

//Problem 6 :  Find max of 3 elements
public class MaximumNumber {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 3 numbers");
        int num1 = input.nextInt();
        int num2 = input.nextInt();
        int num3 = input.nextInt();

        if (num1 > num2 && num1 > num3) {
            System.out.println("The largest number is: " + num1);
        } else if (num2 > num1 && num2 > num3) {
            System.out.println("The largest number is: " + num2);
        } else if (num3 > num1 && num3 > num2) {
            System.out.println("The largest number is: " + num3);
        } else {
            System.out.println("Not able to find the largest number as num1: "
                    + num1 + ", num2: " + num2 + ", num3: " + num3);
        }
    }
}
