package org.example.Preperation_2026;

import java.util.*;

// https://leetcode.com/problems/network-delay-time/
public class NetworkDelayTime {
    // Main method with test cases
    public static void main(String[] args) {
        NetworkDelayTime solver = new NetworkDelayTime();

        // Test Case 1: Standard case where everyone is reachable
        int[][] times1 = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        int n1 = 4;
        int k1 = 2;
        int result1 = solver.networkDelayTime(times1, n1, k1);
        System.out.println("Test Case 1 Result: " + result1 + " (Expected: 2)");

        // Test Case 2: One node is completely disconnected/unreachable
        int[][] times2 = {{1, 2, 1}};
        int n2 = 2;
        int k2 = 2;
        int result2 = solver.networkDelayTime(times2, n2, k2);
        System.out.println("Test Case 2 Result: " + result2 + " (Expected: -1)");

        // Test Case 3: Only 1 node exists in the network
        int[][] times3 = {{1, 2, 1}}; // Edge exists but we only care about node 1 reaching itself
        int n3 = 1;
        int k3 = 1;
        int result3 = solver.networkDelayTime(times3, n3, k3);
        System.out.println("Test Case 3 Result: " + result3 + " (Expected: 0)");
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        // 1. Build the network
        Map<Integer, List<int[]>> graph = buildGraph(times);

        // 2. Track the shortest time it takes to reach each person
        int[] minTime = new int[n + 1];
        Arrays.fill(minTime, Integer.MAX_VALUE);
        minTime[k] = 0;

        // 3. Process the graph using the Dijkstra helper method
        dijkstraAlgorithm(graph, minTime, k);

        // 4. Find out who got the message last
        int totalTime = 0;
        for (int i = 1; i <= n; i++) {
            if (minTime[i] == Integer.MAX_VALUE) {
                return -1;
            }
            totalTime = Math.max(totalTime, minTime[i]);
        }

        return totalTime;
    }

    // Helper method 1: Converts the edge array into an adjacency list graph
    private Map<Integer, List<int[]>> buildGraph(int[][] times) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] edge : times) {
            int source = edge[0];
            int target = edge[1];
            int weight = edge[2];
            graph.computeIfAbsent(source, x -> new ArrayList<>()).add(new int[]{target, weight});
        }
        return graph;
    }

    // Helper method 2: Runs Dijkstra's Algorithm to update minimum arrival times
    private void dijkstraAlgorithm(Map<Integer, List<int[]>> graph, int[] minTime, int startNode) {
        // Format: {currentNode, totalTimeTakenSoFar}
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        queue.offer(new int[]{startNode, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int u = current[0];
            int time = current[1];

            if (time > minTime[u]) continue;

            if (graph.containsKey(u)) {
                for (int[] neighbor : graph.get(u)) {
                    int v = neighbor[0];
                    int travelTime = neighbor[1];

                    if (time + travelTime < minTime[v]) {
                        minTime[v] = time + travelTime;
                        queue.offer(new int[]{v, minTime[v]});
                    }
                }
            }
        }
    }
}
