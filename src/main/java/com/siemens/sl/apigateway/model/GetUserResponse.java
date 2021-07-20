package com.siemens.sl.apigateway.model;

import lombok.Data;

@Data
public class GetUserResponse {
    private boolean isSuccess;
    private int code;
    private int subCode;
    private String subCodeDetail;
    private String timeStamp;
    private com.siemens.sl.apigateway.model.v1.User data;
}
