package com.threedollar.common.exception;


public class InvalidException extends BaseException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.E400_INVALID;

    public InvalidException(String message) {
        super(message, DEFAULT_ERROR_CODE);
    }

    public InvalidException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
}
