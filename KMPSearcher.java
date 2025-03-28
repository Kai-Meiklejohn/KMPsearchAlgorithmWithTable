// KMPSearcher.java
// Implements string searching without lps using a skip table
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.ArrayList;

/**
 * Performs string searching using a precomputed 2D skip table instead of an LPS array.
 */
public class KMPSearcher {
    private final String pattern;    // Pattern to search for
    private final int[][] skipTable; // 2D skip table for all ASCII chars

    /**
     * Initializes with a pattern and precomputed skip table from SkipTable.
     * @param pattern String to search for
     * @param skipTable Precomputed skip table
     * @throws IllegalArgumentException if pattern or skipTable is invalid
     */
    public KMPSearcher(String pattern, int[][] skipTable) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        // Ensure we match the same dimensions (256 rows)
        if (skipTable == null || skipTable.length != 256 || skipTable[0].length != pattern.length()) {
            throw new IllegalArgumentException("skipTable must be valid for all ASCII chars with pattern length");
        }
        this.pattern = pattern;
        this.skipTable = skipTable;
    }

    /**
     * Searches for all pattern occurrences in text using the skip table.
     * @param text Text to search in
     * @return List of starting indices of matches
     */
    public ArrayList<Integer> search(String text) {
        ArrayList<Integer> matches = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return matches; // No matches possible
        }

        int textPos = 0; // Position in text
        int patPos = 0;  // Position in pattern

        while (textPos < text.length()) {
            if (pattern.charAt(patPos) == text.charAt(textPos)) {
                textPos++;
                patPos++;
                if (patPos == pattern.length()) {
                    matches.add(textPos - patPos); // Record match start
                    patPos = 0; // Reset for next match
                }
            } else {
                // Get ASCII index, skip if out of range
                int charIndex = text.charAt(textPos) < 256 ? text.charAt(textPos) : -1;
                if (charIndex < 0) {
                    // Non-ASCII character: just move on
                    textPos++;
                    patPos = 0;
                } else {
                    int skip = skipTable[charIndex][patPos];
                    if (skip == 0) {
                        // Rare case—can increment the pattern pos
                        patPos++;
                    } else if (skip > patPos) {
                        textPos += skip - patPos;
                        patPos = 0;
                    } else {
                        patPos = patPos - skip + 1;
                    }
                }
            }
        }
        return matches;
    }
}