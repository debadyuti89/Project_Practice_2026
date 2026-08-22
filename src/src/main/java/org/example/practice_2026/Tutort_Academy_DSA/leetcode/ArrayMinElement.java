package Tutort_Academy_DSA.leetcode;

import java.util.*;

public class ArrayMinElement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of elements you want to insert into array:");
        int n = input.nextInt();
        int[] inputArray = new int[n];
        System.out.println("Enter the " + n + " numbers into array:");
        for (int i = 0; i < n; i++) {
            inputArray[i] = input.nextInt();
        }

        int minNumber = findMinNumber(inputArray);


//        System.out.println("Tie minimum number in array is: " + minNumber);
        System.out.println("Tie minimum number in array present in index: " + minNumber);
    }

    private static int findMinNumber(int[] inputArray) {
        int minNumber = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i<inputArray.length; i++) {
            if (minNumber >= inputArray[i]) {
                minNumber = inputArray[i];
                index = i;
            }
        }

        return index;
    }
}
