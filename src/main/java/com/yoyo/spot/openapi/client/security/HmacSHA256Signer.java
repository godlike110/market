package com.yoyo.spot.openapi.client.security;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

/**
 * Utility class to sign messages using HMAC-SHA256.
 */
public class HmacSHA256Signer {

    /**
     * Sign the given message using the given secret.
     *
     * @param message message to sign
     * @param secret  secret key
     * @return a signed message
     */
    public static String sign(String message, String secret) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256HMAC.init(secretKeySpec);
            return new String(Hex.encodeHex(sha256HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8))));
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }

//    public static String sign(String message,PrivateKey key) {
//
//        try {
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE, key);
//            byte[] cipherText = new BASE64Decoder().decodeBuffer(content);
//            byte[] decryptText = cipher.doFinal(cipherText);
//            return new String(decryptText);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Unable to sign message.", e);
//        }
//    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String sha256_HMAC_RSA(String message, PrivateKey key) throws InvalidKeyException, SignatureException {

        String hash = "";
        try {
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initSign(key);
            publicSignature.update(message.getBytes());
            // return Base64.getEncoder().encode(publicSignature.sign());
            hash = Base64.getUrlEncoder().encodeToString(publicSignature.sign());
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

}
