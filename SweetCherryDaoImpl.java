package com.capgemini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;

@Repository
public class SweetCherryDaoImpl implements SweetCherryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UserDetails> AllAdminDetails() {
		String jpql = "From UserDetails"; // To show all the details of the administrator
		TypedQuery<UserDetails> query = entityManager.createQuery(jpql, UserDetails.class);
		return query.getResultList();
	}
	
	@Override
	public UserDetails RegisterCustomer(UserDetails registerCustomer) {
		entityManager.persist(registerCustomer); // Insert a new User
		return registerCustomer;
	}

	@Override
	public String UpdatePassword(int userId, String oldPassword, String newPassword) { // Checks whether old password
																						// matches database and updates
																						// entered new password in the
																						// database
		UserDetails userDetails = entityManager.find(UserDetails.class, userId);
		String result = null;
		if (oldPassword.equals(userDetails.getPassword())) {
			if (newPassword != null) {
				userDetails.setPassword(newPassword);
				entityManager.merge(userDetails);
			} else {
				result = "Entered password is null";
			}
		} else {
			result = "Please enter a valid password";
		}

		result = "successful";
		return result;
	}

	@Override
	public String Login(String userName, String password) {
		String jpql = "Select u from UserDetails u where u.email = :userName";
		TypedQuery<UserDetails> tquery = entityManager.createQuery(jpql, UserDetails.class);
		String result = null;
		tquery.setParameter("userName", userName);
		UserDetails userDetails = tquery.getSingleResult();
		if (userDetails != null) {
			if (userName.equals(userDetails.getEmail()) && password.equals(userDetails.getPassword())) {
				result = "Login Sucessfull";
			} else {
				result = "UserName/E-mail is not registered or empty";
			}
		} else {
			result = "wrong username or password ";
		}

		return result;
	}

	@Override
	public String Logout() {
		return "You have been sucessfully logged out !"; // Show a message after successful Logout
	}

	@Override
	public UserDetails AllUserDetailsById(int userId) {
		UserDetails userDetails = entityManager.find(UserDetails.class, userId);	
		return userDetails ;
	}

	@Override
	public UserDetails UpdateCustomerProfile(UserDetails customer) {
		return entityManager.merge(customer);
	}

}
