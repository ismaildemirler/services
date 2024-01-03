package com.emlakjet.ismaildemirler.productservice.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emlakjet.ismaildemirler.productservice.dto.product.ProductDto;
import com.emlakjet.ismaildemirler.productservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.productservice.service.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product Service Documentation")
public class ProductController {
	
	private final ProductService productService;
	

	@GetMapping("/isUp")
	@Operation(summary = "Is Service Up?", description = "Checking if the Service is Running Method")
	public boolean isUp() {
		return true;
	}
	
	@PostMapping
	@Operation(summary = "Add New Product", description = "New Product Adding Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The Product Has Been Successfully Added!")
    })
	public ResponseEntity<ServiceResponse> saveProduct(@Valid @RequestBody ProductDto productDto) {
		ProductDto product = productService.saveProduct(productDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(product.getProductId()).toUri();
		return ResponseEntity.created(uri)
				.body(ServiceResponse.builder()
								.success(true)
								.message("The Product Has Been Successfully Added!")
								.data(product)
								.build());
	}
	
	@PutMapping
	@Operation(summary = "Update Existing Product", description = "Updating Existing Product With New Data in Request Body Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The Product Has Been Successfully Updated!")
    })
	public ResponseEntity<ServiceResponse> updateProduct(@Valid @RequestBody ProductDto productDto) {
		ProductDto product = productService.updateProduct(productDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}").buildAndExpand(product.getProductId()).toUri();
		return ResponseEntity.created(uri)
				.body(ServiceResponse.builder()
								.success(true)
								.message("The Product Has Been Successfully Updated!")
								.data(product)
								.build());
	}
	
	@GetMapping("/{productId}")
	@Operation(summary = "Get Product", description = "Finding Product By ProductId Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Has Been Successfully Retrieved!")
    })
	public ResponseEntity<ServiceResponse> getProduct(@PathVariable UUID productId) {
		return ResponseEntity.ok(ServiceResponse.builder()
				.success(true)
				.message("Product Has Been Successfully Retrieved!")
				.data(productService.getProduct(productId))
				.build());
	}
	
	@GetMapping
	@Operation(summary = "Get All Products", description = "Listing All of the Products Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product List Has Been Successfully Fetched!")
    })
	public ResponseEntity<ServiceResponse> getProducts() {
		List<ProductDto> products = productService.getProducts();
		if(products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(ServiceResponse.builder()
				.success(true)
				.message("Product List Has Been Successfully Fetched!")
				.data(products)
				.build());
	}
	
	@DeleteMapping("/{productId}")
	@Operation(summary = "Delete Selected Product", description = "Deleting The Product Which Is Queried By ProductId Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Product Has Been Deleted Successfully!")
    })
	public ResponseEntity<ServiceResponse> deleteProduct(@PathVariable UUID productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.ok(ServiceResponse.builder()
				.success(true)
				.message("The Product Has Been Deleted Successfully!")
				.data(ProductDto.builder().productId(productId).build())
				.build());
	}
}
