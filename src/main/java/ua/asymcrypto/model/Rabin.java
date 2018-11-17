package ua.asymcrypto.model;

import ua.asymcrypto.model.util.NumberUtil;
import ua.asymcrypto.model.util.PrimeGenerator;
import ua.asymcrypto.model.util.PrimeTests;

import java.math.BigInteger;

public class Rabin {
    private RabinKey key;

    public Ciphertext encrypt(BigInteger plaintext) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger textToEncrypt = formatePlainText(plaintext);
        //BigInteger textToEncrypt = plaintext;
        return new Ciphertext(
                textToEncrypt.modPow(two, key.getN()),
                getParityBit(textToEncrypt),
                NumberUtil.calculateIversonSymbol(NumberUtil.calculateJacobiSymbol(textToEncrypt, key.getN())));
    }

    private int getParityBit(BigInteger num) {
        BigInteger two = BigInteger.valueOf(2);
        return num.mod(two).intValue();
    }

    public BigInteger decrypt(Ciphertext ciphertext) {
        BigInteger[] roots = NumberUtil.calculateSquareRootFromBloomsNumberMod(ciphertext.getY(), key.getP(), key.getQ());
        BigInteger result = null;
        for (BigInteger root: roots) {
            if ((getParityBit(root) == ciphertext.getC1()) &&
                    (NumberUtil.calculateIversonSymbol(NumberUtil.calculateJacobiSymbol(root, key.getN())) == ciphertext.getC2())) {
                result = root;
                break;
            }
        }

        return result;
    }

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
