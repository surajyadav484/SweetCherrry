package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
