package ua.asymcrypto;

import ua.asymcrypto.model.Ciphertext;
import ua.asymcrypto.model.Rabin;
import ua.asymcrypto.model.RabinKey;
import ua.asymcrypto.model.SignedMessage;

import java.math.BigInteger;

public class App
{
    public static void main( String[] args )
    {
        Rabin rabin = new Rabin();
        RabinKey key = rabin.generateKey();

        System.out.println(key);

        BigInteger plainText = new BigInteger("123456789ABCDEF", 16);
        //BigInteger plainText = new BigInteger("1234F", 16);

        System.out.println(plainText.toString(16));

        BigInteger formattedPlainText = rabin.formatePlainText(plainText);

        System.out.println(formattedPlainText.toString(16));


        Ciphertext ciphertext = rabin.encrypt(formattedPlainText);
        System.out.println(ciphertext);

        System.out.println("--------");



        System.out.println(rabin.decrypt(ciphertext).toString(16));

        SignedMessage sign = rabin.sign(plainText);
        System.out.println(sign);
        System.out.println(rabin.verify(sign));


    }
}
