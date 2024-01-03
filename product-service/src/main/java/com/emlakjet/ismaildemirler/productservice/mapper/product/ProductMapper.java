package com.emlakjet.ismaildemirler.productservice.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.emlakjet.ismaildemirler.productservice.dto.product.ProductDto;
import com.emlakjet.ismaildemirler.productservice.entity.product.Product;
import com.emlakjet.ismaildemirler.productservice.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	Product toEntity(ProductDto productDto);

	ProductDto toDto(Product product);
	
	List<ProductDto> toListDto(List<Product> products);
}
