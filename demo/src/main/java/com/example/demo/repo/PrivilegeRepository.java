package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	List<Privilege> findByApi(String api);
}