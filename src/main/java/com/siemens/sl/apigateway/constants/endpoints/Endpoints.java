package com.siemens.sl.apigateway.constants.endpoints;

public interface Endpoints {
    String[] allowedEndpoints = {
            Endpoints.LOGIN,
            Endpoints.LOGOUT,
            Endpoints.USER_MANAGEMENT_PATTERN,
            Endpoints.ADD_USER,
            Endpoints.USERS,
            Endpoints.ADD_GROUP,
            Endpoints.GROUPS,
            Endpoints.UPDATE_PASSWORD,
            Endpoints.ROLES,
            Endpoints.UPDATE_LOGIN_FAILED
    };

    String LOGIN = "/api/crc/login";
    String LOGOUT = "/api/crc/logout";
    String ADD_USER = "/api/crc/adduser";
    String USERS = "/api/crc/users";
    String ADD_GROUP = "/api/crc/addgroup";
    String GROUPS = "/api/crc/groups";
    String UPDATE_PASSWORD = "/api/crc/updatepassword";
    String ROLES = "/api/crc/roles";
    String UPDATE_LOGIN_FAILED = "/api/crc/updateloginfailed";

    String USER_MANAGEMENT_PATTERN = "/api/userManagement/**";
}