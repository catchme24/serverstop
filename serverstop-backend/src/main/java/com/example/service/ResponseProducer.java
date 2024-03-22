package com.example.service;

import com.example.service.response.ServiceResponse;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public interface ResponseProducer<D> {

    default <D> ServiceResponse<D> goodResponse(HttpStatus httpStatus  , List<D> dto) {
        return new ServiceResponse<>(httpStatus, "", dto);
    }


    default <D> ServiceResponse<D> goodResponse(HttpStatus httpStatus  , D dto) {
        return new ServiceResponse<>(httpStatus, "", new ArrayList<>(List.of(dto)));
    }


    default <D> ServiceResponse<D> errorResponse(HttpStatus httpStatus, String errorMessage) {
        return new ServiceResponse<>(httpStatus, errorMessage, new ArrayList<>());
    }
}
