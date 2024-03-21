package com.example.service;

import com.example.dto.ServerDto;
import com.example.dto.UserDto;
import com.example.entity.Server;
import com.example.entity.User;
import com.example.repository.ServerRepository;
import com.example.repository.UserRepository;
import com.example.service.response.ServiceMessage;
import com.example.service.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServerService implements ApiService<ServerDto> {

    private final ServerRepository serverRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public ServiceResponse getAll(UserDto principal) {
        log.debug("Start finding all servers");
        List<ServerDto> findedServers = serverRepository.findAll()
                .stream()
                .map(server -> mapper.map(server, ServerDto.class))
                .collect(Collectors.toList());
        log.debug("End finging all servers");
        return goodResponse(HttpStatus.OK , findedServers);
    }

    @Override
    public ServiceResponse get(Long id, UserDto principal) {
        log.debug("Start finding server with id={}", id);
        Optional<Server> findedServer  = serverRepository.findById(id);
        if (findedServer.isEmpty()) {
            log.warn("Сannot find server with id={}", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        ServerDto serverDto = mapper.map(findedServer.get(), ServerDto.class);
        log.debug("End finging server with id={}", id);
        return goodResponse(HttpStatus.OK, serverDto);
    }

    @Override
    @Transactional
    public ServiceResponse add(ServerDto serverToBeAdded, UserDto principal) {
        log.debug("Start adding new server = {}", serverToBeAdded);
        if (serverToBeAdded.getId() != null) {
            log.warn("Сannot add new server: {}, new server should not has id", serverToBeAdded);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_NOT_HAS_ID.name());
        }
        Server server = mapper.map(serverToBeAdded, Server.class);
        server.setOwner(userRepository.findById(principal.getId()).get());
        ServerDto addedServer = mapper.map(serverRepository.save(server), ServerDto.class);
        log.debug("End adding new server: {}", addedServer);
        return goodResponse(HttpStatus.CREATED, addedServer);
    }

    @Override
    @Transactional
    public ServiceResponse update(ServerDto serverToBeUpdated, UserDto principal) {
        log.debug("Start updating server with id={}", serverToBeUpdated.getId());
        ServerDto updatedServer;
        if (serverToBeUpdated.getId() == null) {
            log.warn("Сannot update server with id: {}, updated server should has existing id", serverToBeUpdated.getId());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        Optional<Server> existingServer = serverRepository.findById(serverToBeUpdated.getId());
        if (existingServer.isEmpty()) {
            log.warn("Сannot update server with id: {}, updated server should has existing id", serverToBeUpdated.getId());
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (existingServer.get().getOwner() == null || !existingServer.get().getOwner().getId().equals(principal.getId())) {
            log.warn("Сannot update server with id: {}, authenticated user id not equals to server owner id", serverToBeUpdated.getId());
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.AUTH_USER_ID_NOT_EQUAL_TO_SERVER_OWNER_ID.name());
        }

        mapper.map(serverToBeUpdated, existingServer.get());
        existingServer.get().setUpdatedAt(LocalDateTime.now());
        updatedServer = mapper.map(serverRepository.save(existingServer.get()), ServerDto.class);
        log.debug("End updating server with id={}", serverToBeUpdated.getId());
        return goodResponse(HttpStatus.ACCEPTED, updatedServer);
    }

    @Override
    @Transactional
    public ServiceResponse delete(Long id, UserDto principal) {
        log.debug("Start deleting server with id={}", id);
        Optional<Server> existingServer = serverRepository.findById(id);
        if (id == null) {
            log.warn("Сannot delete server with id: {}, deleted server should has existing id", id);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (serverRepository.existsById(id)) {
            log.warn("Сannot delete server with id: {}, deleted server should has existing id", id);
            return errorResponse(HttpStatus.NOT_FOUND, ServiceMessage.SHOULD_HAS_EXISTING_ID.name());
        }
        if (existingServer.get().getOwner() == null || !existingServer.get().getOwner().getId().equals(principal.getId())) {
            log.warn("Сannot delete server with id: {}, authenticated user id not equals to server owner id", id);
            return errorResponse(HttpStatus.BAD_REQUEST, ServiceMessage.AUTH_USER_ID_NOT_EQUAL_TO_SERVER_OWNER_ID.name());
        }
        serverRepository.deleteById(id);
        log.debug("End deleting server with id={}", id);
        return goodResponse(HttpStatus.OK, null);
    }

    public ServiceResponse getAllByChronicleAndServerRate(String chronicle, String serverRate) {
        log.debug("Start findig server with chronicle={} and serverRate={}", chronicle, serverRate);
        List<ServerDto> findedServers;
        findedServers = serverRepository.findAllByChronicleIgnoreCaseAndServerRate(chronicle, serverRate)
                .stream()
                .map(server -> mapper.map(server, ServerDto.class))
                .collect(Collectors.toList());
        log.debug("End findig server with chronicle={} and serverRate={}", chronicle, serverRate);
        return goodResponse(HttpStatus.OK, findedServers);
    }

    public ServiceResponse getAllByServerRate(String serverRate) {
        log.debug("Start findig server with serverRate={}", serverRate);
        List<ServerDto> findedServers = serverRepository.findAllByServerRate(serverRate)
                .stream()
                .map(server -> mapper.map(server, ServerDto.class))
                .collect(Collectors.toList());
        log.debug("End findig server with serverRate={}", serverRate);
        return goodResponse(HttpStatus.OK, findedServers);
    }

    public ServiceResponse getAllByChronicle(String chronicle) {
        log.debug("Start findig server with chronicle={}", chronicle);
        List<ServerDto> findedServers = serverRepository.findAllByChronicleIgnoreCase(chronicle)
                .stream()
                .map(server -> mapper.map(server, ServerDto.class))
                .collect(Collectors.toList());
        log.info("End findig server with chronicle={}", chronicle);
        return goodResponse(HttpStatus.OK, findedServers);
    }

    public ServiceResponse getAllChronicles() {
        log.debug("Start findig all chronicles");
        List<String> findedChronicles = serverRepository.findAllChroicles();
        log.debug("End findig all chronicles");
        return goodResponse(HttpStatus.OK, findedChronicles);
    }

    public ServiceResponse getAllRates() {
        log.debug("Start findig all rates");
        List<String> findedRates = serverRepository.findAllRates();
        log.debug("End findig all rates");
        return goodResponse(HttpStatus.OK, findedRates);
    }

    public ServiceResponse getAllMyServers(UserDto principal) {
        log.debug("Start findig user's servers with id={}", principal.getId());
        List<ServerDto> findedUserServers = userRepository.findById(principal.getId()).get().getServers()
                .stream()
                .map(server -> {
                    ServerDto serverDto = mapper.map(server, ServerDto.class);
                    return serverDto;
                })
                .collect(Collectors.toList());
        log.debug("End findig user's servers with id={}", principal.getId());
        return goodResponse(HttpStatus.OK, findedUserServers);
    }
}