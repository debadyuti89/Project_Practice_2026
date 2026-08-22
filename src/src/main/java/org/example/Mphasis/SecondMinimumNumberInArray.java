package org.example.Mphasis;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SecondMinimumNumberInArray implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		int value = o1.compareTo(o2);

		// elements are sorted in reverse order
		if (value > 0) {
			return -1;
		} else if (value < 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public static int getSecondSmallest_optimized(int[] arr) {

		if (arr.length < 2) {
			// System.out.println("Invalid Input");
			return -2;
		}

		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new SecondMinimumNumberInArray());
		for (int i = 0; i < arr.length; ++i) {
			pq.add(arr[i]);

			if (pq.size() > 2) {
				pq.poll();
			}
		}

		// Print the 2nd smallest element
		// System.out.print(heap1.peek() +"\n");
		return pq.peek();
	}
	/*
	 * public static int getSecondSmallest_brute_force(int[] arr) { int temp = 0;
	 * int length = arr.length; if(length < 2) {
	 * //System.out.println("Invalid Input"); return -2; } // ascending order. for
	 * (int i = 0; i < length; i++) { for (int j = i + 1; j < length; j++) { if
	 * (arr[i] > arr[j]) { temp = arr[i]; arr[i] = arr[j]; arr[j] = temp; } } }
	 * return arr[1]; // 2nd element because index starts from 0 }
	 */

	public static void main(String[] args) {
		int arr[] = { 1, 2, 5, 6, 3, 1 }; /*{ 9, 3, 5, 8, 4, 7, 8 };*/ // 

		if (/* getSecondSmallest_brute_force(arr) != -2 && */getSecondSmallest_optimized(arr) != -2) {
			// System.out.println("Second Minimum Number: " +
			// getSecondSmallest_brute_force(arr));
			System.out.println("Optimized Second Minimum Number: " + getSecondSmallest_optimized(arr));
		} else {
			System.out.println("Invalid Input");
		}
	}

}
