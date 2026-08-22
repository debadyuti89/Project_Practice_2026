package org.example.Arrays;

import java.util.Arrays;

public class MinMaxOfAnArray {

	static class Pair{
		int min;
		int max;
	}
	
	public static void main(String[] args) {
		int arr[] = {755, 555, 2, 666, 5, 999};
		Arrays.sort(arr);
		System.out.println(arr[0]+" "+arr[arr.length-1]);
	//	Pair minmax = getMinMax(arr, arr.length);
		
	//	System.out.println("Minimum element is "+ minmax.min);
    //    System.out.println("Maximum element is "+ minmax.max);
 
	}

	private static Pair getMinMax(int[] arr, int length) {
		Pair minmax = new Pair();
		
		/*If there is only one element then return it as min and max both*/
		if(length == 1) {
			minmax.min = arr[0];
			minmax.max = arr[0];
		}
		
		/* If there are more than one elements, then initialize min
	    and max*/
		
		if (arr[0] > arr[1]) {
            minmax.max = arr[0];
            minmax.min = arr[1];
        } else {
            minmax.max = arr[1];
            minmax.min = arr[0];
        }
		
		
		
		
		return null;
	}

}
