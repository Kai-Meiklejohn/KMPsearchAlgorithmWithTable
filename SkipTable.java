// SkipTable.java
// Computes and prints the KMP skip table for a given target string
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.TreeSet;

/**
 * Handles the computation and printing of a KMP skip table for a pattern string
 */
public class SkipTable {
    private final String pattern;
    private final int[] lps;

    /**
     * initializes a SkipTable with the given pattern and computes its LPS array
     * @param pattern the string to generate the skip table for
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public SkipTable(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        this.pattern = pattern;
        this.lps = new int[pattern.length()];
        constructLps();
    }

    // constructs the LPS array, which stores the length of the longest proper prefix
    // that is also a suffix for each position in the pattern
    private void constructLps() {
        int len = 0; // length of the current prefix that’s also a suffix
        lps[0] = 0;  // no prefix/suffix possible at the first character

        // loop through pattern starting at second character
        for (int i = 1; i < pattern.length(); i++) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                // characters match, extend the prefix/suffix length
                len++;
                lps[i] = len;
            } else if (len != 0) {
                // mismatch occurred, fall back to the previous prefix length
                len = lps[len - 1];
                i--; // re-check this position with the updated length
            } else {
                // no prefix matches at all, set LPS to 0
                lps[i] = 0;
            }
        }
    }

    // prints the kmp skip table with pattern, unique char rows, and default row
    public void printSkipTable() {
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char letter : pattern.toCharArray()) {
            alphabet.add(letter);
        }

        // print pattern row
        System.out.print("*");
        for (char letter : pattern.toCharArray()) {
            System.out.print("," + letter);
        }
        System.out.println();

        // print rows for each unique character
        for (char letter : alphabet) {
            System.out.print(letter);
            for (int pos = 0; pos < pattern.length(); pos++) {
                System.out.print("," + getSkip(letter, pos));
            }
            System.out.println();
        }

        // print default row
        System.out.print("*");
        for (int pos = 0; pos < pattern.length(); pos++) {
            System.out.print("," + (pos + 1));
        }
        System.out.println();
    }

    // computes skip distance for a character at a position
    private int getSkip(char letter, int pos) {
        if (pattern.charAt(pos) == letter) {
            return 0; // no shift on match
        }
        if (pos == 0) {
            return 1; // shift one if mismatch at start
        }

        int prefixLen = lps[pos - 1];
        while (prefixLen > 0 && pattern.charAt(prefixLen) != letter) {
            prefixLen = lps[prefixLen - 1];
        }
        return (pattern.charAt(prefixLen) == letter) ? pos - prefixLen : pos + 1;
    }
}