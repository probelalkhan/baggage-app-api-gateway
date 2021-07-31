package com.siemens.sl.apigateway.model;

import lombok.Data;

import java.util.Date;

@Data
public class AddUserRequest {
    private String name;
    private String description;
    private String department;
    private long role_id;
    private long group_id;
    private String password;
    private String password_expires_on;
    private boolean locked;
    private String account_expires_on;
}