package com.emlakjet.ismaildemirler.billservice.service.auth;

import com.emlakjet.ismaildemirler.billservice.dto.user.UserDto;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginRequest;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginResponse;
import com.emlakjet.ismaildemirler.billservice.payload.auth.RegisterResponse;

public interface AuthService {

	public RegisterResponse register(UserDto userDto);
	public LoginResponse login(LoginRequest loginRequest);
	public LoginResponse refreshToken(String refreshToken);
}
