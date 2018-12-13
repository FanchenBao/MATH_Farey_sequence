import java.util.*;
import java.lang.*; // for Math.log10() and Math.pow()

/*
Author: Fanchen Bao
Date: 12/13/2018


*/

public class Farey_sequence
{

    public static void main(String[] args){
        long startTime = System.nanoTime();

        // p73 sol = new p73();
        
        int limit = 12000;
        int a = 1, b = 3; // most left fraction in the Farey sequence
        int c, d; // c/d is the adjacent fraction to the right of a/b
        int c0 = 1, d0 = 2; // Any fraction that satisfies b*c0 - a*d0 = 1 and c0 <= a, d0 <= b. 
                            // The purpose of c0/d0 is to calculate the first c/d adjacent to a/b.
                            // Once we have the first adjacent pair of a/b and c/d in the Farey sequence, all the remaining fractions can be generated
        int k; // k is coefficient

        // generate the first c/d. The equation is acquired from b*c0 - a*d0 = 1 = b*c - a*d, given gcd(a, b) = 1 and d <= limit < b + d 
        k = (limit - d0) / b; // integer division
        c = k * a + c0;
        d = k * b + d0;

        // generate the rest of the Farey sequence with limit = 12000, and each next fraction is adjacent to the previous one
        // until c = 1, d = 2 (end condition). Of course, count the number of fraction generated along the way.
        int count = 0;
        int e, f; // the next adjacent fraction to c/d

        // formula for the generation of the next adjancet fraction e/f is as follows.
        // Since a/b < c/d < e/f, and all three are adjacent in the current Farey sequence, we have
        // (a + e)/(b + f) = c/d. Since gcd(c,d) = 1, we have a + e = k*c, b + f = k*d.
        // Since c/d and e/f are adjacent, then the mediant in between them must have denominator d + f > limit.
        // Thus, b + f = k*d <= b + limit < b + d + f = b + k*d = (k + 1)*d
        // k*d <= b + limit < (k + 1)*d
        // k <= (b + limit) / d < k + 1
        // So k = (b + limit) / d with integer division
        while (c != 1 && d != 2){
            count++;
            k = (b + limit) / d; // integer division
            e = k * c - a; // next c
            f = k * d - b; // next d
            a = c; // updating a/b and c/d, move them to the next position
            b = d;
            c = e;
            d = f;
        }

        System.out.println(count);

        // runtime = 0.08 s




        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000000.0;  //divide by 1000000 to get milliseconds.
        System.out.println("\nRuntime: " + duration + " s");

    }
}