package ua.asymcrypto.model;

import ua.asymcrypto.model.util.NumberUtil;
import ua.asymcrypto.model.util.PrimeGenerator;
import ua.asymcrypto.model.util.PrimeTests;

import java.math.BigInteger;
import java.util.Random;

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

    //expanded version
    public Ciphertext encrypt(BigInteger plaintext, BigInteger b) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger textToEncrypt = formatePlainText(plaintext);
        return new Ciphertext(
                textToEncrypt.multiply(textToEncrypt.add(b)).mod(key.getN()),
                getParityBit(textToEncrypt.add(b.divide(two)).mod(key.getN())),
                NumberUtil.calculateJacobiSymbol(textToEncrypt.add(b.divide(two)), key.getN()));
    }

    //expanded version stateless
    public Ciphertext encrypt(BigInteger plaintext, BigInteger b, BigInteger modulus) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger textToEncrypt = formatePlainText(plaintext, modulus);
        return new Ciphertext(
                textToEncrypt.multiply(textToEncrypt.add(b)).mod(modulus),
                getParityBit(textToEncrypt.add(b.divide(two)).mod(modulus)),
                NumberUtil.calculateJacobiSymbol(textToEncrypt.add(b.divide(two)), modulus));
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
        return deformatePlainText(result);
    }

    //expanded version
    public BigInteger decrypt(Ciphertext ciphertext, BigInteger b) {
        BigInteger[] roots = NumberUtil.calculateSquareRootFromBloomsNumberMod(ciphertext.getY().add(b.pow(2).divide(BigInteger.valueOf(4))), key.getP(), key.getQ());
        BigInteger result = null;
        for (BigInteger root: roots) {
            if ((getParityBit(root) == ciphertext.getC1()) &&
                    (NumberUtil.calculateJacobiSymbol(root, key.getN())) == ciphertext.getC2()) {
                result = root.subtract(b.divide(BigInteger.valueOf(2)));
                break;
            }
        }
        return deformatePlainText(result);
    }

    public SignedMessage sign(BigInteger text) {
        BigInteger textToSign = formatePlainText(text);

        /*if (NumberUtil.calculateJacobiSymbol(textToSign, key.getP()) != 1 ||
        NumberUtil.calculateJacobiSymbol(textToSign, key.getQ()) != 1) {
            return sign(text);
        }*/
        while (NumberUtil.calculateJacobiSymbol(textToSign, key.getP()) !=1 ||
                NumberUtil.calculateJacobiSymbol(textToSign, key.getQ()) != 1) {
            textToSign = formatePlainText(text);
        }

        BigInteger[] roots = NumberUtil.calculateSquareRootFromBloomsNumberMod(textToSign, key.getP(), key.getQ());

        Random random = new Random();

        int rootIndex = random.nextInt(4);

        return new SignedMessage(text, roots[rootIndex]);
    }

    //r is different each time, how will it be equals???

    public boolean verify(SignedMessage message) {
        BigInteger temp = message.getSignature().modPow(BigInteger.valueOf(2), key.getN());
        return deformatePlainText(temp).equals(message.getText());
    }

    public boolean verify(SignedMessage message, BigInteger modulus) {
        BigInteger temp = message.getSignature().modPow(BigInteger.valueOf(2), modulus);
        return deformatePlainText(temp, modulus).equals(message.getText());
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

       // if (getNumberLengthInBytes(text) <= (byteLengthOfN - 10)) {
            result = two.pow((byteLengthOfN - 8)*8).multiply(BigInteger.valueOf(255))// - 2
                    .add(two.pow(64).multiply(text))
                    .add(PrimeGenerator.generateRandomPrimeIntegerWithBitLength(64));
       // } else {
        //    result = text;
       // }

        return result;
    }

    public BigInteger formatePlainText(BigInteger text, BigInteger modulus) {
        BigInteger two = BigInteger.valueOf(2);
        BigInteger result;
        int byteLengthOfN = getNumberLengthInBytes(modulus);
        result = two.pow((byteLengthOfN - 8)*8).multiply(BigInteger.valueOf(255))// - 2
                .add(two.pow(64).multiply(text))
                .add(PrimeGenerator.generateRandomPrimeIntegerWithBitLength(64));
        return result;
    }

    public BigInteger deformatePlainText(BigInteger text) {
        BigInteger two = BigInteger.valueOf(2);
        int byteLengthOfN = getNumberLengthInBytes(key.getN());
        return text
                .mod(two.pow((byteLengthOfN - 8)*8))
                .divide(two.pow(64));
    }

    public BigInteger deformatePlainText(BigInteger text, BigInteger modulus) {
        BigInteger two = BigInteger.valueOf(2);
        int byteLengthOfN = getNumberLengthInBytes(modulus);
        return text
                .mod(two.pow((byteLengthOfN - 8)*8))
                .divide(two.pow(64));
    }

    private int getNumberLengthInBytes(BigInteger number) {
        return (int)Math.ceil((number.bitLength()*1.0)/8);
    }
}
