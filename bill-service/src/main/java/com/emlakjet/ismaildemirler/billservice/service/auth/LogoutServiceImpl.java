package com.emlakjet.ismaildemirler.billservice.service.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.emlakjet.ismaildemirler.billservice.core.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.repository.auth.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByAuthToken(jwt).orElse(null);
		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
		}
		SecurityContextHolder.clearContext();
		var apiResponse = ServiceResponse.builder()
				.message("Logout Succeeded!")
				.success(true)
				.data(null)
				.build();
		try {
			new ObjectMapper().writeValue(response.getOutputStream(), apiResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
