// KMPSearcher.java
// implements string searching without lps using a skip table
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.ArrayList;

/**
 * performs string searching to find pattern occurrences in text without lps
 */
public class KMPSearcher {
    private final String pattern;
    private final int[][] skipTable; // 2d array: [char index][position]

    /**
     * initializes searcher with pattern and prebuilt skip table
     * @param skipTable the precomputed skip table from SkipTable
     * @param pattern the string to search for
     * @throws IllegalArgumentException if pattern or skipTable is null or invalid
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

    // searches for pattern in text and returns list of match start indices
    public ArrayList<Integer> search(String text) {
        ArrayList<Integer> matches = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return matches; // no matches in null/empty text
        }

        int textPos = 0; // position in text
        int patPos = 0; // position in pattern

        while (textPos < text.length()) {
            if (pattern.charAt(patPos) == text.charAt(textPos)) {
                textPos++;
                patPos++;
                if (patPos == pattern.length()) {
                    matches.add(textPos - patPos); // record match start
                    patPos = 0; // reset to start after match
                }
            } else {
                char textChar = text.charAt(textPos);
                int charIndex;
                if (textChar >= 'A' && textChar <= 'Z') {
                    charIndex = textChar - 'A'; // 0-25 for A-Z
                } else if (textChar >= 'a' && textChar <= 'z') {
                    charIndex = 26 + (textChar - 'a'); // 26-51 for a-z
                } else {
                    textPos++; // char not in A-Z or a-z, shift text
                    patPos = 0;
                    continue;
                }
                int skip = skipTable[charIndex][patPos];
                if (skip == 0) {
                    patPos++; // should never happen due to mismatch
                } else if (skip > patPos) {
                    textPos += skip - patPos; // shift text past current alignment
                    patPos = 0;
                } else {
                    patPos = patPos - skip + 1; // shift pattern back
                }
            }
        }
        return matches;
    }
}