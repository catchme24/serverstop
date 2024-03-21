package com.example.service;


import com.example.dto.UserDto;
import com.example.security.JwtTokenUtils;
import com.example.service.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationService implements ResponseProducer {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtTokenUtils jwtTokenProvider;

    public ServiceResponse login(@RequestBody UserDto userDto) {
        log.debug("Start authenticating user: {}", userDto);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("End authenticating user: {}", userDto);
        return goodResponse(HttpStatus.ACCEPTED, jwtTokenProvider.generate(authentication));
    }

    public ServiceResponse registration(UserDto userDto) {
        log.debug("Start registration user: {}", userDto);
        ServiceResponse sr = userService.add(userDto, null);
        log.debug("End registration user: {}", userDto);
        return sr;
    }
}
