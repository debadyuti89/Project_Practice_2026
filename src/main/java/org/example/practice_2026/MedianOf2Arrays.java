public class MedianOf2Arrays {

	public static void main(String[] args) {
		int[] nums1 = {1, 3};
		int[] nums2 = {4, 5, 9};
		System.out.println(findMedianSortedArrays(nums1, nums2));
	}

	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int i = 0, j = 0;
		int index = 0;
		int[] output = new int[nums1.length + nums2.length];

		while (i < nums1.length && j < nums2.length) {
			if(nums1[i] < nums2[j]) {
				output[index++] = nums1[i++];
			}else {
				output[index++] = nums2[j++];
			}
		}
		
		while(i < nums1.length) {
			output[index++] = nums1[i++];
		}
		
		while(j < nums2.length) {
			output[index++] = nums2[j++];
		}
		
		int mid = output.length / 2;
		if(output.length % 2 == 0) {
			double median = (output[mid] + output[mid - 1]) / 2.0;
			return median;
		}else {
			return output[mid];
		}
	}
}
