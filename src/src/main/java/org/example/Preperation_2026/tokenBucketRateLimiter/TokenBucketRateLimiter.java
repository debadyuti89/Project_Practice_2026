package org.example.Preperation_2026.tokenBucketRateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter {

    private final long maxCapacity;
    private final long refillRatePerSecond;

    // Tracks state per user profile efficiently and concurrently
    private final ConcurrentHashMap<String, BucketState> userBuckets = new ConcurrentHashMap<>();

    // Internal class representing immutable snapshots of current bucket states
    private static class BucketState {
        final long tokens;
        final long lastRefillTimestamp;

        BucketState(long tokens, long lastRefillTimestamp) {
            this.tokens = tokens;
            this.lastRefillTimestamp = lastRefillTimestamp;
        }
    }

    public TokenBucketRateLimiter(long maxCapacity, long refillRatePerSecond) {
        this.maxCapacity = maxCapacity;
        this.refillRatePerSecond = refillRatePerSecond;
    }

    /**
     * Thread-safe evaluation to verify if a user has sufficient quota remaining to execute a request.
     */
    public boolean allowRequest(String userId) {
        long currentTimeMillis = System.currentTimeMillis();

        // Compute if absent initializing a full bucket directly matching current timestamp
        BucketState currentState = userBuckets.computeIfAbsent(userId,
                k -> new BucketState(maxCapacity, currentTimeMillis));

        while (true) {
            long elapsedSeconds = Math.max(0, (currentTimeMillis - currentState.lastRefillTimestamp) / 1000);

            // Calculate total tokens available after accounting for elapsed duration
            long refilledTokens = Math.min(maxCapacity, currentState.tokens + (elapsedSeconds * refillRatePerSecond));

            // If tokens are regenerated, update lastRefillTimestamp based on completed seconds elapsed
            long nextRefillTimestamp = currentState.lastRefillTimestamp + (elapsedSeconds * 1000);

            if (refilledTokens < 1) {
                return false; // Rate limit exceeded
            }

            // Create target state decremented by 1 token
            BucketState targetState = new BucketState(refilledTokens - 1, nextRefillTimestamp);

            // Atomic CAS operation ensures no execution race conditions overwrite state modifications
            if (userBuckets.replace(userId, currentState, targetState)) {
                return true; // Quota successfully claimed
            }

            // If CAS failed due to concurrent modification, grab newest state configuration and loop retry
            currentState = userBuckets.get(userId);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Configuration: Max 3 requests capacity, refilling at 1 token per second
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(3, 1);
        String user = "user_dev_123";

        // Burst traffic test
        System.out.println("Req 1: " + rateLimiter.allowRequest(user)); // True
        System.out.println("Req 2: " + rateLimiter.allowRequest(user)); // True
        System.out.println("Req 3: " + rateLimiter.allowRequest(user)); // True
        System.out.println("Req 4: " + rateLimiter.allowRequest(user)); // False (Empty bucket)

        // Wait 1.5 seconds for token generation
        Thread.sleep(1500);
        System.out.println("Req 5 (After sleep): " + rateLimiter.allowRequest(user)); // True (1 token refilled)
    }
}

