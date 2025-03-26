// KMPsearch.java
// Main entry point for the KMP search program, handles command-line arguments and delegates tasks
// Name: Kai Meiklejohn
// ID: 1632448
// Solo project

public class KMPsearch {
    public static void main(String[] args) {
        if (args.length == 1) {
            // Print skip table for target string
            String target = args[0];
            SkipTable skipTable = new SkipTable(target);
            skipTable.printSkipTable();
        } else if (args.length == 2) {
            // Placeholder for file search (to be implemented later)
            String target = args[0];
            String filename = args[1];
            System.out.println("File search not implemented yet for: " + target + " in " + filename);
        } else {
            System.out.println("Usage: java KMPsearch \"target\" [filename.txt]");
        }
    }
}