package org.example.allOne;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AllOneStructure {

    private static class Node {
        int count;
        Set<String> keys;
        Node prev;
        Node next;

        Node(int count) {
            this.count = count;
            this.keys = new HashSet<>();
        }
    }

    private final Map<String, Node> keyToNodeMap;
    private final Node head;
    private final Node tail;

    public AllOneStructure() {
        this.keyToNodeMap = new HashMap<>();
        this.head = new Node(0);
        this.tail = new Node(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
    }

    public void increasePopularity(String key) {
        if (keyToNodeMap.containsKey(key)) {
            Node currentNode = keyToNodeMap.get(key);
            int nextCount = currentNode.count + 1;

            Node nextNode = currentNode.next;
            if (nextNode == tail || nextNode.count != nextCount) {
                nextNode = insertNodeAfter(currentNode, nextCount);
            }

            nextNode.keys.add(key);
            keyToNodeMap.put(key, nextNode);

            currentNode.keys.remove(key);
            if (currentNode.keys.isEmpty()) {
                removeNode(currentNode);
            }
        } else {
            Node firstBucket = head.next;
            if (firstBucket == tail || firstBucket.count != 1) {
                firstBucket = insertNodeAfter(head, 1);
            }
            firstBucket.keys.add(key);
            keyToNodeMap.put(key, firstBucket);
        }
    }

    public void decreasePopularity(String key) {
        if (!keyToNodeMap.containsKey(key)) {
            return;
        }

        Node currentNode = keyToNodeMap.get(key);
        currentNode.keys.remove(key);
        int prevCount = currentNode.count - 1;

        if (prevCount == 0) {
            keyToNodeMap.remove(key);
        } else {
            Node prevNode = currentNode.prev;
            if (prevNode == head || prevNode.count != prevCount) {
                prevNode = insertNodeAfter(currentNode.prev, prevCount);
            }
            prevNode.keys.add(key);
            keyToNodeMap.put(key, prevNode);
        }

        if (currentNode.keys.isEmpty()) {
            removeNode(currentNode);
        }
    }

    public String getMostPopular() {
        return (tail.prev == head) ? "" : tail.prev.keys.iterator().next();
    }

    public String getLeastPopular() {
        return (head.next == tail) ? "" : head.next.keys.iterator().next();
    }

    private Node insertNodeAfter(Node target, int count) {
        Node newNode = new Node(count);
        newNode.next = target.next;
        newNode.prev = target;
        target.next.prev = newNode;
        target.next = newNode;
        return newNode;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
        node.prev = null;
    }

    // --- INTERVIEW MOCK TEST SUITE ---
    public static void main(String[] args) {
        System.out.println("\n=== RUNNING ALL O(1) STRUCTURE EDGE-CASE TESTS ===");

        // Test 1: Empty State Invariants
        System.out.print("Test 1 (Empty State Invariants): ");
        AllOneStructure cache = new AllOneStructure();
        if ("".equals(cache.getMostPopular()) && "".equals(cache.getLeastPopular())) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }

        // Test 2: Zero-Floor Dropoff Edge Case
        System.out.print("Test 2 (Zero-Floor Dropoff): ");
        cache.increasePopularity("Jira");
        cache.decreasePopularity("Jira");
        if ("".equals(cache.getMostPopular())) {
            System.out.println("PASSED (Key cleanly evicted from system when count hit zero)");
        } else {
            System.out.println("FAILED");
        }

        // Test 3: Node Cleanout & Dynamic Sorting
        System.out.print("Test 3 (Node Cleanout & Min/Max Sorting): ");
        cache.increasePopularity("Confluence");
        cache.increasePopularity("Confluence"); // Confluence = 2
        cache.increasePopularity("Bitbucket");  // Bitbucket = 1

        boolean matchBefore = "Confluence".equals(cache.getMostPopular()) && "Bitbucket".equals(cache.getLeastPopular());

        // Equalize scores to force node cleanups and merging
        cache.increasePopularity("Bitbucket");  // Bitbucket = 2, Confluence = 2
        cache.increasePopularity("Bitbucket");  // Bitbucket = 3, Confluence = 2

        boolean matchAfter = "Bitbucket".equals(cache.getMostPopular()) && "Confluence".equals(cache.getLeastPopular());

        if (matchBefore && matchAfter) {
            System.out.println("PASSED (Nodes managed pointers and extracted metrics in O(1))");
        } else {
            System.out.println("FAILED");
        }
    }
}

