// KMPsearch.java
// Main entry point for the KMP search program, handles command-line arguments and delegates tasks
// Name: Kai Meiklejohn
// ID: 1632448
// Solo project

public class KMPsearch {
    public static void main(String[] args) {
        // check how many args we got
        if (args.length == 1) {
            String target = args[0];
            // just one arg means skip table time
            SkipTable skipTable = new SkipTable(target);
            skipTable.printSkipTable();
        } else if (args.length == 2) {
            // two args, so we are searching a file
            String target = args[0];
            String filename = args[1];
            FileProcessor fileProcessor = new FileProcessor(filename, target);
            // solo mode, first hit only
            fileProcessor.processFile(false);
        } else {
            // throw error to useer
            System.out.println("Usage: java KMPsearch \"target\" [filename.txt]");
        }
    }
}