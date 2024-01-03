package com.emlakjet.ismaildemirler.productservice.service.product;

import java.util.List;
import java.util.UUID;

import com.emlakjet.ismaildemirler.productservice.dto.product.ProductDto;

public interface ProductService {
	
	public ProductDto saveProduct(ProductDto productDto);
	public ProductDto updateProduct(ProductDto productDto);
	public ProductDto getProduct(UUID productId);
	public List<ProductDto> getProducts();
	void deleteProduct(UUID productId);
}
