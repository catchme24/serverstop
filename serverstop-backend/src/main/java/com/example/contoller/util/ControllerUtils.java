package com.example.contoller.util;

import com.example.service.response.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ControllerUtils {

    public static <D> ResponseEntity<?> mapServiceResonseToHttpResponse(ServiceResponse<D> serviceResponse) {
        Object requestBody = serviceResponse.getErrorMessage().equals("") ? serviceResponse.getContent() : serviceResponse.getErrorMessage();
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(requestBody);
    }

    public static ResponseEntity<?> mapBindingResultToHttpResponse(BindingResult br) {
        StringBuilder sb = new StringBuilder()
                .append("Cannot validate object: ")
                .append(br.getObjectName())
                .append(" ");
        br.getFieldErrors()
                .forEach(filedError -> sb
                        .append("\n")
                        .append("Error in field with name: ")
                        .append(filedError.getField())
                        .append(", error: ")
                        .append(filedError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
    }
}
