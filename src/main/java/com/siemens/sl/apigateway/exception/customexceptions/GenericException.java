package com.siemens.sl.apigateway.exception.customexceptions;

import com.siemens.sl.apigateway.model.ErrorResponse;
import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {
    private String errorMessage;
    private HttpStatus errorCode;
    private int code;
    private int subCode;
    private ErrorResponse errorBody;

    public GenericException(HttpStatus errorCode, int code, int subCode, ErrorResponse errorBody) {
        this.errorCode = errorCode;
        this.code = code;
        this.subCode = subCode;
        this.errorBody = errorBody;
    }

    public GenericException(String message, String errorMessage, HttpStatus errorCode, int code, int subCode, ErrorResponse errorBody) {
        super(message);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.code = code;
        this.subCode = subCode;
        this.errorBody = errorBody;
    }

    public GenericException(String message, Throwable cause, String errorMessage, HttpStatus errorCode, int code, int subCode, ErrorResponse errorBody) {
        super(message, cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.code = code;
        this.subCode = subCode;
        this.errorBody = errorBody;
    }

    public GenericException(Throwable cause, String errorMessage, HttpStatus errorCode, int code, int subCode, ErrorResponse errorBody) {
        super(cause);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.code = code;
        this.subCode = subCode;
        this.errorBody = errorBody;
    }

    public GenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorMessage, HttpStatus errorCode, int code, int subCode, ErrorResponse errorBody) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.code = code;
        this.subCode = subCode;
        this.errorBody = errorBody;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    public ErrorResponse getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(ErrorResponse errorBody) {
        this.errorBody = errorBody;
    }
}

