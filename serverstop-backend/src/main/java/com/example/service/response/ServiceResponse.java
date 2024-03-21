package com.example.service.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ServiceResponse {

    private HttpStatus httpStatus;
    private String errorMessage;
    private List<?> content;
}
