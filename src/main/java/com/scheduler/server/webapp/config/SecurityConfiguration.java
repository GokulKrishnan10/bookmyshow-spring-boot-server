package com.scheduler.server.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("/api/user/*").permitAll()
                .requestMatchers("/api/jobs/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
