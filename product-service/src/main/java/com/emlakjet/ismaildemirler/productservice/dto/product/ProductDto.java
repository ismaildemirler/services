package com.emlakjet.ismaildemirler.productservice.dto.product;

import java.util.UUID;

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
public class ProductDto {

	private UUID productId;
	private String name;
	private int price;
	private boolean status;
}
