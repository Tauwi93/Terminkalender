package com.example.terminkalender;

import java.security.SecureRandom;


public class KeyGenerator {
    public static String generatePublicCode() {
        return generateRandomString(12);
    }

    public static String generatePrivateCode() {
        return generateRandomString(16);
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}


