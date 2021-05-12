package com.capgemini.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
import com.capgemini.repository.RoleRepository;
import com.capgemini.repository.UserDetailsRepository;

@SpringBootTest
class SweetCherryServiceImplUsingMockitoTest {

	
	@MockBean
	private UserDetailsRepository userDetailsRepository ;
	
	@Autowired
	private RoleRepository roleRepository ; 
	
	@Autowired
	private SweetCherryService cupcakeService;
	
	
	@MockBean
	private CupcakeDetailsRepository cupcakeRepository;
	
	
	@MockBean
	private OrderRepository orderRepository;
	
	@MockBean
	private PaymentRepository paymentRepository;
	
	
	@MockBean
	// Creating reference of AddressRepository
	private AddressRepository addressRepository;

	
	// TEST CASES FOR LOGIN MODUE ---------------------------------------------------------------------------------------------------------------------------------------------------

	
	
	@Test
    void testFindUserByUserIdShouldReturnUserObject() throws NoSuchUserExists, InvalidIdException{
    
		UserDetails userDetails = new UserDetails() ;
    
		userDetails.setUserId(101);
		userDetails.setEmail("dummyyydata123@gmail.com");
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

		Optional<UserDetails> expected = Optional.of(userDetails) ;
		when(userDetailsRepository.findById(101)).thenReturn(expected);
		
		UserDetails actual = cupcakeService.allUserDetailsById(userDetails.getUserId()) ;
		
		assertEquals(expected.get(), actual);
		
	}
	@Test
	void testFindUserByUserIdShouldThrowException() {
		assertThrows(InvalidIdException.class, () -> {
			cupcakeService.allUserDetailsById(-1);
		});
	}
	
	@Test
	void testLoginShouldReturnStringMessageForUser() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails() ;
		userDetails.setEmail("dummyyydata123@gmail.com");
		userDetails.setPassword("DummyDattaqwerty@123");
		userDetails.setUserId(10001);
		
		Role role =  new Role(2,"user");
		roleRepository.save(role);
		userDetails.setRole(role);
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		when(userDetailsRepository.login("dummyyydata123@gmail.com", "DummyDattaqwerty@123")).thenReturn(expected.get());
		String str = cupcakeService.login(userDetails.getEmail(),userDetails.getPassword());
		
