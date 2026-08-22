package org.example.Arrays;

public class ReverseArray {

	public static void main(String[] args) {
		int arr[] = {1, 2, 3, 4, 6, 5};
		
		printArray(arr, arr.length);
		System.out.println("Reverse Array");
		reverseArray(arr);
		printArray(arr, arr.length);
	}

	private static void reverseArray(int[] arr) {
		int temp;
		
		for(int start = 0, end = arr.length-1; start < end; start++, end--) {
			temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
		}
	}

	private static void printArray(int[] arr, int length) {
		for(int i=0; i<arr.length; i++)
			System.out.print(arr[i]+" ");
		System.out.println();
	}

}
