package com.jjhhh.dice;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {

    private String keyHalf1 = "DhCjWZKQmOjDwRfIm3QekA==";

    private String keyHalf2 = "V2DHa/739Iu2pl+A+ERr2Q==";

    private String ivHalf1 = "zOW9S+1jY7Dd1bRq7HIfvg==";

    private String ivHalf2 = "j67aPqYXO8WknNkIvktT9w==";

    public String decrypt(String encryptedString) {
        try {
            IvParameterSpec iv = new IvParameterSpec(getOriginalIv().getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(getOriginalKey().getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] decodedString = Base64.decode(encryptedString, Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(decodedString);
            String decryptedString = new String(decryptedBytes);
            return decryptedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getOriginalKey() {
        byte[] half1 = Base64.decode(keyHalf1, 0);
        byte[] half2 = Base64.decode(keyHalf2, 0);

        byte[] key = new byte[half1.length];
        for(int i = 0; i < half1.length; i++) {
            key[i] = (byte) (half1[i] ^ half2[i]);
        }

        System.out.println("Key: " + new String(key));

        return new String(key);
    }

    private String getOriginalIv() {
        byte[] half1 = Base64.decode(ivHalf1, 0);
        byte[] half2 = Base64.decode(ivHalf2, 0);

        byte[] key = new byte[half1.length];
        for(int i = 0; i < half1.length; i++) {
            key[i] = (byte) (half1[i] ^ half2[i]);
        }

        System.out.println("Iv: " + new String(key));

        return new String(key);
    }
}

