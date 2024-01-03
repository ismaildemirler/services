package com.emlakjet.ismaildemirler.productservice.entity.product;

import java.util.UUID;

import com.emlakjet.ismaildemirler.productservice.entity.EntityModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Entity(name = "product")
public class Product implements EntityModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8923887315708312580L;

	@Id
    @Column(name = "product_id")
	private UUID productId;
	
    @Column(name = "name")
    @NotBlank(message = "Name must not be empty!")
	private String name;
    
    @Column(name = "price")
    @Positive(message = "Price must be bigger than 0!")
	private int price;
    
    @Column(name = "status")
	private boolean status;	
}
