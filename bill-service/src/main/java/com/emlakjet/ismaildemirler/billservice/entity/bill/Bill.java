package com.emlakjet.ismaildemirler.billservice.entity.bill;

import java.util.UUID;

import com.emlakjet.ismaildemirler.billservice.entity.EntityModel;
import com.emlakjet.ismaildemirler.billservice.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "bill")
@Entity(name = "bill")
public class Bill implements EntityModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7096841471389127240L;

	@Id
	@Column(name = "bill_id")
	private UUID billId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "amount")
	private int amount;	

	@Column(name = "valid")
	private boolean valid;	

	@Column(name = "product_name")
	private String productName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User user;
}
