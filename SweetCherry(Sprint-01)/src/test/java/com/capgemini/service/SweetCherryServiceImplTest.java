package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;

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
		category.setCategoryId(11);
		category.setCategoryName("Custom Cupcakes");
		
		cupcakeDetails.setCupcakeCategory(category);
		
		CupcakeDetails expected = service.addCupcakeDetails(cupcakeDetails);
		CupcakeDetails actual = service.findCupcakeDetailsById(expected.getCupcakeId());
		
		assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		assertEquals(expected.getCupcakeName(), actual.getCupcakeName());
		
	}
	
	 
	
	 @Test 
	  void testCupcakeByIdShouldThrowNoSuchCupcakeExistException() {
		  assertThrows(NoSuchCupcakeExists.class, () -> {
			  service.findCupcakeDetailsById(-1);
		  });
	  }
	 
	 
		/*
		 * @Test void testModifyCupCakeRatingShouldReturnUpdatedCupcakeDetails() throws
		 * NoSuchCupcakeExists { CupcakeDetails expected =
		 * service.modifyCupcakeRating(101, 4); CupcakeDetails actual =
		 * service.findCupcakeDetailsById(101);
		 * 
		 * assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		 * assertEquals(expected.getRating(), actual.getRating()); }
		 * 
		 * @Test void testModifyCupcakeRatingShouldThrowNoSuchCupcakeExistsException() {
		 * assertThrows(NoSuchCupcakeExists.class, () -> {
		 * service.findCupcakeDetailsById(-1); }); }
		 */
	 
		
		  @Test void testAddCupcakeToCartShouldReturnOrderDetails() throws
		  NoSuchOrderExists { CupcakeDetails cupcakeDetails = new CupcakeDetails();
		  
		  cupcakeDetails.setCupcakeName(" Burnt Almond Cupcake"); cupcakeDetails.
		  setCupcakeDescription("A light and fluffy two-layer cake with a middle layer of creamy custard, topped with vanilla frosting and toasted almonds."
		  ); cupcakeDetails.setPrice(50); cupcakeDetails.setRating(0);
		  
		  CupcakeCategory category = new CupcakeCategory(); category.setCategoryId(11);
		  category.setCategoryName("Custom Cupcakes ");
		  
		  Set<CupcakeDetails> cupcakeSet = new HashSet();
		  cupcakeSet.add(cupcakeDetails);
		  
		  cupcakeDetails.setCupcakeCategory(category);
		  
		  Role role = new Role(); role.setRoleId(2); role.setRoleName("Customer");
		  
		  UserDetails userDetails = new UserDetails();
		  
		  userDetails.setFirstName("Shivani"); userDetails.setLastName("Dash");
		  userDetails.setEmail("shivani@gmail.com"); userDetails.setPassword("abc");
		  userDetails.setRole(role);
		  
		  Orders order = new Orders();
		  
		  order.setOrderDate("28/04/2021"); order.setQuantity(5);
		  order.setOrderStatus("pending"); order.setTotalPrice(325);
		  order.setCupcakeDetails(cupcakeSet); order.setUserDetails(userDetails);
		  
		  
		  Orders actual = service.addCupcakeToCart(order); 
		  
		  
		  assertEquals(actual.getTotalPrice(),325); 
		  }
		 
}


