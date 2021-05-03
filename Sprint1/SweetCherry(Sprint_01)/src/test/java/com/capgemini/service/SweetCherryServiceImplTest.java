package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.CupcakeDetailsRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;

/*The @SpringBootTest annotation tells Spring Boot to look for a main configuration class 
and use that to start a Spring application context that is to be used in test*/

@SpringBootTest
class SweetCherryServiceImplTest {
	// @Autowired is used for object injection automatically*/
	@Autowired
	// Creating reference of SweetCherryService interface
	private SweetCherryService cupcakeService;

	// Creating reference of CupcakeDetailsRepository interface
	@Autowired
	private CupcakeDetailsRepository cupcakeRepository;

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testFindCupcakeByIdShouldReturnCupcakeDetails() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object
		CupcakeCategory category = new CupcakeCategory();
		// Setting CupcakeCategory properties using setter method of CupcakeCategory
		// class
		category.setCategoryId(102);
		category.setCategoryName("Premium Cupcake");

		// Initializing CupcakeDetails Object
		CupcakeDetails cupcake = new CupcakeDetails();
		// Setting CupcakeDetails properties using setter method of CupcakeDetails class
		cupcake.setCupcakeName("Strawberry Cupcake");
		cupcake.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcake.setPrice(24.0);
		cupcake.setRating(2);
		cupcake.setCupcakeCategory(category);

		// Saving CupcakeDetails object into database using save method of
		// CrudRepository
		CupcakeDetails expected = cupcakeRepository.save(cupcake);

		// Calling findCupcakeDetailsById from SweetCherryServiceImpl class using the
		// sweetCherryService reference

		CupcakeDetails actual = cupcakeService.findCupcakeDetailsById(expected.getCupcakeId());

		// AssertEquals asserts that the passed parameters are equal

		assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		assertEquals(expected.getCupcakeName(), actual.getCupcakeName());

	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testCupcakeByIdShouldThrowNoSuchCupcakeExistException() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			// AssertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testAddCupcakeDetailsShouldAddCupcakeDetails() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object
		CupcakeCategory category = new CupcakeCategory();
		// Setting CupcakeCategory properties using setter method of CupcakeCategory
		// class
		category.setCategoryId(102);
		category.setCategoryName("Premium Cupcake");
		// Initializing CupcakeDetails Object
		CupcakeDetails cupcake = new CupcakeDetails();
		// Setting CupcakeDetails properties using setter method of CupcakeDetails class
		cupcake.setCupcakeName("Strawberry Cupcake");
		cupcake.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcake.setPrice(24.0);
		cupcake.setRating(2);
		cupcake.setCupcakeCategory(category);
		// Saving CupcakeDetails object into database using save method of
		// CrudRepository
		CupcakeDetails expected = cupcakeService.addCupcakeDetails(cupcake);
		// Calling findCupcakeDetailsById from SweetCherryServiceImpl class using the
		// sweetCherryService reference
		CupcakeDetails actual = cupcakeService.findCupcakeDetailsById(expected.getCupcakeId());
		// AssertEquals asserts that the passed parameters are equal
		assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		assertEquals(expected.getCupcakeName(), actual.getCupcakeName());
		assertEquals(expected.getCupcakeDescription(), actual.getCupcakeDescription());
		assertEquals(expected.getPrice(), actual.getPrice());
		assertEquals(expected.getRating(), actual.getRating());

	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testAddCupcakeDetailsShouldThrowNoSuchCupcakeExistException() {
		// AssertThrows asserts that the method should throw an exception of specified
		// type and return the exception
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.addCupcakeDetails(null);
		});
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testUpdateCupcakePriceShouldReturnUpdatePrice() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object
		CupcakeCategory category = new CupcakeCategory();
		// Setting CupcakeCategory properties using setter method of CupcakeCategory
		category.setCategoryId(102);
		category.setCategoryName("Premium Cupcake");
		// Initializing CupcakeDetails Object
		CupcakeDetails cupcake = new CupcakeDetails();
		// Setting CupcakeDetails properties using setter method of CupcakeDetails class
		cupcake.setCupcakeName("Strawberry Cupcake");
		cupcake.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcake.setPrice(24.0);
		cupcake.setRating(2);
		cupcake.setCupcakeCategory(category);
		// Saving CupcakeDetails object into database using save method of
		// CrudRepository
		CupcakeDetails add = cupcakeService.addCupcakeDetails(cupcake);// cupcakeRepository.save(cupcake);
		// Getting the cupcakeId
		add.setCupcakeId(cupcake.getCupcakeId());
		// Updatig the cupcake price
		add.setPrice(30.0);
		// Calling updateCupcakePriceByCupcakeId from SweetCherryServiceImpl class using
		// the sweetCherryService reference
		CupcakeDetails update = cupcakeService.updateCupcakePriceByCupcakeId(add.getCupcakeId(), add.getPrice());
		// AssertEquals asserts that the passed parameters are equal
		assertEquals(add.getPrice(), update.getPrice());
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testUpdateCupcakePriceShouldThrowNoSuchCupcakeExistException() {
		// AssertThrows asserts that the method should throw an exception of specified
		// type and return the exception
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.updateCupcakePriceByCupcakeId(-1, -1);
		});
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testUpdateCupcakeNameShouldReturnUpdateName() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object
		CupcakeCategory category = new CupcakeCategory();
		// Setting CupcakeCategory properties using setter method of CupcakeCategory
		category.setCategoryId(102);
		category.setCategoryName("Premium Cupcake");
		// Initializing CupcakeDetails Object
		CupcakeDetails cupcake = new CupcakeDetails();
		// Setting CupcakeDetails properties using setter method of CupcakeDetails class
		cupcake.setCupcakeName("Strawberry Cupcake");
		cupcake.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcake.setPrice(24.0);
		cupcake.setRating(2);
		cupcake.setCupcakeCategory(category);
		// Saving CupcakeDetails object into database using save method of
		// CrudRepository

		CupcakeDetails add = cupcakeService.addCupcakeDetails(cupcake);
		// Getting the cupcakeId
		add.setCupcakeId(cupcake.getCupcakeId());
		// Updating the cupcake name
		add.setCupcakeName("Carrot Cupcake");
		// Calling modifyCupcakeName from SweetCherryServiceImpl class using
		// the sweetCherryService reference
		CupcakeDetails update = cupcakeService.modifyCupcakeName(add.getCupcakeId(), add.getCupcakeName());
		// AssertEquals asserts that the passed parameters are equal
		assertEquals(add.getCupcakeName(), update.getCupcakeName());
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testUpdateCupcakeNameShouldThrowNoSuchCupcakeExistException() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			// AssertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			cupcakeService.updateCupcakePriceByCupcakeId(-1, -1);
		});
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void removeCupcakeDetailsShouldReturnStringMessage() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object
		CupcakeCategory category = new CupcakeCategory();
		// Setting CupcakeCategory properties using setter method of CupcakeCategory
		category.setCategoryId(102);
		category.setCategoryName("Premium Cupcake");
		// Initializing CupcakeDetails Object
		CupcakeDetails cupcake = new CupcakeDetails();
		// Setting CupcakeDetails properties using setter method of CupcakeDetails class
		cupcake.setCupcakeName("Strawberry Cupcake");
		cupcake.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcake.setPrice(24.0);
		cupcake.setRating(2);
		cupcake.setCupcakeCategory(category);
		// Saving CupcakeDetails object into database using save method of
		// CrudRepository
		CupcakeDetails add = cupcakeService.addCupcakeDetails(cupcake);
		// Calling removeCupcakeDetails from SweetCherryServiceImpl class using
		// the sweetCherryService reference
		String remove = cupcakeService.removeCupcakeDetails(add.getCupcakeId());
		// AssertEquals asserts that the passed parameters are equal
		assertEquals( "Cupcake Removed",remove);
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testremoveCupcakeDetailsShouldThrowNoSuchCupcakeExistException() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			// AssertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			cupcakeService.removeCupcakeDetails(-1);
		});
	}

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit

	void testShowCupcakeDetailsShouldReturnListOfCupcakes() throws NoSuchCupcakeExists {
		// Initializing CupcakeCategory Object and using constructor insert values
		CupcakeCategory category = new CupcakeCategory(102, "Premium Cupcakes");
		// Initializing CupcakeDetails Object and using constructor insert values
		CupcakeDetails cupcake1 = new CupcakeDetails("Carrot Cupcake", "vgwgd", 50, 1, category);
		// Initializing CupcakeDetails Object and using constructor insert values
		CupcakeDetails cupcake2 = new CupcakeDetails("Mocho Cupcake", "wrfsfgs", 60, 2, category);
		// Saving CupcakeDetails object into database using addCupcakeDetails method
		cupcakeService.addCupcakeDetails(cupcake1);
		cupcakeService.addCupcakeDetails(cupcake2);
		// Calling showCupcakeDetails from SweetCherryServiceImpl class using
		// the sweetCherryService reference

		List<CupcakeDetails> actual = cupcakeService.showCupcakeDetails();
		// AssertEquals asserts that the passed parameters are equal
		assertEquals(cupcake2.getCupcakeId(), actual.get(actual.size() - 1).getCupcakeId());
	}

}
