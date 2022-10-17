package com.example.demo.repo;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Cacheable("roles")
	Optional<Role> findByRole(String role);
}