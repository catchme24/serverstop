package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StaticResourceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.debug("Analyzing path: {}", request.getRequestURI());
        boolean isApi = request.getRequestURI().matches("^/api/.*");
        boolean isStaticResource = request.getRequestURI().matches(".*\\.(js|css|ico|html|png|jpg)");
        log.debug("Request URI: {}, request for: {}", request.getRequestURI(), isApi ? "API" : "static resources");

        if (isApi || isStaticResource) {
            log.debug("Processing request to API | static resources");
            filterChain.doFilter(request, response);
        } else {
            log.debug("Processing request for wrong path");
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    };
}
