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
        if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
            return matches;
        }

        int textPos = 0;
        int patPos = 0;

        while (textPos < text.length()) {
            if (patPos < pattern.length() && text.charAt(textPos) == pattern.charAt(patPos)) {
                textPos++;
                patPos++;
                if (patPos == pattern.length()) {
                    matches.add(textPos - patPos); // match found, record start position
                    patPos = 0; // reset to search for next match
                }
            } else {
                int charIndex = (int) text.charAt(textPos);
                if (charIndex >= 256) charIndex = 255; // clamp to highest ASCII

                int skip = skipTable[charIndex][patPos];
                if (skip == 0) {
                    patPos = 0; // reset pattern position
                    textPos++; // move text forward
                } else {
                    textPos += skip; // skip ahead in text
                    patPos = 0; // reset pattern position
                }
            }
        }

        return matches;
    }
}