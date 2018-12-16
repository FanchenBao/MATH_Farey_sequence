import java.util.*;

/*
Author: Fanchen Bao
Date: 12/13/2018

The following algorithm produces a Farey sequence with a given upper limit (limit) and within
the range beginN/beginD to endN/endD (inclusive, user given range must be within [0, 1]). The given range itself must be part of the Farey sequence.

The algorithm is based on three principles:
1. Two reduced fractions 0 <= a/b < c/d <= 1 are neighbors in some Farey sequence iff b*c - a*d = 1.
2. If b*c - a*d = 1, the unique fracction between a/b and c/d with the smallest denominator is the mediant (a+b)/(c+d).
3. If a/b < c/d < e/f are three consecutive Farey sequence fractions, then c/d = (a+e)/(b+f)

See https://en.wikipedia.org/wiki/Farey_sequence

*/


public class Farey_sequence
{
    public class Fraction implements Comparable<Fraction>{ // wrapper class
        private int n, d;

        // constructor
        public Fraction(int num, int den){
            if (den == 0){
                System.out.println("Error! Denominator cannot be 0");
                System.exit(0);
            }
            n = num; d = den;
        }

        @Override
        public String toString() { // for printing purpose
            return String.format(n + "/" + d); 
        }

        @Override
        public int compareTo(Fraction otherFraction){
            Double thisValue = (double)n / d;
            Double otherValue = (double)otherFraction.n / otherFraction.d;
            return thisValue.compareTo(otherValue);
        }

        public int getNumerator(){return n;}
        public int getDenominator(){return d;}
    };
    
    private ArrayList<Fraction> fareySequence; // array of Farey sequence fractions



    /**
    * Constructor. Constructing full Farey sequence from 0 to 1
    * @param limit Largest numerator allowed in the Farey sequence
    * @return none
    */
    public Farey_sequence(int limit){
        fareySequence = new ArrayList<Fraction>();
        makeFareySequence(limit, new Fraction(0, 1), new Fraction(1, 1));
    }

    /**
    * Constructor. Constructing Farey sequence from beginN/beginD to endN/endD
    * @param limit Largest numerator allowed in the Farey sequence
    * @param beginN Numerator of the lower bound fraction
    * @param beginD Denominator of the lower bound fraction. Lower bound fraction must be part of the Farey sequence.
    * @param endN Numerator of the upper bound fraction
    * @param endD Denominator of the upper bound fraction. Upper bound fraction must be part of the Farey sequence.
    * @return none
    */
    public Farey_sequence(int limit, int beginN, int beginD, int endN, int endD){
        fareySequence = new ArrayList<Fraction>();
        // turn begin and end into proper fraction
        int beginGCD = GCD(beginN, beginD);
        int endGCD = GCD(endN, endD);
        Fraction lowerBound = new Fraction(beginN/beginGCD, beginD/beginGCD);
        Fraction upperBound = new Fraction(endN/endGCD, endD/endGCD);
        
        if (!checkBound(limit, lowerBound, upperBound))
            System.exit(0);

        makeFareySequence(limit, lowerBound, upperBound);
    }

    /**
    * Print the generated farey sequence.
    */
    public void printSeq(){ 
        System.out.println(fareySequence);
    }

    /**
    * Get the total number of fractions in the sequence
    */
    public int getSize(){
        return fareySequence.size();
    }
 
    /**
    * Get the fraction from the generated Farey sequence at the given position.
    * @param pos Fraction of the Farey sequence on the pos will be returned
    * @return A long array of size two with first element being the numerator and second the denominator of the specific fraction
    */
    public long[] getFrac(int pos){
        long[] res = new long[2];
        if (pos >= fareySequence.size() || pos < 0){
            System.out.println("Error, index out of range.");
            return res;
        }
        res[0] = fareySequence.get(pos).getNumerator();
        res[1] = fareySequence.get(pos).getDenominator();
        return res;
    }



    private int GCD(int a, int b) { return b==0 ? a : GCD(b, a%b); }

