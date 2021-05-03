package com.capgemini.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.AddressRepository;
import com.capgemini.repository.CupcakeDetailsRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;
import com.capgemini.repository.UserDetailsRepository;

@SpringBootTest
class SweetCherryServiceImplTest {

	@Autowired
	private UserDetailsRepository userRepository;
	
	@Autowired
	private SweetCherryService cupcakeService;
	
	@Autowired
	private CupcakeDetailsRepository cupcakeRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	// creating reference of AddressRepository interface
	private AddressRepository addressRepository;

	
	
	
	// TEST CASES FOR LOGIN MODUE ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	
	@Test
	void testGetAllAdminAndUserDetails() throws NoSuchUserExists {
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmail("dummyydata123@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyDattaqwerty@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhhi");
		address.setHouseNo("345");
		address.setLandmark("Neaar landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);

		userRepository.save(userDetails);
		
		List<UserDetails> expected = userRepository.findAll();
		List<UserDetails> actual = cupcakeService.allDetailsOfAdminAndUser();

		// Asserts that the supplied condition is true
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0).getEmail(), actual.get(0).getEmail());
		assertEquals(expected.get(0).getPassword() , actual.get(0).getPassword());
		
	}
                
	@Test
	void testFindUserByUserIdShouldReturnUserObject() throws InvalidIdException {
		UserDetails userDetails = new UserDetails();

		userDetails.setEmail("dummydata123@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyDataqwerty@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhhi");
		address.setHouseNo("345");
		address.setLandmark("Neaar landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = cupcakeService.allUserDetailsById(expected.getUserId());

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());

	}

	@Test
	void testFindUserByUserIdShouldThrowException() {
		assertThrows(InvalidIdException.class, () -> {
			cupcakeService.allUserDetailsById(-1);
		});
	}

	// Test case for register customer
	@Test
	void testregisterCustomerShouldReturnUserDetailsObject() throws UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails();

		
		userDetails.setEmail("dummydata12@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyData@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhi");
		address.setHouseNo("345");
		address.setLandmark("Near landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = cupcakeService.registerCustomer(userDetails);

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void testregisterCustomerShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.registerCustomer(null);
		});
	}

	@Test
	void testLogoutShouldDisplayMessageOfSuccessfulLogout() {

		String expectedMsg = "You have been logged out successfully";
		String actualMsg = cupcakeService.logout();

		assertTrue(expectedMsg.equalsIgnoreCase(actualMsg));
	}

	@Test
	void testLoginShouldReturnLoginSuccessfulAsUser() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {

		UserDetails userDetails = new UserDetails();

		userDetails.setEmail("Dummydataaa456@gmail.com");
		userDetails.setPassword("DummyData@1Dummy");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");

		Role role = new Role(2, "user"); 
		userDetails.setRole(role);

		UserDetails expected = userRepository.save(userDetails);
		String actual = cupcakeService.login(expected.getEmail(), expected.getPassword());

		assertEquals("login SuccessFul as User",actual);

	}

	
	  @Test void LoginShouldThrowNoSuchUserExistsForAdmin(){
	  assertThrows(NoSuchUserExists.class, () -> {
		  cupcakeService.login("KetanMishra", null); }); }
	  
	  @Test void testLoginShouldReturnLoginSuccessfulAsAdmin() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
	  
	  UserDetails userDetails = new UserDetails();
	  
	  userDetails.setEmail("KetanMishraaaa@gmail.com");
	  userDetails.setPassword("KetanMishra@123"); userDetails.setFirstName("Dummy");
	  userDetails.setLastName("Data");
	  
	  Role role = new Role(1,"admin") ; // 
	  userDetails.setRole(role);
	  
	  UserDetails expected = userRepository.save(userDetails);
	  String actual =cupcakeService.login(expected.getEmail(), expected.getPassword());
	  
	  assertEquals("login SuccessFul as Administrator",actual);
	  
	  }
	  
	  @Test void LoginShouldThrowNoSuchUserExists(){
	  assertThrows(NoSuchUserExists.class, () -> {
		  cupcakeService.login("KetanMishra","Password12"); }); }
	 

	@Test
	void updateCustomerProfileShouldReturnUpdatedCustomerProfile() throws UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails();

		userDetails.setFirstName("Ketan");
		userDetails.setLastName("Mishra");
		userDetails.setEmail("KetanMishra@gmail.com");
		userDetails.setPassword("KetanM@1234");

		Role role = new Role(2, "user");

		userDetails.setRole(role);

		Address address = new Address();
		address.setCity("Delhi");
		address.setHouseNo("756");
		address.setLandmark("Near plaza");
		address.setPinCode("321494");
		address.setState("Delhi");

		Set<Address> addressSet = new HashSet<>();
		addressSet.add(address);
		userDetails.setAddress(addressSet);

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = cupcakeService.updateCustomerProfile(expected);

		for(Address addresss : actual.getAddress()) {
			
			assertEquals("Delhi",addresss.getCity());
			assertEquals("Delhi",addresss.getState());
			assertEquals("321494",addresss.getPinCode());
			assertEquals("756",addresss.getHouseNo());
			assertEquals("Near plaza",addresss.getLandmark());
		}
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void updateCustomerProfileThrowsUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.updateCustomerProfile(null);
		});
	}

	@Test
	void UpdatePasswordShouldReturnUpdatedPassword() throws UserNameAndPasswordDoNotMatchRegularExpression {
     UserDetails userDetails = new UserDetails();
     
    userDetails.setFirstName("Ketan");
	userDetails.setLastName("Mishraa");
	userDetails.setEmail("KetanMishraa@gmail.com");
	userDetails.setPassword("KetanM@1234");
	
	UserDetails expected = userRepository.save(userDetails);
    String msg = cupcakeService.modifyPassword(expected.getUserId(),expected.getPassword(),"KetanMishra@123");
    
    assertEquals("Password changed successfully",msg);
    
	}

	@Test
	void UpdatePasswordShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.modifyPassword(1, "oldPass@124", "newpassword123");
		});
	}
	
	
	
	
	
	
	
	// TEST CASES FOR CUPCAKE MODULE ------------------------------------------------------------------------------------------------------------------------------------------------
	
	// admin module
	
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


	
	
			
	
	//user module
	
	@Test
	void testFindCupcakeByIdShouldReturnCupcakeDetails() throws NoSuchCupcakeExists {
		
		CupcakeCategory category = new CupcakeCategory(102,"Premium Cupcakes");
		cupcakeService.addCupcakeCategory(category);
		CupcakeDetails cupcakeDetails = new  CupcakeDetails( "Carrot Cupcake", "The cake is very moist and the icing is delicious. It's not too sweet and not greasy." , 50,0,category);
		
		  CupcakeDetails expected = cupcakeService.addCupcakeDetails(cupcakeDetails);
		  CupcakeDetails actual = cupcakeService.findCupcakeDetailsById(expected.getCupcakeId());
		  
		  assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
		  assertEquals(expected.getCupcakeName(), actual.getCupcakeName());
		 
		
	}
	
	 
	 @Test 
	  void testCupcakeByIdShouldThrowNoSuchCupcakeExistException() {
		  assertThrows(NoSuchCupcakeExists.class, () -> {
			 cupcakeService.findCupcakeDetailsById(-1);
		  });
	  }
	 
	 
	 
	 
	 
	 
		@Test void testModifyCupCakeRatingShouldReturnUpdatedCupcakeDetails() throws NoSuchCupcakeExists 
		{ 
			CupcakeCategory category = new CupcakeCategory(101,"Premium Cupcakes");
			cupcakeService.addCupcakeCategory(category);
			CupcakeDetails cupcake = new CupcakeDetails(504,"Choco lava", "Cupcake filled wwith chocolate",60,1,category);
			CupcakeDetails cup= cupcakeRepository.save(cupcake);
			CupcakeDetails expected =cupcakeService.modifyCupcakeRating(cup.getCupcakeId(), 4); 
			CupcakeDetails actual =cupcakeService.findCupcakeDetailsById(cup.getCupcakeId());
			
			assertEquals(expected.getCupcakeId(), actual.getCupcakeId());
			assertEquals(expected.getRating(), actual.getRating());
		}


		@Test
		void testModifyCupcakeRatingShouldThrowNoSuchCupcakeExistsException() {
			assertThrows(NoSuchCupcakeExists.class, () -> {
				cupcakeService.findCupcakeDetailsById(-1);
			});
		}
		 
	 
		
		@Test
		void testAddCupcakeToCartShouldReturnOrderDetails() throws NoSuchOrderExists {
			
			  
			 
			Orders order= new  Orders(01, "20/03/2021", 1,"pending", 520,null,null);
			Orders orderObj = orderRepository.save(order);
			Orders actualobj = cupcakeService.addCupcakeToCart(orderObj);
			
			assertEquals(orderObj.getOrderId(),actualobj.getOrderId());
		}

		
		@Test
		void testAddCupcakeToCartShouldReturnOrderDetailsShouldThrowNoSuchOrderExistsException() {
			Orders order= new  Orders(01, "20/03/2021",15,"pending", 520,null,null);
			assertThrows(NoSuchOrderExists.class, ()-> cupcakeService.addCupcakeToCart(order));
		}
		
		
		@Test
		
		 void testShowCupcakeDetailsShouldReturnListOfCupcakes() throws NoSuchCupcakeExists {
			 CupcakeCategory category = new CupcakeCategory(101,"Premium Cupcakes");
			 cupcakeService.addCupcakeCategory(category);
			 CupcakeDetails cupcake1 = new CupcakeDetails( "msbc", "vgwgd", 50, 1, category);
			 CupcakeDetails cupcake2 = new CupcakeDetails( "dvg cupcake", "wrfsfgs", 60, 2, category);

			 cupcakeService.addCupcakeDetails(cupcake1);
			 cupcakeService.addCupcakeDetails(cupcake2);
			 
			 List<CupcakeDetails> actual = cupcakeService.showCupcakeDetails();
			 assertEquals(cupcake2.getCupcakeId() ,actual.get(actual.size() -1).getCupcakeId());
		 }
		
		
		@Test
		void testAddPaymentDetailsShouldReturnPaymentObject() throws NoSuchOrderExists {
			Payment payment = new Payment( 102354789652L, 102, "manisha", "10/10/2023", "successful", null);
			
			
			Payment expected = paymentRepository.save(payment);
			Payment actual= cupcakeService.addPaymentDetails(payment);
			
			assertEquals(expected.getPaymentId(), actual.getPaymentId());
			assertEquals(expected.getCardNo(), actual.getCardNo());
			
		}
		
		@Test
		void testAddPaymentDetailsShouldThrowNoSuchOrderExistsException() {
			Orders order= new  Orders(05, "20/04/2021", 11,"successful", 520,null,null);
			assertThrows(NoSuchOrderExists.class, ()-> cupcakeService.addCupcakeToCart(order));
		}
		
		/*
		 * @Test void testAddCategoryShouldReturnCupcakeCategory() { CupcakeCategory
		 * category = new CupcakeCategory(200, "Birthday Cupcakes"); CupcakeCategory
		 * categoryObject = cupcakeService.addCupcakeCategory(category);
		 * Optional<CupcakeCategory> expected = Optional.of(categoryObject);
		 * Optional<CupcakeCategory> actual =
		 * cupcakeCategoryRepository.findById(category.getCategoryId());
		 * 
		 * assertEquals(expected, actual); }
		 */
		
		
		
		// 3. TEST CASES FOR ORDER MODULE -------------------------------------------------------------------------------------------------------
		
		

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlinePaymentShouldSavePaymentObject() throws PaymentFailedException {
			// initializing payment Object
			Payment payment = new Payment();
			// setting payment properties using setter method of Payment class
			payment.setCardHolderName("suraj");
			payment.setCardNo(3637477883l);
			payment.setCvv(317);
			payment.setExpiryDate("28/04/2021");
			payment.setPaymentStatus("cart");

			// saving payment object into database using save method of CrudRepository
			Payment expected = paymentRepository.save(payment);

			// calling makeOnlinePayment(payment) from SweetCherryServiceImpl class using
			// the sweetCherryService reference
			Payment actual = cupcakeService.makeOnlinePayment(payment);

			// assertEquals asserts that the passed parameters are equal
			assertEquals(expected.getPaymentId(), actual.getPaymentId());
			assertEquals(expected.getCardHolderName(), actual.getCardHolderName());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlinePaymentShouldThrowPaymentFailedException() {
			// initializing Payment object with by calling a parameterized constructor
			Payment payment = new Payment("123");

			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(PaymentFailedException.class, () -> cupcakeService.makeOnlinePayment(payment));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlineOrderShouldSetOrderStatusAsOrdered() throws NoSuchOrderExists {
			// Initializing an Order object by calling the parameterized constructor
			Orders order = new Orders(1, "29/02/2021", 1, "pending", 50, null, null);

			// saving the order object using save method of CrudRepository
			Orders orderObj = orderRepository.save(order);

			// Calling makeOnlineOrder() method from SweetCherryServiceimpl Class using
			// SweetCherryService reference
			Orders actualobj = cupcakeService.makeOnlineOrder(orderObj.getOrderId());

			// assertEquals asserts that expected and actual are equal
			assertEquals("Ordered", actualobj.getOrderStatus());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.makeOnlineOrder(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testCancelOnlineOrderShoudSetOrderStatusAsCancelled() throws NoSuchOrderExists {
			// Initializing an Order object by calling the parameterized constructor
			Orders order = new Orders(1, "29/02/2021", 1, "Ordered", 50, null, null);

			// saving the order object using save method of CrudRepository
			Orders orderObj = orderRepository.save(order);

			// Calling cancelOnlineOrder() method from SweetCherryServiceimpl Class using
			// SweetCherryService reference
			Orders actualObj = cupcakeService.cancelOnlineOrder(orderObj.getOrderId());

			// assertEquals asserts that expected and actual are equal
			assertEquals("cancelled", actualObj.getOrderStatus());
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {

			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.cancelOnlineOrder(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testShowOrderDetailsByUserIdShouldReturnOrederDetails() throws NoSuchOrderExists {
			// Initializing an UserDetails object by calling the parameterized constructor
			UserDetails userDetails = new UserDetails(1, "suraj", "yadav", "sky@gmail.com", "2232", null, null);

			// Initializing an Order object by calling the default constructor
			Orders order = new Orders();

			// Setting properties of order object
			order.setOrderDate("27/04/2021");

			order.setOrderStatus("ordered");
			order.setQuantity(1);
			order.setTotalPrice(50);
			order.setUserDetails(userDetails);

			// saving the order object using save method of CrudRepository
			Orders orderObj = orderRepository.save(order);

			// Calling findByuserId() from OrderRepository using orderRepository reference
			List<Orders> expected = orderRepository.findByuserId(orderObj.getUserDetails().getUserId());

			// Calling showOrderDetailsByUserId() from method from SweetCherryServiceimpl
			// Class using SweetCherryService reference
			List<Orders> actual = cupcakeService.showOrderDetailsByUserId(orderObj.getUserDetails().getUserId());

			// assertTrue asserts that the supplied condition is true.
			assertTrue(expected.size() == actual.size()); // checking for equality of size of both list
			assertEquals(expected.get(0).getUserDetails().getUserId(), actual.get(0).getUserDetails().getUserId()); // checking
																													// for
																													// equality
																													// of
																													// userId
			assertEquals(expected.get(0).getUserDetails().getFirstName(), actual.get(0).getUserDetails().getFirstName()); // checking
																															// for
																															// equality
																															// of
																															// firstName
			assertEquals(expected.get(0).getOrderDate(), actual.get(0).getOrderDate()); // checking for equality of
																						// orderDate
			assertEquals(expected.get(0).getOrderStatus(), actual.get(0).getOrderStatus()); // checking for equality of
																							// orderStatus
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testShowOrderDetailsByUserIdShouldThrowNoSuchOrderExistsException() {
			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.showOrderDetailsByUserId(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testmodifyDeliveryAddressShoudUpdateAddress() throws NoSuchUserExists {
			// Initializing an Address object by calling the default constructor
			Address address = new Address();

			// Setting properties of address object
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("Stadium");
			address.setState("MH");
			address.setPinCode("22343");

			// Initializing a hashSet Object using new operator
			Set<Address> addressSet = new HashSet<Address>();
			addressSet.add(address);

			// Initializing UserDetails object using default constructor
			UserDetails userDetails = new UserDetails();
			userDetails.setEmail("sky@322");
			userDetails.setFirstName("suraj");
			userDetails.setLastName("yadav");
			userDetails.setPassword("23233");
			userDetails.setAddress(addressSet);

			// Saving userDetails Object using save() method of CrudRepository
			UserDetails expected = userRepository.save(userDetails);

			// Calling modifyDeliveryAddress() from SweetCherryServiceImpl using
			// sweetCherryService reference
			UserDetails actualObj = cupcakeService.modifyDeliveryAddress(expected);

			// Iterating through set Using enhanced for loop
			for (Address actualAddressobj : actualObj.getAddress()) {

				// assertTrue asserts that the supplied condition is true.
				assertEquals("mumbai", actualAddressobj.getCity()); // Checking for the equality of city
				assertEquals("12", actualAddressobj.getHouseNo()); // Checking for the equality of houseNo
				assertEquals("22343", actualAddressobj.getPinCode()); // Checking for the equality of pinCode

			}

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testmodifyDeliveryAddressShoudThrowNoSuchUserExistsException() {
			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(NoSuchUserExists.class, () -> cupcakeService.modifyDeliveryAddress(null));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testAddDeliveryAddressShouldAddAddress() throws NoSuchUserExists {
			// Initializing Address Object using default Constructor
			Address address = new Address();

			// Setting Properties using Setter Methods
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("Stadium");
			address.setState("MH");
			address.setPinCode("22343");

			Set<Address> addressSet = new HashSet<Address>();
			addressSet.add(address);

			UserDetails userDetails = new UserDetails();
			userDetails.setEmail("sky@322");
			userDetails.setFirstName("suraj");
			userDetails.setLastName("yadav");
			userDetails.setPassword("23233");
			userDetails.setAddress(addressSet);

			// Calling addDeliveryAddress() method from SweetCherryServiceImpl Using
			// sweetCherryService reference
			UserDetails actualObj = cupcakeService.addDeliveryAddress(userDetails);

			// Iterating Through Address object Using Enhanced For Loop
			for (Address actualAddressobj : actualObj.getAddress()) {
				assertEquals("mumbai", actualAddressobj.getCity());
				assertEquals("12", actualAddressobj.getHouseNo());
				assertEquals("22343", actualAddressobj.getPinCode());
			}

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testAddDeliveryAddressShouldThrowNoSuchUSerExistsException() {
			// assertThrows asserts that the method should throw an exception of specified
			// type and return the exception
			assertThrows(NoSuchUserExists.class, () -> cupcakeService.addDeliveryAddress(null));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void deleteDeliveryAddressShouldDeleteAddress() throws NoSuchAddressExists {
			// Initializing Address Object using default Constructor
			Address address = new Address();
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("Stadium");
			address.setState("MH");
			address.setPinCode("22343");

			Address addressObj = addressRepository.save(address);
			// Calling deleteDeliveryAddress() Method Using sweetCherryService reference
			boolean result = cupcakeService.deleteDeliveryAddress(addressObj.getAddressId());

			// Asserts that the supplied condition is true.
			assertTrue(result);

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void deleteDeliveryAddressShouldThrowNoSuchAddressExists() {
			assertThrows(NoSuchAddressExists.class, () -> cupcakeService.deleteDeliveryAddress(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testGetAllOrderDetails() throws NoSuchOrderExists {
			UserDetails userDetails = new UserDetails(1, "suraj", "yadav", "sky@gmail.com", "2232", null, null);

			Orders order = new Orders();
			order.setOrderDate("27/04/2021");
			order.setOrderId(1);
			order.setOrderStatus("ordered");
			order.setQuantity(1);
			order.setTotalPrice(50);
			order.setUserDetails(userDetails);

			Orders orderObj = orderRepository.save(order);

			List<Orders> expected = orderRepository.findAll();
			// Calling getAllOrderDetails() method from SweetCherryServiceImpl Using
			// sweetCherryService reference
			List<Orders> actual = cupcakeService.getAllOrderDetails();

			// Asserts that the supplied condition is true
			assertEquals(expected.size(), actual.size());
			assertEquals(expected.get(0).getUserDetails().getFirstName(), actual.get(0).getUserDetails().getFirstName());
			assertEquals(expected.get(0).getOrderId(), actual.get(0).getOrderId());
			assertEquals(expected.get(0).getOrderDate(), actual.get(0).getOrderDate());

		}

		/*
		 * @Test void testGetAllOrderDetailsShouldThrowNoSuchOrderExistsException() {
		 * orderRepository.save(null); assertThrows(NoSuchOrderExists.class, () ->
		 * sweetCherryService.getAllOrderDetails()); }
		 */

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testGetDeliveryAddressShouldReturnAddress() throws NoSuchAddressExists {

			Address address = new Address();
			address.setAddressId(1);
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("dairy");
			address.setState("MH");

			Address addressObj = addressRepository.save(address);
			// Calling getDeliveryAddress() method from SweetCherryServiceImpl Using
			// sweetCherryService reference
			Address actualObj = cupcakeService.getDeliveryAddress(addressObj.getAddressId());

			assertEquals("mumbai", actualObj.getCity());
			assertEquals("MH", actualObj.getState());
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
			assertThrows(NoSuchAddressExists.class, () -> cupcakeService.getDeliveryAddress(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testGetOrdersdeDetailsByIdShoudReturnOrder() throws NoSuchOrderExists {
			Orders order = new Orders();

			order.setOrderDate("29/04/2021");
			order.setOrderStatus("pending");
			order.setQuantity(1);
			order.setTotalPrice(40);

			Orders orderObj = orderRepository.save(order);
			// Calling getOrderDetailsById() method from SweetCherryServiceImpl Using
			// sweetCherryService reference
			Orders actualObj = cupcakeService.getOrderDetailsById(orderObj.getOrderId());

			assertEquals(actualObj.getOrderDate(), orderObj.getOrderDate());
			assertEquals(actualObj.getOrderId(), orderObj.getOrderId());
			assertEquals(actualObj.getOrderStatus(), orderObj.getOrderStatus());
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested

		// Defining a void method to be tested by JUnit
		void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.getOrderDetailsById(-1));
		}

	
	}
