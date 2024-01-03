package com.emlakjet.ismaildemirler.billservice.entity.auth;

import java.util.UUID;

import com.emlakjet.ismaildemirler.billservice.entity.user.User;
import com.emlakjet.ismaildemirler.billservice.enums.auth.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
@Entity(name = "token")
public class Token {
	
	@Id
	@GeneratedValue
	@Column(name = "token_id")
	public UUID tokenId;

	@Column(name = "auth_token", unique = true)
	public String authToken;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	public TokenType tokenType = TokenType.BEARER;

	public boolean revoked;

	public boolean expired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User user;
}
