package com.jjhhh.dice;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {

    private String[] keyHalves = {"yolhg+O/7klcs8je3rO7nw==", "uuUn7IjZoXoewYPppP/dzA=="};

    private String[] ivHalves = {"COpJW/I+CCW9RmZi3rgywA==", "YdMDFKMHUnHRPw9XiO1Vpw=="};

    public String decrypt(String encryptedString) {
        try {
            IvParameterSpec iv = new IvParameterSpec(getOriginal(0).getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(getOriginal(1).getBytes("UTF-8"), "AES");
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

    private String getOriginal(int mode) {
        byte[] half1;
        byte[] half2;
        if (mode == 0) {
            half1 = Base64.decode(ivHalves[0], 0);
            half2 = Base64.decode(ivHalves[1], 0);
        } else {
            half1 = Base64.decode(keyHalves[0], 0);
            half2 = Base64.decode(keyHalves[1], 0);
        }
        byte[] key = new byte[half1.length];

        int i = 0;
        int size = half1.length;
        int swVar = 0;

        while (swVar >= 0) {
            switch (swVar) {
                case 0:
                    if(size > 0 && half1.length == half2.length) {
                        key[i] = (byte) (half1[i] ^ half2[i]);
                    } else {
                        half1[i] = (byte) (key[i] ^ half2[i]);
                    }
                    swVar = 1;
                    break;
                case 1:
                    i++;
                    if (i < size) {
                        swVar = 0;
                    } else {
                        swVar = -1;
                    }
                    break;
            }
        }

        return new String(key);
    }
}

