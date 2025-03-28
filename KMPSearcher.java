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
        // return empty list if text is invalid
        if (text == null || text.isEmpty()) {
            return matches;
        }

        int textPos = 0; // current position in text
        int patPos = 0;  // current position in pattern

        // process text until end
        while (textPos < text.length()) {
            // characters match, move forward
            if (pattern.charAt(patPos) == text.charAt(textPos)) {
                textPos++;
                patPos++;
                // full match found
                if (patPos == pattern.length()) {
                    matches.add(textPos - patPos); // add match start index
                    patPos = 0; // reset for next match
                }
            }
            // mismatch, use skip table
            else {
                int charIndex = text.charAt(textPos) < 256 ? text.charAt(textPos) : -1;
                // handle non-ASCII chars
                if (charIndex < 0) {
                    textPos++;
                    patPos = 0;
                }
                // apply skip logic
                else {
                    int skip = skipTable[charIndex][patPos];
                    if (skip == 0) {
                        patPos++; // rare case, increment pattern pos
                    }
                    else if (skip > patPos) {
                        textPos += skip - patPos; // shift text forward
                        patPos = 0;
                    }
                    else {
                        patPos = patPos - skip + 1; // adjust pattern position
                    }
                }
            }
        }
        return matches;
    }
}