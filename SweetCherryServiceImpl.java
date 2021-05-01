package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoMatchingRegex;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.UserDetailsRepository;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {
	
	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	
	@Autowired
	private SweetCherryDao dao;

	@Autowired
	private UserDetailsRepository userRepository;

	@Override
	public List<UserDetails> AllAdminDetails() {
		
		methodName ="AllAdminDetails()";
		logger.info(methodName + " method is called");
		
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public UserDetails RegisterCustomer(UserDetails registerCustomer) throws NoMatchingRegex {
		
		methodName ="RegisterCustomer(UserDetails registerCustomer)";
		logger.info(methodName + " method is called");
		
		String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		String userNameRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
		try {
			if (registerCustomer.getEmail().matches(userNameRegex)) {
				if (registerCustomer.getPassword().matches(passwordRegex)) {
					return userRepository.save(registerCustomer);
				} else {
					throw new NoMatchingRegex("Invalid format of password");

				}
			} else {
				throw new NoMatchingRegex("Invalid format of email");
			}
		} catch (NoMatchingRegex e) {
			throw new NoMatchingRegex(
					"Invalid format of username or password check again\n \nYour password should contain  \n At least 8 characters and at most 20 characters."
							+ "\n It should contain at least one digit."
							+ "\n It should contain at least one upper case alphabet."
							+ "\n It should contain at least one lower case alphabet."
							+ "\n It should contain at least one special character which includes special character"
							+ "\n It should not contain any blank space.\n \n Your UserName/Email should contain \n1) A-Z characters allowed \n2) a-z characters allowed"
							+ "0-9 numbers allowed \n4) Additionally email may contain"
							+ "only dot(.), dash(-) and underscore(_)\n5) Rest all" + "characters are not allowed");
		}
	}

	@Override
	@Transactional
	public String modifyPassword(int userId, String oldPassword, String newPassword) throws NoMatchingRegex {
		// A password is considered valid if all the following conditions are satisfied:

		// It contains at least 8 characters and at most 20 characters.
		// It contains at least one digit.
		// It contains at least one upper case alphabet.
		// It contains at least one lower case alphabet.
		// It contains at least one special character which includes special character
		// !@#$%&*()-+=^.
		// It doesn’t contain any blank space.

		methodName ="modifyPassword(int userId, String oldPassword, String newPassword)";
		logger.info(methodName + " method is called");
		
		try {
			if (newPassword
					.matches("^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$")) {

				userRepository.updatePassword(userId, oldPassword, newPassword);
				return "Password changed successfully";
			} else {
				throw new NoMatchingRegex("Invalid Password");
			}
		} catch (NoMatchingRegex e) {
			throw new NoMatchingRegex(" Your password should contain at least 8 characters and at most 20 characters."
					+ "\n It should contain at least one digit."
					+ "\n It should contain at least one upper case alphabet."
					+ "\n It should contain at least one lower case alphabet"
					+ "\n It should contain at least one special character which includes special character like !@#$%&*()-+=^."
					+ "\n It should not contain any blank space.");
		}
	}

	@Override
	@Transactional
	public String Login(String userName, String password) throws NoSuchUserExists {
		
		methodName ="Login(String userName, String password)";
		logger.info(methodName + " method is called");
		
		String result = null;
		try {
			if (verifyLoginCredential(userName, password)) {
				UserDetails det = userRepository.Login(userName, password);
				if (det != null) {
					if (userName.equals(det.getEmail()) && password.equals(det.getPassword())) {
						if (det.getRole().getRoleId() == 2) {
							result = "Login SuccessFul as User";
						} else if (det.getRole().getRoleId() == 1) {
							result = "Login SuccessFul as Administrator";
						}
					}
				}

			} else {
				throw new Exception("Invalid Entry");
			}
			return result;
		} catch (Exception e) {
			throw new NoSuchUserExists("No such User Found, please check username and password again");
		}
	}

	@Override
	public String Logout() {
		
		methodName ="Logout()";
		logger.info(methodName + " method is called");
		
		return dao.Logout();
	}

	@Override
	@Transactional
	public UserDetails AllUserDetailsById(int userId) throws InvalidIdException {
		
		methodName ="AllUserDetailsById(int userId)";
		logger.info(methodName + " method is called");
		
		try {
			Optional<UserDetails> user = userRepository.findById(userId);
			if (user.get() != null) {
				return user.get();
			}
		} catch (NoSuchElementException e) {
			throw new InvalidIdException("User with id " + userId + " not found, try another id.");
		}
		return null;
	}

	@Override
	public UserDetails UpdateCustomerProfile(UserDetails customer) throws NoMatchingRegex {
		
		methodName ="UpdateCustomerProfile(UserDetails customer)";
		logger.info(methodName + " method is called");
		
		String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		try {
			if (customer.getPassword().matches(passwordRegex)) {
				return userRepository.save(customer);
			} else {
				throw new NoMatchingRegex("Invalid format of password");

			}
		} catch (NoMatchingRegex e) {
			throw new NoMatchingRegex(" Your password should contain at least 8 characters and at most 20 characters."
					+ "\n It should contain at least one digit."
					+ "\n It should contain at least one upper case alphabet."
					+ "\n It should contain at least one lower case alphabet"
					+ "\n It should contain at least one special character which includes special character like !@#$%&*()-+=^."
					+ "\n It should not contain any blank space.");
		}
	}

	public boolean verifyLoginCredential(String userName, String password) {
		
		methodName ="verifyLoginCredential(String userName, String password)";
		logger.info(methodName + " method is called");
		
		String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		String userNameRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; 
		
		if (userName.matches(userNameRegex) && password.matches(passwordRegex))
			return true;
		else
			return false;
	}

}
