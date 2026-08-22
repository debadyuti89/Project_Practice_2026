package org.example.Preperation_2026.TeamRanker;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Atlassian Code Craftsmanship: Rank Teams by Votes
 * Rules:
 * 1. Teams are ranked by most 1st place votes. Ties are broken by 2nd place, then 3rd, etc.
 * 2. If completely tied across all vote positions, sort alphabetically by Team ID.
 */
public class TeamRanker {

    public String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) {
            return "";
        }

        int totalTeams = votes[0].length();
        Map<Character, Team> teamMap = new HashMap<>();

        // Initialize all unique teams dynamically based on input data
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                char teamId = vote.charAt(i);
                teamMap.putIfAbsent(teamId, new Team(teamId, totalTeams));
                teamMap.get(teamId).recordVote(i);
            }
        }

        // Sort using the encapsulated domain logic inside the Team class
        return teamMap.values().stream()
                .sorted()
                .map(team -> String.valueOf(team.getId()))
                .collect(Collectors.joining());
    }

    // Domain model encapsulating state and comparison logic
    private static class Team implements Comparable<Team> {
        private final char id;
        private final int[] voteCounts;

        public Team(char id, int totalPositions) {
            this.id = id;
            this.voteCounts = new int[totalPositions];
        }

        public char getId() {
            return id;
        }

        public void recordVote(int position) {
            if (position >= 0 && position < voteCounts.length) {
                voteCounts[position]++;
            }
        }

        @Override
        public int compareTo(Team other) {
            // Step 1: Compare vote counts position by position
            for (int i = 0; i < this.voteCounts.length; i++) {
                if (this.voteCounts[i] != other.voteCounts[i]) {
                    // Descending order for votes (more votes is better)
                    return Integer.compare(other.voteCounts[i], this.voteCounts[i]);
                }
            }
            // Step 2: Tie-breaker - Alphabetical order (Ascending order for characters)
            return Character.compare(this.id, other.id);
        }
    }

    // Basic local validation runner
    public static void main(String[] args) {
        TeamRanker ranker = new TeamRanker();

        String[] case1 = {"ABC", "ACB", "ABC", "ACB", "ACB"};
        System.out.println("Output 1: " + ranker.rankTeams(case1)); // Expected: ACB

        String[] case2 = {"WXYZ", "XYZW"};
        System.out.println("Output 2: " + ranker.rankTeams(case2)); // Expected: XWYZ
    }
}