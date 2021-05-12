package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchAddressExists;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.PaymentFailedException;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.logger.LoggerController;
import com.capgemini.mail.EmailSenderService;
import com.capgemini.model.Address;
import com.capgemini.model.CupcakeCategory;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.AddressRepository;
import com.capgemini.repository.CupcakeCategoryRepository;
import com.capgemini.repository.CupcakeDetailsRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;
import com.capgemini.repository.UserDetailsRepository;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private CupcakeDetailsRepository cupcakeRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private EmailSenderService mailService;

	@Autowired
	private CupcakeCategoryRepository cupcakeCategoryRepository;

	@Autowired
	private AddressRepository addressrepository;

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	static final String DESCRIPTION = "Method is called from SweetCherryServiceImpl class";

	String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
	String userNameRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

	// 1. LOGIN
	// MODULE-----------------------------------------------------------------------------------

	@Override
	public List<UserDetails> allDetailsOfAdminAndUser() throws NoSuchUserExists {
		try {
			List<UserDetails> list = userRepository.findAll();
			if (!list.isEmpty()) {
				methodName = "allDetailsOfAdminAndUser()";
				logger.info(methodName, DESCRIPTION);

				return list;
			} else {
				throw new NullPointerException("Null value found");
			}
		} catch (NullPointerException e) {
			throw new NoSuchUserExists("No details found");
		}
	}

	@Override

	public UserDetails registerCustomer(UserDetails registerCustomer)
			throws UserNameAndPasswordDoNotMatchRegularExpression {

		methodName = "registerCustomer(UserDetails registerCustomer)";
		logger.info(methodName, DESCRIPTION);

		try {
			if (registerCustomer != null) {
				if (registerCustomer.getEmail().matches(userNameRegex)) {
					if (registerCustomer.getPassword().matches(passwordRegex)) {
						return userRepository.save(registerCustomer);
					} else {
						throw new UserNameAndPasswordDoNotMatchRegularExpression("Invalid format of password");

					}
				} else {
					throw new UserNameAndPasswordDoNotMatchRegularExpression("Invalid format of email");
				}
			} else {
				throw new NullPointerException("Null values passed");
			}
		} catch (UserNameAndPasswordDoNotMatchRegularExpression e) {
			throw new UserNameAndPasswordDoNotMatchRegularExpression(
					"Please choose a more secure password. It should be longer than 6 characters, unique to you and difficult for others to guess.");
		}
	}

	@Override
   @Transactional
	public String modifyPassword(int userId, String oldPassword, String newPassword)
			throws UserNameAndPasswordDoNotMatchRegularExpression {
		// A password is considered valid if all the following conditions are satisfied:

		// It contains at least 8 characters and at most 20 characters.
		// It contains at least one digit.
		// It contains at least one upper case alphabet.
		// It contains at least one lower case alphabet.
		// It contains at least one special character which includes special character
		// !@#$%&*()-+=^
		// It doesn’t contain any blank space.

		methodName = "modifyPassword(int userId, String oldPassword, String newPassword)";
		logger.info(methodName, DESCRIPTION);

		try {
			if (newPassword.matches(passwordRegex)) {

				userRepository.updatePassword(userId, oldPassword, newPassword);
				return "Password changed successfully";
			} else {
				throw new UserNameAndPasswordDoNotMatchRegularExpression("Invalid Password");
			}
		} catch (UserNameAndPasswordDoNotMatchRegularExpression e) {
			throw new UserNameAndPasswordDoNotMatchRegularExpression(
					" Please choose a more secure password. It should be longer than 6 characters, unique to you and difficult for others to guess");
		}
	}

	@Override

	public String login(String userName, String password) throws NoSuchUserExists {

		methodName = "login(String userName, String password)";
		logger.info(methodName, DESCRIPTION);

		String result = null;
		try {
			if (verifyLoginCredential(userName, password)) { 
				UserDetails det = userRepository.login(userName, password);
				if (det != null && userName.equals(det.getEmail()) && password.equals(det.getPassword())) {
					if (det.getRole().getRoleId() == 2) {
						result = "login SuccessFul as User";
					} else if (det.getRole().getRoleId() == 1) {
						result = "login SuccessFul as Administrator";
					}
				}else
					throw new NoSuchUserExists("no user found");

			} else {
				throw new NoSuchUserExists("Invalid Entry");
			}
			return result;
		} catch (NoSuchUserExists e) {
			throw new NoSuchUserExists("No such User Found, please check username and password again");
		}
	}

	@Override
	public String logout() {

		methodName = "logout()";
		logger.info(methodName, DESCRIPTION);

		return "You have been logged out successfully";
	}

	@Override
	
	public UserDetails allUserDetailsById(int userId) throws InvalidIdException {

		methodName = "allUserDetailsById(int userId)";
		logger.info(methodName, DESCRIPTION);

		try {
			if (userId > 0) {
				Optional<UserDetails> user = userRepository.findById(userId);
				if (user.isPresent() && user.get() != null) {
					return user.get();
				}
				
			} else
				throw new NoSuchElementException("invalid id");
		} catch (NoSuchElementException e) {
			throw new InvalidIdException("User with id " + userId + " not found, try another id.");
		}
		return null;
	}

	@Override
	public UserDetails updateCustomerProfile(UserDetails customer)
			throws UserNameAndPasswordDoNotMatchRegularExpression {

		methodName = "updateCustomerProfile(UserDetails customer)";
		logger.info(methodName, DESCRIPTION);

		try {
			if (customer.getPassword().matches(passwordRegex)) {
				return userRepository.save(customer);
			} else {
				throw new NullPointerException("Invalid format of password");
			}

		} catch (NullPointerException e) {
			throw new UserNameAndPasswordDoNotMatchRegularExpression(
					" Please choose a more secure password. It should be longer than 6 characters, unique to you and difficult for others to guess");
		}
	}

	public boolean verifyLoginCredential(String userName, String password) {
		boolean flag = false;
		methodName = "verifyLoginCredential(String userName, String password)";
		logger.info(methodName, DESCRIPTION);

		if (userName.matches(userNameRegex) && password.matches(passwordRegex)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;

	}

	// 2. CUPCAKE
	// MODULE---------------------------------------------------------------------------------

	@Override

	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {

		methodName = "addCupcakeDetails(CupcakeDetails cupcakedetails)";
		logger.info(methodName, DESCRIPTION);

		try {

			String regex = "^[a-zA-Z\\s]+";
			if (cupcakedetails.getCupcakeName().matches(regex)) {
				return cupcakeRepository.save(cupcakedetails);
			} else {
				throw new NoSuchElementException("Cupcake name regular expression is not matched");
			}
		} catch (Exception e) {
			throw new NoSuchCupcakeExists(
					"The Cupcake Details you have entered is invalid! Please enter valid Cupcake Details");
		}

	}

	@Override
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists {

		methodName = "showCupcakeDetails()";
		logger.info(methodName, DESCRIPTION);
		try {
			List<CupcakeDetails> cupcakeList = cupcakeRepository.findAll();
			if (!cupcakeList.isEmpty())
				return cupcakeList;
			else
				throw new NullPointerException("Cupcakes Not Found!");
		} catch (NullPointerException e) {
			throw new NoSuchCupcakeExists("No cupcakes avialable to show");
		}

	}

	@Override
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "findCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (cupcakeId > 0) {
				Optional<CupcakeDetails> cupcakeDetails = cupcakeRepository.findById(cupcakeId);
				if (!cupcakeDetails.isEmpty())
					return cupcakeDetails.get();
				else
					throw new NoSuchElementException("No Cupcakes Found");
			} else
				throw new NullPointerException("You have entered a negative Cupcake Id");

		} catch (Exception ne) {
			throw new NoSuchCupcakeExists("Cupcake Details with cupcake id = " + cupcakeId + " not found");
		}
	}

	@Override
	//@Transactional
	public CupcakeDetails modifyCupcakeRating(int cupcakeId, int rating) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakeRating(CupcakeDetails cupcakeDetails)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (rating > 0 && rating <= 6) {
				cupcakeRepository.updateRating(cupcakeId, rating);
				return findCupcakeDetailsById(cupcakeId);
			} else
				throw new NoSuchElementException("Rating should be between 1 to 5");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake details you have entered is invalid!\n Please enter valid details.");
		}

	}

	@Override
	//@Transactional
	public Orders addCupcakeToCart(Orders order) throws NoSuchOrderExists {
		methodName = "addCupcakeToCart(Orders order) ";
		logger.info(methodName, DESCRIPTION);
		try {
			if (order.getQuantity() <= 10 && order.getOrderStatus().equalsIgnoreCase("Pending")) {
				return orderRepository.save(order);
			} else
				throw new NoSuchElementException("Maximum Order quantity is 10");
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
	}

	@Override
	//@Transactional
	public Payment addPaymentDetails(Payment payment) throws NoSuchOrderExists {
		methodName = "addPaymentDetails(Payment payment)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (payment.getPaymentStatus().equalsIgnoreCase("successful")) {
				Payment paymentObject = paymentRepository.save(payment);
				if (paymentObject != null)
					return paymentObject;
				else
					throw new NoSuchElementException("Invalid details");
			} else
				throw new NoSuchElementException("Please update the status as 'Successful'");
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists(
					"The payment details you have entered is incorrect! Please enter valid payment details");
		}
	}

	@Override
	//@Transactional
	public CupcakeDetails updateCupcakePriceByCupcakeId(int cupcakeId, double price) throws NoSuchCupcakeExists {

		methodName = "modifyCupcakePrice(CupcakeDetails cupcakedetails)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (cupcakeId > 1) {
				cupcakeRepository.updatePrice(cupcakeId, price);
				return findCupcakeDetailsById(cupcakeId);
			} else
				throw new NoSuchElementException("Cupcake Id can't be negative");
		}

		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake Price is not modified.");
		}

	}

	@Override
	//@Transactional
	public CupcakeDetails modifyCupcakeName(int cupcakeId, String cupcakeName) throws NoSuchCupcakeExists {

		methodName = "modifyCupcakeName(int cupcakeId,String cupcakeName)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (cupcakeId > 0) {
				cupcakeRepository.updateCupcakeName(cupcakeId, cupcakeName);
				return findCupcakeDetailsById(cupcakeId);
			}

			else
				throw new NoSuchElementException("Cupcake Id can't be negative");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake Name is not modified.");
		}

	}

	@Override
	//@Transactional
	public String removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "removeCupcakeDetails(int cupcakId)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (cupcakeId > 0) {
				cupcakeRepository.deleteById(cupcakeId);
				return "Cupcake Removed";
			} else
				throw new NoSuchElementException("Cupcake id can't be negative or zero.");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake with id" + cupcakeId + " is not found.");

		}
	}

	@Override
	//@Transactional
	public CupcakeCategory addCupcakeCategory(CupcakeCategory cupcakeCategory) {

		return cupcakeCategoryRepository.save(cupcakeCategory);
	}

	// ORDER
	// MODULE--------------------------------------------------------------------------------------------------

	@Override
	//@Transactional
	public Payment makeOnlinePayment(Payment payment) throws PaymentFailedException {
		Payment result = null;
		methodName = "makeOnlinePayment(Payment payment)";
		logger.info(methodName, DESCRIPTION);
		try {
			if (payment != null && isValidPaymentDetails(payment)) {
				Payment paymentObject = paymentRepository.save(payment);
				result = paymentObject;

			} else
				throw new NullPointerException("Error");
			return result;

		} catch (NullPointerException e) {
			throw new PaymentFailedException("Error in making Payment...please enter valid details!");
		}

	}

	@Override
	//@Transactional
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "makeOnlineOrder(int orderId)";
			logger.info(methodName, DESCRIPTION);
			if (orderId > 0) {
				Optional<Orders> orders = orderRepository.findById(orderId);
				if (!orders.isEmpty() && orders.get().getOrderStatus().equalsIgnoreCase("pending")) {
					orders.get().setOrderStatus("Ordered");
					String orderDetails = "Order Id: " + orders.get().getOrderId() + "\n" + "Order Date: "
							+ orders.get().getOrderDate() + "\n" + "Order status: " + orders.get().getOrderStatus()
							+ "\n" + "Total Price:" + orders.get().getTotalPrice() + "\n" + "Cupcake Name:"
							+ orders.get().getCupcakeDetails();
					mailService.senMail(orders.get().getUserDetails().getEmail(), "Order Confirmed", orderDetails);
					logger.info("Mail Sent");
					return orderRepository.save(orders.get());
				}
			} else
				throw new NoSuchElementException("no element found");

		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
		return null;
	}

	@Override
	public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "cancelOnlineOrder(int orderId)";
			logger.info(methodName, DESCRIPTION);

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
			logger.info(methodName, DESCRIPTION);

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
	//@Transactional
	public UserDetails modifyDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists {
		UserDetails result = null;
		try {
			methodName = "modifyDeliveryAddress(UserDetails userDetails)";
			logger.info(methodName, DESCRIPTION);
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
	//@Transactional
	public UserDetails addDeliveryAddress(UserDetails userDetails) throws NoSuchUserExists {
		UserDetails result = null;
		try {
			methodName = "addDeliveryAddress(UserDetails userDetails)";
			logger.info(methodName, DESCRIPTION);
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
	//@Transactional
	public boolean deleteDeliveryAddress(int addressId) throws NoSuchAddressExists {
		try {
			methodName = "deleteDeliveryAddress(int addressId)";
			logger.info(methodName, DESCRIPTION);
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
			logger.info(methodName, DESCRIPTION);

			List<Orders> orderDetails = orderRepository.findAll();
			if (!orderDetails.isEmpty()) {
				return orderDetails;
			} else
				throw new NullPointerException("null value present");
		} catch (NullPointerException e) {
			throw new NoSuchOrderExists("there is no any order details available");
		}
	}

	@Override
	public Address getDeliveryAddress(int addressId) throws NoSuchAddressExists {
		try {
			methodName = "getDeliveryAddress(int addressId)";
			logger.info(methodName, DESCRIPTION);

			Optional<Address> address = addressrepository.findById(addressId);
			if (address.isPresent()) {
				return address.get();
			} else
				throw new NullPointerException("null value present");
		} catch (NullPointerException e) {
			throw new NoSuchAddressExists("Address with address id " + addressId + " does not exist");
		}

	}

	@Override
	public Orders getOrderDetailsById(int orderId) throws NoSuchOrderExists {
		try {
			methodName = "getOrderDetailsById(int orderId)";
			logger.info(methodName, DESCRIPTION);

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
		logger.info(methodName, DESCRIPTION);

		String stringRegex = "[A-Za-z]+";

		if(payment.getCardHolderName().matches(stringRegex)) {
			return true;
		} else
			return false;
	}

	public boolean isValidAddressDetails(UserDetails userDetails) {
		methodName = "isValidAddressDetails(UserDetails userDetails)";
		logger.info(methodName, DESCRIPTION);

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
