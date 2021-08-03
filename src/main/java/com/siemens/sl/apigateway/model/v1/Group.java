package com.siemens.sl.apigateway.model.v1;

import lombok.Data;

@Data
public class Group {

    private long id;
    private String name;
    private String description;
    private boolean locked;
    private long session_timeout;

}