package org.example.Mphasis;

public class secondMinimumNumberArray2 {

	public static void main(String[] args) {
		int[] arr = { 12, 13, 1, 10, 34, 1 };
		System.out.println(secondMinimum(arr));
	}

	static int secondMinimum(int[] arr) {
		int first, second;
		first = second = Integer.MAX_VALUE;
		if (arr == null || arr.length < 2)
			return -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < first) {
				second = first;
				first = arr[i];
			} else if (arr[i] < second && arr[i] != first)
				second = arr[i];
		}
		return second;
	}
}