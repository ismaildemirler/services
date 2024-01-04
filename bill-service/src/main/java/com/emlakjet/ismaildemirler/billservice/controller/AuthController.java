package com.emlakjet.ismaildemirler.billservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakjet.ismaildemirler.billservice.core.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.dto.user.UserDto;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginRequest;
import com.emlakjet.ismaildemirler.billservice.payload.auth.RefreshRequest;
import com.emlakjet.ismaildemirler.billservice.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Bill Service Authentication Documentation")
public class AuthController {

	private final AuthService authService;
	
	@GetMapping("/isUp")
	@Operation(summary = "Is Service Up?", description = "Checking if the Service is Running Method")
	public boolean isUp() {
		return true;
	}

	@PostMapping("/register")
	@Operation(summary = "New User", description = "New User Registration Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The User Has Been Successfully Registered!")
    })
	public ResponseEntity<ServiceResponse> register(@Valid @RequestBody UserDto userDto) {
		System.out.println(userDto.toString());
		return ResponseEntity
				.created(null)
				.body(ServiceResponse.builder()
					.message("The User Has Been Successfully Registered!")
					.success(true)
					.data(authService.register(userDto))
					.build());
	}
	
	@PostMapping
	@Operation(summary = "User Login", description = "Login Process For User To Authenticate")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Succeeded!")
    })
	public ResponseEntity<ServiceResponse> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(
				ServiceResponse.builder()
					.message("Login Succeeded!")
					.success(true)
					.data(authService.login(loginRequest))
					.build());
	}
	
	@PostMapping("/refreshToken")
	@Operation(summary = "Auth Token Refreshing", description = "After Auth Token Expires, User Need To Refresh Token To Authenticate Again")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Succeeded!")
    })
	public ResponseEntity<ServiceResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
		return ResponseEntity.ok(
				ServiceResponse.builder()
					.message("Login Succeeded!")
					.success(true)
					.data(authService.refreshToken(refreshRequest.getRefreshToken()))
					.build());
	}
	
	@PostMapping("/logout")
	@Operation(summary = "Logout", description = "Necessary Process After User Logout")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout Succeeded!")
    })
	public ResponseEntity<ServiceResponse> logout() {
		return ResponseEntity.ok(
				ServiceResponse.builder()
					.message("Logout Succeeded!")
					.success(true)
					.data(null)
					.build());
	}
}
