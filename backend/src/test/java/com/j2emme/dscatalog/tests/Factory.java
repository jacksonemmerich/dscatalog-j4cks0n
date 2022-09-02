package com.j2emme.dscatalog.tests;

import java.time.Instant;

import com.j2emme.dscatalog.dto.ProductDTO;
import com.j2emme.dscatalog.entities.Category;
import com.j2emme.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, Instant.parse("2020-10-20T03:00:00Z"), "https://img.com/img.png");
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}
}
