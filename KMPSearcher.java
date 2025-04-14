// KMPSearcher.java
// Implements string searching using a skip table
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.ArrayList;

/**
 * Performs string searching using a precomputed 2D skip table instead of an LPS array.
 */
public class KMPSearcher {
    private final String pattern;    // pattern to search for
    private final int[][] skipTable; // 2d skip table for all ASCII chars

    /**
     * initializes with a pattern and precomputed skip table from SkipTable
     * @param pattern string to search for
     * @param skipTable precomputed skip table
     * @throws IllegalArgumentException if pattern or skipTable is invalid
     */
    public KMPSearcher(String pattern, int[][] skipTable) {
        // check pattern validity
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        // verify skip table dimensions
        if (skipTable == null || skipTable.length != 256 || skipTable[0].length != pattern.length()) {
            throw new IllegalArgumentException("skipTable must be valid for all ASCII chars with pattern length");
        }
        this.pattern = pattern;
        this.skipTable = skipTable;
    }

    /**
     * searches for all pattern occurrences in text using the skip table
     * @param text text to search in
     * @return list of starting indices of matches
     */
    public ArrayList<Integer> search(String text) {
        ArrayList<Integer> matches = new ArrayList<>();
        if (text == null || text.isEmpty() || pattern.isEmpty()) {
            return matches;
        }

        int textPos = 0;
        // only search up to where there are enough characters left for a complete match
        while (textPos <= text.length() - pattern.length()) {
            int patPos = 0;
            // compare characters while they match
            while (patPos < pattern.length() && text.charAt(textPos + patPos) == pattern.charAt(patPos)) {
                patPos++;
            }

            // if entire pattern matched
            if (patPos == pattern.length()) {
                matches.add(textPos);
                textPos++;  // move on to find next match
            } else {
                // mismatch, compute skip
                int mismatchChar = text.charAt(textPos + patPos);
                // if mismatchChar is not in the pattern, skip to next character
                if (mismatchChar >= 256) {
                    mismatchChar = 255; // clamp
                }
                int skip = skipTable[mismatchChar][patPos];
                textPos += Math.max(1, skip);
            }
        }

        return matches;
    }
}