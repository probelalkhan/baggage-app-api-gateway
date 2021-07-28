package com.siemens.sl.apigateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationResponseData implements Serializable {
    private double userId;
    private String accessToken = null;
    @JsonIgnore
    private String tokenExpiryTime = null;

}
