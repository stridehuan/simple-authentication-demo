package com.gmail.gao.gary.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 2:21 PM
 */
public class CommonConfig {
    private static CommonConfig instance = new CommonConfig();

    /**
     * used for the encoding and decoding of password
     */
    public static final String PWD_ENCRYPT_SEED = "password.encrypt.seed";

    /**
     * used for the encoding and decoding of token
     */
    public static final String TOKEN_ENCRYPT_SEED = "token.encrypt.seed";

    /**
     * token timeout threshold, second
     */
    public static final String TOKEN_TIMEOUT_THRESHOLD = "token.timeout.threshold";

    private Properties properties = new Properties();

    /**
     * singleton instance
     * @return
     */
    public static CommonConfig getInstance() {
        return instance;
    }

    private CommonConfig () {
        InputStream in = null;

        try {
            in = CommonConfig.class.getClassLoader().getResourceAsStream("common.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringValue(String key) {
        return this.properties.getProperty(key);
    }

    public String getStringValue(String key, String defaultVal) {
        return this.properties.getProperty(key, defaultVal);
    }

    public Integer getIntegerValue(String key) {
        String strVal = getStringValue(key);

        return strVal == null ? null : Integer.valueOf(strVal);
    }

    public Integer getIntegerValue(String key, Integer defaultVal) {
        Integer integerValue = getIntegerValue(key);

        return integerValue == null ? defaultVal : integerValue;
    }

    public Long getLongValue(String key) {
        String strVal = getStringValue(key);

        return strVal == null ? null : Long.valueOf(strVal);
    }

    public Long getLongValue(String key, Long defaultVal) {
        Long longValue = getLongValue(key);

        return longValue == null ? defaultVal : longValue;
    }
}
