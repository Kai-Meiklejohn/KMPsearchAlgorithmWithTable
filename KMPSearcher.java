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
     * initializes searcher with pattern and its skip table
     * @param pattern the string to search for
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public KMPSearcher(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        this.pattern = pattern;
        this.skipTable = buildSkipTable();
    }

    // builds 2d skip table for all possible characters (a-z) at each position
    private int[][] buildSkipTable() {
        int len = pattern.length();
        int[][] table = new int[26][len]; // lowercase a-z
        for (int pos = 0; pos < len; pos++) {
            for (char c = 'a'; c <= 'z'; c++) {
                int charIndex = c - 'a';
                table[charIndex][pos] = computeSkip(c, pos);
            }
        }
        return table;
    }

    // computes skip distance without lps by finding valid prefix alignment
    private int computeSkip(char letter, int pos) {
        if (pattern.charAt(pos) == letter) {
            return 0; // match, no shift
        }
        if (pos == 0) {
            return 1; // mismatch at start, shift 1
        }

        // find longest prefix that matches a suffix ending before pos
        for (int k = pos - 1; k >= 0; k--) {
            boolean match = true;
            for (int i = 0; i < k; i++) {
                if (pattern.charAt(i) != pattern.charAt(pos - k + i)) {
                    match = false;
                    break;
                }
            }
            if (match && pattern.charAt(pos - k) == letter) {
                return pos - k; // shift to align prefix
            }
        }
        return pos + 1; // no matching prefix, shift fully past
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
                int charIndex = text.charAt(textPos) - 'a';
                if (charIndex >= 0 && charIndex < 26) {
                    int skip = skipTable[charIndex][patPos];
                    if (skip == 0) {
                        patPos++; // should never happen due to mismatch
                    } else if (skip > patPos) {
                        textPos += skip - patPos; // shift text past current alignment
                        patPos = 0;
                    } else {
                        patPos = patPos - skip + 1; // shift pattern back
                    }
                } else {
                    textPos++; // char not in a-z, shift text
                    patPos = 0;
                }
            }
        }
        return matches;
    }
}