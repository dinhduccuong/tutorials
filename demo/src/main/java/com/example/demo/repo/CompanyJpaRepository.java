package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Long> {
}