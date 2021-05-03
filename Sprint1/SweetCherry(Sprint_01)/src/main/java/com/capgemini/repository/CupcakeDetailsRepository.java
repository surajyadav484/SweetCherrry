package com.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.model.CupcakeDetails;
@Repository
public interface CupcakeDetailsRepository extends JpaRepository<CupcakeDetails, Integer> {

	@Query("UPDATE CupcakeDetails c SET c.rating= ?2 WHERE c.cupcakeId = ?1 ")
	@Modifying
	public void updateRating(int cupcakeId, int rating);
	
	@Query("UPDATE CupcakeDetails c SET c.price = ?2 WHERE c.cupcakeId= ?1")
	@Modifying
	public void updatePrice(int cupcakeId,double price);
	
	@Query("UPDATE CupcakeDetails c SET c.cupcakeName = ?2 WHERE c.cupcakeId= ?1")
	@Modifying
	public void updateCupcakeName(int cupcakeId,String cupcakeName);
}
