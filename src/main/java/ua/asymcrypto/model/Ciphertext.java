package ua.asymcrypto.model;

import java.math.BigInteger;

public class Ciphertext {
    private BigInteger y;
    private int c1;
    private int c2;

    public Ciphertext(BigInteger y, int c1, int c2) {
        this.y = y;
        this.c1 = c1;
        this.c2 = c2;
    }

    public BigInteger getY() {
        return y;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }

    @Override
    public String toString() {
        return "Ciphertext{" +
                "y=" + y.toString(16) + '\n' +
                "c1=" + c1 + '\n' +
                "c2=" + c2 + '\n' +
                '}';
    }
}
