package com.example.config;

import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.security.InMemoryUserDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

@TestConfiguration
public class WebSecurityTestConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetails manager = new InMemoryUserDetails();
        UserDto user = new UserDto();
        user.setPassword("user1");
        user.setUsername("user1");
        user.setRoles(Set.of(Role.USER));

        UserDto admin = new UserDto();
        admin.setPassword("admin1");
        admin.setUsername("admin1");
        admin.setRoles(Set.of(Role.ADMIN));

        manager.createUser(user);
        manager.createUser(admin);
        return manager;
    }
}
