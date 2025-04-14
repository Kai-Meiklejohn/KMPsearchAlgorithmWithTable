// SkipTable.java
// Computes and prints the kmp skip table for a target string 
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.TreeSet;

/**
 * Handles KMP skip table computation and printing without an LPS array.
 */
public class SkipTable {
    private static final int CHAR_RANGE = 256; // allow all ASCII chars
    private final String pattern;              // target string for the skip table
    private final int[][] skipTable;           // 2d array: [char index][position] for skips

    /**
     * initializes with pattern and builds the skip table
     * @param pattern string to generate skip table for
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public SkipTable(String pattern) {
        // validate input
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        this.pattern = pattern;
        this.skipTable = buildSkipTable();
    }

    /**
     * builds a 2d skip table for all ASCII characters at each pattern position
     * @return the computed skip table
     */
    private int[][] buildSkipTable() {
        int len = pattern.length();
        int[][] table = new int[CHAR_RANGE][len];
        // collect unique chars for printing later
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char c : pattern.toCharArray()) {
            alphabet.add(c);
        }

        // fill table with skip distances
        for (int pos = 0; pos < len; pos++) {
            for (int ch = 0; ch < CHAR_RANGE; ch++) {
                table[ch][pos] = computeSkip((char) ch, pos);
            }
        }
        return table;
    }

    /**
     * computes skip distance for a character at a position without LPS
     * @param letter character to compute skip for
     * @param pos position in pattern
     * @return number of positions to skip
     */
    private int computeSkip(char letter, int pos) {
        // match found, no skip needed
        if (pattern.charAt(pos) == letter) {
            return 0;
        }
        // at start of pattern, minimal skip
        if (pos == 0) {
            return 1;
        }
        // look for a prefix that matches a suffix ending before pos
        for (int k = pos - 1; k >= 0; k--) {
            boolean isPrefixMatch = true;
            for (int i = 0; i < k; i++) {
                if (pattern.charAt(i) != pattern.charAt(pos - k + i)) {
                    isPrefixMatch = false;
                    break;
                }
            }
            // if prefix matches and next char aligns, return skip distance
            if (isPrefixMatch && pattern.charAt(k) == letter) {
                return pos - k;
            }
        }
        // no prefix match, skip entire current alignment
        return pos + 1;
    }

    /**
     * prints the skip table with pattern, unique chars, and default row
     */
    public void printSkipTable() {
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char letter : pattern.toCharArray()) {
            alphabet.add(letter);
        }
        // print header with pattern
        System.out.print("*");
        for (char letter : pattern.toCharArray()) {
            System.out.print("," + letter);
        }
        System.out.println();
        // print rows for chars in pattern
        for (char letter : alphabet) {
            System.out.print(letter);
            int charIndex = letter; // use ASCII value directly
            for (int pos = 0; pos < pattern.length(); pos++) {
                System.out.print("," + skipTable[charIndex][pos]);
            }
            System.out.println();
        }
        // print default skip values
        System.out.print("*");
        for (int pos = 0; pos < pattern.length(); pos++) {
            System.out.print("," + (pos + 1));
        }
        System.out.println();
    }

    // getter for the skip table
    public int[][] getSkipTable() {
        return skipTable;
    }

    // getter for the pattern
    public String getPattern() {
        return pattern;
    }
}