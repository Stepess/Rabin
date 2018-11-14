package ua.asymcrypto.model.util;

import java.math.BigInteger;
import java.util.Arrays;

public class NumberUtil {

    public static BigInteger[] calculateSquareRootFromBloomsNumberMod(BigInteger num, BigInteger modP, BigInteger modQ) {
        BigInteger[] result = new BigInteger[4];

        BigInteger s1 = num.modPow(modP.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), modP);
        BigInteger s2 = num.modPow(modQ.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), modQ);

        BigInteger[] duv = extendedEuclid(modP, modQ);

        result[0] = duv[1].multiply(modP).multiply(s1).add(duv[2].multiply(modQ).multiply(s2));
        result[1] = duv[1].multiply(modP).multiply(s1).add(duv[2].multiply(modQ).multiply(s2).multiply(BigInteger.valueOf(-1)));
        result[2] = duv[1].multiply(modP).multiply(s1).multiply(BigInteger.valueOf(-1)).add(duv[2].multiply(modQ).multiply(s2));
        result[3] = duv[1].multiply(modP).multiply(s1).multiply(BigInteger.valueOf(-1)).add(duv[2].multiply(modQ).multiply(s2).multiply(BigInteger.valueOf(-1)));

        return result;
    }

    public static BigInteger[] extendedEuclid(BigInteger p, BigInteger q) {
            if (q.equals(BigInteger.ZERO)) {
                return new BigInteger[]{p, BigInteger.ONE, BigInteger.ZERO};
            }

            BigInteger[] vals = extendedEuclid(q, p.mod(q));

            BigInteger d = vals[0];
            BigInteger a = vals[2];
            BigInteger b = vals[1].subtract(p.divide(q).multiply(vals[2]));

            return new BigInteger[] { d, a, b };
    }

    public static BigInteger extendedEuqlidAlgorithm(BigInteger a, BigInteger b, BigInteger[] uv) {

        if (b.equals(BigInteger.ZERO)) {
            uv[0] = BigInteger.ONE; uv[1] = BigInteger.ZERO;
            return b;
        }
        System.out.println("uv" + Arrays.toString(uv));
        BigInteger[] uv1 = new BigInteger[2];
        BigInteger d = extendedEuqlidAlgorithm(b, a.mod(b), uv1);
        System.out.println("uv12" + Arrays.toString(uv1));

        uv[0] = uv1[0].subtract(b.divide(a)).multiply(uv1[1]);
        uv[1] = uv1[0];

        return d;
    }

    public static int calculateIversonSymbol(int num) {
        return num == 1 ? 1 : 0;
    }

    public static int calculateJacobiSymbol(BigInteger numerator, BigInteger denominator) {
        final BigInteger eight = BigInteger.valueOf(8);
        final BigInteger five = BigInteger.valueOf(5);
        final BigInteger four = BigInteger.valueOf(4);
        final BigInteger three = BigInteger.valueOf(3);
        final BigInteger two = BigInteger.valueOf(2);

        if (!numerator.gcd(denominator).equals(BigInteger.ONE)) {
            return 0;
        }
        int r = 1;

        if (numerator.compareTo(BigInteger.ZERO) == -1) {
            numerator = numerator.multiply(BigInteger.valueOf(-1));
            if (denominator.mod(four).equals(three)) {
                r = -r;
            }
        }

        while (!numerator.equals(BigInteger.ZERO)) {
            int t = 0;
            while (numerator.mod(two).equals(BigInteger.ZERO)) {
                t++;
                numerator = numerator.divide(two);
            }
            if ((t & 1) == 1) {
                BigInteger buff = denominator.mod(eight);
                if (buff.equals(three) || buff.equals(five)) {
                    r = -r;
                }
            }

            if (numerator.mod(four).equals(denominator.mod(four)) && numerator.mod(four).equals(three)) {
                r = -r;
            }
            BigInteger c = numerator;
            numerator = denominator.mod(c);
            denominator = c;
        }

        return r;
    }
}
