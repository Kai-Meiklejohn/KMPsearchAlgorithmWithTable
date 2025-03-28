// KMPsearch.java
// Runs the kmp search program and handles command-line inputs
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

/**
 * main class to process command-line args and trigger kmp operations
 */
public class KMPsearch {
    public static void main(String[] args) {
        try {
            // check for valid number of arguments
            // if one argument, print skip table for target string
            if (args.length == 1) {
                String target = args[0];
                // create and print skip table for target string
                SkipTable skipTable = new SkipTable(target);
                skipTable.printSkipTable();
            // if two arguments, search for pattern in file
            } else if (args.length == 2) {
                String target = args[0];
                String filename = args[1];
                // process file and search for pattern
                FileProcessor processor = new FileProcessor(target);
                processor.processFile(filename);
            // invalid number of arguments
            } else {
                printUsage();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("error: " + e.getMessage());
            printUsage();
        }
    }

    // prints usage instructions for the program
    private static void printUsage() {
        System.out.println("usage: java KMPsearch \"target\" [filename.txt]");
    }
}