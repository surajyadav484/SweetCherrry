package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.model.CupcakeDetails;

@Repository
public interface CupCakeDetailsrepository extends JpaRepository<CupcakeDetails, Integer>{

}
