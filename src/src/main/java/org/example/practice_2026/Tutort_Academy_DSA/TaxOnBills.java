package Tutort_Academy_DSA;

import java.util.Scanner;
//Problem 3: Print tax amount if bill amount is 50000 above then tax is 10% else 5% , using ternary operator.

public class TaxOnBills {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the bill amount:");
        double amount = input.nextInt();
        double taxAmount = amount > 50000 ? ((amount * 10) / 100) : ((amount * 5) / 100);
        System.out.println("Taxable amount is: " + taxAmount);
    }
}