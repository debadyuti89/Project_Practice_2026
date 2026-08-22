package org.example;

import java.util.Arrays;

//https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
public class FloydWarshallAlgorithm {
    public static void main(String[] args) {
        FloydWarshallAlgorithm solver = new FloydWarshallAlgorithm();

        // --- Test Case 1 ---
        int n1 = 4;
        int[][] edges1 = {
                {0, 1, 3},
                {1, 2, 1},
                {1, 3, 4},
                {2, 3, 1}
        };
        int distanceThreshold1 = 4;
        int result1 = solver.findTheCity(n1, edges1, distanceThreshold1);
        System.out.println("Test Case 1 Output: " + result1); // Expected: 3

        // --- Test Case 2 ---
        int n2 = 5;
        int[][] edges2 = {
                {0, 1, 2},
                {0, 4, 8},
                {1, 2, 3},
                {1, 4, 2},
                {2, 3, 1},
                {3, 4, 1}
        };
        int distanceThreshold2 = 2;
        int result2 = solver.findTheCity(n2, edges2, distanceThreshold2);
        System.out.println("Test Case 2 Output: " + result2); // Expected: 0
    }

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Step 1: Create and set up the distance matrix
        int[][] dist = createInitialDistanceMatrix(n, edges);

        // Step 2: Calculate the shortest path between every single city
        floydAlgorithm(n, dist);

        // Step 3: Find the best city based on the rules
        return getBestCity(n, dist, distanceThreshold);
    }

    // Helper 1: Sets up the initial map of distances between directly connected cities
    private int[][] createInitialDistanceMatrix(int n, int[][] edges) {
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], 10001);
            dist[i][i] = 0; // Distance to itself is always 0
        }

        for (int[] edge : edges) {
            int cityA = edge[0];
            int cityB = edge[1];
            int weight = edge[2];
            dist[cityA][cityB] = weight;
            dist[cityB][cityA] = weight; // Roads are bidirectional
        }

        return dist;
    }

    // Helper 2: Uses Floyd-Warshall algorithm to find all-pairs shortest paths
    private void floydAlgorithm(int n, int[][] dist) {
        for (int via = 0; via < n; via++) {
            for (int from = 0; from < n; from++) {
                for (int to = 0; to < n; to++) {
                    dist[from][to] = Math.min(dist[from][to], dist[from][via] + dist[via][to]);
                }
            }
        }
    }

    // Helper 3: Finds the city with the fewest neighbors (breaks ties with the larger ID)
    private int getBestCity(int n, int[][] dist, int distanceThreshold) {
        int bestCity = -1;
        int smallestCount = n;

        for (int i = 0; i < n; i++) {
            int reachableCount = countReachableNeighbors(i, n, dist, distanceThreshold);

            // If it has fewer neighbors, or equal neighbors but a higher city ID
            if (reachableCount <= smallestCount) {
                smallestCount = reachableCount;
                bestCity = i;
            }
        }

        return bestCity;
    }

    // Helper 4: Counts how many cities can be reached from a starting city within the limit
    private int countReachableNeighbors(int fromCity, int n, int[][] dist, int threshold) {
        int count = 0;
        for (int toCity = 0; toCity < n; toCity++) {
            if (fromCity != toCity && dist[fromCity][toCity] <= threshold) {
                count++;
            }
        }
        return count;
    }
}
