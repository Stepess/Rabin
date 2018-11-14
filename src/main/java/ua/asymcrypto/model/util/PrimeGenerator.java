package ua.asymcrypto.model.util;

import java.math.BigInteger;
import java.util.Random;

public class PrimeGenerator {

    public static BigInteger generateRandomPrimeIntegerWithBitLength(int bitLength) {
        return generateRandomPrimeBigIntegerWithBitLengthInRange(bitLength, bitLength);
    }

    public static BigInteger generateRandomPrimeBigIntegerWithBitLengthInRange(int minBitLength, int maxBitLength) {
        BigInteger two = BigInteger.valueOf(2);
        return generateRandomPrimeBigIntegerInRange(two.pow(minBitLength-1), two.pow(maxBitLength));
    }

    public static BigInteger generateRandomPrimeBigIntegerInRange(BigInteger min, BigInteger max) {
        BigInteger x = generateRandomBigIntegerInRange(min, max);
        BigInteger two = BigInteger.valueOf(2);
        BigInteger firstOddNumber;

        firstOddNumber = x.mod(two).equals(BigInteger.ZERO) ?
                x.add(BigInteger.ONE) : new BigInteger(String.valueOf(x));


        int searchBoundary = max.subtract(firstOddNumber).divide(two).intValue();

        for (int i=0; i<searchBoundary; i++) {
            firstOddNumber = firstOddNumber.add(BigInteger.valueOf(2*i));
            if (PrimeTests.isPrime(firstOddNumber)) {
                return firstOddNumber;
            }
        }

        return generateRandomPrimeBigIntegerInRange(min.subtract(BigInteger.ONE),
                max.add(BigInteger.ONE));
    }

    public static BigInteger generateRandomBigIntegerInRange(BigInteger min, BigInteger max) {
        BigInteger boundary = max.subtract(min).add(BigInteger.ONE);
        Random random = new Random(System.nanoTime());
        BigInteger result;

        do {
            result = new BigInteger(boundary.bitLength(), random).add(min);
        } while (result.compareTo(max) >= 0);

        return result;
    }

    private int generateRandomIntWithinRange(int min, int max) {
        Random random = new Random(System.nanoTime());
        return random.nextInt((max-min) + 1) + min;
    }
}
