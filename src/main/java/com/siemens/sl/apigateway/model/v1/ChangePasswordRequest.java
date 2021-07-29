package com.siemens.sl.apigateway.model.v1;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    String name;
    String newPassword;
}
