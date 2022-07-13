package com.gmail.gao.gary.common.exception;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 12:56 AM
 */
public class InvalidEntityException extends CommonRuntimeException {
    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }

    public InvalidEntityException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
