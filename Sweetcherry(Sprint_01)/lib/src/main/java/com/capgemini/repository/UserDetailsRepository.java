package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer>{

	/*
	 * @Query("UPDATE UserDetails u SET u.address=:address") public Address
	 * updateAddress(@ Address address);
	 */
}
