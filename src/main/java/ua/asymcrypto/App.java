package ua.asymcrypto;

import ua.asymcrypto.model.Rabin;
import ua.asymcrypto.model.RabinKey;

import java.math.BigInteger;

public class App
{
    public static void main( String[] args )
    {
        Rabin rabin = new Rabin();
        RabinKey key = rabin.generateKey();

        System.out.println(key);

        BigInteger plainText = new BigInteger("123456789ABCDEF", 16);

        System.out.println(plainText.toString(16));

        BigInteger formattedPlainText = rabin.formatePlainText(plainText);

        System.out.println(formattedPlainText.toString(16));
    }
}
