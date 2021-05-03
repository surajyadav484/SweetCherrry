package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.repository.CupcakeDetailsRepository;
import static org.mockito.Mockito.when;

/*The @SpringBootTest annotation tells Spring Boot to look for a main configuration class 
and use that to start a Spring application context that is to be used in test*/
@SpringBootTest
class SweetCherryServiceImplTestUsingMockito {

	@Autowired
	private SweetCherryService cupcakeService;

	/*
	 * @MockBean allows to mock a class or an interface and to record and verify
	 * behaviors on it
	 */
	@MockBean
	// Creating reference of CupcakeDetailsRepository
	private CupcakeDetailsRepository cupcakeRepository;

	// @Test annotation tells JUnit that this method needs to be tested
	@Test
	// Defining a void method to be tested by JUnit
	void testFindCupcakeByIdShouldReturnCupcakeObjectUsingMockito() throws NoSuchCupcakeExists {
		CupcakeDetails cupcakeDetails = new CupcakeDetails();

		cupcakeDetails.setCupcakeId(10);
		cupcakeDetails.setCupcakeName("Carrot Cupcake");
		cupcakeDetails.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcakeDetails.setPrice(50);
		cupcakeDetails.setRating(0);

		CupcakeCategory category = new CupcakeCategory();
		category.setCategoryId(102);
		category.setCategoryName("Custom Cupcakes");

		cupcakeDetails.setCupcakeCategory(category);
		// Optional.of() method is used to get the object of Optional Class with
		// specified value of Specified type
		// Creating a CupcakeDetails Object with Optional.of() method

		Optional<CupcakeDetails> expected = Optional.of(cupcakeDetails);
		// Mocking the dependencies using when().thenReturns() of Mockito
		when(cupcakeRepository.findById(10)).thenReturn(expected);
		// Calling findCupcakeDetailsById method from SweetCherryServiceImpl using
		// sweetCherryService reference
		CupcakeDetails result = cupcakeService.findCupcakeDetailsById(cupcakeDetails.getCupcakeId());
		assertEquals(cupcakeDetails, result);

	}

	
	@Test
		void testFindCupcakeByIdShouldThrowNoSuchCupcakeExistsUsingMockito() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}

	@Test
	void testAddCupcakeDetailsShouldReturnCupcakeDetailsUsingMockito() throws NoSuchCupcakeExists {
		CupcakeDetails cupcakeDetails = new CupcakeDetails();

		cupcakeDetails.setCupcakeId(10);
		cupcakeDetails.setCupcakeName("Carrot Cupcake");
		cupcakeDetails.setCupcakeDescription(
				"The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcakeDetails.setPrice(50);
		cupcakeDetails.setRating(0);

		CupcakeCategory category = new CupcakeCategory();
		category.setCategoryId(102);
		category.setCategoryName("Custom Cupcakes");

		cupcakeDetails.setCupcakeCategory(category);

		when(cupcakeRepository.save(cupcakeDetails)).thenReturn(cupcakeDetails);

		CupcakeDetails result = cupcakeService.addCupcakeDetails(cupcakeDetails);
		assertEquals(cupcakeDetails, result);

	}

	@Test
	void testAddCupcakeDetailsShouldThrowNoSuchCupcakeExistsUsingMockito() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.addCupcakeDetails(null);
		});
	}

	@Test
	void testModifyCupcakePriceShouldReturnUpdatedCupcakeDetailsUsingMockito() throws NoSuchCupcakeExists {

		CupcakeCategory category = new CupcakeCategory(1, "Premium");
		CupcakeDetails cupcake = new CupcakeDetails(2042, "Tiramisu cupcake", "tasty", 50, 1, category);
	
		Optional<CupcakeDetails> expected = Optional.of(cupcake);
		when(cupcakeRepository.findById(cupcake.getCupcakeId())).thenReturn(expected);
		CupcakeDetails cupcake1 = cupcakeService.updateCupcakePriceByCupcakeId(cupcake.getCupcakeId(), 40);

		// CupcakeDetails result =
		// cupcakeService.findCupcakeDetailsById(cupcake1.getCupcakeId());
		assertEquals(expected.get(), cupcake1);
	}

//ok
	@Test
	void testModifyCupcakePriceShouldThrowNoSuchCupcakeExists() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}

	@Test
	void testModifyCupcakeNameShouldReturnUpdatedCupcakeDetailsUsingMockito() throws NoSuchCupcakeExists {

		CupcakeCategory category = new CupcakeCategory(1, "Premium");
		CupcakeDetails cupcake = new CupcakeDetails(2042, "Tiramisu cupcake", "tasty", 50, 1, category);

		Optional<CupcakeDetails> expected = Optional.of(cupcake);
		when(cupcakeRepository.findById(cupcake.getCupcakeId())).thenReturn(expected);
		CupcakeDetails cupcake1 = cupcakeService.modifyCupcakeName(cupcake.getCupcakeId(), "Carrot Cupcake");

		assertEquals(expected.get(), cupcake1);
	}

	// ok
	@Test
	void testModifyCupcakeNameShouldThrowNoSuchCupcakeExists() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}

	@Test
	void testRemoveCupcakeDetailshouldReturnStringValueUsingMockito() throws NoSuchCupcakeExists {
		CupcakeDetails cupcakeDetails = new CupcakeDetails();

		cupcakeDetails.setCupcakeId(10);
		cupcakeDetails.setCupcakeName("Carrot Cupcake");
		cupcakeDetails.setCupcakeDescription(
				"The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcakeDetails.setPrice(50);
		cupcakeDetails.setRating(0);

		CupcakeCategory category = new CupcakeCategory();
		category.setCategoryId(102);
		category.setCategoryName("Custom Cupcakes");

		cupcakeDetails.setCupcakeCategory(category);

		// Optional<CupcakeDetails> expected = Optional.of(null);
		when(cupcakeRepository.findById(10)).thenReturn(null);

		String result = cupcakeService.removeCupcakeDetails(cupcakeDetails.getCupcakeId());
		assertEquals(result, "Cupcake Removed");

	}

//ok
	@Test
	void testRemoveCupcakeDetailsShouldThrowNoSuchCupcakeExistsUsingMockito() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}
}
