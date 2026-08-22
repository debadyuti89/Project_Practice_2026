package Tutort_Academy_DSA;

import java.util.Scanner;

//Problem 2 :  Check couple is eligible for marriage (girl age should be 18 boy should be 21) print yes or no

public class EligibleForMarriage {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter girl's age:");
        int girlAge = input.nextInt();
        System.out.println("Enter boy's age:");
        int boyAge = input.nextInt();

        if (girlAge>=18 && boyAge>=21) {
            System.out.println("They are eligible for marriage.");
        } else {
            System.out.println("They are not eligible for marriage.");
        }
    }
}
