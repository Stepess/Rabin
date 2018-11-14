package ua.asymcrypto.util;

import java.math.BigInteger;

public class PrimeTests {

    private static final int[] PRIME_NUMBERS = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41,
            43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199};

    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static boolean isPrime(BigInteger num) {
        if (!checkForTrialDivisionMethod(num)) {
            return false;
        }
        return checkMillerRabinTest(num, 5);
    }


    public static boolean checkForTrialDivisionMethod(BigInteger num) {
        for (int prime: PRIME_NUMBERS) {
            if (num.mod(BigInteger.valueOf(prime)).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMillerRabinTest(BigInteger num, int trustLevel) {
        int counter = 0;

        //present our number as d*2^s
        int s = 0;
        BigInteger d = new BigInteger(String.valueOf(num));
        d = d.subtract(BigInteger.ONE);

        while (d.mod(TWO).equals(BigInteger.ZERO)) {
            s++;
            d = d.divide(TWO);
        }

        while (counter < trustLevel) {
            BigInteger x = PrimeGenerator.generateRandomBigIntegerInRange(BigInteger.ONE, num);
            if(x.gcd(num).compareTo(BigInteger.ONE) == 1) {
                return false;
            }
            if (isStrongPseudoPrime(num, x, d, s)) {
                counter++;
            } else {
                return false;
            }
        }

        return true;
    }

    private static boolean isStrongPseudoPrime(BigInteger num, BigInteger base, BigInteger d, int s) {
        BigInteger minusOneByModuleNum = num.subtract(BigInteger.ONE);
        BigInteger temp = base.modPow(d, num);

        if (temp.equals(BigInteger.ONE) || temp.equals(minusOneByModuleNum)) {
            return true;
        }

        BigInteger xr = new BigInteger(String.valueOf(base));
        for (int r=1; r<s; r++) {
            xr = xr.modPow(TWO, num);
            if (xr.equals(minusOneByModuleNum)){
                return true;
            }
            if (xr.equals(BigInteger.ONE)) {
                return false;
            }
        }

        return false;
    }
}
