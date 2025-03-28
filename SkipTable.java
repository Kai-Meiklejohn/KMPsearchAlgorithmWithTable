// SkipTable.java
// Computes and prints the kmp skip table for a target string without lps
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.TreeSet;

/**
 * Handles KMP skip table computation and printing without an LPS array.
 */
public class SkipTable {
    private final String pattern;    // Target string for the skip table
    private final int[][] skipTable; // 2D array: [char index][position] for skips

    /**
     * Initializes with pattern and builds the skip table.
     * @param pattern String to generate skip table for
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public SkipTable(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        this.pattern = pattern;
        this.skipTable = buildSkipTable();
    }

    /**
     * Builds a 2D skip table for A-Z and a-z at each pattern position.
     * @return The computed skip table
     */
    private int[][] buildSkipTable() {
        int len = pattern.length();
        int[][] table = new int[52][len]; // 52 rows: A-Z (0-25), a-z (26-51)
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char c : pattern.toCharArray()) {
            alphabet.add(c); // Collect unique chars for printing
        }

        for (int pos = 0; pos < len; pos++) {
            for (char c = 'A'; c <= 'Z'; c++) {
                table[c - 'A'][pos] = computeSkip(c, pos);
            }
            for (char c = 'a'; c <= 'z'; c++) {
                table[26 + (c - 'a')][pos] = computeSkip(c, pos);
            }
        }
        return table;
    }

    /**
     * Computes skip distance for a character at a position without LPS.
     * @param letter Character to compute skip for
     * @param pos Position in pattern
     * @return Number of positions to skip
     */
    private int computeSkip(char letter, int pos) {
        if (pattern.charAt(pos) == letter) {
            return 0; // Match, no skip
        }
        if (pos == 0) {
            return 1; // Start of pattern, skip one
        }
        // Check for prefix that matches suffix before pos
        for (int k = pos - 1; k >= 0; k--) {
            boolean isPrefixMatch = true;
            for (int i = 0; i < k; i++) {
                if (pattern.charAt(i) != pattern.charAt(pos - k + i)) {
                    isPrefixMatch = false;
                    break;
                }
            }
            if (isPrefixMatch && pattern.charAt(k) == letter) {
                return pos - k; // Skip to align prefix
            }
        }
        return pos + 1; // No prefix match, skip past current position
    }

    /**
     * Prints the skip table with pattern, unique chars, and default row.
     */
    public void printSkipTable() {
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char letter : pattern.toCharArray()) {
            alphabet.add(letter);
        }
        // Print pattern row
        System.out.print("*");
        for (char letter : pattern.toCharArray()) {
            System.out.print("," + letter);
        }
        System.out.println();
        // Print rows for unique characters in pattern
        for (char letter : alphabet) {
            System.out.print(letter);
            int charIndex = (letter >= 'A' && letter <= 'Z') ? (letter - 'A') : (26 + (letter - 'a'));
            for (int pos = 0; pos < pattern.length(); pos++) {
                System.out.print("," + skipTable[charIndex][pos]);
            }
            System.out.println();
        }
        // Print default row for unmatched chars
        System.out.print("*");
        for (int pos = 0; pos < pattern.length(); pos++) {
            System.out.print("," + (pos + 1));
        }
        System.out.println();
    }

    // Getter for the skip table
    public int[][] getSkipTable() {
        return skipTable;
    }

    // Getter for the pattern
    public String getPattern() {
        return pattern;
    }
}