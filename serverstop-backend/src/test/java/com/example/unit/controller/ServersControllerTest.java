package com.example.unit.controller;

import com.example.contoller.ServersController;
import com.example.dto.ServerDto;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.service.ServerService;
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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServersControllerTest {
    private static final String CONTROLLER_API = "/api/servers";
    @Mock
    private ServerService serverService;

    @InjectMocks
    private ServersController serversController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserDto loggedUser;

    private Authentication authentication;

    private List<ServerDto> serverDtos;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(serversController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        ServerDto serverDto1 = new ServerDto();
        serverDto1.setId(1L);
        serverDto1.setServerRate("x50");
        serverDto1.setChronicle("GOD");
        serverDto1.setDomain("top.ru");
        serverDto1.setStatus("vip");
        serverDto1.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        ServerDto serverDto2 = new ServerDto();
        serverDto2.setId(2L);
        serverDto2.setServerRate("x12");
        serverDto2.setChronicle("Interlude");
        serverDto2.setDomain("top.ru");
        serverDto2.setStatus("vip");
        serverDto2.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        ServerDto serverDto3 = new ServerDto();
        serverDto3.setId(3L);
        serverDto3.setServerRate("x50");
        serverDto3.setChronicle("Interlude");
        serverDto3.setDomain("top.ru");
        serverDto3.setStatus("vip");
        serverDto3.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));


        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setPassword("user1");
        userDto.setUsername("user1");
        userDto.setRoles(Set.of(Role.USER));
        loggedUser = userDto;
        TestingAuthenticationToken token = new TestingAuthenticationToken(loggedUser, null);
        authentication = token;

        serverDtos = new ArrayList<>(List.of(serverDto1, serverDto2, serverDto1));
    }

    @Test
    void getById_goodResponce() throws Exception {
        ServerDto serverToBeFound = serverDtos.get(0);
        Long requestingId = serverToBeFound.getId();
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                Collections.singletonList(serverToBeFound));
        String resultJson = objectMapper.writeValueAsString(Collections.singletonList(serverToBeFound));

        when(serverService.get(anyLong(), any())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/{id}", requestingId))
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson));
        verify(serverService, times(1)).get(any(), any());
    }

    @Test
    void getById_errorResponce() throws Exception {
        String errorMessage = ServiceMessage.SHOULD_HAS_EXISTING_ID.name();
        ServiceResponse<UserDto> sr = new ServiceResponse<>(
                HttpStatus.NOT_FOUND,
                errorMessage,
                Collections.emptyList());

        when(serverService.get(anyLong(), any())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/{id}", 123L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(errorMessage));
        verify(serverService, times(1)).get(any(), any());
    }

    @Test
    void filter_byChonicle() throws Exception {
        String findingChronicles = "Intrelude";
        List<ServerDto> findedServers = serverDtos.stream()
                .filter(server -> server.getChronicle().equals(findingChronicles)
                )
                .collect(Collectors.toList());
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                findedServers);
        String responseJson = objectMapper.writeValueAsString(findedServers);

        when(serverService.getAllByChronicle(anyString())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/filter")
                        .queryParam("chronicle", findingChronicles))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(serverService, times(1)).getAllByChronicle(any());
    }

    @Test
    void filter_byServerRate() throws Exception {
        String findingServerRate = "x50";
        List<ServerDto> findedServers = serverDtos.stream()
                .filter(server -> server.getServerRate().equals(findingServerRate)
                )
                .collect(Collectors.toList());
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                findedServers);
        String responseJson = objectMapper.writeValueAsString(findedServers);

        when(serverService.getAllByServerRate(anyString())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/filter")
                        .queryParam("serverRate", findingServerRate))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(serverService, times(1)).getAllByServerRate(any());
    }

    @Test
    void filter_byServerRateAndChronicle() throws Exception {
        String findingChronicles = "Intrelude";
        String findingServerRate = "x50";
        List<ServerDto> findedServers = serverDtos.stream()
                .filter(server -> {
                    return server.getChronicle().equals(findingChronicles) && server.getServerRate().equals(findingServerRate);
                })
                .collect(Collectors.toList());
        ServiceResponse<ServerDto> sr = new ServiceResponse<>(
                HttpStatus.OK,
                "",
                findedServers);
        String responseJson = objectMapper.writeValueAsString(findedServers);

        when(serverService.getAllByChronicleAndServerRate(anyString(), anyString())).thenReturn(sr);

        mockMvc.perform(get(CONTROLLER_API + "/filter")
                        .queryParam("chronicle", findingChronicles)
                        .queryParam("serverRate", findingServerRate))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson));

        verify(serverService, times(1)).getAllByChronicleAndServerRate(any(), any());
    }
}