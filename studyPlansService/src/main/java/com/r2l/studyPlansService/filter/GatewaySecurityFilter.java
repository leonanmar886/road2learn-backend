package com.r2l.studyPlansService.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;

public class GatewaySecurityFilter implements Filter {

  @Value("${gateway.secret:r2l-gateway-secret}")
  private String gatewaySecret;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String gatewayToken = httpRequest.getHeader("X-Gateway-Token");

    if (gatewayToken == null || !gatewayToken.equals(gatewaySecret)) {
      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      httpResponse.getWriter().write("Acesso direto não permitido. Use o gateway.");
      return;
    }

    chain.doFilter(request, response);
  }
}
