package com.j2emme.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.j2emme.dscatalog.dto.ProductDTO;
import com.j2emme.dscatalog.entities.Category;
import com.j2emme.dscatalog.entities.Product;
import com.j2emme.dscatalog.repositories.CategoryRepository;
import com.j2emme.dscatalog.repositories.ProductRepository;
import com.j2emme.dscatalog.services.exceptions.DatabaseException;
import com.j2emme.dscatalog.services.exceptions.ResourceNotFoundException;
import com.j2emme.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;

	private long exintingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		productDTO = Factory.createProductDTO();
		page =  new PageImpl<>(List.of(product));
		
		Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(productRepository.findById(exintingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(productRepository.getReferenceById(exintingId)).thenReturn(product);
		Mockito.when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getReferenceById(exintingId)).thenReturn(category);
		Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.doNothing().when(productRepository).deleteById(exintingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
	}
	
	
		
	@Test
	public void updasteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() { 
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId,productDTO);
		});
	}
	
	@Test
	public void updasteShouldReturnProductDTOWhenIdExists() { 
		ProductDTO result = service.update(exintingId, productDTO);
		
		Assertions.assertNotNull(result);
	}
	
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() { 
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findaById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() { 
		ProductDTO result = service.findaById(exintingId);
		
		Assertions.assertNotNull(result);
	}
	
	
	@Test
	public void findAllPagedShouldReturnPage( ) {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowEmptyResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists( ) {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(exintingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(exintingId);
	}
}
