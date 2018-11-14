package ua.asymcrypto;

import ua.asymcrypto.model.Ciphertext;
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

        System.out.println(formattedPlainText);


        Ciphertext ciphertext = rabin.encrypt(formattedPlainText);
        System.out.println(ciphertext);
        System.out.println(ciphertext.getY());

        System.out.println("--------");

        //NumberUtil.calculateSquareRootFromBloomsNumberMod(plainText, BigInteger.valueOf(5), BigInteger.valueOf(3));


        System.out.println(rabin.decrypt(ciphertext));
        




    }
}
