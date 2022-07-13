package com.gmail.gao.gary.common.exception;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:47 PM
 */
public class CommonRuntimeException extends RuntimeException {
    private ErrorCode errorCode = ErrorCode.COMMON_ERROR;

    public CommonRuntimeException() {
        super();
    }

    public CommonRuntimeException(String message) {
        super(message);
    }

    public CommonRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        if (errorCode != null) {
            this.errorCode = errorCode;
        }
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
