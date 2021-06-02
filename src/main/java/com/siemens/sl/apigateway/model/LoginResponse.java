package com.siemens.sl.apigateway.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.sl.apigateway.utility.DateTimeUtil;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResponse {
    private boolean isSuccess;
    private int code;
    private int subCode;
    private String subCodeDetail;
    private String timeStamp;
    private AuthenticationResponseData data;

    public LoginResponse(boolean isSuccess, int code, int subCode, String subCodeDetail, AuthenticationResponseData data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.subCode = subCode;
        this.subCodeDetail = subCodeDetail;
        this.data = data;
        this.timeStamp = DateTimeUtil.getFormattedDate(new Date());
    }

    @Override
    public String toString() {
        //Clone object and encrypt password
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoginResponse clonedObject = mapper.readValue(mapper.writeValueAsString(this), LoginResponse.class);
            clonedObject.data.setAccessToken("************************");
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clonedObject);
        } catch (Exception e) {
            return "LoginResponse object";
        }
    }
}
