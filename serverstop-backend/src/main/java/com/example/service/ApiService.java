package com.example.service;

import com.example.dto.UserDto;
import com.example.service.response.ServiceResponse;

public interface ApiService<D> extends ResponseProducer<D> {

    ServiceResponse getAll(UserDto principal);

    ServiceResponse get(Long id, UserDto principal);

    ServiceResponse add(D dto, UserDto principal);

    ServiceResponse update(D dto, UserDto principal);

    ServiceResponse delete(Long id, UserDto principal);
}
