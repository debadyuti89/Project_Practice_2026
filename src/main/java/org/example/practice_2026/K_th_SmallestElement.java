/*
Expected Approach
Intuition
Idea is to use quick select algorithm which uses a random partition and recursion to efficiently find the kth smallest element in an array. The pivot position is used to determine whether to search in the left or right half of the array.

Dry Run:
arr = [10, 4, 5, 8, 6, 11, 26]

k = 3

1. First Call: kthSmallest(arr, 0, 6, 3)
        randomPartition(arr, 0, 6) is called:
            The pivot element is randomly selected. Suppose pivot = 4.
            Swap arr[l + pivot] with arr[r]: Swap arr[0 + 4] (which is 6) with arr[6] (which is 26).
            Array after swap: [10, 4, 5, 8, 26, 11, 6]
            Now, call partition(arr, 0, 6).
2. First Call to partition(arr, 0, 6):
        Pivot: x = arr[r] = 6
        Initial i = 0
        Iterate through j from 0 to 5:
            j=0: arr[j]=10, no swap as arr[j] > pivot.
            j=1: arr[j]=4, swap arr[i] with arr[j]. Now i=1, arr=[4, 10, 5, 8, 26, 11, 6].
            j=2: arr[j]=5, swap arr[i] with arr[j]. Now i=2, arr=[4, 5, 10, 8, 26, 11, 6].
            j=3: arr[j]=8, no swap as arr[j] > pivot.
            j=4: arr[j]=26, no swap as arr[j] > pivot.
            j=5: arr[j]=11, no swap as arr[j] > pivot.
        Final Swap: Swap arr[i] with arr[r]. Now arr=[4, 5, 6, 8, 26, 11, 10], i=2.
        Return i=2 as the position of the pivot.
3. Back to kthSmallest(arr, 0, 6, 3)
        pos = 2
        Check if pos-l == k-1:
            pos-l = 2-0 = 2
            k-1 = 3-1 = 2
        Condition satisfied: The pivot position is the 3rd smallest element, so return arr[pos] = 6.

Result:
The 3rd smallest element in the array [10, 4, 5, 8, 6, 11, 26] is 6.

Complexity:
** Time Complexity: O(n*logn)
----------------------------
    The time complexity of the quick select algorithm is on average O(n*logn), where 'n' is the number of elements in the array.
The randomization in choosing the pivot helps achieve this average time complexity.
However, in the worst case, the time complexity can degrade to O(n^2), but this is rare and occurs when consistently bad pivots are chosen.

** Space Complexity: O(logn)
----------------------------
    The space complexity is O(log n) on average due to the recursive calls in the call stack.
In the worst case, the space complexity can be O(n) if the recursion tree is skewed, but again, this is uncommon.
*/
public class K_th_SmallestElement {
    public static int kthSmallest(int[] arr, int k) {
        int max_element = arr[0];
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            if (arr[i] > max_element) {
                max_element = arr[i];
            }
        }

        // Create an array to store the frequency of each element in the input array
        int[] freq = new int[max_element + 1];
        for (int i = 0; i < n; i++) {
            freq[arr[i]]++;
        }

        // Keep track of the cumulative frequency of elements in the input array
        int count = 0;
        for (int i = 0; i <= max_element; i++) {
            if (freq[i] != 0) {
                count += freq[i];
                if (count >= k) {
                    // If we have seen k or more elements, return the current element
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args)
    {
        int[] A = { 10, 4, 5, 8, 6, 11, 26 };
        int K = 3;
        System.out.println("Minimum element is: " + kthSmallest(A, K));

    }
}
