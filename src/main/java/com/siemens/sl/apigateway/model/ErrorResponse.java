package com.siemens.sl.apigateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    //true or false
    private boolean isSuccess;
    //Same as HTTP error code Eg. 400
    private int code;
    //Specific code of error. Eg. 1
    private int subCode;
    //Error name, Eg. token_expired
    private String subCodeDetail;
    //Error title to be shown to the user
    private String errorTitle;
    //Error description to be shown to the user
    private String errorDescription;
    //Timestamp of the request
    private String timestamp;
    //Data if any
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubError> data;

    @Data
    private class SubError {
        private String errorCode;
        private String errorMessage;
    }
}
