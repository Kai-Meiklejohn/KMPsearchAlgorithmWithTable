// SkipTable.java
// Computes and prints the KMP skip table for a given target string
// Name: Kai Meiklejohn
// ID: 1632448
// Solo project

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SkipTable {
    private final String pattern;
    private final int[] lps; // Longest Prefix Suffix array

    public SkipTable(String pattern) {
        this.pattern = pattern;
        this.lps = new int[this.pattern.length()];
        constructLps(this.pattern, this.lps);
    }

    static void constructLps(String pat, int[] lps) {
        
        // len stores the length of longest prefix which 
        // is also a suffix for the previous index
        int len = 0;

        // lps[0] is always 0
        lps[0] = 0;

        int i = 1;
        while (i < pat.length()) {
            
            // If characters match, increment the size of lps
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            
            // If there is a mismatch
            else {
                if (len != 0) {
                    
                    // Update len to the previous lps value 
                    // to avoid redundant comparisons
                    len = lps[len - 1];
                } 
                else {
                    
                    // If no matching prefix found, set lps[i] to 0
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    static ArrayList<Integer> search(String pat, String txt) {
        int n = txt.length();
        int m = pat.length();

        int[] lps = new int[m];
        ArrayList<Integer> res = new ArrayList<>();

        constructLps(pat, lps);

        // Pointers i and j, for traversing 
        // the text and pattern
        int i = 0;
        int j = 0;

        while (i < n) {
            // If characters match, move both pointers forward
            if (txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;

                // If the entire pattern is matched 
                // store the start index in result
                if (j == m) {
                    res.add(i - j);
                    
                    // Use LPS of previous index to 
                    // skip unnecessary comparisons
                    j = lps[j - 1];
                }
            }
            
            // If there is a mismatch
            else {
                
                // Use lps value of previous index
                // to avoid redundant comparisons
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }
        return res;
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