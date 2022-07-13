package com.gmail.gao.gary.common.exception;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 12:56 AM
 */
public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException() {
    }

    public InvalidEntityException(String message) {
        super(message);
    }
}
