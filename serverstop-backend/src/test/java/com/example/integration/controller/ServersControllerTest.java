package com.example.integration.controller;

import com.example.config.ApplicationConfig;
import com.example.config.WebSecurityConfig;
import com.example.config.WebSecurityTestConfig;
import com.example.contoller.ServersController;
import com.example.dto.ServerDto;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.security.JwtTokenUtils;
import com.example.service.ServerService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServersController.class)
@Import({ApplicationConfig.class, WebSecurityConfig.class, WebSecurityTestConfig.class})
class ServersControllerTest {

    @MockBean
    private ServerService serverService;

    @MockBean
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private List<ServerDto> serverDtos;

    private static final String CONTROLLER_API = "/api/servers";


    @BeforeEach
    void setup() {
        objectMapper.findAndRegisterModules();

        ServerDto serverDto1 = new ServerDto();
        serverDto1.setId(1L);
        serverDto1.setServerRate("x100");
        serverDto1.setChronicle("GOD");
        serverDto1.setDomain("top.ru");
        serverDto1.setStatus("vip");
        serverDto1.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        ServerDto serverDto2 = new ServerDto();
        serverDto2.setId(2L);
        serverDto2.setServerRate("x12");
        serverDto2.setChronicle("GOD");
        serverDto2.setDomain("top.ru");
        serverDto2.setStatus("vip");
        serverDto2.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        ServerDto serverDto3 = new ServerDto();
        serverDto3.setId(3L);
        serverDto3.setServerRate("x33");
        serverDto3.setChronicle("GOD");
        serverDto3.setDomain("top.ru");
        serverDto3.setStatus("vip");
        serverDto3.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));


        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setPassword("user1");
        userDto.setUsername("user1");
        userDto.setRoles(Set.of(Role.USER));

        serverDtos = new ArrayList<>(List.of(serverDto1, serverDto2, serverDto1));
    }

    @Test
    @WithUserDetails(
            value = "user1",
            userDetailsServiceBeanName = "userDetailsService")
    void add_dtoWithOutErrors() throws Exception {
        ServerDto serverToBeAdded = serverDtos.get(0);
        Long requestingId = serverToBeAdded.getId();
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                Collections.singletonList(serverToBeAdded));
        String requestJson = objectMapper.writeValueAsString(serverToBeAdded);
        String responseJson = objectMapper.writeValueAsString(Collections.singletonList(serverToBeAdded));

        when(serverService.add(any(ServerDto.class), any())).thenReturn(sr);

        mockMvc.perform(post(   CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(serverService, times(1)).add(any(), any());
    }

    @Test
    @WithUserDetails(
            value = "user1",
            userDetailsServiceBeanName = "userDetailsService")
    void add_dtoWithErrors() throws Exception {
        ServerDto serverToBeAdded = serverDtos.get(0);
        Long requestingId = serverToBeAdded.getId();
        serverToBeAdded.setChronicle("");
        String requestJson = objectMapper.writeValueAsString(serverToBeAdded);
        String responseJson = objectMapper.writeValueAsString(Collections.singletonList(serverToBeAdded));

        mockMvc.perform(post(   CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isString());
    }


    @Test
    @WithUserDetails(
            value = "user1",
            userDetailsServiceBeanName = "userDetailsService")
    void update_dtoWithOutErrors() throws Exception {
        ServerDto serverToBeAdded = serverDtos.get(0);
        Long requestingId = serverToBeAdded.getId();
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                Collections.singletonList(serverToBeAdded));
        String requestJson = objectMapper.writeValueAsString(serverToBeAdded);
        String responseJson = objectMapper.writeValueAsString(Collections.singletonList(serverToBeAdded));

        when(serverService.update(any(ServerDto.class), any())).thenReturn(sr);

        mockMvc.perform(put(   CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(serverService, times(1)).update(any(), any());
    }

    @Test
    @WithUserDetails(
            value = "user1",
            userDetailsServiceBeanName = "userDetailsService")
    void update_dtoWithErrors() throws Exception {
        ServerDto serverToBeAdded = serverDtos.get(0);
        Long requestingId = serverToBeAdded.getId();
        serverToBeAdded.setChronicle("");
        String requestJson = objectMapper.writeValueAsString(serverToBeAdded);
        String responseJson = objectMapper.writeValueAsString(Collections.singletonList(serverToBeAdded));

        mockMvc.perform(put(   CONTROLLER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isString());
    }

}