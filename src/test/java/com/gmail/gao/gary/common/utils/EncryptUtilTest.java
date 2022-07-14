package com.gmail.gao.gary.common.utils;

import com.gmail.gao.gary.ApplicationTest;
import com.gmail.gao.gary.TestUtil;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/14 1:29 AM
 */
public class EncryptUtilTest {


    public static void main(String[] args) {
        int index = 1;
        int successCount = 0;
        int failedCount = 0;
        boolean result;

        result = TestUtil.execTest(EncryptUtilTest::testEncodeAndDecode, "testEncodeAndDecode", index ++);
        if (result) successCount++; else failedCount++;


        System.out.println("\n Total Result: success = " + successCount + ", fail = " + failedCount);

        System.exit(0);
    }

    public static boolean testEncodeAndDecode() {
        String originCode = "123456";
        String seed = "abc";

        System.out.println("origin = " + originCode + ", seed=" + seed);
        String encodedCode = EncryptUtil.encode(originCode, seed);
        System.out.println("encodedCode = " + encodedCode);
        String decodedCode = EncryptUtil.decode(encodedCode, seed);
        System.out.println("decodedCode = " + decodedCode);

        return decodedCode.equals(originCode);
    }
}
