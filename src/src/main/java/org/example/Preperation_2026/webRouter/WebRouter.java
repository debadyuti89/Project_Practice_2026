package org.example.Preperation_2026.webRouter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WebRouter {

    private static final String WILDCARD = "*";
    private static final String PARAM_PREFIX = ":";

    private static class TrieNode {
        // Use ConcurrentHashMap to ensure thread-safe reads and mutations
        final Map<String, TrieNode> children = new ConcurrentHashMap<>();
        volatile String handler = null;
        volatile String paramName = null; // Stores the key name if this node acts as a parameter (e.g., "id" for ":id")
        volatile boolean isWildcard = false;
    }

    private final TrieNode root = new TrieNode();

    /**
     * Registers a path route with a specific string handler.
     * e.g., register("/api/v1/users/:id/profile", "UserProfileHandler")
     */
    public void register(String path, String handler) {
        if (path == null || handler == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path and handler cannot be null or empty");
        }

        String[] tokens = tokenize(path);
        TrieNode current = root;

        for (String token : tokens) {
            if (token.equals(WILDCARD)) {
                current = current.children.computeIfAbsent(WILDCARD, k -> {
                    TrieNode node = new TrieNode();
                    node.isWildcard = true;
                    return node;
                });
            } else if (token.startsWith(PARAM_PREFIX)) {
                String paramName = token.substring(1);
                if (paramName.isEmpty()) {
                    throw new IllegalArgumentException("Invalid parameter name in path: " + path);
                }

                // If a parameter node already exists under a different variable name, throw an error to maintain route sanity
                if (current.paramName != null && !current.paramName.equals(paramName)) {
                    throw new IllegalStateException("Ambiguous route configuration for parameter at token: " + token);
                }

                current = current.children.computeIfAbsent(PARAM_PREFIX, k -> {
                    TrieNode node = new TrieNode();
                    node.paramName = paramName;
                    return node;
                });
            } else {
                current = current.children.computeIfAbsent(token, k -> new TrieNode());
            }
        }
        current.handler = handler;
    }

    /**
     * Resolves an incoming request path.
     * Returns a MatchResult containing the handler and any extracted path variables.
     */
    public Optional<MatchResult> resolve(String path) {
        if (path == null || path.trim().isEmpty()) {
            return Optional.empty();
        }

        String[] tokens = tokenize(path);
        Map<String, String> extractedParams = new HashMap<>();

        TrieNode matchedNode = search(root, tokens, 0, extractedParams);

        if (matchedNode != null && matchedNode.handler != null) {
            return Optional.of(new MatchResult(matchedNode.handler, extractedParams));
        }
        return Optional.empty();
    }

    // Backtracking recursive search to honor priority: Exact Match > Parameter > Wildcard
    private TrieNode search(TrieNode current, String[] tokens, int index, Map<String, String> extractedParams) {
        if (index == tokens.length) {
            return current;
        }

        String token = tokens[index];

        // 1. Try Exact Match
        if (current.children.containsKey(token)) {
            TrieNode res = search(current.children.get(token), tokens, index + 1, extractedParams);
            if (res != null && res.handler != null) return res;
        }

        // 2. Try Parameter Match (e.g., ":id")
        if (current.children.containsKey(PARAM_PREFIX)) {
            TrieNode paramNode = current.children.get(PARAM_PREFIX);
            extractedParams.put(paramNode.paramName, token); // Optimistically track parameter

            TrieNode res = search(paramNode, tokens, index + 1, extractedParams);
            if (res != null && res.handler != null) return res;

            extractedParams.remove(paramNode.paramName); // Backtrack if route failed downstream
        }

        // 3. Try Wildcard Match (e.g., "*")
        if (current.children.containsKey(WILDCARD)) {
            return current.children.get(WILDCARD); // Wildcard matches all remaining path configurations
        }

        return null;
    }

    private String[] tokenize(String path) {
        // Strip leading/trailing slashes and split by forward slashes
        String cleanPath = path.replaceAll("^/+", "").replaceAll("/+$", "");
        return cleanPath.isEmpty() ? new String[0] : cleanPath.split("/");
    }

    // Immutable DTO for result encapsulation
    public static class MatchResult {
        private final String handler;
        private final Map<String, String> pathParameters;

        public MatchResult(String handler, Map<String, String> pathParameters) {
            this.handler = handler;
            this.pathParameters = Collections.unmodifiableMap(pathParameters);
        }

        public String getHandler() {
            return handler;
        }

        public Map<String, String> getPathParameters() {
            return pathParameters;
        }
    }

    // Mini Test Suite to demonstrate functionality
    public static void main(String[] args) {
        WebRouter router = new WebRouter();
        router.register("/api/v1/users", "GetAllUsersHandler");
        router.register("/api/v1/users/:id", "GetSingleUserHandler");
        router.register("/api/v1/users/:id/posts/*", "UserPostsWildcardHandler");

        // Test Exact Match
        router.resolve("/api/v1/users").ifPresent(res -> System.out.println("Match 1: " + res.getHandler()));

        // Test Parameter Parsing
        router.resolve("/api/v1/users/42").ifPresent(res -> {
            System.out.println("Match 2: " + res.getHandler());
            System.out.println("Extracted ID: " + res.getPathParameters().get("id"));
        });

        // Test Wildcard Fallback
        router.resolve("/api/v1/users/42/posts/recent/drafts").ifPresent(res -> {
            System.out.println("Match 3: " + res.getHandler());
        });
    }
}

