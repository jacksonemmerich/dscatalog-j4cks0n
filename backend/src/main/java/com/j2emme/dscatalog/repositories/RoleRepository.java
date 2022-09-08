package com.j2emme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.j2emme.dscatalog.entities.Role;


//camada de acesso a dados
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
