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
import com.capgemini.service.SweetCherryService;

@SpringBootTest
class SweetCherryServiceImplTestUsingMock {

	@Autowired
	private SweetCherryService sweetCherryService;

	@MockBean
	private PaymentRepository paymentRepository;

	@MockBean
	private OrderRepository orderRepository;
	
	@MockBean
	private UserDetailsRepository userRepository;
	
	@MockBean
	private AddressRepository addressRepository;

	@Test
	void testMakeOnlinePaymentShouldSavePaymentObject() throws PaymentFailedException {
		Payment payment = new Payment();
		payment.setPaymentId(1);
		payment.setCardNo(535663);
		payment.setCardHolderName("suraj");
		payment.setCvv(123);
		payment.setPaymentStatus("successful");

		Optional<Payment> expected = Optional.of(payment);

		when(paymentRepository.save(payment)).thenReturn(expected.get());

		Payment actual = sweetCherryService.makeOnlinePayment(expected.get());
		assertEquals(actual, expected.get());

	}

	@Test
	void testMakeOnlinePaymentShouldThrowPaymentFailedException() {
		Payment payment = new Payment("123");
		assertThrows(PaymentFailedException.class, () -> sweetCherryService.makeOnlinePayment(payment));
	}

	@Test
	void testMakeOnlineOrderShouldSetOrderStatusAsOrdered() throws NoSuchOrderExists {
		Orders order = new Orders();
		order.setOrderId(1);
		order.setOrderDate("29/02/2021");
		order.setOrderStatus("pending");
		order.setQuantity(1);
		order.setTotalPrice(50);

		Optional<Orders> expected = Optional.of(order);
		when(orderRepository.findById(1)).thenReturn(expected);

		when(orderRepository.save(expected.get())).thenReturn(expected.get());
		Orders actual = sweetCherryService.makeOnlineOrder(order.getOrderId());
		assertTrue(actual.getOrderStatus().equalsIgnoreCase("ordered"));
	}

	@Test
	void testMakeOnlineOrderShouldThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.makeOnlineOrder(-1));
	}

	@Test
	void testCancelOnlineOrderShoudSetOrderStatusAsCancelled() throws NoSuchOrderExists {
		Orders order = new Orders();
		order.setOrderId(1);
		order.setOrderDate("29/02/2021");
		order.setOrderStatus("Ordered");
		order.setQuantity(1);
		order.setTotalPrice(50);

		Optional<Orders> expected = Optional.of(order);
		when(orderRepository.findById(1)).thenReturn(expected);

		when(orderRepository.save(expected.get())).thenReturn(expected.get());
		Orders actual = sweetCherryService.cancelOnlineOrder(order.getOrderId());
		assertTrue(actual.getOrderStatus().equalsIgnoreCase("cancelled"));

	}
	
	@Test
	void testCancelOnlineOrderShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.cancelOnlineOrder(-1));
	}
	
	@Test
	void testShowOrderDetailsByUserIdShouldReturnOrederDetails() throws NoSuchOrderExists{
		UserDetails userDetails = new UserDetails(1, "suraj", "yadav", "sky@gmail.com", "2232", null, null);

		Orders order = new Orders();
		order.setOrderDate("27/04/2021");
		order.setOrderId(1);
		order.setOrderStatus("ordered");
		order.setQuantity(1);
		order.setTotalPrice(50);
		order.setUserDetails(userDetails);
		
		List<Orders> expected = List.of(order);
		
		when(orderRepository.findByuserId(1)).thenReturn(expected);
		
		List<Orders> actual = sweetCherryService.showOrderDetailsByUserId(userDetails.getUserId());
		
		assertEquals(expected, actual);
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
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		
		when(userRepository.save(userDetails)).thenReturn(expected.get());
		
		UserDetails actual = sweetCherryService.modifyDeliveryAddress(expected.get());
		
		assertEquals(actual,expected.get());
		
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
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		
		when(userRepository.save(userDetails)).thenReturn(expected.get());
		
		UserDetails actual = sweetCherryService.addDeliveryAddress(expected.get());
		
		assertEquals(actual,expected.get());
		
	}

	@Test
	void testAddDeliveryAddressShouldThrowNoSuchUSerExistsException() {
		assertThrows(NoSuchUserExists.class, () -> sweetCherryService.addDeliveryAddress(null));
	}
	
	@Test
	void testGetOrdersdeDetailsByIdShoudReturnOrder() throws NoSuchOrderExists {
		Orders order = new Orders();
		order.setOrderId(1);
		order.setOrderDate("29/04/2021");
		order.setOrderStatus("pending");
		order.setQuantity(1);
		order.setTotalPrice(40);
		
		Optional<Orders> expected = Optional.of(order);
		
		when(orderRepository.findById(1)).thenReturn(expected);
		
		Orders actual = sweetCherryService.getOrderDetailsById(order.getOrderId());
		
		assertEquals(actual, expected.get());
	}
	
	@Test
	void testGetOrdersdeDetailsByIdShoudThrowNoSuchOrderExistsException() {
		assertThrows(NoSuchOrderExists.class, () -> sweetCherryService.getOrderDetailsById(-1));
	}
	
	@Test
	void deleteDeliveryAddressShouldDeleteAddress() throws NoSuchAddressExists {
		Address address = new Address();
		address.setAddressId(1);
		address.setCity("mumbai");
		address.setHouseNo("12");
		address.setLandmark("Stadium");
		address.setState("MH");
		address.setPinCode("22343");

		Optional<Address> expected = Optional.of(address);
		
		when(addressRepository.findById(1)).thenReturn(expected);
		
		boolean actual = sweetCherryService.deleteDeliveryAddress(address.getAddressId());
		
		
		assertTrue(actual);

	}
	
	@Test
	void testGetDeliveryAddressShouldReturnAddress() throws NoSuchAddressExists {

		Address address = new Address();
		address.setAddressId(1);
		address.setCity("mumbai");
		address.setHouseNo("12");
		address.setLandmark("dairy");
		address.setState("MH");

		Optional<Address> expected = Optional.of(address);
		
		when(addressRepository.findById(1)).thenReturn(expected);
		
		Address actual = sweetCherryService.getDeliveryAddress(address.getAddressId());
		
		assertEquals(actual, expected.get());

	}

	@Test
	void testGetDeliveryAddressShouldThrowNoSuchAddressExistsException() {
		assertThrows(NoSuchAddressExists.class, () -> sweetCherryService.getDeliveryAddress(-1));
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

		List<Orders> expected = List.of(order);
		
		when(orderRepository.findAll()).thenReturn(expected);
		
		List<Orders> actual = sweetCherryService.getAllOrderDetails();
		
		assertEquals(expected, actual);
	}
	
	

}
