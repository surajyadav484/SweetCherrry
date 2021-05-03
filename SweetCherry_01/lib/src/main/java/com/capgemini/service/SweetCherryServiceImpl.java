package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.AddressRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;
import com.capgemini.repository.UserDetailsRepository;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private AddressRepository addressrepository;

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	
	static final String DESCRIPTION = "Method is called";

	// CUPCAKE
	// MODULE---------------------------------------------------------------------------------

	
	/*
	 * @Override
	 * 
	 * @Transactional public CupcakeDetails addCupcakeDetails(CupcakeDetails
	 * cupcakedetails) throws NoSuchCupcakeExists { methodName =
	 * "addCupcakeDetails(CupcakeDetails cupcakedetails)"; logger.info(methodName +
	 * " method is called from SweetCherryServiceImpl Layer"); try { CupcakeDetails
	 * cupcakeDetails = cupcakeDao.createCupcakeDetails(cupcakedetails); if
	 * (cupcakeDetails != null) return cupcakeDetails; else throw new
	 * NoSuchCupcakeExists(); } catch (NoSuchElementException e) { throw new
	 * NoSuchCupcakeExists(
	 * "The Cupcake Details you have entered is invalid! Please enter valid Cupcake Details"
	 * ); }
	 * 
	 * }
	 */
	  
		/*
		 * @Override public List<CupcakeDetails> showCupcakeDetails() throws
		 * NoSuchCupcakeExists { methodName = "showCupcakeDetails()";
		 * logger.info(methodName +
		 * " method is called from SweetCherryServiceImpl Layer"); try {
		 * List<CupcakeDetails> cupcakeList = cupcakeDao.readAllCupcakeDetails(); if
		 * (cupcakeList != null) return cupcakeList; else throw new
		 * NoSuchElementException("Cupcakes Not Found!"); } catch
		 * (NoSuchElementException e) { throw new
		 * NoSuchCupcakeExists("No cupcakes avialable to show"); }
		 * 
		 * }
		 * 
		 * @Override public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws
		 * NoSuchCupcakeExists { methodName = "findCupcakeDetailsById(int cupcakeId)";
		 * logger.info(methodName +
		 * " method is called from SweetCherryServiceImpl Layer"); try { CupcakeDetails
		 * cupcakDetails = cupcakeDao.readCupcakeDetailsById(cupcakeId); if
		 * (cupcakDetails != null) return cupcakDetails; else throw new
		 * NoSuchElementException("Cupcake is not available"); } catch
		 * (NoSuchElementException e) { throw new
		 * NoSuchCupcakeExists("Cupcake Details with cupcake id = " + cupcakeId +
		 * " not found"); } }
		 * 
		 * @Override
		 * 
		 * @Transactional public CupcakeDetails modifyCupcakeRating( int cupcakeId,int
		 * rating) throws NoSuchCupcakeExists { methodName =
		 * "modifyCupcakeRating(CupcakeDetails cupcakeDetails)"; logger.info(methodName
		 * + " method is called from SweetCherryServiceImpl Layer"); try {
		 * CupcakeDetails cupcakeObject = cupcakeDao.updateCupcakeRating(
		 * cupcakeId,rating); if (cupcakeObject != null) return cupcakeObject; else
		 * throw new NoSuchElementException("Wrong values entered!!!"); } catch
		 * (NoSuchElementException e) { throw new NoSuchCupcakeExists(
		 * "Cupcake details you have entered is invalid!\n Please enter valid details. "
		 * ); }
		 * 
		 * }
		 * 
		 * @Override
		 * 
		 * @Transactional public Orders addCupcakeToCart(Orders order) throws
		 * NoSuchOrderExists { methodName = "addCupcakeToCart(Orders order) ";
		 * logger.info(methodName +
		 * " method is called from SweetCherryServiceImpl Layer"); try { Orders
		 * orderObject = cupcakeDao.addCupcakeToCart(order); if (orderObject != null)
		 * return orderObject; else throw new
		 * NoSuchElementException("Entered order values are incorrect"); } catch
		 * (NoSuchElementException e) { throw new
		 * NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details."
		 * ); } }
		 * 
		 * @Override
		 * 
		 * @Transactional public Payment addPaymentDetails(Payment payment) throws
		 * NoSuchOrderExists { methodName = "addPaymentDetails(Payment payment)";
		 * logger.info(methodName +
		 * " method is called from SweetCherryServiceImpl Layer"); try { Payment
		 * paymentObject = cupcakeDao.createPaymentDetails(payment); if (paymentObject
		 * != null) return paymentObject; else throw new NoSuchOrderExists(); } catch
		 * (NoSuchOrderExists e) { throw new NoSuchOrderExists(
		 * "The payment details you have entered is incorrect! Please enter valid payment details"
		 * ); } }
		 */
	 

	// ORDER
	// MODULE--------------------------------------------------------------------------------------------------

	@Override
	@Transactional 
	public Payment makeOnlinePayment(Payment payment) throws PaymentFailedException {
		Payment result = null;
		methodName = "makeOnlinePayment(Payment payment)";
		logger.info(methodName , DESCRIPTION);
		try {
			if (isValidPaymentDetails(payment)) {
				Payment paymentObject = paymentRepository.save(payment);
				result = paymentObject;

			} else
				throw new NoSuchElementException("Error");
			return result;

		} catch (NoSuchElementException e) {
			throw new PaymentFailedException("Error in making Payment...please enter valid details!");
		}

	}

	@Override
	@Transactional
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "makeOnlineOrder(int orderId)";
			logger.info(methodName , DESCRIPTION);
			Optional<Orders> orders = orderRepository.findById(orderId);
			if (orders.isPresent() && orders.get() != null && orders.get().getOrderStatus().equalsIgnoreCase("pending")) {
				orders.get().setOrderStatus("Ordered");
				return orderRepository.save(orders.get());
			}else
				throw new NullPointerException("null pointer exxception");

		} catch (NullPointerException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
		
	}

	

	@Override
	public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "cancelOnlineOrder(int orderId)";
			logger.info(methodName , DESCRIPTION);

			Optional<Orders> orders = orderRepository.findById(orderId);
			if (orders.isPresent() && orders.get().getOrderStatus().equalsIgnoreCase("ordered")) {
				orders.get().setOrderStatus("cancelled");
				orderRepository.save(orders.get());
				return orders.get();
			} else
				throw new NoSuchElementException("Failed to cancel");
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("Failed to cancel the order");
		}
	}

	@Override
	public List<Orders> showOrderDetailsByUserId(int userId) throws NoSuchOrderExists {
		try {
			methodName = "showOrderDetailsByUserId(int userId)";
			logger.info(methodName , DESCRIPTION);

			List<Orders> orderDetails = orderRepository.findByuserId(userId);
			if (!orderDetails.isEmpty()) {
				return orderDetails;
			}

			else
				throw new NoSuchElementException("empty OrderList");

		} catch (NoSuchElementException e) {
			
			throw new NoSuchOrderExists("There is no order present with userId " + userId);
		}

	}

	@Override
	@Transactional
	public UserDetails modifyDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists {
		UserDetails result = null;
		try {
			methodName = "modifyDeliveryAddress(UserDetails userDetails)";
			logger.info(methodName , DESCRIPTION);
			if (isValidAddressDetails(userDetails)) {
				UserDetails userDetail = userRepository.save(userDetails);
				result = userDetail;
			} else
				throw new NullPointerException("null value is passed");
			return result;
		} catch (NullPointerException e) {
			throw new NoSuchUserExists("The user details you have entered is incorrect! Please enter valid details");
		}
	}

	@Override
	@Transactional
	public UserDetails addDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists {
		UserDetails result = null;
		try {
			methodName = "addDeliveryAddress(UserDetails userDetails)";
			logger.info(methodName , DESCRIPTION);
			if (isValidAddressDetails(userDetails)) {
				UserDetails userDetail = userRepository.save(userDetails);
				result = userDetail;
			} else
				throw new NullPointerException("Invalid User details");
			return result;

		} catch (NullPointerException e) {
			throw new NoSuchUserExists("The details you have entered is incorrect! Please enter valid details");
		}
	}

	@Override
	@Transactional
	public boolean deleteDeliveryAddress(int addressId) throws NoSuchAddressExists {
		try {
			methodName = "deleteDeliveryAddress(int addressId)";
			logger.info(methodName , DESCRIPTION);
			Optional<Address> address = addressrepository.findById(addressId);
			if (address.isPresent()) {
				addressrepository.delete(address.get());
				return true;
			} else
				throw new NoSuchElementException("address id is not valid");

		} catch (NoSuchElementException e) {
			throw new NoSuchAddressExists("Address with address id " + addressId + " not present");
		}

	}

	@Override
	public List<Orders> getAllOrderDetails() throws NoSuchOrderExists {
		
		try {
		methodName = "getAllOrderDetails()";
		logger.info(methodName , DESCRIPTION);

		List<Orders> orderDetails = orderRepository.findAll();
		if (!orderDetails.isEmpty()) {
			return orderDetails;
		} else
			throw new NullPointerException("null value present");
		}catch(NullPointerException e) {
			throw new NoSuchOrderExists("there is no any order details available");
		}
	}

	@Override
	public Address getDeliveryAddress(int addressId) throws NoSuchAddressExists {
		try {
			methodName = "getDeliveryAddress(int addressId)";
			logger.info(methodName , DESCRIPTION);

			Optional<Address> address = addressrepository.findById(addressId);
			if (address.isPresent()) {
				return address.get();
			}else 
				throw new NullPointerException("null value present");
		} catch (NullPointerException e) {
			throw new NoSuchAddressExists("Address with address id " + addressId + " does not exist");
		}
		

	}

	@Override
	public Orders getOrderDetailsById(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "getOrderDetailsById(int orderId)";
			logger.info(methodName , DESCRIPTION);

			Optional<Orders> orderDetails = orderRepository.findById(orderId);
			if (orderDetails.isPresent())
				return orderDetails.get();
			else
				throw new NullPointerException("null values passed");
		} catch (NullPointerException e) {
			throw new NoSuchOrderExists("There is no any order- details available with orderId " + orderId);
		}
		
	}

	public boolean isValidPaymentDetails(Payment payment) {

		methodName = "isValidPaymentDetails(Payment payment)";
		logger.info(methodName , DESCRIPTION);

		String stringRegex = "[A-Za-z]+";

		if (payment.getCardHolderName().matches(stringRegex)) {
			return true;
		} else
			return false;
	}

	public boolean isValidAddressDetails(UserDetails userDetails) {
		methodName = "isValidAddressDetails(UserDetails userDetails)";
		logger.info(methodName , DESCRIPTION);

		String stringRegex = "[A-Za-z]+";
		String pincodeRegex = "[0-9]+";

		boolean result = false;
		for (Address address : userDetails.getAddress()) {
			if (address.getCity().matches(stringRegex) && address.getState().matches(stringRegex)
					&& address.getPinCode().matches(pincodeRegex))
				result = true;
			else
				result = false;
		}
		return result;
	}

}