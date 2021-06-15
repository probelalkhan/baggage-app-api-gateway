package com.siemens.sl.apigateway.constants.endpoints;

public interface Endpoints {
    String[] allowedEndpoints = {
            Endpoints.LOGIN,
            Endpoints.LOGOUT,
            Endpoints.USER_MANAGEMENT_PATTERN,
            Endpoints.ADD_USER,
            Endpoints.USERS,
    };

    String LOGIN = "/api/crc/login";
    String LOGOUT = "/api/crc/logout";
    String ADD_USER = "/api/crc/adduser";
    String USERS = "/api/crc/users";

    String USER_MANAGEMENT_PATTERN = "/api/userManagement/**";
}