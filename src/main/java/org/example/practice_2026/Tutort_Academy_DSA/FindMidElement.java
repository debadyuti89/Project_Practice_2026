package Tutort_Academy_DSA;

import java.util.Scanner;

//Problem 7 :  Find mid elements out of 3 elements.
public class FindMidElement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter 3 numbers");
        int num1 = input.nextInt();
        int num2 = input.nextInt();
        int num3 = input.nextInt();

        if (num1 > num2) {
            if (num2 > num3) {
                System.out.println("Mid element is " + num2);
            } else if (num1 > num3) {
                System.out.println("Mid element is " + num3);
            } else {
                System.out.println("Mid element is " + num1);
            }
        } else {
            if (num1 > num3) {
                System.out.println("Mid element is " + num1);
            } else if (num2 > num3) {
                System.out.println("Mid element is " + num3);
            } else {
                System.out.println("Mid element is " + num2);
            }
        }
    }
}