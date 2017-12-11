package balbert.edu.orangecoastcollege.cs273.encryptedmessages;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by brendantyleralbert on 12/10/17.
 */

public class AESEncryption {
    public static String ALGORITHM = "AES";
    private SecretKey secretKey;
    private Cipher cipher;
    private SecureRandom rand;

    public AESEncryption() {
        try {
            cipher = Cipher.getInstance( ALGORITHM );
            rand = new SecureRandom();
            KeyGenerator keyGen = KeyGenerator.getInstance( ALGORITHM );
            keyGen.init( 256 );
            secretKey = keyGen.generateKey();
        } catch ( GeneralSecurityException gse ) {

        }
    }

    public String crypt( int opMode, String message, SecretKey key ) {
        try {
            cipher.init( opMode, key, rand);
            /* We use the ISO-8859-1 encoding standard to convert the array of bytes to a String.
                ISO-8859-1 is the default encoding standard for transmitting documents via the HTTP
                protocol with the MIME type; one-to-one mapping between a String and an array
                of bytes.
             */
            byte [] messageBytes = message.getBytes( "ISO-8859-1" );
            byte [] encodedBytes = cipher.doFinal( messageBytes );
            String encoded = new String( encodedBytes, "ISO-8859-1" );
            return encoded;
        } catch ( Exception e) {
            return null;
        }
    }

    public SecretKey getKey() { return secretKey; }

    public byte[] getKeyBytes() { return secretKey.getEncoded(); }
}
