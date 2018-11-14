package ua.asymcrypto.model;

import java.util.Arrays;

public class ExtendedEuclid {

    //  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
    static int[] gcd(int p, int q) {
        if (q == 0)
            return new int[] { p, 1, 0 };
        ;

        int[] vals = gcd(q, p % q);
        System.out.println(Arrays.toString(vals));
        int d = vals[0];
        int a = vals[2];
        int b = vals[1] - (p / q) * vals[2];
        return new int[] { d, a, b };
    }

    public static void main(String[] args) {
        int p = 36163 ;
        int q = 21199;
        int vals[] = gcd(p, q);
        System.out.println(("gcd(" + p + ", " + q + ") = " + vals[0]));
        System.out.println((vals[1] + "(" + p + ") + " + vals[2] + "(" + q + ") = " + vals[0]));
    }
}

