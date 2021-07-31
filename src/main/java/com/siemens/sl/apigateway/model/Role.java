package com.siemens.sl.apigateway.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Role {
    private Long role_id;
    private String name;
    private Boolean active;
}