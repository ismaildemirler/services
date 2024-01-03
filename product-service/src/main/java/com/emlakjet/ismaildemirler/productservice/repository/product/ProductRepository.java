package com.emlakjet.ismaildemirler.productservice.repository.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.ismaildemirler.productservice.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
