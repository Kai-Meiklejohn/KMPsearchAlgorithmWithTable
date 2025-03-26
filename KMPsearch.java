// KMPsearch.java
// runs the KMP search program and handles command-line inputs
// Name: Kai Meiklejohn
// Student ID: 1632448
// Solo project

/**
 * main class to process command-line args and trigger KMP operations
 */
public class KMPsearch {
    // entry point for the program
    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                // handle single argument: print skip table
                String target = args[0];
                SkipTable skipTable = new SkipTable(target);
                skipTable.printSkipTable();
            } else if (args.length == 2) {
                // handle two arguments: search target in file (placeholder for now)
                String target = args[0];
                String filename = args[1];
                processFileSearch(target, filename);
            } else {
                // show usage message for invalid arg count
                printUsage();
            }
        } catch (IllegalArgumentException e) {
            // catch invalid pattern errors from SkipTable
            System.err.println("error: " + e.getMessage());
            printUsage();
        }
    }

    // processes file search (stub until FileProcessor is implemented)
    private static void processFileSearch(String target, String filename) {
        // todo: implement with FileProcessor and KMPSearcher
        System.out.println("file search not yet implemented for '" + target + "' in " + filename);
    }

    // prints usage instructions to console
    private static void printUsage() {
        System.out.println("usage: java KMPsearch \"target\" [filename.txt]");
    }
}