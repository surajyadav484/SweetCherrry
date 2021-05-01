package com.capgemini.service;

import static org.junit.Assert.assertTrue;
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

@SpringBootTest
class SweetCherryServiceImplTest {

	@Autowired
	private SweetCherryService sweetCherryService;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserDetailsRepository userDetailstRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Test
	void testMakeOnlinePaymentShouldSavePaymentObject() throws PaymentFailedException {

		Payment payment = new Payment();
		payment.setCardHolderName("suraj");
		payment.setCardNo(3637477883l);
		payment.setCvv(317);
		payment.setExpiryDate("28/04/2021");
		payment.setPaymentStatus("cart");
		Payment expected = paymentRepository.save(payment);

		Payment actual = sweetCherryService.makeOnlinePayment(payment);

		assertEquals(expected.getPaymentId(), actual.getPaymentId());
		assertEquals(expected.getCardHolderName(), actual.getCardHolderName());

	}

	@Test
	void testMakeOnlinePaymentShouldThrowPaymentFailedException() {
		Payment payment = new Payment("123");
		assertThrows(PaymentFailedException.class, () -> sweetCherryService.makeOnlinePayment(payment));
	}

	@Test
	void testMakeOnlineOrderShouldSetOrderStatusAsOrdered() throws NoSuchOrderExists {
		Orders order = new Orders(1, "29/02/2021", 1, "pending", 50, null, null);

		Orders orderObj = orderRepository.save(order);
		Orders actualobj = sweetCherryService.makeOnlineOrder(orderObj.getOrderId());

		assertEquals(actualobj.getOrderStatus(), "Ordered");
		
	}

	@Test
	void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.makeOnlineOrder(-1));
	}

	@Test
	void testCancelOnlineOrderShoudSetOrderStatusAsCancelled() throws NoSuchOrderExists {
		Orders order = new Orders(1, "29/02/2021", 1, "Ordered", 50, null, null);
		Orders orderObj = orderRepository.save(order);
		Orders actualObj = sweetCherryService.cancelOnlineOrder(orderObj.getOrderId());

		assertEquals(actualObj.getOrderStatus(), "cancelled");
	}

	@Test
	void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.cancelOnlineOrder(-1));
	}

	@Test
	void testShowOrderDetailsByUserIdShouldReturnOrederDetails() throws NoSuchOrderExists {
		UserDetails userDetails = new UserDetails(1, "suraj", "yadav", "sky@gmail.com", "2232", null, null);

		Orders order = new Orders();
		order.setOrderDate("27/04/2021");
		order.setOrderId(1);
		order.setOrderStatus("ordered");
		order.setQuantity(1);
		order.setTotalPrice(50);
		order.setUserDetails(userDetails);

		Orders orderobj = orderRepository.save(order);
		List<Orders> actualObj = sweetCherryService.showOrderDetailsByUserId(orderobj.getUserDetails().getUserId());

		for (Orders orders : actualObj) {
			assertEquals(orders.getOrderId(), orderobj.getOrderId());
			assertEquals(orders.getOrderDate(), orderobj.getOrderDate());

		}
	}

	@Test
	void testShowOrderDetailsByUserIdShouldThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.showOrderDetailsByUserId(-1));
	}

	@Test
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

		UserDetails actualObj = sweetCherryService.modifyDeliveryAddress(userDetails);
		for (Address actualAddressobj : actualObj.getAddress()) {
			assertEquals(actualAddressobj.getCity(), "mumbai");
			assertEquals(actualAddressobj.getHouseNo(), "12");
			assertEquals(actualAddressobj.getPinCode(), "22343");

		}

	}

	@Test
	void testmodifyDeliveryAddressShoudThrowNoSuchUserExistsException() {
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.modifyDeliveryAddress(null));
	}

	@Test
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

		UserDetails actualObj = sweetCherryService.addDeliveryAddress(userDetails);

		for (Address actualAddressobj : actualObj.getAddress()) {
			assertEquals(actualAddressobj.getCity(), "mumbai");
			assertEquals(actualAddressobj.getHouseNo(), "12");
			assertEquals(actualAddressobj.getPinCode(), "22343");
		}

	}

	@Test
	void testAddDeliveryAddressShouldThrowNoSuchUSerExistsException() {
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.addDeliveryAddress(null));
	}

	@Test
	void deleteDeliveryAddressShouldDeleteAddress() throws NoSuchAddressExists {
		Address address = new Address();
		address.setCity("mumbai");
		address.setHouseNo("12");
		address.setLandmark("Stadium");
		address.setState("MH");
		address.setPinCode("22343");

		Address addressObj = addressRepository.save(address);
		boolean result = sweetCherryService.deleteDeliveryAddress(addressObj.getAddressId());
		assertTrue(result,"");

	}

	@Test
	void deleteDeliveryAddressShouldThrowNoSuchAddressExists() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.deleteDeliveryAddress(-1));
	}

	@Test
	void testGetAllOrderDetails() throws NoSuchOrderExists {
		Orders order = new Orders();
		order.setOrderId(1);
		order.setOrderDate("29/04/2021");
		order.setOrderStatus("pending");
		order.setQuantity(1);
		order.setTotalPrice(40);

		Orders orderObj = orderRepository.save(order);
		List<Orders> actual = sweetCherryService.getAllOrderDetails();

		for (Orders orderList : actual) {
			assertEquals(orderList.getOrderId(), 1);
			assertEquals(orderList.getQuantity(), 1);
			assertEquals(orderList.getOrderDate(), "29/04/2021");
		}
	}
	/*
	 * @Test void testGetAllOrderDetailsShouldThrowNoSuchOrderExistsException() {
	 * assertThrows(NoSuchOrderExists.class, () ->
	 * sweetCherryService.getAllOrderDetails()); }
	 */

	@Test
	void testGetDeliveryAddressShouldReturnAddress() throws NoSuchAddressExists {

		Address address = new Address();
		address.setAddressId(1);
		address.setCity("mumbai");
		address.setHouseNo("12");
		address.setLandmark("dairy");
		address.setState("MH");

		Address addressObj = addressRepository.save(address);
		Address actualObj = sweetCherryService.getDeliveryAddress(addressObj.getAddressId());

		assertEquals(actualObj.getCity(), "mumbai");
		assertEquals(actualObj.getState(), "MH");
	}

	@Test
	void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.getDeliveryAddress(-1));
	}

	@Test
	void testGetOrdersdeDetailsByIdShoudReturnOrder() throws NoSuchOrderExists {
		Orders order = new Orders();
		order.setOrderId(1);
		order.setOrderDate("29/04/2021");
		order.setOrderStatus("pending");
		order.setQuantity(1);
		order.setTotalPrice(40);

		Orders orderObj = orderRepository.save(order);
		Orders actualObj = sweetCherryService.getOrderDetailsById(orderObj.getOrderId());

		assertEquals(actualObj.getOrderDate(), "29/04/2021");
		assertEquals(actualObj.getOrderId(), 1);
		assertEquals(actualObj.getOrderStatus(), "pending");
	}

	@Test
	void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.getOrderDetailsById(-1));
	}

}
