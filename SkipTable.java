// SkipTable.java
// computes and prints the kmp skip table for a target string without lps
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.util.TreeSet;

/**
 * handles kmp skip table computation and printing without lps array
 */
public class SkipTable {
    private final String pattern;
    private final int[][] skipTable; // 2d array: [char index][position]

    /**
     * initializes skip table with pattern and builds 2d skip array
     * @param pattern the string to generate the skip table for
     * @throws IllegalArgumentException if pattern is null or empty
     */
    public SkipTable(String pattern) {
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
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char c : pattern.toCharArray()) {
            alphabet.add(c);
        }

        // fill table for each position and character
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

        // check prefixes that could align after mismatch
        for (int k = pos - 1; k >= 0; k--) {
            boolean isPrefixMatch = true;
            // compare prefix (0 to k) with suffix (pos-k to pos)
            for (int i = 0; i < k; i++) {
                if (pattern.charAt(i) != pattern.charAt(pos - k + i)) {
                    isPrefixMatch = false;
                    break;
                }
            }
            if (isPrefixMatch && pattern.charAt(k) == letter) {
                return pos - k; // shift to align prefix
            }
        }
        return pos + 1; // no matching prefix, shift fully past
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

        // print rows for unique characters
        for (char letter : alphabet) {
            System.out.print(letter);
            int charIndex = letter - 'a';
            for (int pos = 0; pos < pattern.length(); pos++) {
                System.out.print("," + skipTable[charIndex][pos]);
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
}