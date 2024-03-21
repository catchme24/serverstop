package com.example.contoller;


import com.example.dto.ServerDto;
import com.example.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.contoller.util.ControllerUtils;
import com.example.service.ServerService;
import com.example.service.response.ServiceResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/servers")
@Slf4j
public class ServersController {

    private final ServerService serverService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        ServiceResponse sr = serverService.getAll(null);
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        ServiceResponse sr = serverService.get(id, null);
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "chronicle", required = false) String chronicle,
                                    @RequestParam(value = "serverRate", required = false) String serverRate) {
        ServiceResponse sr;
        if (chronicle != null && serverRate != null) {
            sr = serverService.getAllByChronicleAndServerRate(chronicle, serverRate);
        } else if (chronicle == null && serverRate == null) {
            sr = serverService.getAll(null);
        } else if (chronicle == null) {
            sr = serverService.getAllByServerRate(serverRate);
        } else {
            sr = serverService.getAllByChronicle(chronicle);
        }
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> add(@Valid @RequestBody ServerDto server,
                                 BindingResult br,
                                 Authentication authentication) {

        if (br.hasErrors()) {
            return ControllerUtils.mapBindingResultToHttpResponse(br);
        }
        ServiceResponse sr = serverService.add(server, (UserDto) authentication.getPrincipal());
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> update(@Valid @RequestBody ServerDto server,
                             BindingResult br,
                             Authentication authentication) {

        if (br.hasErrors()) {
           return ControllerUtils.mapBindingResultToHttpResponse(br);
        }
        ServiceResponse sr = serverService.update(server, (UserDto) authentication.getPrincipal());
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                Authentication authentication) {

        ServiceResponse sr = serverService.delete(id, (UserDto) authentication.getPrincipal());
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllMyServers(Authentication authentication) {

        ServiceResponse sr = serverService.getAllMyServers((UserDto) authentication.getPrincipal());
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @GetMapping("/allchronicles")
    public ResponseEntity<?> getAllChronicles(Authentication authentication) {
        ServiceResponse sr = serverService.getAllChronicles();
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

    @GetMapping("/allrates")
    public ResponseEntity<?> getAllRates(Authentication authentication) {
        ServiceResponse sr = serverService.getAllRates();
        return ControllerUtils.mapServiceResonseToHttpResponse(sr);
    }

}
