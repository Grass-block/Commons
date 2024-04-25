package me.gb2022.commons.math;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface SHA {
    static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }

    static String getSHA1(String painText, boolean uppercase) {
        return SHAImpl.getSha(painText, SHAImpl.SHA_1, uppercase);
    }

    static String getSHA224(String painText, boolean uppercase) {
        return SHAImpl.getSha(painText, SHAImpl.SHA_224, uppercase);
    }

    static String getSHA256(String painText, boolean uppercase) {
        return SHAImpl.getSha(painText, SHAImpl.SHA_256, uppercase);
    }

    static String getSHA384(String painText, boolean uppercase) {
        return SHAImpl.getSha(painText, SHAImpl.SHA_384, uppercase);
    }

    static String getSHA512(String painText, boolean uppercase) {
        return SHAImpl.getSha(painText, SHAImpl.SHA_512, uppercase);
    }

    interface SHAImpl {
        String SHA_1 = "SHA-1";
        String SHA_224 = "SHA-224";
        String SHA_256 = "SHA-256";
        String SHA_384 = "SHA-384";
        String SHA_512 = "SHA-512";
        char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private static String getSha(String plainText, String algorithm, boolean uppercase) {
            byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.update(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA签名过程中出现错误,算法异常");
            }
            byte[] digest = messageDigest.digest();
            String result = byteArrayToHexString(digest);
            return uppercase ? result.toUpperCase() : result;
        }
    }
}
