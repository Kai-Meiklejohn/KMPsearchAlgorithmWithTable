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
     * entry point: handles args to either print skip table or search a file
     * @param args command-line arguments: "target" or "target filename.txt"
     */
    public static void main(String[] args) {
        try {
            // check if one argument is provided
            if (args.length == 1) {
                String target = args[0];
                SkipTable skipTable = new SkipTable(target);
                skipTable.printSkipTable(); // print skip table for target
            }
            // check if two arguments are provided
            else if (args.length == 2) {
                String target = args[0];
                String filename = args[1];
                FileProcessor processor = new FileProcessor(target);
                processor.processFile(filename); // search file for target
            }
            // invalid number of arguments
            else {
                printUsage(); // show usage instructions
            }
        }
        // catch invalid inputs like null or empty pattern
        catch (IllegalArgumentException e) {
            System.err.println("error: " + e.getMessage());
            printUsage(); // show usage on error
        }
    }

    /**
     * prints usage instructions for running the program
     */
    private static void printUsage() {
        System.out.println("usage: java KMPsearch \"target\" [filename.txt]");
    }
}