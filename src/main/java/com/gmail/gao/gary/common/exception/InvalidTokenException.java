package com.gmail.gao.gary.common.exception;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 4:38 PM
 */
public class InvalidTokenException extends CommonRuntimeException {
    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
