package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
