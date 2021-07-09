package com.siemens.sl.apigateway.model.v1;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private long id;
    private String name;
    private String description;
    private String department;
    private long role_id;
    private String password;
    private Date password_expires_on;
    private boolean locked;
    private Date account_expires_on;
}
