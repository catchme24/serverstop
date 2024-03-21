package com.example.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "topservers.security")
public class SecurityProperty {

    private Long tokenLifetime;
    private String secretKey;
}
