package com.gmail.gao.gary.entity;

import com.gmail.gao.gary.common.error.ErrorCode;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 1:34 PM
 */
public class Result <T> {

    private boolean success;

    private T data;

    private String errorCode;

    private String errorMsg;

    public Result(boolean success, T data, String errorCode, String errorMsg) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static Result success() {
        return new Result(true, null, null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, data, null, null);
    }

    public static Result failed(String errorMsg) {
        return new Result(false, null, ErrorCode.COMMON_ERROR.getErrorCode(), errorMsg);
    }

    public static Result failed(String errorCode, String errorMsg) {
        return new Result(false, null, errorCode, errorMsg);
    }

    @Override
    public String toString() {
        if (success) {
            return "success = true, data = " + (data == null ? "null" : data.toString());
        } else {
            return "success = false, errorMsg = " + errorMsg;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
