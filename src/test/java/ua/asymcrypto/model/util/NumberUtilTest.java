package ua.asymcrypto.model.util;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class NumberUtilTest {

    @Test
    public void calculateJacobiSymbol() {
        final BigInteger numerator = BigInteger.valueOf(219);
        final BigInteger denominator = BigInteger.valueOf(383);
        int expected = 1;
        int actual = NumberUtil.calculateJacobiSymbol(numerator, denominator);
        assertEquals(expected, actual);
    }
}