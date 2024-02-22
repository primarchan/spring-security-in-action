package org.example.ssiach11ex1s1.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateCodeUtil {

    private GenerateCodeUtil() {}

    public static String generateCode() {
        String  code = "";

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();  // 임의의 값을 생성하는 SecureRandom 의 인스턴스를 만든다.
            int c = random.nextInt(9_000) + 1_000;  // 0 ~ 8,_999 사이의 값을 생성하고 1_000 을 더해서 1_000 ~ 9_999 (4자리 임의 코드) 사이의 값을 얻는다.
            code = String.valueOf(c);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem when generating the random code.");
        }

        return code;
    }

}
