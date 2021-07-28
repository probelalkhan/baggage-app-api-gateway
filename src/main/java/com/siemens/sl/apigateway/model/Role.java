package com.siemens.sl.apigateway.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Role {
    private Long id;
    private String name;
    private Boolean active;
}