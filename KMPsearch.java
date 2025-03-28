// KMPsearch.java
// Runs the kmp search program and handles command-line inputs
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

/**
 * Main class to process command-line args and trigger KMP operations.
 */
public class KMPsearch {
    /**
     * Entry point: handles args to either print skip table or search a file.
     * @param args Command-line arguments: "target" or "target filename.txt"
     */
    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                String target = args[0];
                SkipTable skipTable = new SkipTable(target);
                skipTable.printSkipTable(); // Print skip table for target
            } else if (args.length == 2) {
                String target = args[0];
                String filename = args[1];
                FileProcessor processor = new FileProcessor(target);
                processor.processFile(filename); // Search file for target
            } else {
                printUsage(); // Invalid args, show usage
            }
        } catch (IllegalArgumentException e) {
            System.err.println("error: " + e.getMessage());
            printUsage(); // Handle invalid input (e.g., null pattern)
        }
    }

    /**
     * Prints usage instructions for running the program.
     */
    private static void printUsage() {
        System.out.println("usage: java KMPsearch \"target\" [filename.txt]");
    }
}