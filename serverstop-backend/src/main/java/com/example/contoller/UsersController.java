package com.example.contoller;

import com.example.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.contoller.util.ControllerUtils;
import com.example.service.UserService;
import com.example.service.response.ServiceResponse;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        ServiceResponse<UserDto> sr = userService.get(id, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> add(@RequestBody UserDto user) {
        ServiceResponse<UserDto> sr = userService.add(user, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> update(@RequestBody UserDto user) {
        ServiceResponse<UserDto> sr = userService.update(user, null);
        return ControllerUtils.mapServiceResponseToHttpResponse(sr);
    }
}
