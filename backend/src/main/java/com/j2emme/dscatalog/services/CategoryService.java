package com.j2emme.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j2emme.dscatalog.entities.Category;
import com.j2emme.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> findall() {
		return repository.findAll();
	}
}
