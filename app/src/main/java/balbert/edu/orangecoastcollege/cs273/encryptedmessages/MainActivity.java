package balbert.edu.orangecoastcollege.cs273.encryptedmessages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * EncryptedMessages is an app to practice using some of the encryption functionality
 * available in the Android library.  When we start making apps that users will provide
 * sensitive information in, such as emails and passwords, we will want to encrypt that
 * data.  To not do so would be negligent, unprofessional, and potentially opens a can
 * of legal worms.
 *
 * MainActivity is the main and only controller for this EncryptedMessages app.
 *
 * The other two classes of this app are Model classes encapsulating two types of encryption
 * algorithms.
 *
 * AES, which stands for Advanced Encryption Standard, is a symmetric encryption
 * algorithm available in the Android library. Symmetric encryption means that the key used
 * to encrypt the plain message is the same as the key used to decrypt the encrypted message;
 * there is only one key.
 *
 * RSA, which stands for the fellows who invented it, Rivest, Shamir, and Adleman,
 * is asymmetric.  This means every person has two keys, one public, one private.  Only
 * with the correct private key can a message be read which was sent to a user's public key.
 */
public class MainActivity extends AppCompatActivity {
    public static final String MA = "MainActivity";
    //DONE (1): Create member variables for the AESEncryption and RSAEncryption classes you created earlier
    private AESEncryption aes;
    private RSAEncryption rsa;

    private TextView mDecryptedTextView;
    private EditText mOriginalEditText;
    private TextView mEncryptedTextView;


    /**
     * Instantiate each of the AESEncryption and RSAEncryption objects
     * test AES encryption and decryption
     * test RSA encryption and decryption
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DONE (2): Instantiate each of the AESEncryption and RSAEncryption objects
        aes = new AESEncryption();
        rsa = new RSAEncryption();

        /*  Start of AES encryption  */
        // testing encryption and decryption
        String original = "Encryption is fun";
        Log.w( MA, "original: " + original );
        String encrypted = aes.crypt( Cipher.ENCRYPT_MODE, original, aes.getKey() );
        Log.w( MA, "encrypted: " + encrypted);
        String decrypted = aes.crypt( Cipher.DECRYPT_MODE, encrypted, aes.getKey() );
        Log.w( MA, "decrypted: " + decrypted );

        // testing key distribution
        byte [] keyBytes = aes.getKeyBytes();
        // distribute keyBytes to a user
        String keyString = Arrays.toString( keyBytes );
        Log.w( MA, "original key: " + keyBytes + ": " + keyString );

        // having received keyBytes, reconstruct the key
        SecretKey reconstructedKey = new SecretKeySpec( keyBytes, "AES" );
        // check that the reconstructed key is the same as the original key
        byte [] bytesFromReconstructedKey = reconstructedKey.getEncoded();
        String stringFromReconstructedKey = Arrays.toString( bytesFromReconstructedKey );
        Log.w( MA, "reconstructed key: " + bytesFromReconstructedKey + ": " + stringFromReconstructedKey );

        /*  End of AES encryption */

        /*  Start of RSA encryption */

        // encrypt with public key, decrypt with private key
        String original1 = "Hello";
        Log.w( MA, "original1: " + original1 );
        String encrypted1 = rsa.crypt( Cipher.ENCRYPT_MODE, original1, rsa.getPublicKey() );
        Log.w( MA, "encrypted1: " + encrypted1 );
        String decrypted1 = rsa.crypt( Cipher.DECRYPT_MODE, encrypted1, rsa.getPrivateKey() );
        Log.w( MA, "decrypted1: " + decrypted1 );

        // encrypt with private key, decrypt with public key
        String original2 = "Hello";
        Log.w( MA, "original2: " + original2 );
        String encrypted2 = rsa.crypt( Cipher.ENCRYPT_MODE, original2, rsa.getPrivateKey() );
        Log.w( MA, "encrypted2: " + encrypted2 );
        String decrypted2 = rsa.crypt( Cipher.DECRYPT_MODE, encrypted2, rsa.getPublicKey() );
        Log.w( MA, "decrypted2: " + decrypted2 );

        /* End of RSA encryption */



        mOriginalEditText = (EditText) findViewById(R.id.string_original);
        mEncryptedTextView = (TextView) findViewById(R.id.string_encrypted);
        mDecryptedTextView = (TextView) findViewById(R.id.string_decrypted);



    }

    /**
     * DONE (3): Get the original text, encrypt it using the AESEncryption object
     * DONE (3): then put the result in the mEncryptedTextView.
     * DONE (3): Finally, decrypt the encrypted text and put the result in the mDecryptedTextView.
     * @param v
     */
    public void encryptAndDecryptAES(View v) {
        String original = mOriginalEditText.getText().toString();
        String encrypted = aes.crypt( Cipher.ENCRYPT_MODE, original, aes.getKey() );

        mEncryptedTextView.setText( encrypted );

        String decrypted = aes.crypt( Cipher.DECRYPT_MODE, encrypted, aes.getKey() );
        mDecryptedTextView.setText( decrypted );
    }

    /**
     * DONE (4): Get the original text, encrypt it using the RSAEncryption object, using RSA1 (private, then public key)
     * DONE (4): then put the result in the mEncryptedTextView.
     * DONE (4): Finally, decrypt the encrypted text and put the result in the mDecryptedTextView.
     * @param v
     */
    public void encryptAndDecryptRSA1(View v) {
        String original = mOriginalEditText.getText().toString();
        String encrypted = rsa.crypt( Cipher.ENCRYPT_MODE, original, rsa.getPrivateKey() );
        mEncryptedTextView.setText( encrypted );
        String decrypted = rsa.crypt( Cipher.DECRYPT_MODE, encrypted, rsa.getPublicKey() );
        mDecryptedTextView.setText( decrypted );
    }

    /**
     * DONE (5): Get the original text, encrypt it using the RSAEncryption object, using RSA2 (public, then private key)
     * DONE (5): then put the result in the mEncryptedTextView.
     * DONE (5): Finally, decrypt the encrypted text and put the result in the mDecryptedTextView.
     * // adding this change for new push to GitHub
     * @param v
     */
    public void encryptAndDecryptRSA2(View v) {
        String original = mOriginalEditText.getText().toString();
        String encrypted = rsa.crypt( Cipher.ENCRYPT_MODE, original, rsa.getPublicKey() );
        mEncryptedTextView.setText( encrypted );
        String decrypted = rsa.crypt( Cipher.DECRYPT_MODE, encrypted, rsa.getPrivateKey() );
        mDecryptedTextView.setText( decrypted );
    }
}