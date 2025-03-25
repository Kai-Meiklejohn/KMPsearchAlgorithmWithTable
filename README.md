# KMPsearch Assignment
Name: Kai Meiklejohn  
Student ID: 1632448  
Solo project implementing the Knuth-Morris-Pratt (KMP) string search algorithm.

## Overview
This project implements the Knuth-Morris-Pratt (KMP) string search algorithm in Java for plain-text files, as specified for a solo solution. It searches for a target substring in a text file line-by-line by:

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
Compile all components: `javac KMPsearch.java KMPSearcher.java SkipTable.java FileProcessor.java`

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

### KMPsearch
The main controller program that:
1. Parses command-line arguments
2. Launches `SkipTable` to print the skip table if only a target is provided
3. Launches `FileProcessor` to search the file if both target and filename are provided
4. Handles error cases (e.g., incorrect arguments)

### KMPSearcher
Implements the core KMP algorithm:
1. Computes the Longest Prefix Suffix (LPS) array for the target string
2. Performs the string search, returning indices of occurrences
3. Optimized to skip unnecessary comparisons using the LPS array

### SkipTable
Generates and displays the KMP skip table:
1. Takes the target string and LPS array from `KMPSearcher`
2. Prints a formatted table with rows for the pattern, unique characters, and default case
3. Ensures alphabetical ordering of character rows as per spec

### FileProcessor
Handles file reading and output:
1. Reads the input file line-by-line
2. Uses `KMPSearcher` to find the target in each line
3. Prints each matching line once, prefixed with the first occurrence index (1-based)
4. Manages file I/O errors gracefully

## Tools and Resources Used
- [KMP Algorithm Tutorial](https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/)
- [KMP Table](https://stackoverflow.com/questions/13792118/kmp-prefix-table)

## Pseudocode (High-Level Description)
Below is a plain-English breakdown of the program’s algorithm, split into four components that work together to implement the KMP string search.

### KMPsearch (Main Wrapper)
**Purpose:** Coordinate execution based on user input.

**Algorithm:**
- Check command-line arguments (1 or 2 args allowed)
- If one arg:
  - Pass target string to `SkipTable` to print skip table
- If two args:
  - Pass target and filename to `FileProcessor` to search file
- If invalid args:
  - Print usage message: "Usage: java KMPsearch \"target\" [filename.txt]"

### KMPSearcher (KMP Algorithm)
**Purpose:** Perform efficient string searching using KMP.

**Algorithm:**
- Build LPS array for target string:
  - Compare characters to find longest prefix that is also a suffix
  - Store lengths in array for skipping
- Search text for target:
  - Use LPS to skip mismatches efficiently
  - Record all occurrence indices (solo mode uses first only)
- Return array of found indices

### SkipTable (Skip Table Generation)
**Purpose:** Create and display the KMP skip table.

**Algorithm:**
- Get target string and LPS array
- Print pattern row (target chars prefixed with "*")
- For each unique char in target (sorted alphabetically):
  - Compute skip values using LPS and position
  - Print row with char and skip distances
- Print default row (for chars not in target, prefixed with "*")
- Format all rows as comma-separated values

### FileProcessor (File Search)
**Purpose:** Process file and output matching lines.

**Algorithm:**
- Open input file
- For each line:
  - Use `KMPSearcher` to find target occurrences
  - If target found:
    - Print line prefixed with first occurrence index (1-based)
- Close file and handle any I/O errors
- (Solo mode: only first occurrence per line printed)
