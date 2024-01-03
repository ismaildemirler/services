package com.emlakjet.ismaildemirler.billservice.config;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import com.emlakjet.ismaildemirler.billservice.core.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LogoutHandler logoutHandler;
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
				
		http
			.cors(Customizer.withDefaults())
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/**").permitAll()
			.requestMatchers("/swagger-ui/**").permitAll()
			.requestMatchers("/swagger-resources/**").permitAll()
			.requestMatchers("/swagger-ui.html").permitAll()
			.requestMatchers("/v3/api-docs/**").permitAll()			
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).logout()
			.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
			.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

		return http.build();
	}
}
