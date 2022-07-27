package com.j2emme.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.j2emme.dscatalog.dto.CategoryDTO;
import com.j2emme.dscatalog.entities.Category;
import com.j2emme.dscatalog.repositories.CategoryRepository;
import com.j2emme.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findall() {
		List<Category> list = repository.findAll();

		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public CategoryDTO findaById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity no found"));
		return new CategoryDTO(entity);
	}
}
