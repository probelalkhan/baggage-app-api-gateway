package com.siemens.sl.apigateway.constants.endpoints;

public interface Endpoints {
    String[] allowedEndpoints = {Endpoints.LOGIN};

    String LOGIN = "/api/crc/login";
    String LOGOUT = "/api/crc/logout";

    String USER_MANAGEMENT_PATTERN= "/api/userManagement/**";
}