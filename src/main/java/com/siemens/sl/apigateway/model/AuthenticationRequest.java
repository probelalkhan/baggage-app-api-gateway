package com.siemens.sl.apigateway.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;

    @Override
    public String toString() {
        //Clone object and encrypt password
        ObjectMapper mapper = new ObjectMapper();
        try {
            AuthenticationRequest clonedRequest = mapper.readValue(mapper.writeValueAsString(this), AuthenticationRequest.class);
            clonedRequest.password = "********";
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clonedRequest);
        }
        catch (Exception e) {
            return "AuthenticationRequest object";
        }
    }
}
