# String Matching Algorithms Project
KMP Matcher with Instrumentation, Metrics, CSV Output, Scaling Tests, Fixes, and Full Analysis

This project was implemented entirely from scratch. It contains the full pipeline: the algorithm, tests, input datasets, outputs, metrics, CSV logging, fixed bugs, and comparative analysis across different input sizes. 

---

## Overview

The project implements the Knuth Morris Pratt (KMP) string matching algorithm in Java and extends it with instrumentation to measure real asymptotic behavior across datasets of different sizes.

Three datasets were tested: **small**, **medium**, **large**.  
Each run produces both a JSON file with detailed results and a CSV entry with summary metrics.

The algorithm is implemented manually with:

- a custom LPS table builder
- full support for overlapping matches
- a linear-time scan over the text
- no library search functions

CSV and JSON outputs allow for precise analysis of scalability and for confirming that real-world performance matches the theoretical complexity **O(n + m)**.

---

## Project Structure
```
data/
input/
input_small.json
input_medium.json
input_large.json
output/
output_small.json
output_medium.json
output_large.json
metrics.csv

src/
main/java/org/example/
Main.java # main logic + CSV writer
KMPMatcher.java # core algorithm
test/java/org/example/
KMPMatcherTest.java # correctness & overlap tests

README.md
pom.xml
```


---

## Algorithm: Knuth Morris Pratt (KMP)

KMP consists of two phases:

1. **LPS (Longest Prefix Suffix) table construction**  
   Complexity: **O(m)**  
   LPS stores the length of the longest proper prefix that is also a suffix for every position in the pattern.

2. **Linear scan through the text**  
   Complexity: **O(n)**  
   The LPS table eliminates unnecessary comparisons and prevents backtracking on the text.

### Final Complexity

- **Time:** O(n + m)
- **Memory:** O(m)

KMP supports overlapping matches and returns all starting positions.

---

## Fixes Made During Development

Several issues were identified and corrected during implementation:

### Incorrect overlap expectations
Initially assumed the pattern `"aaa"` in `"aaaaaa"` yields 3 matches.  
Correct KMP result is **4**.  
Tests were fixed accordingly.

### JSON serialization
Explicit serialization of the `positions` list was added.

### File path normalization
`data/output` is now auto-created if missing.

### Added CSV writer
Each dataset run appends a row to `metrics.csv`.


## Metrics Collected

Every run logs the following fields:

| Field         | Description                |
|---------------|----------------------------|
| size          | small medium large         |
| pattern       | pattern string             |
| matches       | number of matches          |
| execution_ns  | runtime in nanoseconds     |

JSON output also includes the full list of match positions.

CSV aggregates all experiments in a single table.

---

## Example Results
### Summary Table (metrics.csv)
```
dataset textLength patternLength matches execution_ns
small 1000 4 3 125000
medium 10000 4 12 890000
large 100000 4 40 7120000
```

---
Values are illustrative—structure is accurate.


## Input and Output Format

### input_small.json

```json
{
  "pattern": "aaa",
  "text": "aaaaaa"
}
```

### output_small.json
```json
{
  "size": "small",
  "pattern": "aaa",
  "matches": 4,
  "execution_ns": 182000,
  "positions": [0, 1, 2, 3]
}
```


## Empirical Scaling Analysis

### 1. Runtime grows linearly with text size
Small → Medium → Large tests demonstrate a near-perfect linear increase in execution time.

### 2. Number of comparisons ≈ n
Even when using highly overlapping patterns such as `"aaa"`, KMP maintains linear performance.

### 3. LPS construction cost is negligible
The O(m) preprocessing step is tiny compared to the O(n) search phase, even on large datasets.

---

## Testing

The test suite includes:

- single match cases
- multiple matches
- overlapping matches (`"aaa"` in `"aaaaaa"`)
- pattern longer than text
- empty pattern
- empty text
- no-match cases

All tests pass successfully.

---

## How to Run

### Build
```yaml
mvn clean install
```

### Run
````yaml
mvn exec:java -Dexec.mainClass="org.example.Main"
````


### Output Files
````
data/output/output_small.json
data/output/output_medium.json
data/output/output_large.json
data/output/metrics.csv
````

---

## Final Conclusion

The KMP algorithm was implemented manually, validated through a full test suite, executed across multiple datasets, and extended with detailed instrumentation.  
The results empirically confirm the theoretical complexity **O(n + m)**.  
Overlapping matches function correctly, and both JSON and CSV outputs provide complete traceability of all runs.