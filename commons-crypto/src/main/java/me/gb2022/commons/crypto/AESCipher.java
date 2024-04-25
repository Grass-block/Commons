package me.gb2022.commons.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AESCipher implements CodecCipher{
    public static final String KEY_GEN_ID = "AES";
    public static final String CIPHER_INSTANCE_ID = "AES/ECB/PKCS5Padding";

    private final Cipher encoder;
    private final Cipher decoder;

    public AESCipher(String key) {
        SecretKeySpec k = createAESKey(key);
        try {
            this.encoder = javax.crypto.Cipher.getInstance(CIPHER_INSTANCE_ID);
            this.decoder = javax.crypto.Cipher.getInstance(CIPHER_INSTANCE_ID);
            this.encoder.init(javax.crypto.Cipher.ENCRYPT_MODE, k);
            this.decoder.init(Cipher.DECRYPT_MODE, k);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public byte[] encode(byte[] data) {
        try {
            return this.encoder.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decode(byte[] data) {
        try {
            return this.decoder.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    static SecretKeySpec createAESKey(String content) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_GEN_ID);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(content.getBytes(StandardCharsets.UTF_8));
            kg.init(128, random);
            return new SecretKeySpec(kg.generateKey().getEncoded(), KEY_GEN_ID);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
