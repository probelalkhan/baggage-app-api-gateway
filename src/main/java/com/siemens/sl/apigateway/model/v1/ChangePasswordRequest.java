package com.siemens.sl.apigateway.model.v1;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    long id;
    String newPassword;
}
