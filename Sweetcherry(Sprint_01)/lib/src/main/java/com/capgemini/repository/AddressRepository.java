package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
