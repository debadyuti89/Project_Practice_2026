import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WinnerCircle {

	public static void main(String[] args) {
		System.out.println(findTheWinner(5, 5));
		System.out.println(findTheWinner_2(5, 5));
	}

	private static int findTheWinner(int n, int k) {
		List<Integer> list = new ArrayList<>();
		for (int i = 1; i <= n; i++)
			list.add(i);

		int index = 0;
		while (list.size() > 1) {
			index += k - 1;
			if (index >= list.size()) {
				index = index % list.size();
			}
			list.remove(list.get(index));
		}
		return list.get(0);
	}

	private static int findTheWinner_2(int n, int k) {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 1; i <= n; i++)
			queue.offer(i);
		
		while (queue.size() > 1) {
			for(int j = 1; j <= k-1; j++)
				queue.offer(queue.poll());
			queue.poll();
		}
		return queue.poll();
	}
}
