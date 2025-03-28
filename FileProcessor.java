// FileProcessor.java
// reads a file and finds pattern occurrences in each line
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * processes file to locate and display pattern matches
 */
public class FileProcessor {
    private final KMPSearcher searcher;

    /**
     * sets up processor with a kmp searcher for the pattern
     * @param pattern the string to search for
     */
    public FileProcessor(String pattern) {
        this.searcher = new KMPSearcher(pattern);
    }

    // reads file and prints pattern occurrences with line numbers
    public void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                ArrayList<Integer> matches = searcher.search(line);
                if (!matches.isEmpty()) {
                    System.out.print("line " + lineNum + " (" + matches.size() + "): ");
                    for (int i = 0; i < matches.size(); i++) {
                        System.out.print(matches.get(i));
                        if (i < matches.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("error reading file '" + filename + "': " + e.getMessage());
        }
    }
}