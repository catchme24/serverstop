package com.example.unit.service;

import com.example.dto.ServerDto;
import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.Server;
import com.example.entity.User;
import com.example.repository.ServerRepository;
import com.example.repository.UserRepository;
import com.example.service.ServerService;
import com.example.service.UserService;
import com.example.service.response.ServiceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServerServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ServerRepository serverRepository;
    @Spy
    private ModelMapper mapper = new ModelMapper();
    @InjectMocks
    private ServerService serverService;
    private List<User> users;
    private List<Server> servers;

    private List<ServerDto> serverDtos;

    private UserDto loggedUser;


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

        ServerDto serverDto1 = new ServerDto();
        serverDto1.setChronicle("Interlude");
        serverDto1.setServerStart(LocalDateTime.parse("2000-02-02T11:30:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        serverDto1.setServerRate("x111");
        serverDto1.setStatus("normal");
        serverDto1.setDomain("somedomain.ru");

        UserDto userDto1 = new UserDto();
        userDto1.setId(20L);
        userDto1.setUsername("user1");
        userDto1.setPassword("user1");
        userDto1.setRoles(Set.of(Role.USER));

        loggedUser = userDto1;

        users = new ArrayList<>(List.of(user1, user2, user3));
        servers = new ArrayList<>(List.of(server1, server2, server3, server4, server5, server6));
        serverDtos = new ArrayList<>(List.of(serverDto1));

    }

    @Test
    void getAll_haveServers() {
        List<Server> dbServers = this.servers;
        System.out.println(dbServers);

        when(serverRepository.findAll()).thenReturn(dbServers);

        ServiceResponse<ServerDto> response = serverService.getAll(null);

        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getContent().size()).isEqualTo(6);
        Assertions.assertThat(response.getContent()).hasOnlyElementsOfType(ServerDto.class);
    }

    @Test
    void getAll_zeroServers() {
        List<Server> dbServers = new ArrayList<Server>();

        when(serverRepository.findAll()).thenReturn(dbServers);

        ServiceResponse<UserDto> response = serverService.getAll(null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get_existingServer() {
        Server dbServer = servers.get(0);
        Long dbServerId = dbServer.getId();

        when(serverRepository.findById(dbServerId)).thenReturn(Optional.of(dbServer));

        ServiceResponse<ServerDto> response = serverService.get(dbServerId, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(dbServerId);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get_notExistingServer() {
        Server dbServer = servers.get(0);
        Long dbServerId = dbServer.getId();

        when(serverRepository.findById(dbServerId)).thenReturn(Optional.empty());

        ServiceResponse<ServerDto> response = serverService.get(dbServerId, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void add_serverWithId() {
        UserDto currentUser = this.loggedUser;
        Long currentUserId = currentUser.getId();
        Long newServerId = 23L;
        ServerDto serverToBeAdded = serverDtos.get(0);
        serverToBeAdded.setId(newServerId);

        ServiceResponse<ServerDto> response = serverService.add(serverToBeAdded, currentUser);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void add_serverWithOutId() {
        User currentUser = this.users.get(0);
        UserDto currentUserDto = new UserDto();
        currentUserDto.setId(currentUser.getId());
        currentUserDto.setRoles(currentUser.getRoles());
        currentUserDto.setUsername(currentUser.getUsername());
        currentUserDto.setPassword(currentUser.getPassword());
        Long currentUserId = currentUser.getId();

        ServerDto serverToBeAdded = serverDtos.get(0);
        Long newServerId = 23L;
        Server addedServer = mapper.map(serverToBeAdded, Server.class);
        addedServer.setId(newServerId);
        addedServer.setOwner(currentUser);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(currentUser));
        when(serverRepository.save(any(Server.class))).thenReturn(addedServer);

        ServiceResponse<ServerDto> response = serverService.add(serverToBeAdded, currentUserDto);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(addedServer.getId());
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void update_withOutId() {
        Long updatingServerId = null;
        ServerDto serverToBeUpdated = serverDtos.get(0);
        serverToBeUpdated.setId(updatingServerId);

        ServiceResponse<ServerDto> response = serverService.update(serverToBeUpdated, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_existingServerWithAnotherOwner() {
        User currentUser = this.users.get(1);
        UserDto currentUserDto = new UserDto();
        currentUserDto.setId(111L);
        currentUserDto.setRoles(currentUser.getRoles());
        currentUserDto.setUsername(currentUser.getUsername());
        currentUserDto.setPassword(currentUser.getPassword());

        ServerDto serverToBeUpdated = serverDtos.get(0);
        Server existingServer = currentUser.getServers().get(0);
        Server updatedServer = mapper.map(serverToBeUpdated, Server.class);
        serverToBeUpdated.setId(existingServer.getId());

        when(serverRepository.findById(anyLong())).thenReturn(Optional.of(existingServer));

        ServiceResponse<ServerDto> response = serverService.update(serverToBeUpdated, currentUserDto);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_notExistingServerWithId() {
        Long updatingServerId = 1L;
        ServerDto serverToBeUpdated = serverDtos.get(0);
        serverToBeUpdated.setId(updatingServerId);

        when(serverRepository.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<ServerDto> response = serverService.update(serverToBeUpdated, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void update_existingServerWithOwner() {
        User currentUser = this.users.get(1);
        UserDto currentUserDto = new UserDto();
        currentUserDto.setId(currentUser.getId());
        currentUserDto.setRoles(currentUser.getRoles());
        currentUserDto.setUsername(currentUser.getUsername());
        currentUserDto.setPassword(currentUser.getPassword());

        ServerDto serverToBeUpdated = serverDtos.get(0);
        Server existingServer = currentUser.getServers().get(0);
        serverToBeUpdated.setId(existingServer.getId());

        Server updatedServer = mapper.map(serverToBeUpdated, Server.class);
        updatedServer.setOwner(currentUser);

        when(serverRepository.findById(anyLong())).thenReturn(Optional.of(existingServer));
        when(serverRepository.save(any(Server.class))).thenReturn(updatedServer);

        ServiceResponse<ServerDto> response = serverService.update(serverToBeUpdated, currentUserDto);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(serverToBeUpdated.getId());
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void delete() {
    }

    @Test
    void getAllByChronicleAndServerRate() {
    }

    @Test
    void getAllByServerRate() {
    }

    @Test
    void getAllByChronicle() {
    }

    @Test
    void getAllChronicles() {
    }

    @Test
    void getAllRates() {
    }

    @Test
    void getAllMyServers() {
    }
}