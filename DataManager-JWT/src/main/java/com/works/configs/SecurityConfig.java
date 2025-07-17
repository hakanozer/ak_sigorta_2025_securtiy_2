package com.works.configs;

import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( req ->
                            req.requestMatchers("/product/**").hasRole("product")
                                .requestMatchers("/note/**").hasRole("note")
                                .requestMatchers("/customer/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf( csrf -> csrf.disable() )
                .formLogin( formLogin -> formLogin.disable() )
                .authenticationProvider(authenticationManager())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class )
                .headers(headers ->
                        headers.xssProtection(xss ->
                                xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        ).contentSecurityPolicy(
                                cps ->
                                        cps.policyDirectives("script-src 'self'")
                        ))
                .build();
    }


    public AuthenticationProvider authenticationManager() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(customerService);
        return daoAuthenticationProvider;
    }


}

/*
ali01 -> product
veli01 -> note
zehra01 -> product, note
 */
