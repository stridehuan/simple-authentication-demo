package com.gmail.gao.gary.common.exception;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:15 AM
 */
public class DataSourceProcessException extends CommonRuntimeException {
    public DataSourceProcessException() {
    }

    public DataSourceProcessException(String message) {
        super(message);
    }

    public DataSourceProcessException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
