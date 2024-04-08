package com.example.integration.controller;

import com.example.config.ApplicationConfig;
import com.example.config.WebSecurityConfig;
import com.example.config.WebSecurityTestConfig;
import com.example.contoller.UsersController;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.security.JwtTokenUtils;
import com.example.service.UserService;
import com.example.service.response.ServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
@Import({ApplicationConfig.class, WebSecurityConfig.class, WebSecurityTestConfig.class})
class UsersControllerTest {


    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserDto> userDtos;

    private static final String CONTROLLER_API = "/api/users";


    @BeforeEach
    void setup() {
        objectMapper.findAndRegisterModules();

        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setUsername("user1");
        userDto1.setPassword("user1");
        userDto1.setRoles(Set.of(Role.USER));

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setUsername("user2");
        userDto2.setPassword("user2");
        userDto2.setRoles(Set.of(Role.USER));

        UserDto userDto3 = new UserDto();
        userDto3.setId(3L);
        userDto3.setUsername("user3");
        userDto3.setPassword("user3");
        userDto3.setRoles(Set.of(Role.USER));

        userDtos = new ArrayList<>(List.of(userDto1, userDto2, userDto3));
    }

    @Test
    @WithUserDetails(
            value = "admin1",
            userDetailsServiceBeanName = "userDetailsService")
    void getById() {

    }

    @Test
    @WithUserDetails(
            value = "admin1",
            userDetailsServiceBeanName = "userDetailsService")
    void add_dtoWithOutErrors() throws Exception {
        UserDto userToBeAdded = userDtos.get(0);
        Long requestingId = userToBeAdded.getId();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                Collections.singletonList(userToBeAdded));
        String requestJson = objectMapper.writeValueAsString(userToBeAdded);
        String responseJson = objectMapper.writeValueAsString(Collections.singletonList(userToBeAdded));

        when(userService.add(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(post(   CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(userService, times(1)).add(any(), any());
    }

    @Test
    @WithUserDetails(
            value = "admin1",
            userDetailsServiceBeanName = "userDetailsService")
    void update() {
    }
}