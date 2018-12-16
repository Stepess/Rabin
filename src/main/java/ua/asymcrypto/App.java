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
        //BigInteger plainText = new BigInteger("1234F", 16);

        System.out.println(plainText.toString(16));

        BigInteger formattedPlainText = rabin.formatePlainText(plainText);

        System.out.println(formattedPlainText.toString(16));


        Ciphertext ciphertext = rabin.encrypt(formattedPlainText);
        System.out.println(ciphertext);

        System.out.println("--------");

        //NumberUtil.calculateSquareRootFromBloomsNumberMod(plainText, BigInteger.valueOf(5), BigInteger.valueOf(3));


        System.out.println(rabin.decrypt(ciphertext).toString(16));


        //System.out.println(Arrays.toString(NumberUtil.calculateSquareRootFromBloomsNumberMod(BigInteger.valueOf(16), BigInteger.valueOf(7), BigInteger.valueOf(11))));





    }
}
