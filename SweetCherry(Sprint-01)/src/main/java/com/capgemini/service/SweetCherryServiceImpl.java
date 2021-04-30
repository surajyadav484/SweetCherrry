package com.capgemini.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
import com.capgemini.repository.CupcakeDetailsRepository;
import com.capgemini.repository.OrderRepository;
import com.capgemini.repository.PaymentRepository;

@Service
public class SweetCherryServiceImpl implements SweetCherryService {

	@Autowired
	SweetCherryDao cupcakeDao;
	@Autowired
	CupcakeDetailsRepository cupcakeRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PaymentRepository paymentRepository;

	private Logger logger = LoggerController.getLogger(SweetCherryServiceImpl.class);
	String methodName = null;
	
	
	// CUPCAKE MODULE---------------------------------------------------------------------------------
	
	@Override
	@Transactional
	public CupcakeDetails addCupcakeDetails(CupcakeDetails cupcakedetails) throws NoSuchCupcakeExists {
		methodName = "addCupcakeDetails(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			String regex = "[A-Za-z]+";
			if(cupcakedetails.getCupcakeName().matches(regex)) {
				CupcakeDetails cupcakeDetails = cupcakeRepository.save(cupcakedetails);
				if (cupcakeDetails != null)
					return cupcakeDetails;
				else
					throw new NoSuchElementException("Incorrect details");
			}
			else
				throw new NoSuchElementException("Cupcake details constraints are not satisfied");
		} 
		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("The Cupcake Details you have entered is invalid! Please enter valid Cupcake Details");
		}

	}
	
	/*@Override
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
*/
	
	@Override
	public List<CupcakeDetails> showCupcakeDetails() throws NoSuchCupcakeExists {
		methodName = "showCupcakeDetails()";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			List<CupcakeDetails> cupcakeList = cupcakeRepository.findAll();
			if (cupcakeList != null)
				return cupcakeList;
			else
				throw new NoSuchElementException("Cupcakes Not Found!");
		} catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("No cupcakes avialable to show");
		}

	}
	
	
	
	
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
	 */

	
	@Override
	public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "findCupcakeDetailsById(int cupcakeId)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			if(cupcakeId > 0) {
				Optional<CupcakeDetails> cupcakeDetails = cupcakeRepository.findById(cupcakeId);
				if (cupcakeDetails.get() != null) 
					return cupcakeDetails.get();
				else
					throw new NoSuchElementException("No Cupcakes Found");
			}
			else 
				throw new NoSuchElementException("You have entered a negative Cupcake Id");
			
		}
		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake Details with cupcake id = " + cupcakeId + " not found");
		}
	}
	
	
	
	
	/*
	 * @Override public CupcakeDetails findCupcakeDetailsById(int cupcakeId) throws
	 * NoSuchCupcakeExists { methodName = "findCupcakeDetailsById(int cupcakeId)";
	 * logger.info(methodName +
	 * " method is called from SweetCherryServiceImpl Layer"); try { if(cupcakeId >
	 * 0) { CupcakeDetails cupcakDetails =
	 * cupcakeDao.readCupcakeDetailsById(cupcakeId); if (cupcakDetails != null)
	 * return cupcakDetails; else throw new
	 * NoSuchElementException("No Cupcakes Found"); } else throw new
	 * NoSuchElementException("You have entered a negative Cupcake Id");
	 * 
	 * } catch (NoSuchElementException e) { throw new
	 * NoSuchCupcakeExists("Cupcake Details with cupcake id = " + cupcakeId +
	 * " not found"); } }
	 */
	
	
	@Override

	@Transactional
	public void modifyCupcakeRating(int cupcakeId, int rating) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakeRating(CupcakeDetails cupcakeDetails)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			if (rating > 0 && rating <= 6) {
				cupcakeRepository.updateRating(cupcakeId, rating);
			} 
			else
				throw new NoSuchElementException("Rating should be between 1 to 5");
		}
		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake details you have entered is invalid!\n Please enter valid details.");
		}

	}
	

	/*
	 * @Override
	 * 
	 * @Transactional public CupcakeDetails modifyCupcakeRating(int cupcakeId, int
	 * rating) throws NoSuchCupcakeExists { methodName =
	 * "modifyCupcakeRating(CupcakeDetails cupcakeDetails)"; logger.info(methodName
	 * + " method is called from SweetCherryServiceImpl Layer"); try { if (rating >
	 * 0 && rating <= 6) { CupcakeDetails cupcakeObject =
	 * cupcakeDao.updateCupcakeRating(cupcakeId, rating); if (cupcakeObject != null)
	 * return cupcakeObject; else throw new
	 * NoSuchElementException("Wrong values entered!!!"); } else throw new
	 * NoSuchElementException("Rating should be between 1 to 5"); } catch
	 * (NoSuchElementException e) { throw new
	 * NoSuchCupcakeExists("Cupcake details you have entered is invalid!\n Please enter valid details."
	 * ); }
	 * 
	 * }
	 * 
	 */
	
	@Override
	@Transactional
	public Orders addCupcakeToCart(Orders order) throws NoSuchOrderExists {
		methodName = "addCupcakeToCart(Orders order) ";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		
		try {
			if(order.getQuantity() <= 10) {
				//order.setCupcakeDetails(order.getCupcakeDetails());
				Orders orderObject = orderRepository.save(order);
				if (orderObject != null)
					return orderObject;
				else
					throw new NoSuchElementException("Entered order values are incorrect");
			}
			else
				throw new NoSuchElementException("Maximum Order quantity is 10");
		} 
		catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details.");
		}
	}
	
	
	
	

	/*
	 * @Override
	 * 
	 * @Transactional public Orders addCupcakeToCart(Orders order) throws
	 * NoSuchOrderExists { methodName = "addCupcakeToCart(Orders order) ";
	 * logger.info(methodName +
	 * " method is called from SweetCherryServiceImpl Layer"); try {
	 * if(order.getQuantity() <= 10) { Orders orderObject =
	 * cupcakeDao.addCupcakeToCart(order); if (orderObject != null) return
	 * orderObject; else throw new
	 * NoSuchElementException("Entered order values are incorrect"); } else throw
	 * new NoSuchElementException("Maximum Order quantity is 10"); } catch
	 * (NoSuchElementException e) { throw new
	 * NoSuchOrderExists("The Order details you have entered is invalid ! Please enter valid details."
	 * ); } }
	 */

	@Override
	@Transactional
	public Payment addPaymentDetails(Payment payment) throws NoSuchOrderExists {
		methodName = "addPaymentDetails(Payment payment)";
		logger.info(methodName + " method is called from SweetCherryServiceImpl Layer");
		try {
			if(payment.getStatus().equalsIgnoreCase("successful")) {
				Payment paymentObject = paymentRepository.save(payment);
				if (paymentObject != null)
					return paymentObject;
				else
					throw new NoSuchElementException("Invalid details");
			}
			else
				throw new NoSuchElementException("Please update the status as 'Successful'");
		} 
		catch (NoSuchElementException e) {
			throw new NoSuchOrderExists("The payment details you have entered is incorrect! Please enter valid payment details");
		}
	}
	
	
	
	/*
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

	
	
	
	
	
	
	@Override
	@Transactional
	public CupcakeDetails updateCupcakePriceByCupcakeId(int cupcakeId,double price ) throws NoSuchCupcakeExists {
		
		methodName = "modifyCupcakePrice(CupcakeDetails cupcakedetails)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
		try {
			if(cupcakeId > 1) {
				cupcakeRepository.updatePrice(cupcakeId, price);
				CupcakeDetails cupcakedetails = findCupcakeDetailsById(cupcakeId);
				return cupcakedetails;
			}
			else
				throw new NoSuchElementException("Cupcake Id can't be negative");
		}

		catch (NoSuchElementException e) {
		throw new NoSuchCupcakeExists("Cupcake Price is not modified.");
		}

	}

	
	@Override
	public CupcakeDetails modifyCupcakeName(int cupcakeId,String cupcakeName) throws NoSuchCupcakeExists {
		methodName = "modifyCupcakeName(int cupcakeId,String cupcakeName)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
		try {
			if(cupcakeId > 1) {
				cupcakeRepository.updateCupcakeName(cupcakeId, cupcakeName);
				CupcakeDetails cupcakedetails = findCupcakeDetailsById(cupcakeId);
				return cupcakedetails;
			}
		
			else
				throw new NoSuchElementException("Cupcake Id can't be negative");
		}

		catch (NoSuchElementException e) {
			throw new NoSuchCupcakeExists("Cupcake Name is not modified.");
		}

	}

	@Override
	@Transactional
	public String removeCupcakeDetails(int cupcakeId) throws NoSuchCupcakeExists {
		methodName = "removeCupcakeDetails(int cupcakId)";
		logger.info(methodName + " method is called from SweetCherryService Layer");
        try {
        	if(cupcakeId > 1) {
        		cupcakeRepository.deleteById(cupcakeId);
        		return "Cupcake Removed";
        	}
        	else
        		throw new NoSuchElementException("Cupcake id can't be negative or zero.");
        }
        catch(NoSuchElementException e) {
        	throw new NoSuchCupcakeExists("Cupcake with id" + cupcakeId + " is not found.");
        	
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
