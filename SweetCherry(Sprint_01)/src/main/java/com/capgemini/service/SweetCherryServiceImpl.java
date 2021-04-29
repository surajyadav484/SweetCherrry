package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.SweetCherryDao;
import com.capgemini.exceptions.NoSuchCupcakeExists;
import com.capgemini.exceptions.NoSuchOrderExists;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.logger.LoggerController;
import com.capgemini.model.CupcakeDetails;
import com.capgemini.model.Orders;
import com.capgemini.model.Payment;
import com.capgemini.model.UserDetails;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	SweetCherryDao cupcakeDao;

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;

	// CUPCAKE MODULE---------------------------------------------------------------------------------
	
	@Override
	@Transactional
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		methodName = "addCupcakeDetails(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			CupcakeDetails cupcakeDetails = cupcakeDao.createCupcakeDetails(cupcakedetails);
			if (cupcakeDetails != null)
				return cupcakeDetails;
			else
				throw new NoSuchCupcakeExists();
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists(
					"The Cupcake Details you have entered is invalid! Please enter valid Cupcake Details");
		}

	}

	@Override
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists {
		methodName = "showCupcakeDetails()";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			List<CupcakeDetails> cupcakeList = cupcakeDao.readAllCupcakeDetails();
			if (cupcakeList != null)
				return cupcakeList;
			else
				throw new NoSuchElementException("Cupcakes Not Found!");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("No cupcakes avialable to show");
		}

	}

	@Override
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "findCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			CupcakeDetails cupcakDetails = cupcakeDao.readCupcakeDetailsById(cupcakeId);
			if (cupcakDetails != null)
				return cupcakDetails;
			else
				throw new NoSuchElementException("Cupcake is not available");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake Details with cupcake id = " + cupcakeId + " not found");
		}
	}

	@Override
	@Transactional
	public CupcakeDetails modifyCupcakeRating( int cupcakeId,int rating) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakeRating(CupcakeDetails cupcakeDetails)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			CupcakeDetails cupcakeObject = cupcakeDao.updateCupcakeRating( cupcakeId,rating);
			if (cupcakeObject != null)
				return cupcakeObject;
			else
				throw new NoSuchElementException("Wrong values entered!!!");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists(
					"Cupcake details you have entered is invalid!\n Please enter valid details. ");
		}

	}

	@Override
	@Transactional
	public Orders addCupcakeToCart(Orders order) throws NoSuchOrderExists {
		methodName = "addCupcakeToCart(Orders order) ";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			Orders orderObject = cupcakeDao.addCupcakeToCart(order);
			if (orderObject != null)
				return orderObject;
			else
				throw new NoSuchElementException("Entered order values are incorrect");
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
	}

	@Override
	@Transactional
	public Payment addPaymentDetails(Payment payment) throws NoSuchOrderExists {
		methodName = "addPaymentDetails(Payment payment)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			Payment paymentObject = cupcakeDao.createPaymentDetails(payment);
			if (paymentObject != null)
				return paymentObject;
			else
				throw new NoSuchOrderExists();
		} catch (NoSuchOrderExists e) {
			throw new NoSuchOrderExists(
					"The payment details you have entered is incorrect! Please enter valid payment details");
		}
	}

	
	
	
	// ORDER MODULE--------------------------------------------------------------------------------------------------
	
	
	@Override
	@Transactional
	public Payment makeOnlinePayment(Payment payment) {
		return cupcakeDao.payOnline(payment);

	}

	@Override
	@Transactional
	public Orders makeOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			 Orders orders = cupcakeDao.placeOrder(orderId);
			 if(orders != null)
				 return orders;
			 else
				 throw new NoSuchElementException("Entered order values are incorrect");
		}
		catch(NoSuchElementException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
	}

	@Override
	@Transactional
	public UserDetails addUserDetails(UserDetails userDetails) throws NoSuchOrderExists {
		try {
			cupcakeDao.createUserDetails(userDetails);
			if(userDetails != null)
				return userDetails;
			else
				throw new NoSuchElementException("Entered user details are incorrect");
		}
		catch(NoSuchElementException e) {
			throw new NoSuchOrderExists("The user details you have entered is invalid ! Please enter valid details.");
		}
	}

	/*
	 * @Override public CupcakeDetails addCupcakeDetails(CupcakeDetails
	 * cupCakeDetails) {
	 * 
	 * }
	 */

	@Override
	public Orders cancelOnlineOrder(int orderId) throws NoSuchOrderExists {
		try {
			Orders order = cupcakeDao.cancelOrder(orderId);
			if (order.getOrderStatus().equalsIgnoreCase("cancelled"))
				return order;
			else
				throw new NoSuchElementException("Failed to cancel");
		} catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("Failed to cancel the order");
		}
	}

	@Override
	public List<Orders> showOrderDetailsByUserId(int userId) throws NoSuchOrderExists {
		try {
		   List<Orders> ordersList = cupcakeDao.readOrderDetailsByUserId(userId);
		   if(ordersList != null)
			   return ordersList;
		   else
			   throw new NoSuchElementException("Empty list");
		}
		catch(NoSuchElementException e) {
			throw new NoSuchOrderExists("There is no order");
		}
	}

	@Override
	@Transactional
	public UserDetails modifyDeliveryAddress(UserDetails address) throws NoSuchUserExists {
		try {
			UserDetails userDetails = cupcakeDao.updateDeliveryAddress(address);
			if(userDetails != null)
				return userDetails;
			else
				throw new NoSuchElementException("Invalid User details");
		}
		catch(NoSuchElementException e) {
			throw new NoSuchUserExists("The user details you have entered is incorrect! Please enter valid details");
		}
	}

	@Override
	@Transactional
	public UserDetails addDeliveryAddress(UserDetails userDetails) {
		return cupcakeDao.createDeliveryAddress(userDetails);
	}

	@Override
	@Transactional
	public boolean deleteDeliveryAddress(int addressId) {
		return cupcakeDao.removeDeliveryAddress(addressId);
	}

	
	


}
