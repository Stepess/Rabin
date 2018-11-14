package ua.asymcrypto.util;

public class RadixUtil {
    private static final int NUMBER_OF_BITS_IN_HEX_BASE = 4;
    private static final int HEX_BASE = 0xF;
    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String decToHex(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > HEX_BASE) {
            int hexDigit = num & HEX_BASE;
            sb.append(HEX_DIGITS[hexDigit]);
            num >>= NUMBER_OF_BITS_IN_HEX_BASE;
        }
        sb.append(HEX_DIGITS[num]);
        return sb.reverse().toString();
    }

}
