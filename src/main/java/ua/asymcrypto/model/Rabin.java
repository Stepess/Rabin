package ua.asymcrypto.model;

import ua.asymcrypto.model.util.PrimeGenerator;
import ua.asymcrypto.model.util.PrimeTests;

import java.math.BigInteger;

public class Rabin {
    private RabinKey key;

    public RabinKey generateKey() {
        BigInteger four = BigInteger.valueOf(4);
        BigInteger three = BigInteger.valueOf(3);
        BigInteger p, q;

        do {
            p = PrimeGenerator.generateRandomPrimeIntegerWithBitLength(256).multiply(four).add(three);
        } while (!PrimeTests.isPrime(p));

        do {
            q = PrimeGenerator.generateRandomPrimeIntegerWithBitLength(256).multiply(four).add(three);
        } while (!PrimeTests.isPrime(q));

        key = new RabinKey(p, q);
        return key;
    }

    public BigInteger formatePlainText(BigInteger text) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger result;
        int byteLengthOfN = getNumberLengthInBytes(key.getN());

        if (getNumberLengthInBytes(text) <= (byteLengthOfN - 10)) {
            result = two.pow((byteLengthOfN - 8)*8).multiply(BigInteger.valueOf(255))
                    .add(two.pow(64).multiply(text))
                    .add(PrimeGenerator.generateRandomPrimeIntegerWithBitLength(64));
        } else {
            result = text;
        }

        return result;
    }

    private int getNumberLengthInBytes(BigInteger number) {
        return (int)Math.ceil((number.bitLength()*1.0)/8);
    }
}
