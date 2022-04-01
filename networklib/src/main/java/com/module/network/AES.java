package com.module.network;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author BD
 */
public class AES {

    private static final String IV_STRING = "A-16-Byte-String";
    private static final String KEY = "1111111111111111111";

    private AES() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 加密
     *
     * @param content 加密内容
     * @return 已经加密过的字符串
     */
    public static String encryptString(String content) throws Exception {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = aesEncryptBytes(contentBytes, keyBytes);
        return Base64.encode(encryptedBytes);
    }

    private static byte[] aesEncryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE);
    }

    private static byte[] cipherOperation(byte[] contentBytes, byte[] keyBytes, int mode) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        byte[] initParam = IV_STRING.getBytes(StandardCharsets.UTF_8);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, secretKey, ivParameterSpec);
        return cipher.doFinal(contentBytes);
    }

    /**
     * 解密
     *
     * @param content 解密内容
     * @return 已经解密过的字符串
     */
    public static String decryptString(String content) throws Exception {
        byte[] decode = Base64.decode(content);
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        byte[] decryptedBytes = aesDecryptBytes(decode, keyBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private static byte[] aesDecryptBytes(byte[] contentBytes, byte[] keyBytes) throws Exception {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE);
    }
}