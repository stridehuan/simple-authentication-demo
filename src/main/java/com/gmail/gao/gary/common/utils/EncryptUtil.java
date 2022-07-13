package com.gmail.gao.gary.common.utils;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 3:28 PM
 */
public class EncryptUtil {


    public static String encode(String code, String seed) {
        char[] seedCharArr = seed.toCharArray();
        char[] codeCharArr = code.toCharArray();
        char[] encodeCharArr = new char[codeCharArr.length];

        for (int i = 0; i < codeCharArr.length; i++) {
            char codeChar = codeCharArr[i];
            char seedChar = seedCharArr[i % seedCharArr.length];
            encodeCharArr[i] = (char)(codeChar ^ seedChar);
        }

        return new String(encodeCharArr);
    }

    public static String decode(String code, String seed) {
        return encode(code, seed);
    }
}
