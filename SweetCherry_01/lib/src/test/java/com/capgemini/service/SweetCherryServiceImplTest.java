package com.capgemini.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
class SweetCherryServiceImplTest {

	// @Autowired is used for object injection automatically*/
	@Autowired
	// creating reference of SweetCherryService interface
	private SweetCherryService sweetCherryService;

	@Autowired
	// creating reference of PaymentRepository interface
	private PaymentRepository paymentRepository;

	@Autowired
	// creating reference of OrderRepository interface
	private OrderRepository orderRepository;

	@Autowired
	// creating reference of UserDetailsRepository interface
	private UserDetailsRepository userDetailstRepository;

	@Autowired
	// creating reference of AddressRepository interface
	private AddressRepository addressRepository;

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
		Payment actual = sweetCherryService.makeOnlinePayment(payment);

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
		assertThrows(PaymentFailedException.class, () -> sweetCherryService.makeOnlinePayment(payment));
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
		Orders actualobj = sweetCherryService.makeOnlineOrder(orderObj.getOrderId());

		// assertEquals asserts that expected and actual are equal
		assertEquals("Ordered", actualobj.getOrderStatus());

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested

	// Defining a void method to be tested by JUnit
	void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
		// assertThrows asserts that the method should throw an exception of specified
		// type and return the exception
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.makeOnlineOrder(-1));
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
		Orders actualObj = sweetCherryService.cancelOnlineOrder(orderObj.getOrderId());

		// assertEquals asserts that expected and actual are equal
		assertEquals("cancelled", actualObj.getOrderStatus());
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested

	// Defining a void method to be tested by JUnit
	void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {

		// assertThrows asserts that the method should throw an exception of specified
		// type and return the exception
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.cancelOnlineOrder(-1));
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
		List<Orders> actual = sweetCherryService.showOrderDetailsByUserId(orderObj.getUserDetails().getUserId());

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
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.showOrderDetailsByUserId(-1));
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
		UserDetails expected = userDetailstRepository.save(userDetails);

		// Calling modifyDeliveryAddress() from SweetCherryServiceImpl using
		// sweetCherryService reference
		UserDetails actualObj = sweetCherryService.modifyDeliveryAddress(expected);

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
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.modifyDeliveryAddress(null));
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
		UserDetails actualObj = sweetCherryService.addDeliveryAddress(userDetails);

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
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.addDeliveryAddress(null));
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
		boolean result = sweetCherryService.deleteDeliveryAddress(addressObj.getAddressId());

		// Asserts that the supplied condition is true.
		assertTrue(result);

	}

	@Test // @Test annotation tells JUnit that this method needs to be tested

	// Defining a void method to be tested by JUnit
	void deleteDeliveryAddressShouldThrowNoSuchAddressExists() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.deleteDeliveryAddress(-1));
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
		List<Orders> actual = sweetCherryService.getAllOrderDetails();

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
		Address actualObj = sweetCherryService.getDeliveryAddress(addressObj.getAddressId());

		assertEquals("mumbai", actualObj.getCity());
		assertEquals("MH", actualObj.getState());
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested

	// Defining a void method to be tested by JUnit
	void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.getDeliveryAddress(-1));
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
		Orders actualObj = sweetCherryService.getOrderDetailsById(orderObj.getOrderId());

		assertEquals(actualObj.getOrderDate(), orderObj.getOrderDate());
		assertEquals(actualObj.getOrderId(), orderObj.getOrderId());
		assertEquals(actualObj.getOrderStatus(), orderObj.getOrderStatus());
	}

	@Test // @Test annotation tells JUnit that this method needs to be tested

	// Defining a void method to be tested by JUnit
	void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.getOrderDetailsById(-1));
	}

}
