package com.nadunkawishika.helloshoesapplicationserver.util;

import java.util.Random;

public class GenerateId {
    public static String getId(String preFix) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(preFix);
        sb.append("-");
        sb.append(System.currentTimeMillis()); // Append current timestamp


        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        String string = sb.toString();
        return string.substring(0, 11);

    }
}
