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
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        ServiceResponse sr = userService.get(id, null);
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> add(@RequestBody UserDto user) {
        ServiceResponse sr = userService.add(user, null);
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> update(@RequestBody UserDto user) {
        ServiceResponse sr = userService.update(user, null);
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }
}
