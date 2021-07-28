package com.siemens.sl.apigateway.model;

import com.siemens.sl.apigateway.utility.DateTimeUtil;
import lombok.Data;

import java.util.Date;

@Data
public class LogoutResponse {
    private boolean isSuccess;
    private int code;
    private int subCode;
    private String subCodeDetail;
    private String timeStamp;
    private String data;

    public LogoutResponse(boolean isSuccess, int code, int subCode, String subCodeDetail, String data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.subCode = subCode;
        this.subCodeDetail = subCodeDetail;
        this.data = data;
        this.timeStamp = DateTimeUtil.getFormattedDate(new Date());
    }

}
