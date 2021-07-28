package com.siemens.sl.apigateway.model;

import lombok.Data;

import java.util.List;
@Deprecated
@Data
public class User {
    private Integer id;
    private String userName;
    private String firstName;
    private Integer userCode;
    private String lastName;
    private String password;
    private char active;
    private String mobile;
    private String email;
    private Role[] roles;
}