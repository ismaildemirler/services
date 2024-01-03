package com.emlakjet.ismaildemirler.billservice.dto.bill;

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
public class BillDto implements DtoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592759962831634747L;
	
	private UUID billId;
	private String firstName;
	private String lastName;
	private String email;
	private int amount;	
	private boolean valid;	
	private String productName;
	public UUID userId;
}
