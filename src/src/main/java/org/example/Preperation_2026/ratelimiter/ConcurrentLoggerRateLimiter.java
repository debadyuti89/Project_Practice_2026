package org.example.Preperation_2026.ratelimiter;


import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Interface defining the behavioral contract for a rate-limited logging service.
 */
interface RateLimitedLogger {
    boolean shouldLog(long timestampInSeconds, String message);
}

/**
 * Configuration Object representing the rate-limiting parameters.
 * Kept immutable for thread safety.
 */
final class RateLimiterConfig {
    private final long slidingWindowDurationInSeconds;

    public RateLimiterConfig(long slidingWindowDurationInSeconds) {
        this.slidingWindowDurationInSeconds = slidingWindowDurationInSeconds;
    }

    public long getSlidingWindowDurationInSeconds() {
        return slidingWindowDurationInSeconds;
    }
}

/**
 * High-performance, production-ready Logger Rate Limiter.
 * Uses ConcurrentHashMap compute mechanics to eliminate structural data races.
 */
public class ConcurrentLoggerRateLimiter implements RateLimitedLogger {

    private final ConcurrentMap<String, Long> messageToNextAllowedTimeMap;
    private final RateLimiterConfig config;

    public ConcurrentLoggerRateLimiter(RateLimiterConfig config) {
        this.config = Objects.requireNonNull(config, "Configuration cannot be null");
        this.messageToNextAllowedTimeMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean shouldLog(long timestampInSeconds, String message) {
        if (message == null) {
            return false;
        }

        // Periodic maintenance hook to clean up memory if the cache grows too large
        if (messageToNextAllowedTimeMap.size() > 50000) {
            cleanupStaleEntries(timestampInSeconds);
        }

        // Atomically evaluate and mutate the next execution milestone for the key
        long nextAllowedTime = messageToNextAllowedTimeMap.compute(message, (key, currentNextAllowedTime) -> {
            if (currentNextAllowedTime == null || timestampInSeconds >= currentNextAllowedTime) {
                return timestampInSeconds + config.getSlidingWindowDurationInSeconds();
            }
            return currentNextAllowedTime;
        });

        // If the calculation moved the window forward exactly by our interval, the operation was accepted
        return nextAllowedTime == (timestampInSeconds + config.getSlidingWindowDurationInSeconds());
    }

    /**
     * Prevent system degradation and OOM vectors by evicting stale message states.
     */
    private void cleanupStaleEntries(long currentTimestamp) {
        messageToNextAllowedTimeMap.entrySet().removeIf(entry -> currentTimestamp >= entry.getValue());
    }

    // Local execution harness simulation
    public static void main(String[] args) throws InterruptedException {
        RateLimiterConfig config = new RateLimiterConfig(10); // 10-second suppression window
        RateLimitedLogger logger = new ConcurrentLoggerRateLimiter(config);

        System.out.println(logger.shouldLog(1, "foo"));  // true
        System.out.println(logger.shouldLog(2, "bar"));  // true
        System.out.println(logger.shouldLog(3, "foo"));  // false (within window)
        System.out.println(logger.shouldLog(8, "bar"));  // false (within window)
        System.out.println(logger.shouldLog(11, "foo")); // true (1 + 10 = 11, window elapsed)
        System.out.println(logger.shouldLog(12, "bar")); // true (2 + 10 = 12, window elapsed)
    }
}