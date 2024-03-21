package com.example.contoller;

import com.example.contoller.util.ControllerUtils;
import com.example.dto.UserDto;
import com.example.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        return ControllerUtils.mapServiceResonseToHttpResponse(authenticationService.login(userDto));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        return ControllerUtils.mapServiceResonseToHttpResponse(authenticationService.registration(userDto));
    }
}
