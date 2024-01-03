package com.emlakjet.ismaildemirler.billservice.dto.user;

import java.util.UUID;

import com.emlakjet.ismaildemirler.billservice.dto.DtoModel;

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
public class UserDto implements DtoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3092174193926429141L;
	private UUID userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
