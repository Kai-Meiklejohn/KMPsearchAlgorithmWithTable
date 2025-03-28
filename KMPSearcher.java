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
    private final int[][] skipTable; // 2D skip table for A-Z and a-z

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
        if (skipTable == null || skipTable.length != 52 || skipTable[0].length != pattern.length()) {
            throw new IllegalArgumentException("skipTable must be valid for A-Z and a-z with pattern length");
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
                char textChar = text.charAt(textPos);
                int charIndex;
                // Map text char to skip table index
                if (textChar >= 'A' && textChar <= 'Z') {
                    charIndex = textChar - 'A'; // A-Z: 0-25
                } else if (textChar >= 'a' && textChar <= 'z') {
                    charIndex = 26 + (textChar - 'a'); // a-z: 26-51
                } else {
                    textPos++; // Skip non-alphabetic chars
                    patPos = 0;
                    continue;
                }
                
                int skip = skipTable[charIndex][patPos];
                if (skip == 0) {
                    patPos++; // Rare case, fallback increment
                } else if (skip > patPos) {
                    textPos += skip - patPos; // Shift text forward
                    patPos = 0;
                } else {
                    patPos = patPos - skip + 1; // Shift pattern back
                }
            }
        }
        return matches;
    }
}