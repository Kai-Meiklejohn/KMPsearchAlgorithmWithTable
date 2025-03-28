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
            if (args.length == 1) {
                String target = args[0];
                SkipTable skipTable = new SkipTable(target);
                skipTable.printSkipTable();
            } else if (args.length == 2) {
                String target = args[0];
                String filename = args[1];
                FileProcessor processor = new FileProcessor(target); // Skip table built in FileProcessor
                processor.processFile(filename);
            } else {
                printUsage();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("error: " + e.getMessage());
            printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("usage: java KMPsearch \"target\" [filename.txt]");
    }
}