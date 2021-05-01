package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
