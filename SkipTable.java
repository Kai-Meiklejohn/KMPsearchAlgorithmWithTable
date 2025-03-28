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

    public SkipTable(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern cannot be null or empty");
        }
        this.pattern = pattern;
        this.skipTable = buildSkipTable();
    }

    private int[][] buildSkipTable() {
        int len = pattern.length();
        int[][] table = new int[52][len]; // A-Z (0-25), a-z (26-51)
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char c : pattern.toCharArray()) {
            alphabet.add(c);
        }

        for (int pos = 0; pos < len; pos++) {
            for (char c = 'A'; c <= 'Z'; c++) {
                int charIndex = c - 'A';
                table[charIndex][pos] = computeSkip(c, pos);
            }
            for (char c = 'a'; c <= 'z'; c++) {
                int charIndex = 26 + (c - 'a');
                table[charIndex][pos] = computeSkip(c, pos);
            }
        }
        return table;
    }

    private int computeSkip(char letter, int pos) {
        if (pattern.charAt(pos) == letter) {
            return 0;
        }
        if (pos == 0) {
            return 1;
        }
        for (int k = pos - 1; k >= 0; k--) {
            boolean isPrefixMatch = true;
            for (int i = 0; i < k; i++) {
                if (pattern.charAt(i) != pattern.charAt(pos - k + i)) {
                    isPrefixMatch = false;
                    break;
                }
            }
            if (isPrefixMatch && pattern.charAt(k) == letter) {
                return pos - k;
            }
        }
        return pos + 1;
    }

    public void printSkipTable() {
        TreeSet<Character> alphabet = new TreeSet<>();
        for (char letter : pattern.toCharArray()) {
            alphabet.add(letter);
        }
        System.out.print("*");
        for (char letter : pattern.toCharArray()) {
            System.out.print("," + letter);
        }
        System.out.println();
        for (char letter : alphabet) {
            System.out.print(letter);
            int charIndex = (letter >= 'A' && letter <= 'Z') ? (letter - 'A') : (26 + (letter - 'a'));
            for (int pos = 0; pos < pattern.length(); pos++) {
                System.out.print("," + skipTable[charIndex][pos]);
            }
            System.out.println();
        }
        System.out.print("*");
        for (int pos = 0; pos < pattern.length(); pos++) {
            System.out.print("," + (pos + 1));
        }
        System.out.println();
    }

    // New getter to expose the skip table
    public int[][] getSkipTable() {
        return skipTable;
    }

    // Getter for pattern (needed by KMPSearcher)
    public String getPattern() {
        return pattern;
    }
}