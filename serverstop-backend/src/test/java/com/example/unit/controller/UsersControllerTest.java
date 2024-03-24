package com.example.unit.controller;

import com.example.contoller.UsersController;
import com.example.contoller.util.ControllerUtils;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.service.UserService;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    private static final String CONTROLLER_API = "/api/users";
    @Mock
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private List<UserDto> userDros;

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

        userDros = new ArrayList<>(List.of(userDto1, userDto2, userDto3));
    }
    @Test
    void getById_goodResponce() throws Exception {
        UserDto userToBeFound = userDros.get(0);
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                Collections.singletonList(userToBeFound));
        String resultJson = objectMapper.writeValueAsString(Collections.singletonList(userToBeFound));

        when(userService.get(anyLong(), any())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/{id}", 123L))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
        verify(userService, times(1)).get(any(), any());
    }

    @Test
    void getById_errorResponce() throws Exception {
        String errorMessage = ServiceMessage.SHOULD_HAS_EXISTING_ID.name();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.NOT_FOUND,
                errorMessage,
                Collections.emptyList());

        when(userService.get(anyLong(), any())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/{id}", 123L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(errorMessage));
        verify(userService, times(1)).get(any(), any());
    }

    @Test
    void add_goodResponce() throws Exception {
        UserDto userToBeAdded = userDros.get(0);
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.CREATED,
                "",
                Collections.singletonList(userToBeAdded));
        String userJson  = objectMapper.writeValueAsString(userToBeAdded);
        String resultJson = objectMapper.writeValueAsString(Collections.singletonList(userToBeAdded));

        when(userService.add(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(post(CONTROLLER_API)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(resultJson));
        verify(userService, times(1)).add(any(UserDto.class), any());
    }

    @Test
    void add_errorResponce() throws Exception {
        UserDto userToBeAdded = userDros.get(0);
        String errorMessage = ServiceMessage.SHOULD_NOT_HAS_ID.name();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                new ArrayList<>());
        String userJson  = objectMapper.writeValueAsString(userToBeAdded);

        when(userService.add(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(post(CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(errorMessage));
        verify(userService, times(1)).add(any(UserDto.class), any());
    }

    @Test
    void update_errorResponse404() throws Exception {
        UserDto userToBeUpdated = userDros.get(0);
        String errorMessage = ServiceMessage.SHOULD_HAS_EXISTING_ID.name();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.NOT_FOUND,
                errorMessage,
                Collections.emptyList());
        String userJson  = objectMapper.writeValueAsString(userToBeUpdated);

        when(userService.update(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(put(CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(errorMessage));
        verify(userService, times(1)).update(any(), any());
    }

    @Test
    void update_errorResponse400() throws Exception {
        UserDto userToBeUpdated = userDros.get(0);
        String errorMessage = ServiceMessage.SHOULD_HAS_EXISTING_ID.name();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                Collections.emptyList());
        String userJson  = objectMapper.writeValueAsString(userToBeUpdated);

        when(userService.update(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(put(CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(errorMessage));
        verify(userService, times(1)).update(any(), any());
    }

    @Test
    void update_goodResponse() throws Exception {
        UserDto userToBeUpdated = userDros.get(0);
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.ACCEPTED,
                "",
                Collections.singletonList(userToBeUpdated));
        String userJson  = objectMapper.writeValueAsString(userToBeUpdated);
        String resultJson = objectMapper.writeValueAsString(Collections.singletonList(userToBeUpdated));

        when(userService.update(any(UserDto.class), any())).thenReturn(sr);

        mockMvc.perform(put(CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isAccepted())
                .andExpect(content().json(resultJson));
        verify(userService, times(1)).update(any(), any());
    }
}