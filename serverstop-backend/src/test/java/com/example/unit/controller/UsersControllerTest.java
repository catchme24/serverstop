package com.example.unit.controller;

import com.example.contoller.UsersController;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.service.UserService;
import com.example.service.response.ServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    private static final String CONTROLLER_API = "api/users";
    @Mock
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        objectMapper = new ObjectMapper();

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


    }
    @Test
    void getById() throws Exception {
//        ServiceResponse<UserDto> sr = new ServiceResponse<UserDto>();
//        when(userService.get(anyLong(), any())).thenReturn();
//        mockMvc.perform(get(
//                CONTROLLER_API +
//                        "/{id}",
//                123L))
//                .andExpect(status().isOk());
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }
}