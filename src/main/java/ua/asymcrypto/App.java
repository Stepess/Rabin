package ua.asymcrypto;

import ua.asymcrypto.model.Ciphertext;
import ua.asymcrypto.model.Rabin;
import ua.asymcrypto.model.RabinKey;
import ua.asymcrypto.model.SignedMessage;

import java.math.BigInteger;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Rabin rabin = new Rabin();
        RabinKey key = rabin.generateKey();

        System.out.println(key);

        BigInteger plainText = new BigInteger("123456789ABCDEF", 16);
        //BigInteger plainText = new BigInteger("1234F", 16);

        /*System.out.println(plainText.toString(16));

        BigInteger formattedPlainText = rabin.formatePlainText(plainText);

        System.out.println(formattedPlainText.toString(16));


        Ciphertext ciphertext = rabin.encrypt(formattedPlainText);
        System.out.println(ciphertext);

        System.out.println("--------");



        System.out.println(rabin.decrypt(ciphertext).toString(16));*/

        SignedMessage sign = rabin.sign(plainText);
        System.out.println(sign);
        System.out.println(rabin.verify(sign));


        System.out.println("--------");
        BigInteger b = new BigInteger("1358", 16);
        System.out.println(plainText.toString(16));
        Ciphertext encrypt = rabin.encrypt(plainText, b);
        System.out.println(encrypt);

        System.out.println(rabin.decrypt(encrypt, b).toString(16));


        System.out.println("enter modulus: ");
        Scanner scanner = new Scanner(System.in);

        BigInteger modulus = new BigInteger(scanner.nextLine(), 16);
        System.out.println("enter b: ");
        BigInteger bs = new BigInteger(scanner.nextLine(), 16);

        System.out.println(rabin.encrypt(plainText, b, modulus));

        System.out.println("enter signature ");
        SignedMessage signedMessage = new SignedMessage(plainText, new BigInteger(scanner.nextLine(), 16));
        System.out.println(rabin.verify(signedMessage, modulus));
    }
}
