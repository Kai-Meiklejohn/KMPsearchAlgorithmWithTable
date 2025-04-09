# KMPsearch Assignment
Name: Kai Meiklejohn  
Student ID: 1632448  
Solo project implementing the Knuth-Morris-Pratt (KMP) string search algorithm.

## Overview
This project implements the Knuth-Morris-Pratt (KMP) string search algorithm in Java for plain-text files. The program searches for a target substring within a text file or displays a skip table for the target string, using a 2D skip table approach. It processes files line-by-line and outputs matching lines with their occurrence indices.

1. Computing the KMP skip table (Longest Prefix Suffix array) for efficient searching.
2. Processing the file to find and output lines containing the target substring.

The program consists of four components:
- `KMPsearch.java` - Main wrapper that handles arguments and coordinates execution
- `KMPSearcher.java` - Core KMP algorithm implementation
- `SkipTable.java` - Generates and prints the skip table
- `FileProcessor.java` - Reads the file and processes search results

The program supports two modes:
- Printing the skip table when given only a target string.
- Searching a file and printing each line containing the target (first occurrence only, as per solo spec).

## Usage Instructions
Compile all components: `javac *.java*`

Run:
- Print skip table: `java KMPsearch "target"`
- Search file: `java KMPsearch "target" filename.txt`

Arguments:
- `"target"`: The substring to search for (in quotes if it contains spaces or special characters).
- `filename.txt`: The text file to search (optional).

Examples:
- `java KMPsearch "kokako"` (prints the skip table for "kokako").
- `java KMPsearch "whale" MobyDick.txt > results.txt` (searches MobyDick.txt for "whale").

## Implementation Details

#### KMPsearch
The main controller program that:
1. Parses command-line arguments: either a single target string or a target string followed by a filename.
2. If one argument is provided (target string):
   - Instantiates `SkipTable` to compute and print the skip table for the target.
3. If two arguments are provided (target and filename):
   - Instantiates `FileProcessor` to search for the target in the specified file.
4. Handles errors (e.g., incorrect number of arguments or invalid input) by printing an error message and usage instructions: `"usage: java KMPsearch \"target\" [filename.txt]"`.

#### SkipTable
Generates and displays the KMP skip table without using an LPS array:
1. Takes a target string and constructs a 2D skip table (`int[256][pattern.length]`) for all ASCII characters (0-255) at each position in the pattern.
2. Computes skip distances dynamically:
   - For each position and character, determines the number of positions to skip by checking for prefix-suffix alignments up to that point.
   - If the character matches at the current position, skip is 0; otherwise, it calculates the shift based on the longest matching prefix or defaults to skipping past the current position.
3. Prints the skip table in CSV format:
   - First row: `"*"` followed by the pattern characters.
   - Rows for each unique character in the pattern (sorted alphabetically): character followed by skip values for each position.
   - Default row: `"*"` followed by full-shift values (`position + 1`) for characters not in the pattern.

#### KMPSearcher
Implements the core KMP string searching algorithm using a precomputed 2D skip table:
1. Takes a pattern and its skip table as input, validating that the table matches the pattern length and covers all ASCII characters (256 rows).
2. Searches for all occurrences of the pattern in a given text:
   - Advances pointers in text and pattern on character matches.
   - On a full match, records the starting index and resets the pattern pointer.
   - On a mismatch, uses the skip table to determine how to adjust the pointers:
     - Skips forward in the text if the skip value exceeds the current pattern position.
     - Backtracks in the pattern if the skip value is less than the current position.
     - Handles non-ASCII characters by skipping them and resetting the pattern pointer.
3. Returns an `ArrayList` of all match starting indices (0-based).

#### FileProcessor
Handles file reading and output formatting:
1. Initializes with a pattern, creating a `SkipTable` and `KMPSearcher` instance for searching.
2. Reads the specified file line-by-line using `BufferedReader`.
3. For each line:
   - Uses `KMPSearcher` to find all occurrences of the pattern.
   - If matches are found, prints: `"line X (Y): Z, W, ..."` where `X` is the line number, `Y` is the match count, and `Z, W, ...` are the 0-based indices of matches.
4. Gracefully handles I/O errors by printing an error message with the filename and exception details.

---

## Tools and Resources Used
- [KMP Algorithm Tutorial](https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/)

## Pseudocode (High-Level Description)
Below is a plain-English breakdown of the program’s algorithm, split into four components that work together to implement the KMP string search.

#### KMPsearch (Main Wrapper)
**Purpose:** Coordinate execution based on user input.

**Algorithm:**
- Check command-line arguments (1 or 2 args allowed).
- If one arg:
  - Pass target string to `SkipTable` to build and print the skip table.
- If two args:
  - Pass target and filename to `FileProcessor` to search the file.
- If invalid args:
  - Print usage message: "usage: java KMPsearch \"target\" [filename.txt]" and exit.

#### KMPSearcher (KMP Algorithm)
**Purpose:** Perform efficient string searching using KMP.

**Algorithm:**
- Build 2D skip table for target string:
  - For each position and lowercase letter (a-z):
    - If match: Set skip to 0.
    - Else: Compute shift by finding longest prefix that aligns with a suffix.
- Search text for target:
  - Compare characters between text and pattern.
  - On match: Advance both pointers; record full matches.
  - On mismatch: Use skip table to shift text or pattern pointers.
- Return array of all found indices.

#### SkipTable (Skip Table Generation)
**Purpose:** Create and display the KMP skip table.

**Algorithm:**
- Build 2D skip table for target string:
  - For each position and lowercase letter:
    - Compute skip distance by checking prefix-suffix matches.
- Print table:
  - Pattern row: List target characters prefixed with "*".
  - For each unique char in target (sorted alphabetically):
    - Print row with char and skip values for each position.
  - Default row: Print full-shift values (position + 1) prefixed with "*".
- Format all rows as comma-separated values.

#### FileProcessor (File Search)
**Purpose:** Process file and output matching lines.

**Algorithm:**
- Open input file.
- For each line:
  - Use `KMPSearcher` to find all target occurrences.
  - If target found:
    - Print line number, match count, and all occurrence indices (0-based).
- Close file and handle any I/O errors with error messages.