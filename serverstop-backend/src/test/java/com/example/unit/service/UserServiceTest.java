package com.example.unit.service;


import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.Server;
import com.example.entity.User;
import com.example.repository.UserRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper mapper = new ModelMapper();
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private UserService userService;

    private List<User> users;
    private List<Server> servers;

    private List<UserDto> userDtos;


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

        UserDto userDto1 = new UserDto();
        userDto1.setUsername("user1");
        userDto1.setPassword("user1");
        userDto1.setRoles(Set.of(Role.USER));


        users = new ArrayList<>(List.of(user1, user2, user3));
        servers = new ArrayList<>(List.of(server1, server2, server3, server4, server5, server6));
        userDtos = new ArrayList<>(List.of(userDto1));
    }

    @Test
    void getAll_haveUsers() {
        List<User> dbUsers = this.users;
        System.out.println(dbUsers);

        when(userRepository.findAll()).thenReturn(dbUsers);

        ServiceResponse<UserDto> response = userService.getAll(null);

        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getContent().size()).isEqualTo(3);
        Assertions.assertThat(response.getContent()).hasOnlyElementsOfType(UserDto.class);
    }

    @Test
    void getAll_zeroUsers() {
        List<User> dbUsers = new ArrayList<User>();

        when(userRepository.findAll()).thenReturn(dbUsers);

        ServiceResponse<UserDto> response = userService.getAll(null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get_existingUser() {
        User dbUser = users.get(0);
        Long dbUserId = dbUser.getId();

        when(userRepository.findById(dbUserId)).thenReturn(Optional.of(dbUser));

        ServiceResponse<UserDto> response = userService.get(dbUserId, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(dbUserId);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get_notExistingUser() {
        User dbUser = users.get(0);
        Long userId = dbUser.getId();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<UserDto> response = userService.get(userId, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void add_withId() {
        Long newUserId = 23L;
        UserDto userToBeAdded = userDtos.get(0);
        userToBeAdded.setId(newUserId);

        ServiceResponse<UserDto> response = userService.add(userToBeAdded, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void add_existingUserWithOutId() {
        Long newUserId = null;
        UserDto userToBeAdded = userDtos.get(0);
        userToBeAdded.setId(newUserId);

        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        ServiceResponse<UserDto> response = userService.add(userToBeAdded, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void add_notExistingUserWithOutId() {
        Long newUserId = null;
        UserDto userToBeAdded = userDtos.get(0);
        User savedUser = users.get(0);
        userToBeAdded.setId(newUserId);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userRepository.existsByUsername(any(String.class))).thenReturn(false);

        ServiceResponse<UserDto> response = userService.add(userToBeAdded, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(savedUser.getId());
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void update_withOutId() {
        Long newUserId = null;
        UserDto userToBeUpdated = userDtos.get(0);
        userToBeUpdated.setId(newUserId);

        ServiceResponse<UserDto> response = userService.update(userToBeUpdated, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void update_existingUserWithId() {
        Long id = 1L;
        String newUsername = "NEW USER NAME";
        UserDto userToBeUpdated = userDtos.get(0);
        User existingUser = users.get(0);
        User updatedUser = new User();
        mapper.map(existingUser, updatedUser);
        updatedUser.setUsername(newUsername);
        userToBeUpdated.setUsername(newUsername);
        userToBeUpdated.setId(id);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        ServiceResponse<UserDto> response = userService.update(userToBeUpdated, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(1);
        Assertions.assertThat(response.getContent().get(0).getId()).isEqualTo(userToBeUpdated.getId());
        Assertions.assertThat(response.getContent().get(0).getUsername()).isEqualTo(newUsername);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void update_notExistingUserWithId() {
        Long id = 1L;
        String newUsername = "NEW USER NAME";
        UserDto userToBeUpdated = userDtos.get(0);

        userToBeUpdated.setUsername(newUsername);
        userToBeUpdated.setId(id);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ServiceResponse<UserDto> response = userService.update(userToBeUpdated, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void delete_withOutId() {
        Long deletingUserId = null;

        ServiceResponse<UserDto> response = userService.delete(deletingUserId, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isNotBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void delete_notExistingUserWithId() {
        Long id = 111L;

        when(userRepository.existsById(anyLong())).thenReturn(false);

        ServiceResponse<UserDto> response = userService.delete(id, null);

        Assertions.assertThat(response.getContent().size()).isEqualTo(0);
        Assertions.assertThat(response.getErrorMessage()).isBlank();
        Assertions.assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
}
