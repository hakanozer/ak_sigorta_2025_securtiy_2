package com.works.configs;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitingFilter implements Filter {

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket getBucket(String clientId) {
        return buckets.computeIfAbsent( clientId, k ->
                Bucket.builder().addLimit(Bandwidth.classic(10, Refill.intervally(1, Duration.ofSeconds(1)))).build());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String clientIp = request.getRemoteAddr();
        Bucket bucket = getBucket(clientIp);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        }else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String username = auth.getName();
                String role = auth.getAuthorities().toString();
                System.out.println("username = " + username + ", role = " + role);
            }
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(429);
            out.write("{ \"status\": false, \"message\": \"Too Many Request\" }");
            out.flush();
        }
    }

}
