package com.siemens.sl.apigateway.model;

import lombok.Data;

import java.util.Date;

@Data
public class AddUserRequest {
    private String name;
    private String description;
    private String department;
    private long role_id;
    private String password;
    private Date password_expires_on;
    private boolean locked;
    private Date account_expires_on;
}