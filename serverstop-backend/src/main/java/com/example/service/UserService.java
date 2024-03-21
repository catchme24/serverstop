package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.repository.UserRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements ApiService<UserDto> {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ServiceResponse getAll(UserDto principal) {
        log.debug("Start finding all users");
        List<UserDto> findedUsers = userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        log.debug("End finging all users");
        return goodResponse(HttpStatus.OK , findedUsers);
    }

    @Override
    public ServiceResponse get(Long id, UserDto principal) {
        log.debug("Start finding user with id={}", id);
        UserDto findedUser;
        Optional<User> user  = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("Сannot find user with id={}", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        findedUser = mapper.map(user.get(), UserDto.class);
        log.debug("End finging user with id={}", id);
        return goodResponse(HttpStatus.OK, findedUser);
    }

    @Override
    @Transactional
    public ServiceResponse add(UserDto userToBeAdded, UserDto principal) {
        log.debug("Start adding new user: {}", userToBeAdded);
        if (userToBeAdded.getId() != null) {
            log.warn("Сannot add new user: {}, new user should not has id", userToBeAdded);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_NOT_HAS_ID.name());
        }
        if (userRepository.existsByUsername(userToBeAdded.getUsername())) {
            log.warn("Сannot add new user: {}, user with this username already exist", userToBeAdded);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.ALREADY_EXIST_USERNAME.name());
        }
        userToBeAdded.setPassword(passwordEncoder.encode(userToBeAdded.getPassword()));
        userToBeAdded.setRoles(Set.of(Role.USER));
        UserDto addedUser = mapper.map(userRepository.save(mapper.map(userToBeAdded, User.class)) , UserDto.class);
        log.debug("End adding new user: {}", addedUser);
        return goodResponse(HttpStatus.CREATED, addedUser);
    }

    @Override
    @Transactional
    public ServiceResponse update(UserDto userToBeUpdated, UserDto principal) {
        log.debug("Start updating user with id={}", userToBeUpdated.getId());
        if (userToBeUpdated.getId() == null) {
            log.warn("Сannot update user with id: {}, updated user should has existing id", userToBeUpdated.getId());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<User> existingUser = userRepository.findById(userToBeUpdated.getId());
        if (existingUser.isEmpty()) {
            log.warn("Сannot update user with id: {}, updated user should has existing id", userToBeUpdated.getId());
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        mapper.map(userToBeUpdated, existingUser.get());
        existingUser.get().setPassword(passwordEncoder.encode(userToBeUpdated.getPassword()));
        existingUser.get().setUpdatedAt(LocalDateTime.now());
        UserDto updatedUser = mapper.map(userRepository.save(existingUser.get()), UserDto.class);
        log.debug("End updating user with id={}", userToBeUpdated.getId());
        return goodResponse(HttpStatus.ACCEPTED, updatedUser);
    }

    @Override
    @Transactional
    public ServiceResponse delete(Long id, UserDto principal) {
        log.debug("Start deleting user with id={}", id);
        if (id == null) {
            log.warn("Сannot delete user with id: {}, deleted user should has existing id", id);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (userRepository.existsById(id)) {
            log.warn("Сannot delete user with id: {}, deleted user should has existing id", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        userRepository.deleteById(id);
        log.debug("End deleting user with id={}", id);
        return goodResponse(HttpStatus.OK, null);
    }

}