    /**
    * Check whether the given upper and lower bound are legal
    * @param limit Largest denominator allowed in the current Farey sequence
    * @param lowerBound a Fraction object representing the fraction value of lower bound.
    * @param upperBound a Fraction object representing the fraction value of upper bound.
    * @return A boolean value true if upper and loewr bound are in the range of [0, 1], lower bound < upper bound, 
    *           and the denominator in the most reduced form of both upper and lower bound does not exceed limit
    */
    private boolean checkBound(int limit, Fraction lowerBound, Fraction upperBound){
        // check user input
        if (lowerBound.compareTo(upperBound) > 0){
            System.out.println("Error in range. Lower bound must be smaller or equal to upper bound");
            return false;
        }
        if (lowerBound.compareTo(new Fraction(0, 1)) <= 0){
            System.out.println("Error in range. Lower bound must be non-negative");
            return false;
        }
        if (upperBound.compareTo(new Fraction(1, 1)) > 0){
            System.out.println("Error in range. Upper bound must not be bigger than 1");
            return false;
        }
        if (lowerBound.getDenominator() > limit || upperBound.getDenominator() > limit){
            System.out.println("Error in range. Denominators of lower or upper bound cannot exceed limit");
            return false;   
        }

        return true;
    }

    
    /**
    * Produce Farey sequence with the given limit, its fraction ranges from lowerBound to upperBound (inclusive). 
    * The produced sequence is stored in the private class field fareySequence.
    * @param limit Largest denominator allowed in the current Farey sequence
    * @param lowerBound a Fraction object representing the fraction value of lower bound.
    * @param upperBound a Fraction object representing the fraction value of upper bound.
    * @return none
    */
    private void makeFareySequence(int limit, Fraction lowerBound, Fraction upperBound){
        fareySequence.add(lowerBound);

        int a, b; // most left fraction in the Farey sequence
        if (lowerBound.getNumerator() == 0){ // when lowerBound is 0, we must use the next fraction as starting point
            fareySequence.add(new Fraction(1, limit));
            a = 1;
            b = limit; 
        }
        else{
            a = lowerBound.getNumerator();
            b = lowerBound.getDenominator();
        }

        int c, d; // c/d is the adjacent fraction to the right of a/b
        int c0 = a, d0 = b - 1/a; // Any fraction that satisfies b*c0 - a*d0 = 1 and c0 <= a, d0 <= b. Here we choose c0 = a, d0 = b - 1/a for convenience.
                                    // The purpose of c0/d0 is to calculate the first c/d adjacent to a/b.
                                    // Once we have the first adjacent pair of a/b and c/d in the Farey sequence, all the remaining fractions can be generated
        int k; // k is coefficient

        // generate the first c/d. The equation is acquired from b*c0 - a*d0 = 1 = b*c - a*d, given gcd(a, b) = 1 and d <= limit < b + d 
        k = (limit - d0) / b; // integer division
        c = k * a + c0;
        d = k * b + d0;

        // generate the rest of the Farey sequence with the given limit and each next fraction is adjacent to the previous one
        // until c = 1, d = 2 (end condition). Of course, count the number of fraction generated along the way.
        int e, f; // the next adjacent fraction to c/d

        // formula for the generation of the next adjancet fraction e/f is as follows.
        // Since a/b < c/d < e/f, and all three are adjacent in the current Farey sequence, we have
        // (a + e)/(b + f) = c/d. Since gcd(c,d) = 1, we have a + e = k*c, b + f = k*d.
        // Since c/d and e/f are adjacent, then the mediant in between them must have denominator d + f > limit.
        // Thus, b + f = k*d <= b + limit < b + d + f = b + k*d = (k + 1)*d
        // k*d <= b + limit < (k + 1)*d
        // k <= (b + limit) / d < k + 1
        // So k = (b + limit) / d with integer division
        while (c != upperBound.getNumerator() || d != upperBound.getDenominator()) {
            fareySequence.add(new Fraction(c, d));
            k = (b + limit) / d; // integer division
            e = k * c - a; // next c
            f = k * d - b; // next d
            a = c; // updating a/b and c/d, move them to the next position
            b = d;
            c = e;
            d = f;
        }
        fareySequence.add(upperBound); // last fraction
    }
}