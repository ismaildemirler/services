package com.emlakjet.ismaildemirler.billservice.core.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emlakjet.ismaildemirler.billservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.repository.auth.TokenRepository;
import com.emlakjet.ismaildemirler.billservice.security.JwtHelper;
import com.emlakjet.ismaildemirler.billservice.util.Util;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final Util util;
	private final JwtHelper jwtHelper;
	private final TokenRepository tokenRepository;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if (request.getServletPath().contains("/api/v1/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		try {
			userEmail = jwtHelper.extractUsername(jwt);
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				var isTokenValid = tokenRepository.findByAuthToken(jwt).map(t -> !t.isExpired() && !t.isRevoked())
						.orElse(false);
				if (jwtHelper.isTokenValid(jwt, userDetails) && isTokenValid) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			var apiResponse = ServiceResponse.builder()
					.message("Expired Token!")
					.success(true)
					.error(e)
					.build();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(util.convertObjectToJson(apiResponse));
		}
	}
}
