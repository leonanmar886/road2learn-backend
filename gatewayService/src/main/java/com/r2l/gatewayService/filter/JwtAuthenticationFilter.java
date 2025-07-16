package com.r2l.gatewayService.filter;

import com.r2l.gatewayService.util.PemUtils;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter {

	@Value("classpath:public_key.pem")
	private Resource publicKeyResource;

	private PublicKey publicKey;

	@PostConstruct
	public void init() throws Exception {
		try (InputStream inputStream = publicKeyResource.getInputStream()) {
			String key = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			this.publicKey = PemUtils.parsePublicKey(key);
			log.info("Public key loaded successfully in Gateway.");
		} catch (Exception e) {
			log.error("Error loading public key in Gateway: {}", e.getMessage(), e);
			throw e;
		}
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		log.error("Authentication error: {}", err);
		return response.setComplete();
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		final List<String> openApiEndpoints = List.of(
				"/api/auth/login",
				"/api/auth/register",
				"/api/auth"
		);

		String path = request.getURI().getPath();

		if (openApiEndpoints.contains(path) || path.startsWith("/api/auth/")) {
			return chain.filter(exchange);
		}

		if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
			return this.onError(exchange, "Unauthorized: Authorization header missing.", HttpStatus.UNAUTHORIZED);
		}

		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return this.onError(exchange, "Unauthorized: Invalid Bearer token.", HttpStatus.UNAUTHORIZED);
		}

		String token = authHeader.substring(7);

		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(publicKey)
					.build()
					.parseClaimsJws(token)
					.getBody();

			Date expiration = claims.getExpiration();
			if (expiration != null && expiration.before(new Date())) {
				return this.onError(exchange, "Unauthorized: Token expired.", HttpStatus.UNAUTHORIZED);
			}

			ServerHttpRequest modifiedRequest = request.mutate()
					.header("X-User-Id", claims.getSubject())
					.build();

			log.info("Valid JWT token for user: {}", claims.getSubject());
			return chain.filter(exchange.mutate().request(modifiedRequest).build());

		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			return this.onError(exchange, "Unauthorized: Invalid JWT token. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (ExpiredJwtException e) {
			return this.onError(exchange, "Unauthorized: Expired JWT token. " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("Unexpected error during JWT validation: {}", e.getMessage(), e);
			return this.onError(exchange, "Internal server error during token validation.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}