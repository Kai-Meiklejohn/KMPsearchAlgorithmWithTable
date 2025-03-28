// FileProcessor.java
// Reads a file and finds pattern occurrences in each line
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Processes a file to locate and display pattern matches.
 */
public class FileProcessor {
    private final KMPSearcher searcher; // Searcher instance for pattern matching

    /**
     * Sets up processor with a KMP searcher for the pattern.
     * @param pattern String to search for
     */
    public FileProcessor(String pattern) {
        SkipTable skipTable = new SkipTable(pattern);
        this.searcher = new KMPSearcher(skipTable.getPattern(), skipTable.getSkipTable());
    }

    /**
     * Reads file line-by-line and prints pattern matches with line numbers.
     * @param filename File to process
     */
    public void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                ArrayList<Integer> matches = searcher.search(line);
                if (!matches.isEmpty()) {
                    // Print matches in format: "line X (Y): Z, W, ..."
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