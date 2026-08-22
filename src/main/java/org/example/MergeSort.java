package org.example;

import java.util.Arrays;

public class MergeSort {

    // Main method to execute the sort
    public static void mergeSort(int[] array) {
        if (array == null || array.length < 2) {
            return; // Base case: Array is already sorted
        }
        sort(array, 0, array.length - 1);
    }

    // Divide phase
    private static void sort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2; // Avoids integer overflow

            sort(array, left, mid);      // Sort left half
            sort(array, mid + 1, right);  // Sort right half

            merge(array, left, mid, right); // Combine sorted halves
        }
    }

    // Conquer & Merge phase
    private static void merge(int[] array, int left, int mid, int right) {
        // Calculate sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary auxiliary holding arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, mid + 1, rightArray, 0, n2);

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of the merged subarray
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray[] if any
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy remaining elements of rightArray[] if any
        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Example Usage
    public static void main(String[] args) {
        int[] data = {12, 11, 13, 5, 6, 7};
        System.out.println("Original Array: " + Arrays.toString(data));

        mergeSort(data);

        System.out.println("Sorted Array:   " + Arrays.toString(data));
    }
}

