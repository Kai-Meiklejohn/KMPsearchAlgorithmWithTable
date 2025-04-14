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
1. Parses command-line arguments: either a single target string or a target string plus a filename.
2. If one argument is provided (target string):
   - Instantiates `SkipTable` to compute and print the skip table for the target.
3. If two arguments are provided (target and filename):
   - Instantiates `FileProcessor` to search for the target in the specified file.
4. Handles errors (e.g., incorrect number of arguments or invalid input) by printing an error message and usage instructions: `"usage: java KMPsearch \"target\" [filename.txt]"`.

#### SkipTable
Generates and displays the KMP skip table without using a standard LPS array:
1. Takes a target string and constructs a 2D skip table (`int[256][pattern.length()]`) for all ASCII characters (0-255) at each position in the pattern.
2. Computes skip distances dynamically:
   - For each position and character, determines how far to skip by checking for a prefix-suffix alignment if there’s a mismatch.
   - If the character matches at the current position, set skip to zero; otherwise, calculate the shift based on the longest matching prefix or default to skipping the full segment (`pos + 1`).
3. Prints the skip table in CSV format:
   - First row: `*` followed by the pattern’s characters.
   - Rows for each unique character in the pattern (sorted alphabetically): character plus skip values for each position.
   - Final row: `*` followed by full-shift values (`position + 1`) for characters not in the pattern.

#### KMPSearcher
Implements the core skip-based KMP string searching algorithm using the precomputed 2D skip table:
1. Takes a pattern and 2D skip table (covering all ASCII characters).
2. Searches for all occurrences of the pattern in a given text:
   - Compares characters while they match, advancing both text and pattern pointers.
   - If a full match occurs, records the starting index and advances in the text.
   - On a mismatch, looks up the skip distance for the mismatched character:
     - Ensures positions near the end of words (e.g., “afternoon”) get checked properly so no occurrence is missed.
   - Continues until no more matches can be found in the text.
3. Returns an `ArrayList<Integer>` of zero-based match starting indices.

#### FileProcessor
Handles file reading and output:
1. Initialises a `SkipTable` and `KMPSearcher`.
2. Reads the file line-by-line.
3. For each line:
   - Uses `KMPSearcher` to find occurrences of the pattern.
   - Prints the line number and first match position (one-based index), along with the line text if found.
4. Gracefully handles input/output errors by printing an error message with the filename and exception details.

---

## Tools and Resources Used
- [KMP Algorithm Tutorial](https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/)

## Pseudocode (High-Level Description)
Below is a plain-English description of the updated program’s algorithm, broken into four components.

#### KMPsearch (Main Wrapper)
**Purpose:** Coordinate execution based on user input.

**Algorithm:**
- Parse command-line arguments (1 or 2).
- If one arg:
  - Create a `SkipTable` for the target and print it.
- If two args:
  - Create a `FileProcessor` with the target and search the file.
- If invalid args:
  - Print usage message and exit.

#### KMPSearcher (KMP Algorithm)
**Purpose:** Perform efficient string searching using a skip-based approach.

**Algorithm:**
- Use the skip table to handle mismatches.
- Compare characters in text and pattern:
  - On match: Continue comparing next characters.  
  - On mismatch: Jump ahead based on the skip table.  
- Record all match indices.

#### SkipTable (Skip Table Generation)
**Purpose:** Create a 2D skip table for quick mismatch handling.

**Algorithm:**
- For each position in the pattern and each ASCII character:
  - If character matches pattern at position, skip is 0.
  - Else, find the largest prefix that matches a suffix before this position.
- Print results in CSV format.

#### FileProcessor (File Search)
**Purpose:** Read a file and show lines that match the target pattern.

**Algorithm:**
- Open the file.
- For each line, run the KMP search.
- Print line number and index of the first occurrence if matches are found.
- Close the file, handling any I/O errors.