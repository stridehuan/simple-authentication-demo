package com.gmail.gao.gary.entity;

import com.gmail.gao.gary.common.exception.InvalidTokenException;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 4:26 PM
 */
public class Token {
    private static final String SPLIT_SEQ = "_";

    private String userName;

    private long generateTimeStamp;

    public Token(String userName, long generateTimeStamp) {
        this.userName = userName;
        this.generateTimeStamp = generateTimeStamp;
    }

    public static String generateTokenSeq(Token token) {
        return token.userName + SPLIT_SEQ + token.generateTimeStamp;
    }

    public static Token parseTokenSeq(String tokenSeq) {
        if (tokenSeq == null) {
            throw new InvalidTokenException("token is null");
        }

        String[] strArr = tokenSeq.split(SPLIT_SEQ);
        if (strArr.length != 2) {
            throw new InvalidTokenException("token parse failed");
        } else {
            String userName = strArr[0];

            if (!strArr[1].matches("\\d+")) {
                throw new InvalidTokenException("token parse failed, generate timestamp is not numeric");
            }
            long generateTimeStamp = Long.parseLong(strArr[1]);

            return new Token(userName, generateTimeStamp);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getGenerateTimeStamp() {
        return generateTimeStamp;
    }

    public void setGenerateTimeStamp(long generateTimeStamp) {
        this.generateTimeStamp = generateTimeStamp;
    }
}
