package Tutort_Academy_DSA;

import java.util.*;
//Problem 1 :  Find Minimum of three numbers

public class MinimumNumbers {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 3 numbers");
        int num1 = input.nextInt();
        int num2 = input.nextInt();
        int num3 = input.nextInt();

        if (num1 < num2 && num1 < num3) {
            System.out.println("The smallest number is: " + num1);
        } else if (num2 < num1 && num2 < num3) {
            System.out.println("The smallest number is: " + num2);
        } else if (num3 < num1 && num3 < num2) {
            System.out.println("The smallest number is: " + num3);
        } else {
            System.out.println("Not able to find the smallest number as num1: "
                    + num1 + ", num2: " + num2 + ", num3: " + num3);
        }
    }
}