		assertEquals("login SuccessFul as User", str);
	}
	  @Test void LoginShouldThrowNoSuchUserExistsForUser(){
	  assertThrows(NoSuchUserExists.class, () -> {
		  cupcakeService.login("KetanMishra", null); }); }                     


   @Test
   void testLoginShouldReturnStringMessageForAdmin() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
	UserDetails userDetails = new UserDetails() ;
	userDetails.setEmail("dummyyydata123@gmail.com");
	userDetails.setPassword("DummyDattaqwerty@123");
	userDetails.setUserId(10001);
	
	Role role =  new Role(1,"admin");
	userDetails.setRole(role);
	
	Optional<UserDetails> expected = Optional.of(userDetails);
	when(userDetailsRepository.login("dummyyydata123@gmail.com", "DummyDattaqwerty@123")).thenReturn(expected.get());
	String str = cupcakeService.login(userDetails.getEmail(),userDetails.getPassword());
	
	assertEquals("login SuccessFul as Administrator", str);
}
  @Test void LoginShouldThrowNoSuchUserExistsForAdmin(){
  assertThrows(NoSuchUserExists.class, () -> {
	  cupcakeService.login("KetanMishra", null); }); }   

  
  @Test
  void LogoutShouldReturnStringSayingLogoutSuccessful() {
	    String expectedMsg = "You have been logged out successfully";
		String actualMsg = cupcakeService.logout();

		assertTrue(expectedMsg.equalsIgnoreCase(actualMsg));
	}  
  
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
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		when(userDetailsRepository.save(userDetails)).thenReturn(expected.get());
		
		UserDetails actual = cupcakeService.registerCustomer(userDetails);
		
		assertEquals(userDetails, actual);
  }
    @Test
	void testregisterCustomerShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.registerCustomer(null);
		});
    }

    @Test
    void testUpdatePasswordShouldReturnUpdatedPassword() throws UserNameAndPasswordDoNotMatchRegularExpression{
    	UserDetails userDetails = new UserDetails();
    	
    	userDetails.setEmail("dummydata12@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyData@123");
		userDetails.setUserId(1);
    	
		Optional<UserDetails> expected =Optional.of(userDetails);
	   // when(userDetailsRepository.updatePassword(1,"DummyData@123","KetanMishra@1234"));
		String str = cupcakeService.modifyPassword(expected.get().getUserId(),expected.get().getPassword(),"KetanMishra@1234");
      
		assertEquals("Password changed successfully", str );
		
    }
    @Test
	void UpdatePasswordShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.modifyPassword(1, "oldPass@124", "newpassword123");
		});
}


  @Test void testUpdateCustomerProfileShouldReturnUserDetailsObject() throws UserNameAndPasswordDoNotMatchRegularExpression{ 
	  
	  UserDetails userDetails = new UserDetails();
  
  userDetails.setFirstName("Ketan");
  userDetails.setLastName("Mishra");
  userDetails.setEmail("KetanMishra@gmail.com");
  userDetails.setPassword("KetanM@1234");
  
  Role role = new Role(2, "user");
  
  userDetails.setRole(role);
  
  Address address = new Address(); address.setCity("Delhi");
  address.setHouseNo("756"); address.setLandmark("Near plaza");
  address.setPinCode("321494"); address.setState("Delhi");
  
  Set<Address> addressSet = new HashSet<>(); addressSet.add(address);
  userDetails.setAddress(addressSet);
  
  when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);
  UserDetails actual = cupcakeService.updateCustomerProfile(userDetails);
  
  assertEquals(actual, userDetails);
  
  
  }
    @Test
	void UpdateCustomerProfileThrowsUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			cupcakeService.updateCustomerProfile(null);
		});
}
	
	
	
	
	// TEST CASES FOR CUPCAKE MODUE ---------------------------------------------------------------------------------------------------------------------------------------------------

	//admin
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


	@Test
	void testRemoveCupcakeDetailsShouldThrowNoSuchCupcakeExistsUsingMockito() {
		assertThrows(NoSuchCupcakeExists.class, () -> {
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}
    
    
    //user
	
	@Test
	void testFindCupcakeByIdShouldReturnCupcakeObjectUsingMockito() throws NoSuchCupcakeExists {
		CupcakeDetails cupcakeDetails = new CupcakeDetails();
		
		cupcakeDetails.setCupcakeId(2245);
		cupcakeDetails.setCupcakeName("Carrot Cupcake");
		cupcakeDetails.setCupcakeDescription("The cake is very moist and the icing is delicious. It's not too sweet and not greasy.");
		cupcakeDetails.setPrice(50);
		cupcakeDetails.setRating(0);
		
	
		CupcakeCategory category = new CupcakeCategory();
		category.setCategoryId(12);
		category.setCategoryName("Custom Cupcakes");
		
		cupcakeDetails.setCupcakeCategory(category);
		
		Optional<CupcakeDetails> expected = Optional.of(cupcakeDetails);
		when(cupcakeRepository.findById(2245)).thenReturn(expected);
		
		CupcakeDetails result = cupcakeService.findCupcakeDetailsById(cupcakeDetails.getCupcakeId());
		assertEquals(cupcakeDetails, result);
		
		
		
	
	}
	
	@Test
	void testFindCupcakeByIdShouldThrowNoSuchCupcakeExists() {
		assertThrows(NoSuchCupcakeExists.class, ()->{
			cupcakeService.findCupcakeDetailsById(-1);
		});
	}

	
	@Test
	void testModifyCupcakeRatingShouldReturnUpdatedCupcakeDetailsUsingMockito() throws NoSuchCupcakeExists {

		CupcakeCategory category = new CupcakeCategory(1, "Premium");
		CupcakeDetails cupcake = new CupcakeDetails(2042,"Tiramisu cupcake", "tasty", 50, 1, category);
		Optional<CupcakeDetails> expected = Optional.of(cupcake);
		when(cupcakeRepository.findById(cupcake.getCupcakeId())).thenReturn(expected);
		CupcakeDetails cupcake1 = cupcakeService.modifyCupcakeRating(cupcake.getCupcakeId(), 4);

		 		assertEquals(expected.get(), cupcake1);
	}
	
 
	
		@Test
		void testModifyCupcakeShouldThrowNoSuchCupcakeExists() {
			assertThrows(NoSuchCupcakeExists.class, ()->{
				cupcakeService.findCupcakeDetailsById(-1);
			});
		}

		
		@Test
		void testAddCupcakeToCartShouldReturnOrderDetails() throws NoSuchOrderExists {
			Orders order= new  Orders(01, "20/03/2021", 1,"pending", 520,null,null);
			Optional<Orders> expected = Optional.of(order);
			when(orderRepository.findById(order.getOrderId())).thenReturn(expected);
			when(orderRepository.save(expected.get())).thenReturn(expected.get());
			
			Orders actualobj = cupcakeService.addCupcakeToCart(order);
			
			assertEquals(expected.get().getOrderId(), actualobj.getOrderId());
			assertEquals(expected.get().getOrderStatus(), actualobj.getOrderStatus());
		}
		
		@Test
		void testAddCupcakeToCartShouldThrowNoSuchOrderExistsException() {
			Orders order= new  Orders(01, "20/03/2021", 15,"pending", 520,null,null);
			assertThrows(NoSuchOrderExists.class, ()-> cupcakeService.addCupcakeToCart(order));
		}
		
		@Test
		
		void testShowCupcakeDetailsShouldReturnListOfCupcakesUsingMockito() throws NoSuchCupcakeExists {
			 CupcakeCategory category = new CupcakeCategory(101,"Premium Cupcakes");
			 CupcakeDetails cupcake1 = new CupcakeDetails( 36,"msbc", "vgwgd", 50, 1, category);
			 CupcakeDetails cupcake2 = new CupcakeDetails(45, "dvg", "wrfsfgs", 60, 2, category);
			
			 List<CupcakeDetails> expected = List.of(cupcake1,cupcake2);
			 
			 when(cupcakeRepository.findAll()).thenReturn(expected);
			 
			 List<CupcakeDetails> actual = (cupcakeService.showCupcakeDetails());

			
			 assertEquals(expected ,actual);
		 }
		
		@Test
		void testShowCupcakeDetailsShouldThrowNoSuchCupcakeExists() {
			when(cupcakeRepository.findAll()).thenReturn(null);
			assertThrows(NoSuchCupcakeExists.class,  () -> cupcakeService.showCupcakeDetails());
		}
		
		
		@Test
		void testAddPaymentDetailsShouldReturnPaymentObjectUsingMockito() throws NoSuchOrderExists {
			Payment payment = new Payment( 102354789652L, 102, "manisha", "10/10/2023", "successful", null);
			
			Optional<Payment> expected = Optional.of(payment);
			when(paymentRepository.save(payment)).thenReturn(expected.get());
			Payment actual= cupcakeService.addPaymentDetails(expected.get());
			
			assertEquals(expected.get(), actual);
		}
		
		@Test
		void testAddPaymentDetailsShouldThrowNoSuchOrderExistsException() {
			Orders order= new  Orders(01, "20/03/2021", 15,"pending", 520,null,null);
			assertThrows(NoSuchOrderExists.class, ()-> cupcakeService.addCupcakeToCart(order));

		}
		
		
		//TEST CASES FOR ORDER MODULE -----------------------------------------------------------------------------------------------------------------------------
		
		
		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlinePaymentShouldSavePaymentObject() throws PaymentFailedException {
			Payment payment = new Payment();
			payment.setPaymentId(1);
			payment.setCardNo(535663);
			payment.setCardHolderName("suraj");
			payment.setCvv(123);
			payment.setPaymentStatus("successful");

			// Optional.of() method is used to get the object of Optional Class with
			// specified value of Specified type
			// Creating a Payment Object with Optional.of() method
			Optional<Payment> expected = Optional.of(payment);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(paymentRepository.save(payment)).thenReturn(expected.get());

			// Calling makeOnlinePayment() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			Payment actual = cupcakeService.makeOnlinePayment(expected.get());
			assertEquals(actual, expected.get());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlinePaymentShouldThrowPaymentFailedException() {
			Payment payment = new Payment("123");
			assertThrows(PaymentFailedException.class, () -> cupcakeService.makeOnlinePayment(payment));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlineOrderShouldSetOrderStatusAsOrdered() throws NoSuchOrderExists {
			Orders order = new Orders();
			order.setOrderId(1);
			order.setOrderDate("29/02/2021");
			order.setOrderStatus("pending");
			order.setQuantity(1);
			order.setTotalPrice(50);

			// Creating a Orders Object with Optional.of() method
			Optional<Orders> expected = Optional.of(order);
			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findById(1)).thenReturn(expected);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.save(expected.get())).thenReturn(expected.get());

			// Calling makeOnlineOrder() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			Orders actual = cupcakeService.makeOnlineOrder(order.getOrderId());
			assertTrue(actual.getOrderStatus().equalsIgnoreCase("ordered"));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.makeOnlineOrder(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testCancelOnlineOrderShoudSetOrderStatusAsCancelled() throws NoSuchOrderExists {
			Orders order = new Orders();
			order.setOrderId(1);
			order.setOrderDate("29/02/2021");
			order.setOrderStatus("Ordered");
			order.setQuantity(1);
			order.setTotalPrice(50);

			// Creating a Orders Object with Optional.of() method
			Optional<Orders> expected = Optional.of(order);
			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findById(1)).thenReturn(expected);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.save(expected.get())).thenReturn(expected.get());

			// Calling cancelOnlineOrder() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			Orders actual = cupcakeService.cancelOnlineOrder(order.getOrderId());
			assertTrue(actual.getOrderStatus().equalsIgnoreCase("cancelled"));

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.cancelOnlineOrder(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testShowOrderDetailsByUserIdShouldReturnOrederDetails() throws NoSuchOrderExists {
			UserDetails userDetails = new UserDetails(1, "suraj", "yadav", "sky@gmail.com", "2232", null, null);

			Orders order = new Orders();
			order.setOrderDate("27/04/2021");
			order.setOrderId(1);
			order.setOrderStatus("ordered");
			order.setQuantity(1);
			order.setTotalPrice(50);
			order.setUserDetails(userDetails);

			// Creating a Orders Object with List.of() method
			List<Orders> expected = List.of(order);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findByuserId(1)).thenReturn(expected);

			// Calling showOrderDetailsByUserId() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			List<Orders> actual = cupcakeService.showOrderDetailsByUserId(userDetails.getUserId());

			assertEquals(expected, actual);
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testShowOrderDetailsByUserIdShouldThrowNoSuchOrderExistsException() {
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.showOrderDetailsByUserId(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testmodifyDeliveryAddressShoudUpdateAddress() throws NoSuchUserExists {
			Address address = new Address();
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

			// Creating a UserDetails Object with Optional.of() method
			Optional<UserDetails> expected = Optional.of(userDetails);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(userDetailsRepository.save(userDetails)).thenReturn(expected.get());

			// Calling modifyDeliveryAddress() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			UserDetails actual = cupcakeService.modifyDeliveryAddress(expected.get());

			assertEquals(actual, expected.get());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testmodifyDeliveryAddressShoudThrowNoSuchUserExistsException() {
			assertThrows(NoSuchUserExists.class, () -> cupcakeService.modifyDeliveryAddress(null));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testAddDeliveryAddressShouldAddAddress() throws NoSuchUserExists {
			Address address = new Address();
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

			// Creating a UserDetails Object with Optional.of() method
			Optional<UserDetails> expected = Optional.of(userDetails);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(userDetailsRepository.save(userDetails)).thenReturn(expected.get());

			// Calling addDeliveryAddress() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			UserDetails actual = cupcakeService.addDeliveryAddress(expected.get());

			assertEquals(actual, expected.get());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testAddDeliveryAddressShouldThrowNoSuchUSerExistsException() {
			assertThrows(NoSuchUserExists.class, () -> cupcakeService.addDeliveryAddress(null));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetOrdersdeDetailsByIdShoudReturnOrder() throws NoSuchOrderExists {
			Orders order = new Orders();
			order.setOrderId(1);
			order.setOrderDate("29/04/2021");
			order.setOrderStatus("pending");
			order.setQuantity(1);
			order.setTotalPrice(40);

			// Creating a Orders Object with Optional.of() method
			Optional<Orders> expected = Optional.of(order);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findById(1)).thenReturn(expected);

			// Calling getOrderDetailsById() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			Orders actual = cupcakeService.getOrderDetailsById(order.getOrderId());

			assertEquals(actual, expected.get());
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.getOrderDetailsById(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void deleteDeliveryAddressShouldDeleteAddress() throws NoSuchAddressExists {
			Address address = new Address();
			address.setAddressId(1);
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("Stadium");
			address.setState("MH");
			address.setPinCode("22343");

			// Creating a Address Object with Optional.of() method
			Optional<Address> expected = Optional.of(address);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(addressRepository.findById(1)).thenReturn(expected);

			// Calling deleteDeliveryAddress() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			boolean actual = cupcakeService.deleteDeliveryAddress(address.getAddressId());

			assertTrue(actual);

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void deleteDeliveryAddressShouldThrowNoSuchAddressExists() {
			assertThrows(NoSuchAddressExists.class, () -> cupcakeService.deleteDeliveryAddress(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetDeliveryAddressShouldReturnAddress() throws NoSuchAddressExists {

			Address address = new Address();
			address.setAddressId(1);
			address.setCity("mumbai");
			address.setHouseNo("12");
			address.setLandmark("dairy");
			address.setState("MH");

			// Creating a Address Object with Optional.of() method
			Optional<Address> expected = Optional.of(address);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(addressRepository.findById(1)).thenReturn(expected);

			// Calling getDeliveryAddress() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			Address actual = cupcakeService.getDeliveryAddress(address.getAddressId());

			assertEquals(actual, expected.get());

		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
			assertThrows(NoSuchAddressExists.class, () -> cupcakeService.getDeliveryAddress(-1));
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetAllOrderDetails() throws NoSuchOrderExists {
			Orders order = new Orders();
			order.setOrderId(1);
			order.setOrderDate("29/04/2021");
			order.setOrderStatus("pending");
			order.setQuantity(1);
			order.setTotalPrice(40);

			// Creating a Orders Object with List.of() method
			List<Orders> expected = List.of(order);

			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findAll()).thenReturn(expected);

			// Calling getAllOrderDetails() method from SweetCherryServiceImpl using
			// sweetCherryService reference
			List<Orders> actual = cupcakeService.getAllOrderDetails();

			assertEquals(expected, actual);
		}

		@Test // @Test annotation tells JUnit that this method needs to be tested
		// Defining a void method to be tested by JUnit
		void testGetAllOrderDetailsShouldThrowNoSuchOrderExistsException() {
			// Mocking the dependencies using when().thenReturns() of Mockito
			when(orderRepository.findAll()).thenReturn(null);
			assertThrows(NoSuchOrderExists.class, () -> cupcakeService.getAllOrderDetails());
		}

}
