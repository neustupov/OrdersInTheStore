package ru.neustupov.ordersinthestore.util.exception;

public enum ErrorType {
    APP_ERROR("error.appError"),
    DATA_NOT_FOUND("error.dataNotFound"),
    DATA_ERROR("error.dataError"),
    VALIDATION_ERROR("error.validationError"),
    WRONG_REQUEST("error.wrongRequest"),
    ACCESS_DENIED("error.accessDenied");

    private final String errorCode;

    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
