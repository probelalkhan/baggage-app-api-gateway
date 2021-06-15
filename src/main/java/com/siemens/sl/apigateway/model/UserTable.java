package com.siemens.sl.apigateway.model;

import java.io.Serializable;
import java.util.Date;

public class UserTable implements Serializable {

    private long id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String userCode;
    private long mobile;
    private char active;
    private String password;
    private Date createdOn;
    private Date lastUpdated;

}
