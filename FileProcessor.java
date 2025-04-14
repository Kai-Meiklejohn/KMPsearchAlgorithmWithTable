// FileProcessor.java
// Reads a file and finds pattern occurrences in each line, outputting lines with "marmot" in the specified format
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Processes a file to locate and display pattern matches in the specified format.
 */
public class FileProcessor {
    private final KMPSearcher searcher; // searcher instance for pattern matching

    /**
     * sets up processor with a KMP searcher for the pattern
     * @param pattern string to search for
     */
    public FileProcessor(String pattern) {
        // initialise skip table and searcher
        SkipTable skipTable = new SkipTable(pattern);
        this.searcher = new KMPSearcher(skipTable.getPattern(), skipTable.getSkipTable());
    }

    /**
     * reads each line of a file and prints pattern matches with line number, first occurrence index, and the line
     * @param filename file to process
     */
    public void processFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                ArrayList<Integer> matches = searcher.search(line);
                if (!matches.isEmpty()) {
                    int firstMatch = matches.get(0) + 1; //1 based indexing
                    System.out.println("line: " + lineNumber + " index: " + firstMatch + " sentence: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("error reading file '" + filename + "': " + e.getMessage());
            System.exit(1);
        }
    }
}