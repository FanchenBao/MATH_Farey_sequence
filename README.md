# MATH_Farey_sequence
Algorithm to produce Farey sequence of the given limit and given range.

## Description:
The Farey_sequence class produces a Farey sequence based on the given limit for the denominator and the given range (must be within [0, 1]). User has the following interface:
1. **Farey_sequence(int limit)**: Upon initialization, construct a Farey sequence with limit being the max denominator and the range being default [0, 1]
2. **Farey_sequence(int limit, int beginN, int beginD, int endN, int endD)**: Upon initialization, construct a Farey sequence with limit being the max denominator and the range being default [beginN/beginD, endN/endD]. The boundaries must satisfy the following conditions: 0 <= beginN/beginD <= endN/endD <= 1; after making both boundaries into proper reduced fraction form, max(beginD, endD) <= limit
3. **void printSeq()**: Print the Farey sequence.
4. **int getSize()**: Get the size of the Farey sequence.
5. **long[] getFrac(int pos)**: Get the fraction's numerator and denominator at any given position in the Farey sequence in the long array returned. The first element in the returned array is the numerator, the second denominator. The pos passed to the function must be a valid position within the range [0, getSize()).

## Usage:
```java
// create a Farey sequence with max denominator 8 and in the default range of [0, 1]
Farey_sequence fs1 = new Farey_sequence(8); 

// create a Farey sequence with max denominator 100 and in range of [1/5, 1/2]
Farey_sequence fs2 = new Farey_sequence(100, 1, 5, 1, 2);

// print Farey sequence produced in object fs1
fs1.printSeq();

// get each fraction's numerator and denominator in the Farey sequence
for (int i = 0; i < fs2.getSize(); i++){ 
    long[] f = fs2.getFrac(i);
    System.out.println("Numerator: " + f[0] + "/" + "Denominator: " + f[1]);
}
```
