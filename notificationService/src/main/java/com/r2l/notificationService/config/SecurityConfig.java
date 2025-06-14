package com.r2l.notificationService.config;

import com.r2l.notificationService.filter.GatewaySecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<GatewaySecurityFilter> gatewaySecurityFilter() {
        FilterRegistrationBean<GatewaySecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new GatewaySecurityFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
} 