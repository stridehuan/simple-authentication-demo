package com.gmail.gao.gary.common.exception;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 12:53 AM
 */
public class DuplicatedKeyException extends DataSourceProcessException {
    public DuplicatedKeyException() {
    }

    public DuplicatedKeyException(String message) {
        super(message);
    }

    public DuplicatedKeyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
