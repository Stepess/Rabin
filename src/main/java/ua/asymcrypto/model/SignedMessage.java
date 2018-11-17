package ua.asymcrypto.model;

import java.math.BigInteger;

public class SignedMessage {
    private BigInteger text;
    private BigInteger signature;

    public SignedMessage(BigInteger text, BigInteger signature) {
        this.text = text;
        this.signature = signature;
    }

    public BigInteger getText() {
        return text;
    }

    public BigInteger getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "SignedMessage{" +
                "text=" + text.toString(16) + '\n' +
                ", signature=" + signature.toString(16) + '\n' +
                '}';
    }
}
