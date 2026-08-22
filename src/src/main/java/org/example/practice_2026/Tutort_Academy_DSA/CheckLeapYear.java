package Tutort_Academy_DSA;

import java.util.Scanner;
//Problem 4 :  Check if the year is leap or not.

public class CheckLeapYear {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the year:");
        int year = input.nextInt();
        boolean flag = false;
        if (year % 4 == 0) {
            flag = true;
        }
        if ((year % 100 == 0) && (year % 400 == 0)) {
            flag = true;
        }
        if (flag) {
            System.out.println("The year " + year + " is leap year.");
        } else {
            System.out.println("The year " + year + " is not leap year.");
        }
    }
}
