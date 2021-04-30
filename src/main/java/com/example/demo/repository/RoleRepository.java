package com.example.demo.repository;


import com.example.demo.enums.Roles;
import com.example.demo.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
