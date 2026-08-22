package org.example.Preperation_2026;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/problems/course-schedule/
public class TopologicalSort {
//-----------------------------------Topological Sort Algorithm-------------------------------------------
/**
    private boolean hasCycle(List<List<Integer>> graph, int[] vis, int index) {
        if (vis[index] == 1) {
            return true;
        }
        if (vis[index] == 2) {
            return false;
        }
        vis[index] = 1;
        for (int n : graph.get(index)) {
            if (hasCycle(graph, vis, n)) {
                return true;
            }
        }
        vis[index] = 2;
        return false;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] p : prerequisites) {
            graph.get(p[1]).add(p[0]); // think
        }
        int[] vis = new int[numCourses];

        //   0 --> unvisited
        //   1 --> visiting in active dfs
        //   2 --> fully processed, visited

        for (int i = 0; i < numCourses; i++) {
            // if we have cycle or we are not able ro handle prereq, we will return false
            if (hasCycle(graph, vis, i)) {
                return false;
            }
        }
        return true;
    }
**/
//-----------------------------------Khan’s Algorithm-------------------------------------------
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1. Initialize the graph structure
        List<List<Integer>> adj = createAdjacencyList(numCourses);
        int[] prerequisiteCounts = new int[numCourses];

        // 2. Build graph and compute prerequisite counts
        buildGraph(prerequisites, adj, prerequisiteCounts);

        // 3. Find starting nodes (courses with no prerequisites)
        Queue<Integer> queue = getStarterCourses(numCourses, prerequisiteCounts);

        // 4. Run the topological sort traversal
        int visitedCourses = processTopologicalSort(queue, adj, prerequisiteCounts);

        // 5. Verify if all courses were successfully processed
        return visitedCourses == numCourses;
    }

    // Helper 1: Instantiates the empty adjacency list
    private List<List<Integer>> createAdjacencyList(int numCourses) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        return adj;
    }

    // Helper 2: Populates the graph connections and counts prerequisite locks
    private void buildGraph(int[][] prerequisites, List<List<Integer>> adj, int[] prerequisiteCounts) {
        for (int[] edge : prerequisites) {
            int course = edge[0];
            int prereq = edge[1];
            adj.get(prereq).add(course);
            prerequisiteCounts[course]++;
        }
    }

    // Helper 3: Collects all initial independent entry points
    private Queue<Integer> getStarterCourses(int numCourses, int[] prerequisiteCounts) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (prerequisiteCounts[i] == 0) {
                queue.offer(i);
            }
        }
        return queue;
    }

    // Helper 4: Core BFS processing loop that resolves dependencies
    private int processTopologicalSort(Queue<Integer> queue, List<List<Integer>> adj, int[] prerequisiteCounts) {
        int count = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            count++;

            for (int neighbor : adj.get(curr)) {
                prerequisiteCounts[neighbor]--;
                int remainingPrereqs = prerequisiteCounts[neighbor];

                if (remainingPrereqs == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return count;
    }

    // Main method containing various test cases
    public static void main(String[] args) {
        TopologicalSort solver = new TopologicalSort();

        System.out.println("--- Running Course Schedule Test Cases ---\n");

        // Case 1: Simple valid linear path (Take 0 before 1)
        int numCourses1 = 2;
        int[][] prerequisites1 = {{1, 0}};
        boolean result1 = solver.canFinish(numCourses1, prerequisites1);
        System.out.println("Test Case 1 (Simple Linear Path):");
        System.out.println("Expected: true | Actual: " + result1 + "\n");

        // Case 2: Simple cyclic block (0 needs 1, 1 needs 0)
        int numCourses2 = 2;
        int[][] prerequisites2 = {{1, 0}, {0, 1}};
        boolean result2 = solver.canFinish(numCourses2, prerequisites2);
        System.out.println("Test Case 2 (Simple Cycle Loop):");
        System.out.println("Expected: false | Actual: " + result2 + "\n");

        // Case 3: Completely independent courses (No requirements at all)
        int numCourses3 = 3;
        int[][] prerequisites3 = {};
        boolean result3 = solver.canFinish(numCourses3, prerequisites3);
        System.out.println("Test Case 3 (No Prerequisites Exist):");
        System.out.println("Expected: true | Actual: " + result3 + "\n");

        // Case 4: Complex Valid DAG (Directed Acyclic Graph)
        // 0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3
        int numCourses4 = 4;
        int[][] prerequisites4 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        boolean result4 = solver.canFinish(numCourses4, prerequisites4);
        System.out.println("Test Case 4 (Complex Multi-dependency Path):");
        System.out.println("Expected: true | Actual: " + result4 + "\n");

        // Case 5: Complex graph containing a hidden cycle
        // 0 -> 1, 1 -> 2, 2 -> 3, 3 -> 1 (Cycle between 1, 2, 3)
        int numCourses5 = 4;
        int[][] prerequisites5 = {{1, 0}, {2, 1}, {3, 2}, {1, 3}};
        boolean result5 = solver.canFinish(numCourses5, prerequisites5);
        System.out.println("Test Case 5 (Complex Path with Hidden Cycle):");
        System.out.println("Expected: false | Actual: " + result5 + "\n");

        // Case 6: Self-loop edge (Course 0 requires Course 0)
        int numCourses6 = 1;
        int[][] prerequisites6 = {{0, 0}};
        boolean result6 = solver.canFinish(numCourses6, prerequisites6);
        System.out.println("Test Case 6 (Course Requires Itself):");
        System.out.println("Expected: false | Actual: " + result6 + "\n");
    }
}
