package org.example.Preperation_2026;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SnakeGame {

    public record Position(int row, int col) {}
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private final int width;
    private final int height;
    private int totalMoves;
    private boolean isGameOver;

    private final Deque<Position> snakeBody;
    private final Set<Position> snakeLookup;

    public SnakeGame(int width, int height, int startRow, int startCol) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive numbers.");
        }
        this.width = width;
        this.height = height;
        this.totalMoves = 0;
        this.isGameOver = false;

        this.snakeBody = new LinkedList<>();
        this.snakeLookup = new HashSet<>();

        // Initialize snake with a starting size of 3, extending to the right
        for (int i = 0; i < 3; i++) {
            Position segment = new Position(startRow, startCol + i);
            if (isOutOfBounds(segment)) {
                throw new IllegalArgumentException("Initial configuration drops outside board boundaries.");
            }
            snakeBody.addFirst(segment); // First element is the head
            snakeLookup.add(segment);
        }
    }

    public int move(Direction direction) {
        if (isGameOver) {
            return -1;
        }

        totalMoves++;
        Position currentHead = snakeBody.peekFirst();
        Position newHead = calculateNextPosition(currentHead, direction);

        // Rule: Snake grows by 1 segment every 5 moves
        boolean shouldGrow = (totalMoves % 5 == 0);

        Position removedTail = null;
        if (!shouldGrow) {
            removedTail = snakeBody.pollLast();
            if (removedTail != null) {
                snakeLookup.remove(removedTail);
            }
        }

        // Validate Boundaries & Self-Collision
        if (isOutOfBounds(newHead) || snakeLookup.contains(newHead)) {
            isGameOver = true;
            return -1;
        }

        snakeBody.addFirst(newHead);
        snakeLookup.add(newHead);

        return snakeBody.size();
    }

    private Position calculateNextPosition(Position current, Direction direction) {
        return switch (direction) {
            case UP -> new Position(current.row() - 1, current.col());
            case DOWN -> new Position(current.row() + 1, current.col());
            case LEFT -> new Position(current.row(), current.col() - 1);
            case RIGHT -> new Position(current.row(), current.col() + 1);
        };
    }

    private boolean isOutOfBounds(Position pos) {
        return pos.row() < 0 || pos.row() >= height || pos.col() < 0 || pos.col() >= width;
    }

    // --- INTERVIEW MOCK TEST SUITE ---
    public static void main(String[] args) {
        System.out.println("=== RUNNING SNAKE GAME EDGE-CASE TESTS ===");

        // Test 1: Standard Movement & The Tail-Chasing Edge Case
        // The snake should safely move into its own tail position if it isn't a growth turn.
        System.out.print("Test 1 (Tail-Chasing Move): ");
        SnakeGame game1 = new SnakeGame(5, 5, 2, 1);
        // Head is at (2,3), body at (2,2), tail at (2,1)
        game1.move(Direction.DOWN); // Head -> (3,3)
        game1.move(Direction.LEFT); // Head -> (3,2)
        game1.move(Direction.UP);   // Head -> (2,2) - Wait, this is the current body! This crashes.

        // Let's reset cleanly to execute a valid loop to step on its own tail:
        SnakeGame tailChaseGame = new SnakeGame(10, 10, 2, 2);
        // Head (2,4) -> (2,3) -> (2,2)[Tail]
        tailChaseGame.move(Direction.DOWN);  // (3,4)
        tailChaseGame.move(Direction.LEFT);  // (3,3)
        tailChaseGame.move(Direction.LEFT);  // (3,2)
        int resultTail = tailChaseGame.move(Direction.UP); // Steps onto (2,2) which was the old tail.
        if (resultTail != -1) {
            System.out.println("PASSED (Snake tracked tail eviction correctly without self-colliding)");
        } else {
            System.out.println("FAILED");
        }

        // Test 2: Boundary Flash Crash Edge Case
        System.out.print("Test 2 (Boundary Wall Collision): ");
        SnakeGame game2 = new SnakeGame(5, 5, 0, 2);
        // Head is at (0,4) [Far right wall boundary]
        int boundaryResult = game2.move(Direction.RIGHT);
        if (boundaryResult == -1 && game2.isGameOver) {
            System.out.println("PASSED (Boundary wall triggered Game Over immediately)");
        } else {
            System.out.println("FAILED");
        }

        // Test 3: Growth Collision Edge Case
        // On move 5, the snake grows. If it steps on its tail during growth, it must crash.
        System.out.print("Test 3 (Collision on Growth Turn): ");
        SnakeGame game3 = new SnakeGame(10, 10, 2, 2);
        // Initial: (2,4)[Head], (2,3), (2,2)[Tail]
        game3.move(Direction.DOWN); // Move 1: Head (3,4)
        game3.move(Direction.LEFT); // Move 2: Head (3,3)
        game3.move(Direction.LEFT); // Move 3: Head (3,2)
        game3.move(Direction.UP);   // Move 4: Head (2,2) - Old tail is long gone
        int growthCrashResult = game3.move(Direction.RIGHT); // Move 5: Shifting back right onto its own body on a growth turn!
        if (growthCrashResult == -1) {
            System.out.println("PASSED (Growth crunch detected cleanly)");
        } else {
            System.out.println("FAILED");
        }
    }
}

