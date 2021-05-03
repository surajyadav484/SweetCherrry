package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.CupcakeCategory;

public interface CupcakeCategoryRepository extends JpaRepository<CupcakeCategory, Integer> {

}
