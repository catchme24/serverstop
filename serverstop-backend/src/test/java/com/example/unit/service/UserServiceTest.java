package com.example.unit.service;


import com.example.entity.Role;
import com.example.entity.Server;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.service.response.ServiceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private List<User> users;
    private List<Server> servers;


    @BeforeEach
    public void setUp() {
        Server server1 = new Server();
        server1.setId(1L);
        server1.setServerRate("x123");
        server1.setChronicle("GOD");
        server1.setDomain("top.ru");
        server1.setStatus("vip");
        server1.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Server server2 = new Server();
        server2.setId(2L);
        server2.setServerRate("x123");
        server2.setChronicle("GOD");
        server2.setDomain("top.ru");
        server2.setStatus("vip");
        server2.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Server server3 = new Server();
        server3.setId(3L);
        server3.setServerRate("x123");
        server3.setChronicle("GOD");
        server3.setDomain("top.ru");
        server3.setStatus("vip");
        server3.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Server server4 = new Server();
        server4.setId(4L);
        server4.setServerRate("x123");
        server4.setChronicle("GOD");
        server4.setDomain("top.ru");
        server4.setStatus("vip");
        server4.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Server server5 = new Server();
        server5.setId(5L);
        server5.setServerRate("x123");
        server5.setChronicle("GOD");
        server5.setDomain("top.ru");
        server5.setStatus("vip");
        server5.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        Server server6 = new Server();
        server6.setId(6L);
        server6.setServerRate("x123");
        server6.setChronicle("GOD");
        server6.setDomain("top.ru");
        server6.setStatus("vip");
        server6.setServerStart(LocalDateTime.parse("2020-01-01T10:00:00", DateTimeFormatter.ISO_DATE_TIME));

        User user1 = new User();
        user1.setId(1L);
        user1.setPassword("user1");
        user1.setUsername("user1");
        user1.setRoles(Set.of(Role.USER));
        user1.setServers(List.of(server1, server2, server3));
        server1.setOwner(user1);
        server2.setOwner(user1);
        server3.setOwner(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setPassword("user2");
        user2.setUsername("user2");
        user2.setRoles(Set.of(Role.USER));
        user2.setServers(List.of(server4, server5));
        server4.setOwner(user2);
        server5.setOwner(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setPassword("user3");
        user3.setUsername("user3");
        user3.setRoles(Set.of(Role.USER));
        user3.setServers(List.of(server6));
        server6.setOwner(user3);

        users = new ArrayList<>(List.of(user1, user2, user3));
        servers = new ArrayList<>(List.of(server1, server2, server3, server4, server5, server6));
    }

    @Test
    void getAll_haveUsers() {
        when(userRepository.findAll()).thenReturn(users);
        ServiceResponse response = userService.getAll(null);
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getContent().size()).isEqualTo(3);
    }

    @Test
    void getAll_zeroUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        ServiceResponse response = userService.getAll(null);
        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
