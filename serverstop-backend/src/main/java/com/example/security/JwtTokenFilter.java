package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX_TOKEN = "Bearer ";

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenUtils jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.debug("Starting filter...");
        try {
            String jwtToken = parseHttpRequest(request);
            if (jwtToken != null && jwtTokenProvider.validate(jwtToken)) {
                String username = jwtTokenProvider.getUsername(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                log.warn("The token is missing or invalid");
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            log.warn("Authentication is failed. Details: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
        log.debug("Ending filter...");
        filterChain.doFilter(request, response);
    }

    private String parseHttpRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        return
            StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith(PREFIX_TOKEN) ?
            authorizationHeader.substring(PREFIX_TOKEN.length()) : null;
    }
}
