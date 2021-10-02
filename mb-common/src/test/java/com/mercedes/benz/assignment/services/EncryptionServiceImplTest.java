package com.mercedes.benz.assignment.services;

import com.mercedes.benz.assignment.exceptions.EncryptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class EncryptionServiceImplTest {

    private RSAEncryptionServiceImpl encryptionService;

    @Mock
    private Cipher eCipher;

    @Mock
    private Cipher dCipher;

    @BeforeEach
    public void setUp() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        KeyPair keyPair = keyPair("C:/D/projects/mercedes-benz/mercedes-benz-test/mb-common/src/main/resources/keypair", "RSA");
        encryptionService = new RSAEncryptionServiceImpl(keyPair, "RSA");
    }

    @Test
    public void testEncrypt() throws EncryptionException {
        String plainText = "test";
        String encryptedText = encryptionService.encrypt(plainText);
        assertNotEquals(encryptedText, plainText);
    }

    @Test
    public void testDecrypt() throws Exception {
        String plainText = "test";
        String encryptedText = encryptionService.encrypt(plainText);
        assertEquals(encryptionService.decrypt(encryptedText).trim(), plainText);
    }

    private KeyPair keyPair(String path, String algorithm)
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        // Read Public Key.
        File filePublicKey = new File(path + "/public.key");
        FileInputStream fis = new FileInputStream(path + "/public.key");
        byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
        fis.read(encodedPublicKey);
        fis.close();

        // Read Private Key.
        File filePrivateKey = new File(path + "/private.key");
        fis = new FileInputStream(path + "/private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        // Generate KeyPair.
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                encodedPublicKey);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
    }
}
