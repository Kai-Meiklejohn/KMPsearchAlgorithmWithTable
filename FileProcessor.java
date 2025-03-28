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
    private final KMPSearcher searcher; // searcher instance for pattern matching

    /**
     * sets up processor with a KMP searcher for the pattern
     * @param pattern string to search for
     */
    public FileProcessor(String pattern) {
        // initialize skip table and searcher
        SkipTable skipTable = new SkipTable(pattern);
        this.searcher = new KMPSearcher(skipTable.getPattern(), skipTable.getSkipTable());
    }

    /**
     * reads file line-by-line and prints pattern matches with line numbers
     * @param filename file to process
     */
    public void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            // read each line
            while ((line = reader.readLine()) != null) {
                lineNum++;
                ArrayList<Integer> matches = searcher.search(line);
                // output matches if any
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
        }
        // handle file reading errors
        catch (IOException e) {
            System.err.println("error reading file '" + filename + "': " + e.getMessage());
        }
    }
}