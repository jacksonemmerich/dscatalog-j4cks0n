package com.j2emme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.j2emme.dscatalog.entities.Category;


//camada de acesso a dados
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
