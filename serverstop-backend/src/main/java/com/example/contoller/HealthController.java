package com.example.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/health")
@Slf4j
public class HealthController {

    @GetMapping("/public")
    public String publicAccess() {
        return "public";
    }

    @GetMapping("/registered")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public String registeredAccess() {
        return "registered";
    }

    @GetMapping("/private")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String privateAccess() {
        return "private";
    }
}
