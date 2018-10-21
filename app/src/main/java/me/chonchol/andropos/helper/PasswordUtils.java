package me.chonchol.andropos.helper;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtils {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }


    public static byte[] hash(byte[] password, byte[] salt) throws Exception {
        SecretKeySpec skeySpec=new SecretKeySpec(password,"AES");
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,skeySpec);
        byte[] encrypted=cipher.doFinal(salt);
        return encrypted;
    }

//    public static byte[] hash(char[] password, byte[] salt) {
//        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
//        Arrays.fill(password, Character.MIN_VALUE);
//        try {
//            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            return skf.generateSecret(spec).getEncoded();
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new Error("Error while hashing a password: " + e.getMessage(), e);
//        } finally {
//            spec.clearPassword();
//        }
//    }

    public static String generateSecurePassword(String pass, String salt) throws Exception {
        String returnPass = null;

        byte[] securePass = hash(pass.getBytes(), salt.getBytes());

        returnPass = Base64.encodeToString(securePass, 1);

        return returnPass;
    }

    public static boolean verifyPassword(String inputPass, String dbPass, String salt) throws Exception{

        boolean returnValue = false;

        String newSecurePass = generateSecurePassword(inputPass, salt);

        returnValue = newSecurePass.equalsIgnoreCase(dbPass);

        return returnValue;

    }

}
