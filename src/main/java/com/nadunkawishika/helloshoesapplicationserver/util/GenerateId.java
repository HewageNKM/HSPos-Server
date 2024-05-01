package com.nadunkawishika.helloshoesapplicationserver.util;

import java.util.Random;

public class GenerateId {
    private static final Random random = new Random();

    public static String getId(String preFix) {
        StringBuilder stringBuilder = new StringBuilder(preFix);
        stringBuilder.append("-");
        stringBuilder.append(System.currentTimeMillis()); // Append current timestamp


        for (int i = 0; i < 3; i++) {
            stringBuilder.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        String string = stringBuilder.toString();
        return string.substring(0, 11);
    }
}
