package ua.asymcrypto.model;

import java.math.BigInteger;

public class RabinKey {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;

    public RabinKey() {
    }

    public RabinKey(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.n = p.multiply(q);
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    @Override
    public String toString() {
        return "RabinKey{" + '\n' +
                "p=" + p + '\n' +
                "q=" + q + '\n' +
                "n=" + n + '\n' +
                '}';
    }
}
