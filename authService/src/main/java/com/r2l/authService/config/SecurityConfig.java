package com.r2l.authService.config;

import com.r2l.authService.filter.GatewaySecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public GatewaySecurityFilter gatewaySecurityFilter() {
        return new GatewaySecurityFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, GatewaySecurityFilter gatewaySecurityFilter) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .addFilterBefore(gatewaySecurityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}