package ua.asymcrypto.model.util;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

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

    @Test
    public void testRoots() {
        BigInteger num = new BigInteger(String.valueOf(16L));
        BigInteger modP = new BigInteger(String.valueOf(3L));
        BigInteger modQ = new BigInteger(String.valueOf(7L));
        System.out.println(Arrays.toString(NumberUtil.calculateSquareRootFromBloomsNumberMod(num, modP, modQ)));
    }
}