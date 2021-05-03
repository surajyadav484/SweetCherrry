package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.model.Address;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.AddressRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;
import com.capgemini.repository.UserDetailsRepository;

/*The @SpringBootTest annotation tells Spring Boot to look for a main configuration class 
and use that to start a Spring application context that is to be used in test*/

@SpringBootTest
class SweetCherryServiceImplTestUsingMock {

	@Autowired
	private SweetCherryService sweetCherryService;
	/*
	 * @MockBean allows to mock a class or an interface and to record and verify
	 * behaviors on it
	 */
	@MockBean
	// Creating reference of PaymentRepository
	private PaymentRepository paymentRepository;

	@MockBean
	// Creating reference of OrderRepository
	private OrderRepository orderRepository;

	@MockBean
	// Creating reference of UserDetailsRepository
	private UserDetailsRepository userRepository;

	@MockBean
	// Creating reference of AddressRepository
	private AddressRepository addressRepository;

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
		Payment actual = sweetCherryService.makeOnlinePayment(expected.get());
		assertEquals(actual, expected.get());

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testMakeOnlinePaymentShouldThrowPaymentFailedException() {
		Payment payment = new Payment("123");
		assertThrows(PaymentFailedException.class, () -> sweetCherryService.makeOnlinePayment(payment));
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
		Orders actual = sweetCherryService.makeOnlineOrder(order.getOrderId());
		assertTrue(actual.getOrderStatus().equalsIgnoreCase("ordered"));
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.makeOnlineOrder(-1));
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
		Orders actual = sweetCherryService.cancelOnlineOrder(order.getOrderId());
		assertTrue(actual.getOrderStatus().equalsIgnoreCase("cancelled"));

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.cancelOnlineOrder(-1));
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
		List<Orders> actual = sweetCherryService.showOrderDetailsByUserId(userDetails.getUserId());

		assertEquals(expected, actual);
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testShowOrderDetailsByUserIdShouldThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.showOrderDetailsByUserId(-1));
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
		when(userRepository.save(userDetails)).thenReturn(expected.get());

		// Calling modifyDeliveryAddress() method from SweetCherryServiceImpl using
		// sweetCherryService reference
		UserDetails actual = sweetCherryService.modifyDeliveryAddress(expected.get());

		assertEquals(actual, expected.get());

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testmodifyDeliveryAddressShoudThrowNoSuchUserExistsException() {
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.modifyDeliveryAddress(null));
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
		when(userRepository.save(userDetails)).thenReturn(expected.get());

		// Calling addDeliveryAddress() method from SweetCherryServiceImpl using
		// sweetCherryService reference
		UserDetails actual = sweetCherryService.addDeliveryAddress(expected.get());

		assertEquals(actual, expected.get());

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testAddDeliveryAddressShouldThrowNoSuchUSerExistsException() {
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.addDeliveryAddress(null));
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
		Orders actual = sweetCherryService.getOrderDetailsById(order.getOrderId());

		assertEquals(actual, expected.get());
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.getOrderDetailsById(-1));
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
		boolean actual = sweetCherryService.deleteDeliveryAddress(address.getAddressId());

		assertTrue(actual);

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void deleteDeliveryAddressShouldThrowNoSuchAddressExists() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.deleteDeliveryAddress(-1));
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
		Address actual = sweetCherryService.getDeliveryAddress(address.getAddressId());

		assertEquals(actual, expected.get());

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.getDeliveryAddress(-1));
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
		List<Orders> actual = sweetCherryService.getAllOrderDetails();

		assertEquals(expected, actual);
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested
	// Defining a void method to be tested by JUnit
	void testGetAllOrderDetailsShouldThrowNoSuchOrderExistsException() {
		// Mocking the dependencies using when().thenReturns() of Mockito
		when(orderRepository.findAll()).thenReturn(null);
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.getAllOrderDetails());
	}

}
