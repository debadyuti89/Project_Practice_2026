package Tutort_Academy_DSA;

import java.util.Scanner;

//Problem 5 :  Check number is odd or not, print odd or even.
public class EvenOdd {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number to check even or odd.");
        int num = input.nextInt();
        if (num % 2 == 0) {
            System.out.println("The number " + num + " is even.");
        } else {
            System.out.println("The number " + num + " is odd.");
        }
    }
}
