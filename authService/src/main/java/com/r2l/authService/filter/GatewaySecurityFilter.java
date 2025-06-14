package com.r2l.authService.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class GatewaySecurityFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(GatewaySecurityFilter.class);

    @Value("${gateway.secret:r2l-gateway-secret}")
    private String gatewaySecret;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        logger.trace("Iniciando filtro para requisição: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        // Log de todos os headers
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.trace("Header {}: {}", headerName, httpRequest.getHeader(headerName));
        }

        String gatewayToken = httpRequest.getHeader("X-Gateway-Token");
        logger.trace("Header X-Gateway-Token: {}", gatewayToken);
        logger.trace("Token recebido: {}", gatewayToken);
        logger.trace("Secret esperado: {}", gatewaySecret);

        if (gatewayToken == null || !gatewayToken.equals(gatewaySecret)) {
            logger.warn("Token inválido ou ausente para requisição: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("The request is unauthenticated.");
            return;
        }

        logger.trace("Token validado com sucesso para requisição: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        chain.doFilter(request, response);
    }
}