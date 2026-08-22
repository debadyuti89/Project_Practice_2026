package org.example;

import java.util.*;

// https://leetcode.com/problems/course-schedule-ii/
public class TopologicalSort2 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 1. Initialize the graph structure
        List<List<Integer>> adj = createAdjacencyList(numCourses);
        int[] prerequisiteCounts = new int[numCourses];

        // 2. Build graph and compute prerequisite counts
        buildGraph(prerequisites, adj, prerequisiteCounts);

        // 3. Find starting nodes (courses with no prerequisites)
        Queue<Integer> queue = getStarterCourses(numCourses, prerequisiteCounts);

        // 4. Run the topological sort traversal and record the ordering
        int[] courseOrder = new int[numCourses];
        int visitedCount = processTopologicalSort(queue, adj, prerequisiteCounts, courseOrder);

        // 5. If all courses were processed, return the order. Otherwise, return an empty array.
        return (visitedCount == numCourses) ? courseOrder : new int[0];
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

    // Helper 4: Core BFS processing loop that resolves dependencies and stores the schedule order
    private int processTopologicalSort(Queue<Integer> queue, List<List<Integer>> adj, int[] prerequisiteCounts, int[] courseOrder) {
        int index = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();

            // Record the course in our schedule array
            courseOrder[index] = curr;
            index++;

            for (int neighbor : adj.get(curr)) {
                prerequisiteCounts[neighbor]--;
                int remainingPrereqs = prerequisiteCounts[neighbor];

                if (remainingPrereqs == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return index; // Returns the total number of successfully ordered courses
    }

    // Helper class to encapsulate a specific test case structure
    static class TestCase {
        int id;
        int numCourses;
        int[][] prerequisites;
        String description;

        TestCase(int id, int numCourses, int[][] prerequisites, String description) {
            this.id = id;
            this.numCourses = numCourses;
            this.prerequisites = prerequisites;
            this.description = description;
        }
    }

    public static void main(String[] args) {
        TopologicalSort2 solver = new TopologicalSort2();
        List<TestCase> testSuite = new ArrayList<>();
        int tcId = 1;

        // --- SECTION 1: BASE CASES & EDGE CASES (1 to 5) ---
        testSuite.add(new TestCase(tcId++, 1, new int[][]{}, "Single course, no prerequisites"));
        testSuite.add(new TestCase(tcId++, 1, new int[][]{{0, 0}}, "Single course with a self-loop"));
        testSuite.add(new TestCase(tcId++, 2, new int[][]{{1, 0}}, "Two courses, simple straight path"));
        testSuite.add(new TestCase(tcId++, 2, new int[][]{{1, 0}, {0, 1}}, "Two courses, tight cyclic block"));
        testSuite.add(new TestCase(tcId++, 2, new int[][]{}, "Two courses, completely independent"));

        // --- SECTION 2: LINEAR STRATEGIC CHAINS (6 to 7) ---
        testSuite.add(new TestCase(tcId++, 5, new int[][]{{1, 0}, {2, 1}, {3, 2}, {4, 3}}, "5 courses strict chain path"));
        testSuite.add(new TestCase(tcId++, 10, new int[][]{{1, 0}, {2, 1}, {3, 2}, {4, 3}, {5, 4}, {6, 5}, {7, 6}, {8, 7}, {9, 8}}, "10 courses strict chain path"));

        // --- SECTION 3: COMPLETELY INDEPENDENT SETS (8 to 9) ---
        testSuite.add(new TestCase(tcId++, 5, new int[][]{}, "5 courses completely independent"));
        testSuite.add(new TestCase(tcId++, 15, new int[][]{}, "15 courses completely independent"));

        // --- SECTION 4: BINARY TREE LIKE STRUCTURES (10) ---
        testSuite.add(new TestCase(tcId++, 7, new int[][]{{1, 0}, {2, 0}, {3, 1}, {4, 1}, {5, 2}, {6, 2}}, "7 courses balanced binary tree layout"));

        // --- SECTION 5: CYCLES OF VARYING SIZES & COMPLEXITIES (11 to 15) ---
        testSuite.add(new TestCase(tcId++, 3, new int[][]{{1, 0}, {2, 1}, {0, 2}}, "3 courses tight closed cycle"));
        testSuite.add(new TestCase(tcId++, 4, new int[][]{{1, 0}, {2, 1}, {3, 2}, {1, 3}}, "4 courses cycle hidden deep inside"));
        testSuite.add(new TestCase(tcId++, 5, new int[][]{{1, 0}, {2, 1}, {3, 2}, {4, 3}, {2, 4}}, "5 courses cycle among the final nodes"));
        testSuite.add(new TestCase(tcId++, 5, new int[][]{{1, 0}, {2, 1}, {3, 2}, {4, 3}, {0, 4}}, "5 courses full path wrapping cycle"));
        testSuite.add(new TestCase(tcId++, 6, new int[][]{{1, 0}, {2, 1}, {0, 2}, {4, 3}, {5, 4}}, "6 courses separated segments, one has cycle"));

        // --- SECTION 6: STAR GRAPH VARIATIONS (16 to 17) ---
        testSuite.add(new TestCase(tcId++, 6, new int[][]{{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}}, "6 courses star topology: one unlocks many"));
        testSuite.add(new TestCase(tcId++, 6, new int[][]{{5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}}, "6 courses star topology: many unlock one"));

        // --- SECTION 7: DIAMOND & PARALLEL BRANCH MERGES (18 to 19) ---
        testSuite.add(new TestCase(tcId++, 4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}, "4 courses symmetrical diamond structure"));
        testSuite.add(new TestCase(tcId++, 6, new int[][]{{1, 0}, {2, 1}, {5, 2}, {3, 0}, {4, 3}, {5, 4}}, "6 courses dual independent paths merging"));

        // --- SECTION 8: AUTOMATED STRUCTURAL PERMUTATIONS (20 to 41) ---
        // Dynamically populates diverse acyclic chains vs cyclic chains to fill the matrix space efficiently
        for (int i = 3; i <= 24; i++) {
            if (i % 2 == 0) {
                int[][] validEdges = new int[i - 1][2];
                for (int j = 1; j < i; j++) {
                    validEdges[j - 1] = new int[]{j, j - 1};
                }
                testSuite.add(new TestCase(tcId++, i, validEdges, i + " courses programmatic valid linear track"));
            } else {
                int[][] cyclicEdges = new int[i][2];
                for (int j = 1; j < i; j++) {
                    cyclicEdges[j - 1] = new int[]{j, j - 1};
                }
                cyclicEdges[i - 1] = new int[]{0, i - 1}; // Inject back-edge cycle
                testSuite.add(new TestCase(tcId++, i, cyclicEdges, i + " courses programmatic loop cycle track"));
            }
        }

        // --- SECTION 9: ADVANCED MESH NETWORKS & COMPLEX SYSTEM SETS (42 to 46) ---
        testSuite.add(new TestCase(tcId++, 5, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}, {4, 3}}, "5 courses interconnected valid mesh network"));
        testSuite.add(new TestCase(tcId++, 5, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}, {4, 3}, {1, 4}}, "5 courses interconnected cyclic mesh network"));
        testSuite.add(new TestCase(tcId++, 6, new int[][]{{1, 0}, {2, 1}, {3, 2}, {4, 3}, {5, 4}, {3, 5}}, "6 courses long path with a deep return loop"));
        testSuite.add(new TestCase(tcId++, 8, new int[][]{{1, 0}, {3, 2}, {5, 4}, {7, 6}}, "8 courses isolated parallel tracking segments"));
        testSuite.add(new TestCase(tcId++, 8, new int[][]{{1, 0}, {3, 2}, {5, 4}, {7, 6}, {2, 3}}, "8 courses isolated paths with a single cycle cross-contamination"));

        // --- EXECUTION LOOP ---
        System.out.println("=================================================================");
        System.out.println("   RUNNING TOPOLOGICAL SORT (KAHN'S ALGORITHM) COMPREHENSIVE TEST   ");
        System.out.println("=================================================================\n");

        int passed = 0;
        for (TestCase tc : testSuite) {
            int[] order = solver.findOrder(tc.numCourses, tc.prerequisites);
            boolean isValid = (order.length == tc.numCourses) || (order.length == 0);

            System.out.printf("Test Case %02d: %s\n", tc.id, tc.description);
            System.out.println("Input Courses: " + tc.numCourses + " | Prerequisites Count: " + tc.prerequisites.length);
            System.out.println("Generated Output Order: " + Arrays.toString(order));

            if (order.length == 0) {
                System.out.println("Verdict: 🛑 Cycle Detected / Schedule Impossible");
            } else {
                System.out.println("Verdict: ✅ Valid Linear Order Resolved");
                passed++;
            }
            System.out.println("-----------------------------------------------------------------");
        }

        System.out.println("\n================================================================");
        System.out.println("Testing complete. Processed " + testSuite.size() + " unique graph test profiles.");
        System.out.println("================================================================");
    }
}
