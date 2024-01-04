package com.emlakjet.ismaildemirler.productservice.service.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.emlakjet.ismaildemirler.productservice.config.RedisConfig;
import com.emlakjet.ismaildemirler.productservice.dto.product.ProductDto;
import com.emlakjet.ismaildemirler.productservice.entity.product.Product;
import com.emlakjet.ismaildemirler.productservice.mapper.product.ProductMapper;
import com.emlakjet.ismaildemirler.productservice.repository.product.ProductRepository;
import com.emlakjet.ismaildemirler.productservice.util.cache.CacheManagement;
import com.emlakjet.ismaildemirler.productservice.util.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "productCache")
public class ProductServiceImpl implements ProductService {

	private final CacheManagement cacheManagement;
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;
	
	@Override
	@CachePut(cacheNames = RedisConfig.PRODUCTS)
	public ProductDto saveProduct(ProductDto productDto) {
		Product product = productRepository.save(productMapper.toEntity(productDto));
		cacheManagement.refreshCache(RedisConfig.PRODUCTS);
		return productMapper.toDto(product);
	}
	
	@Override
	@CachePut(cacheNames = RedisConfig.PRODUCTS)
	public ProductDto updateProduct(ProductDto productDto) {
		Product product = findProduct(productDto.getProductId());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setStatus(productDto.isStatus());
		Product updatedProduct = productRepository.save(product);
		cacheManagement.refreshCache(RedisConfig.PRODUCTS);
		return productMapper.toDto(updatedProduct);
	}
	
	@Override
	@Cacheable(cacheNames = RedisConfig.PRODUCTS)
	public ProductDto getProduct(UUID productId) {
		return productMapper.toDto(findProduct(productId));
	}
	
	@Override
	@Cacheable(cacheNames = RedisConfig.PRODUCTS, sync = true)
	public List<ProductDto> getProducts() {
		return productMapper.toListDto(productRepository.findAll());
	}
	
	@Override
	@CacheEvict(value = RedisConfig.PRODUCTS, allEntries=true)
	public void deleteProduct(UUID productId) {
		productRepository.delete(findProduct(productId));
	}
	
	private Product findProduct(UUID productId) {
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty()) {
			throw new ResourceNotFoundException("There is no product founded by the productId!");
		}
		return product.get();
	}
}
