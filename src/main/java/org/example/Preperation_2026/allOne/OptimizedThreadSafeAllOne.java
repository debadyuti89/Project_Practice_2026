package org.example.Preperation_2026.allOne;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OptimizedThreadSafeAllOne {

    // Internal node structure representing a specific frequency bucket
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

    private final Map<String, Node> keyToNode;
    private final Node head;
    private final Node tail;

    // ReadWriteLock allows multiple concurrent readers but exclusive writers
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public OptimizedThreadSafeAllOne() {
        keyToNode = new HashMap<>();
        head = new Node(0); // Dummy head
        tail = new Node(0); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public void inc(String key) {
        writeLock.lock();
        try {
            if (keyToNode.containsKey(key)) {
                Node curNode = keyToNode.get(key);
                int nextCount = curNode.count + 1;
                Node nextNode = curNode.next;

                if (nextNode == tail || nextNode.count != nextCount) {
                    nextNode = insertNodeAfter(curNode, nextCount);
                }

                nextNode.keys.add(key);
                keyToNode.put(key, nextNode);

                curNode.keys.remove(key);
                if (curNode.keys.isEmpty()) {
                    removeNode(curNode);
                }
            } else {
                Node firstNode = head.next;
                if (firstNode == tail || firstNode.count != 1) {
                    firstNode = insertNodeAfter(head, 1);
                }
                firstNode.keys.add(key);
                keyToNode.put(key, firstNode);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void dec(String key) {
        writeLock.lock();
        try {
            if (!keyToNode.containsKey(key)) {
                return; // Guard clause in case key is absent concurrently
            }
            Node curNode = keyToNode.get(key);
            curNode.keys.remove(key);

            int prevCount = curNode.count - 1;
            if (prevCount == 0) {
                keyToNode.remove(key);
            } else {
                Node prevNode = curNode.prev;
                if (prevNode == head || prevNode.count != prevCount) {
                    prevNode = insertNodeAfter(prevNode, prevCount);
                }
                prevNode.keys.add(key);
                keyToNode.put(key, prevNode);
            }

            if (curNode.keys.isEmpty()) {
                removeNode(curNode);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public String getMaxKey() {
        readLock.lock();
        try {
            if (tail.prev == head) {
                return "";
            }
            return tail.prev.keys.iterator().next();
        } finally {
            readLock.unlock();
        }
    }

    public String getMinKey() {
        readLock.lock();
        try {
            if (head.next == tail) {
                return "";
            }
            return head.next.keys.iterator().next();
        } finally {
            readLock.unlock();
        }
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
    }

    // ==========================================
    // MAIN METHOD WITH CONCURRENT TEST CASES
    // ==========================================
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Test Case 1: Sequential Basic Verification ---");
        runSequentialTest();

        System.out.println("\n--- Test Case 2: Concurrent Multi-Threaded Stress Test ---");
        runConcurrentTest();
    }

    private static void runSequentialTest() {
        OptimizedThreadSafeAllOne allOne = new OptimizedThreadSafeAllOne();

        allOne.inc("apple");
        allOne.inc("apple");
        allOne.inc("banana");

        System.out.println("Max Key (Expected 'apple'): " + allOne.getMaxKey());
        System.out.println("Min Key (Expected 'banana'): " + allOne.getMinKey());

        allOne.dec("apple");
        System.out.println("Min Key after decrements (Expected 'apple' or 'banana'): " + allOne.getMinKey());
    }

    private static void runConcurrentTest() throws InterruptedException {
        OptimizedThreadSafeAllOne sharedDataStructure = new OptimizedThreadSafeAllOne();
        int numberOfThreads = 10;
        int operationsPerThread = 500;

        // Fixed thread pool to run workers concurrently
        try (ExecutorServiceWrapper executorWrapper = new ExecutorServiceWrapper(Executors.newFixedThreadPool(numberOfThreads))) {
            ExecutorService executor = executorWrapper.getExecutorService();

            // Submitting multiple workers writing/reading concurrently
            for (int i = 0; i < numberOfThreads; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    String dynamicKey = "Key-" + threadId;

                    for (int j = 0; j < operationsPerThread; j++) {
                        // Alternate between increments, decrements, and structural reads
                        sharedDataStructure.inc(dynamicKey);

                        if (j % 5 == 0) {
                            sharedDataStructure.getMaxKey();
                        }
                        if (j % 7 == 0) {
                            sharedDataStructure.getMinKey();
                        }
                        if (j % 2 == 0) {
                            sharedDataStructure.dec(dynamicKey);
                        }
                    }
                });
            }

            // Gracefully shutdown thread executions
            executor.shutdown();
            boolean successfullyFinished = executor.awaitTermination(10, TimeUnit.SECONDS);

            if (successfullyFinished) {
                System.out.println("Execution Completed Successfully with zero locks hanging.");
                System.out.println("Final System Max Key Result: " + sharedDataStructure.getMaxKey());
                System.out.println("Final System Min Key Result: " + sharedDataStructure.getMinKey());
            } else {
                System.err.println("Test timed out! Possible deadlock occurred.");
            }
        }
    }
}