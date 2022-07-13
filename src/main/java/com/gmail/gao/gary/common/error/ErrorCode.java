package com.gmail.gao.gary.common.error;

/**
 * Description: common error codes
 * Author: huanbasara
 * Date: 2022/7/13 1:39 PM
 */
public enum ErrorCode {
    COMMON_ERROR("COMMON_ERROR", "common error"),
    USER_HAS_ALREADY_EXISTED("USER_HAS_ALREADY_EXISTED", "user has already existed");


    private String errorCode;

    private String errorMsg;

    ErrorCode(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ErrorCode getByCode(String code) {
        for (ErrorCode instance : ErrorCode.values()) {
            if (instance.errorCode.equals(code)) {
                return instance;
            }
        }

        return COMMON_ERROR;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
