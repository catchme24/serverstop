package com.example.security;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Finding details of user with login={}", username);
        Optional<User> findedUser = userRepository.findByUsername(username);
        if (findedUser.isEmpty()) {
            log.warn("User with login={} not found", username);
            throw new UsernameNotFoundException("User with login=" + username + " not found");
        }
        UserDto userDto = mapper.map(findedUser.get(), UserDto.class);
        log.debug("Founded users details: {}", userDto);
        return userDto;
    }
}
