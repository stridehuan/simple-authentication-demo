package com.gmail.gao.gary;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/14 11:10 AM
 */
public class TestUtil {
    /**
     * exec a test method
     * @param testMethod
     * @param description
     * @param index
     */
    public static boolean execTest(TestMethod testMethod, String description, int index) {
        System.out.println("\n========   Test Case " + index + " : " + description + "  =======");
        boolean result = testMethod.method();
        System.out.println("========   Test Result = " + result);
        return result;
    }

    public interface TestMethod {
        boolean method();
    }
}
