package com.example.service;

import org.springframework.http.HttpStatus;
import com.example.service.response.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

public interface ResponseProducer<D> {

    default <D> ServiceResponse goodResponse(HttpStatus httpStatus  , List<D> dto) {
        return ServiceResponse.builder()
                .httpStatus(httpStatus)
                .content(dto)
                .build();
    }

    default <D> ServiceResponse goodResponse(HttpStatus httpStatus  , D dto) {
        return ServiceResponse.builder()
                .httpStatus(httpStatus)
                .content(List.of(dto))
                .build();
    }


    default <D> ServiceResponse errorResponse(HttpStatus httpStatus, String errorMessage) {
        return ServiceResponse.builder()
                .httpStatus(httpStatus)
                .errorMessage(errorMessage)
                .content(new ArrayList<>())
                .build();
    }
}
