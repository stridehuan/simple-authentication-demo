package com.gmail.gao.gary.common.exception;

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
}
