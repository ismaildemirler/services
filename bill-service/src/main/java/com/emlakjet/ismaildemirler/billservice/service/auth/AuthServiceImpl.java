package com.emlakjet.ismaildemirler.billservice.service.auth;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emlakjet.ismaildemirler.billservice.dto.user.UserDto;
import com.emlakjet.ismaildemirler.billservice.entity.auth.Token;
import com.emlakjet.ismaildemirler.billservice.entity.user.User;
import com.emlakjet.ismaildemirler.billservice.enums.auth.TokenType;
import com.emlakjet.ismaildemirler.billservice.enums.role.Role;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginRequest;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginResponse;
import com.emlakjet.ismaildemirler.billservice.payload.auth.RegisterResponse;
import com.emlakjet.ismaildemirler.billservice.repository.auth.TokenRepository;
import com.emlakjet.ismaildemirler.billservice.repository.user.UserRepository;
import com.emlakjet.ismaildemirler.billservice.security.JwtHelper;
import com.emlakjet.ismaildemirler.billservice.util.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final JwtHelper jwtHelper;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	
	public RegisterResponse register(UserDto userDto) {
		var previousUser = userRepository.findByEmail(userDto.getEmail());
		if (previousUser.isEmpty()) {
			var user = User.builder()
					.firstName(userDto.getFirstName())
					.lastName(userDto.getLastName())
					.email(userDto.getEmail())
					.password(passwordEncoder.encode(userDto.getPassword()))
					.role(Role.USER)
					.build();
			
			var savedUser = userRepository.save(user);
			return RegisterResponse.builder()
					.fullName(savedUser.getFirstName() + " " + savedUser.getLastName())
					.email(savedUser.getEmail())
					.build();
		} else {
			throw new CustomException("This email address has already been used!");
		}
	}
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		var userOpt = userRepository.findByEmail(loginRequest.getEmail());
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("There is no user registered by this email!");
		}
		var user = userOpt.get();
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		var authToken = jwtHelper.generateToken(user);
		var refreshToken = jwtHelper.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, authToken);
		return LoginResponse.builder()
				.authToken(authToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	public LoginResponse refreshToken(String refreshToken) {
		String userEmail = jwtHelper.extractUsername(refreshToken);
		if (userEmail != null) {
			var userOpt = this.userRepository.findByEmail(userEmail);
			if(userOpt.isEmpty()) {
				throw new UsernameNotFoundException("There is no user registered by this email!");
			}
			var user = userOpt.get();
			if (jwtHelper.isTokenValid(refreshToken, user)) {
				var newAuthToken = jwtHelper.generateToken(user);
				var newRefreshToken = jwtHelper.generateRefreshToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, newAuthToken);
				return LoginResponse.builder()
						.authToken(newAuthToken)
						.refreshToken(newRefreshToken)
						.build();
			}
		}
		throw new CustomException("Some technical errors has been occured!");
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.tokenId(UUID.randomUUID())
				.authToken(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}
}
