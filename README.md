# The License Plate Problem

You work for the DMV; you have a specific, sequential way of generating new license plate numbers:

Each license plate number has **6 alphanumeric characters**. The numbers always come before the letters.

- The first plate number is `000000`, followed by `000001`.
- Finally, when you arrive at `999999`, the next entry would be `00000A`, followed by `00001A`.
- When you arrive at `99999A`, the next entry is `00000B`.
- After following the pattern to `99999Z`, the next in the sequence would be `0000AA`.
- When `9999AA` is reached, the next in the series would be `0000AB`...

### The pattern overview looks a bit like the following

```
000000  
000001  
...  
999999  
00000A  
00001A  
...  
99999A  
00000B  
00001B  
...  
99999Z  
0000AA  
0001AA  
...  
9999AA  
0000AB  
0001AB  
...  
9999ZZ  
000AAA  
001AAA  
...  
ZZZZZZ
```

## Goal

Write a function that takes some index `n` as a parameter and returns the `n`th element in this license plate sequence.

## Understand the problem and brainstorm to solve it

### 1. Understand the Pattern
* Plates are 6-character string
* The **first part** (rightmost) increment first, from `000000`to `999999`.
* Then comes letters: `00000A` to `99999Z`, `0000AA` to `9999ZZ`, and so on.
* **Letters appear in suffix blocks**, growing in length from 1 to 6.

### 2. Divide and Conquer approach
To solve the problem we had to think in terms of how many plates can we get, so in a naive approach it can lead us to error whether we try to calculate all combinations of numbers (10 in total) and letters (26 in total) combinations, i.e., sum the total numbers and letters (36) and multiply it by 6-character, e.g.:

$36 \times 36 \times 36 \times 36 \times 36 \times 36 = 36^6 = 2.176.782.336$

But, since the plates start with numbers and then we add letters from the rightmost to the leftmost, we have to follow another approach:

* Think in terms of:
  - How many plates have 0 letters? &rarr; $10^6 = 1.000.000$
  - How many plates have 1 letters? &rarr; $10^5 \times 26$
  - How many plates have 2 letters? &rarr; $10^5 \times 26^2$
  - ...and so on, until...
  - How many plates have 6 letters? &rarr; $26^2$

Where it lead us to the sum all those combinations in a way the we get this kind of formula: 

$```\sum_{k=0}^{6} \left(10^{6-k} \times 26^k\right)```$


This gives:

| **digits** | **k (letters)** | **plates for this level**             |
|------------|-----------------|----------------------------------------|
| 6          | 0               | $\(10^6 = 1.000.000\)$                 |
| 5          | 1               | $\(10^5 \times 26 = 2.600.000\)$       |
| 4          | 2               | $\(10^4 \times 26^2 = 6.760.000\)$     |
| 3          | 3               | $\(10^3 \times 26^3 = 17.576.000\)$    |
| 2          | 4               | $\(10^2 \times 26^4 = 45.697.600\)$    |
| 1          | 5               | $\(10^1 \times 26^5 = 118.813.760\)$   |
| 0          | 6               | $\(10^0 \times 26^6 = 308.915.776\)$   |

**Total:**

$```1.000.000 + 2.600.000 + 6.760.000 + 17.576.000 + 45.697.600 + 118.813.760 + 308.915.776 = \boxed{501.363.136}```$

Given us the correct combination that is $\boxed{501.363.136}$ different plates.

### 3. Strategy

The strategy to solve this problem is based in:

1. Determine which "block" (how many letters) `n` belongs to;
2. Get the numeric prefix (padded with zeros) and;
3. Get the alphabetical suffix (using base-26 mapping).

### 4. Implementation Plan

To creates a function that can solve this problem, we have to implement an algorithm that follows these steps:

1. Create a `getPlateNumber(long number)` method;
2. Define how many plates each block contains;
3. Identify the correct block for `number`;
4. Compute numeric and letter parts, and;
5. Format and return the final string;
6. Create unit tests for the class.

## File Structure

```
license-plate-challenge/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   └── java/com/plategen/LicensePlateGenerator.java
│   └── test/
│       └── java/com/plategen/LicensePlateGeneratorTest.java
```

---

## Pre-requisites

- Java 17 or later
- Maven 3.8+
- Docker (for containerized runs)

---

## Run Locally

To compile and run the project locally:

```bash
# Compile the project
mvn compile

# Run unit tests
mvn test

# Run the application
mvn exec:java -Dexec.mainClass="com.plategen.LicensePlateGenerator"
```

If `exec-maven-plugin` is not configured, you may need to add it or run via compiled `.class` files.

---

## Run with Docker

To build and run the application inside a Docker container:

```bash
# Build Docker image
docker build -t license-plate-generator .

# Run the container unit tests
docker run --rm -it license-plate-generator mvn test

#Run the container application
docker run --rm -it license-plate-generator mvn exec:java -Dexec.mainClass="com.plategen.LicensePlateGenerator"
```
