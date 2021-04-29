package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;

@SpringBootTest
class SweetCherryServiceImplTest {

	@Autowired
	private SweetCherryService service;
	
	@Test
	void testFindCupcakeByIdShouldReturnCupcakeDetails() throws NoSuchCupcakeExists {
		CupcakeDetails cupcakeDetails = new  CupcakeDetails();
		
		cupcakeDetails.setCupcakeName("Carrot Cupcake");
		cupcakeDetails.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcakeDetails.setPrice(50);
		cupcakeDetails.setRating(0);
		
	
		CupcakeCategory category = new CupcakeCategory();
		category.setCategoryId(3);
		category.setCategoryName("Custom Cupcakes");
		
		cupcakeDetails.setCupcakeCategory(category);
		
		CupcakeDetails expected = service.addCupcakeDetails(cupcakeDetails);
		CupcakeDetails actual = service.findCupcakeDetailsById(expected.getCupcakeId());
		
		assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		assertEquals(expected.getCupcakeName(), actual.getCupcakeName());
		
	}
	
	/*
	 * void testUpdateCupCakeRatingShouldReturnUpdatedCupcakeDetails() throws
	 * NoSuchCupcakeExists { CupcakeDetails cupcakeDetails =new CupcakeDetails();
	 * 
	 * 
	 * }
	 */
	
	 @Test 
	  void testCupcakeByIdShouldThrowNoSuchCupcakeException() {
		  assertThrows(NoSuchCupcakeExists.class, () -> {
			  service.findCupcakeDetailsById(-1);
		  });
	  }

}