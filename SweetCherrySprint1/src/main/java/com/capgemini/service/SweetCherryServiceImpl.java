package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.UserDetailsRepository;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;

	static final String DESCRIPTION = " method is called";

	@Autowired
	private UserDetailsRepository userRepository;

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

String str = "^(?=.*[0-9])" ;
String str1 = "(?=.*[a-z])(?=.*[A-Z])" ;
String str2 = "(?=.*[@#$%^&+=])" ;
String str3 ="(?=\\\\S+$).{8,20}$" ;
	
	@Override
	@Transactional
	public UserDetails registerCustomer(UserDetails registerCustomer)
			throws UserNameAndPasswordDoNotMatchRegularExpression {

		methodName = "registerCustomer(UserDetails registerCustomer)";
		logger.info(methodName, DESCRIPTION);

		String passwordRegex = str+ str1+ str2+ str3;
		String userNameRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
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
		} catch (Exception e) {
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
		// !@#$%&*()-+=^.
		// It doesn’t contain any blank space.

		methodName = "modifyPassword(int userId, String oldPassword, String newPassword)";
		logger.info(methodName, DESCRIPTION);

		try {
			if (newPassword
					.matches(str+ str1 + str2+ str3)) {

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
	@Transactional
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
	public String logout() {

		methodName = "logout()";
		logger.info(methodName, DESCRIPTION);

		return "You have been logged out successfully";
	}

	@Override
	@Transactional
	public UserDetails allUserDetailsById(int userId) throws InvalidIdException {

		methodName = "allUserDetailsById(int userId)";
		logger.info(methodName, DESCRIPTION);

		try {
			Optional<UserDetails> user = userRepository.findById(userId);
			if(user.isPresent() && user.get() != null) {
				return user.get();
			}
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

		String passwordRegex = str+ str1 + str2+ str3;
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
     boolean flag= false ;
		methodName = "verifyLoginCredential(String userName, String password)";
		logger.info(methodName, DESCRIPTION);

		String passwordRegex = str+ str1 + str2+ str3;
		String userNameRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

		if (userName.matches(userNameRegex) && password.matches(passwordRegex)) 
			flag = true ;
		
		return flag; 
		 
	}

}
