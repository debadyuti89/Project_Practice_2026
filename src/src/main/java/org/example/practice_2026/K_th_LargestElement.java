import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/*
[Expected Approach] Using Priority Queue(Min-Heap)
    The idea is, as we iterate through the array, we keep track of the k largest elements at each step. To do this, we use a min-heap.
First, we insert the initial k elements into the min-heap. After that, for each next element, we compare it with the top of the heap.
Since the top element of the min-heap is the smallest among the k elements, if the current element is larger than the top,
it means the top element is no longer one of the k largest elements. In this case, we remove the top and insert the larger element.
After completing the entire traversal, the heap will contain exactly the k largest elements of the array.

Input:
int[] arr = {1, 23, 12, 9, 30, 2, 50};
int k = 3;

Output:
50 30 23

Time Complexity: O(n * log k), this solution can work in O(k + (n-k) Log K) as build heap take linear time.
Auxiliary Space: O(k)

*/
public class K_th_LargestElement {
    // Function to find the k largest elements in the array
    static ArrayList<Integer> kLargest(int[] arr, int k) {

        // Min-heap to store the k largest elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        // Add first k elements to the heap
        for (int i = 0; i < k; i++) {
            minHeap.add(arr[i]);
        }

        // Traverse the rest of the array
        for (int i = k; i < arr.length; i++) {

            // If current element is larger than
            // the smallest in heap
            if (arr[i] > minHeap.peek()) {
                minHeap.poll();
                minHeap.add(arr[i]);
            }
        }

        // Extract elements from the heap
        ArrayList<Integer> res = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            res.add(minHeap.poll());
        }

        // Reverse the list for descending order
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1, 23, 12, 9, 30, 2, 50};
        int k = 3;

        ArrayList<Integer> res = kLargest(arr, k);
        for (int ele : res) {
            System.out.print(ele + " ");
        }
    }
}
