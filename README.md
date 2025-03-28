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
1. Parses command-line arguments.
2. Launches `SkipTable` to print the skip table if only a target is provided.
3. Launches `FileProcessor` to search the file if both target and filename are provided.
4. Handles error cases (e.g., incorrect arguments) with appropriate error messages.

#### KMPSearcher
Implements the core KMP algorithm:
1. Constructs a 2D skip table (`int[26][pattern.length]`) for lowercase letters (a-z) to determine shift distances.
2. Performs the string search by comparing characters and using the skip table to handle mismatches efficiently.
3. Returns all indices of occurrences in an `ArrayList`, optimized to skip unnecessary comparisons.

#### SkipTable
Generates and displays the KMP skip table:
1. Takes the target string and builds a 2D skip table without relying on an LPS array.
2. Prints a formatted table with rows for the pattern, unique characters in the pattern (alphabetically sorted), and a default case.
3. Computes skip values dynamically by finding prefix-suffix alignments for each character and position.

#### FileProcessor
Handles file reading and output:
1. Reads the input file line-by-line using `BufferedReader`.
2. Uses `KMPSearcher` to find all occurrences of the target in each line.
3. Prints each matching line with its line number, match count, and all occurrence indices (0-based).
4. Manages file I/O errors gracefully with descriptive error messages.

## Tools and Resources Used
- [KMP Algorithm Tutorial](https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/)
- [KMP Table](https://stackoverflow.com/questions/13792118/kmp-prefix-table)

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