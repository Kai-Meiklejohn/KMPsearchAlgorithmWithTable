// SkipTable.java
// Computes and prints the KMP skip table for a given target string
// Name: Kai Meiklejohn
// ID: 1632448
// Solo project

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SkipTable {
    private final String pattern;
    private final int[] lps; // Longest Prefix Suffix array

    public SkipTable(String pattern) {
        this.pattern = pattern;
        this.lps = computeLPSArray();
    }

    // Compute the LPS array for the pattern
    private int[] computeLPSArray() {
        int[] lps = new int[pattern.length()];
        int length = 0; // Length of the previous longest prefix suffix
        int i = 1;

        lps[0] = 0; // First position has no prefix/suffix

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Print the skip table as per assignment specification
    public void printSkipTable() {
        // Get unique characters in the pattern
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : pattern.toCharArray()) {
            uniqueChars.add(c);
        }
        Character[] chars = uniqueChars.toArray(new Character[0]);
        Arrays.sort(chars); // Sort alphabetically

        // Print pattern row
        System.out.print("*");
        for (int i = 0; i < pattern.length(); i++) {
            System.out.print("," + pattern.charAt(i));
        }
        System.out.println();

        // Print rows for each unique character
        for (char c : chars) {
            System.out.print(c);
            for (int j = 0; j < pattern.length(); j++) {
                int skip = computeSkip(c, j);
                System.out.print("," + skip);
            }
            System.out.println();
        }

        // Print default row for characters not in pattern
        System.out.print("*");
        for (int j = 0; j < pattern.length(); j++) {
            System.out.print("," + (j + 1)); // Default skip is position + 1
        }
        System.out.println();
    }

    // Compute skip distance for a character at a given position
    private int computeSkip(char c, int pos) {
        if (pattern.charAt(pos) == c) {
            return 0; // Match, no shift
        }
        if (pos == 0) {
            return 1; // No match at start, shift by 1
        }
        
        // Find the longest prefix that we can fall back to
        int k = lps[pos - 1];
        while (k > 0 && pattern.charAt(k) != c) {
            k = lps[k - 1];
        }
        
        if (pattern.charAt(k) == c) {
            return pos - k;
        } else {
            return pos + 1; // No prefix matches, shift fully
        }
    }
}