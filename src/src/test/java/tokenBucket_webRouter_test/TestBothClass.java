package tokenBucket_webRouter_test;

import org.example.Preperation_2026.tokenBucketRateLimiter.TokenBucketRateLimiter;
import org.example.Preperation_2026.webRouter.WebRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class TestBothClass {

    @Nested
    @DisplayName("WebRouter Core & Edge Case Tests")
    class WebRouterTests {
        private WebRouter router;

        @BeforeEach
        void setUp() {
            router = new WebRouter();
        }

        @Test
        @DisplayName("Should honor routing priority hierarchy: Exact Match > Parameter > Wildcard")
        void testRoutingPriority() {
            router.register("/api/v1/users/active", "ExactHandler");
            router.register("/api/v1/users/:status", "ParamHandler");
            router.register("/api/v1/users/*", "WildcardHandler");

            // 1. Exact match should win over the other two
            Optional<WebRouter.MatchResult> match1 = router.resolve("/api/v1/users/active");
            assertTrue(match1.isPresent());
            assertEquals("ExactHandler", match1.get().getHandler());

            // 2. Dynamic parameter should win over generic wildcard fallback
            Optional<WebRouter.MatchResult> match2 = router.resolve("/api/v1/users/suspended");
            assertTrue(match2.isPresent());
            assertEquals("ParamHandler", match2.get().getHandler());
            assertEquals("suspended", match2.get().getPathParameters().get("status"));

            // 3. Falling back to wildcard if route paths grow longer
            Optional<WebRouter.MatchResult> match3 = router.resolve("/api/v1/users/archived/nested/paths");
            assertTrue(match3.isPresent());
            assertEquals("WildcardHandler", match3.get().getHandler());
        }

        @ParameterizedTest
        @ValueSource(strings = {"/api/v1/users/42", "api/v1/users/42/", "///api/v1/users/42///"})
        @DisplayName("Should normalize structural inconsistencies like leading/trailing/multiple slashes")
        void testSlashNormalization(String messyPath) {
            router.register("/api/v1/users/:id", "TargetHandler");

            Optional<WebRouter.MatchResult> result = router.resolve(messyPath);
            assertTrue(result.isPresent());
            assertEquals("TargetHandler", result.get().getHandler());
            assertEquals("42", result.get().getPathParameters().get("id"));
        }

        @Test
        @DisplayName("Should crash fast on ambiguous parametric route declarations")
        void testAmbiguousRoutes() {
            router.register("/api/:userId", "HandlerA");
            assertThrows(IllegalStateException.class, () -> {
                router.register("/api/:customerId", "HandlerB");
            });
        }
    }

    @Nested
    @DisplayName("TokenBucketRateLimiter Core & Concurrency Tests")
    class RateLimiterTests {

        @Test
        @DisplayName("Should strictly reject actions exceeding burst capacity limits")
        void testBasicRateLimiting() {
            // Capacity: 2 tokens, Refill: 1 token/sec
            TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(2, 1);
            String user = "test_user";

            assertTrue(limiter.allowRequest(user), "First request should pass");
            assertTrue(limiter.allowRequest(user), "Second request should pass");
            assertFalse(limiter.allowRequest(user), "Third request should fail due to exhaustion");
        }

        @Test
        @DisplayName("Should lazily regenerate tokens after simulated elapsed intervals")
        void testLazyTokenRefill() throws InterruptedException {
            TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(1, 1);
            String user = "refresh_user";

            assertTrue(limiter.allowRequest(user));
            assertFalse(limiter.allowRequest(user));

            // Sleep 1.05s to allow exactly 1 token to regenerate
            Thread.sleep(1050);

            assertTrue(limiter.allowRequest(user), "Token should have successfully regenerated");
        }

        @Test
        @DisplayName("Should handle brutal multi-threaded load conditions without bleeding tokens")
        void testThreadSafetyUnderHeavyLoad() throws InterruptedException {
            final int totalThreads = 20;
            final int allowedBurst = 5;

            // Set refill rate to 0 so we only test safe consumption of initial capacity
            TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(allowedBurst, 0);
            String user = "concurrent_user";

            ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
            CountDownLatch readyLatch = new CountDownLatch(totalThreads);
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch finishLatch = new CountDownLatch(totalThreads);

            AtomicInteger successfulRequests = new AtomicInteger(0);

            for (int i = 0; i < totalThreads; i++) {
                executor.submit(() -> {
                    readyLatch.countDown();
                    try {
                        startLatch.await(); // Sync entry point
                        if (limiter.allowRequest(user)) {
                            successfulRequests.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        finishLatch.countDown();
                    }
                });
            }

            readyLatch.await();
            startLatch.countDown(); // Fire all threads simultaneously
            finishLatch.await();    // Wait for executions to stop
            executor.shutdown();

            assertEquals(allowedBurst, successfulRequests.get(),
                    "Atomic CAS must ensure exactly " + allowedBurst + " threads succeeded despite race conditions.");
        }
    }
}

