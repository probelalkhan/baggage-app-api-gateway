package com.siemens.sl.apigateway.model.v1;

import com.siemens.sl.apigateway.model.Role;
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
    private String password_expires_on;
    private boolean locked;
    private String account_expires_on;
    private Role role;
    private Group group;
}
