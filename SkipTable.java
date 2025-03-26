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

    // prints the KMP skip table in the required format: pattern row, unique char rows,
    // and default row
    public void printSkipTable() {
        // collect unique characters in sorted order
        TreeSet<Character> uniqueChars = new TreeSet<>();
        for (char c : pattern.toCharArray()) {
            uniqueChars.add(c);
        }

        // print the pattern row with '*' prefix
        printRow('*', pattern.chars().mapToObj(ch -> (char) ch).toArray(Character[]::new));

        // generate and print a row for each unique character
        for (char c : uniqueChars) {
            Integer[] skips = new Integer[pattern.length()];
            for (int j = 0; j < pattern.length(); j++) {
                skips[j] = computeSkip(c, j);
            }
            printRow(c, skips);
        }

        // print the default row with incremental skip values
        Integer[] defaults = new Integer[pattern.length()];
        for (int j = 0; j < pattern.length(); j++) {
            defaults[j] = j + 1;
        }
        printRow('*', defaults);
    }

    // prints a row starting with a prefix followed by comma-separated values
    private void printRow(char prefix, Object[] values) {
        System.out.print(prefix);
        for (Object value : values) {
            System.out.print("," + value);
        }
        System.out.println();
    }

    // computes how far to skip when character c mismatches at position pos
    private int computeSkip(char c, int pos) {
        if (pattern.charAt(pos) == c) {
            return 0; // match means no shift needed
        }
        if (pos == 0) {
            return 1; // mismatch at start, shift by one
        }

        // start with the LPS value from the previous position
        int k = lps[pos - 1];
        // backtrack through LPS until we find a prefix where c matches or hit zero
        while (k > 0 && pattern.charAt(k) != c) {
            k = lps[k - 1];
        }
        // if c matches at the prefix position k, shift is the distance from pos to k
        // otherwise, shift fully past the current position
        return (pattern.charAt(k) == c) ? pos - k : pos + 1;
    }
}