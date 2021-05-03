package com.capgemini.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.model.Address;
import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.UserDetailsRepository;

@SpringBootTest
class SweetCherryServiceImplTestWithMockito  {

	@Autowired
	private SweetCherryService sweetCherryService ;
	
	@MockBean
	private UserDetailsRepository userDetailsRepository ;
	
	@Test
    void testFindUserByUserIdShouldReturnUserObject() throws NoSuchUserExists, InvalidIdException{
    
		UserDetails userDetails = new UserDetails() ;
    
		userDetails.setUserId(101);
		userDetails.setEmail("dummyyydata123@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyDattaqwerty@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhhi");
		address.setHouseNo("345");
		address.setLandmark("Neaar landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);

		Optional<UserDetails> expected = Optional.of(userDetails) ;
		when(userDetailsRepository.findById(101)).thenReturn(expected);
		
		UserDetails actual = sweetCherryService.allUserDetailsById(userDetails.getUserId()) ;
		
		assertEquals(expected.get(), actual);
		
	}
	@Test
	void testFindUserByUserIdShouldThrowException() {
		assertThrows(InvalidIdException.class, () -> {
			sweetCherryService.allUserDetailsById(-1);
		});
	}
	
	@Test
	void testLoginShouldReturnStringMessageForUser() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails() ;
		userDetails.setEmail("dummyyydata123@gmail.com");
		userDetails.setPassword("DummyDattaqwerty@123");
		userDetails.setUserId(10001);
		
		Role role =  new Role(2,"user");
		userDetails.setRole(role);
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		when(userDetailsRepository.login("dummyyydata123@gmail.com", "DummyDattaqwerty@123")).thenReturn(expected.get());
		String str = sweetCherryService.login(userDetails.getEmail(),userDetails.getPassword());
		
		assertEquals("login SuccessFul as User", str);
	}
	  @Test void LoginShouldThrowNoSuchUserExistsForUser(){
	  assertThrows(NoSuchUserExists.class, () -> {
	  sweetCherryService.login("KetanMishra", null); }); }                     


   @Test
   void testLoginShouldReturnStringMessageForAdmin() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
	UserDetails userDetails = new UserDetails() ;
	userDetails.setEmail("dummyyydata123@gmail.com");
	userDetails.setPassword("DummyDattaqwerty@123");
	userDetails.setUserId(10001);
	
	Role role =  new Role(1,"admin");
	userDetails.setRole(role);
	
	Optional<UserDetails> expected = Optional.of(userDetails);
	when(userDetailsRepository.login("dummyyydata123@gmail.com", "DummyDattaqwerty@123")).thenReturn(expected.get());
	String str = sweetCherryService.login(userDetails.getEmail(),userDetails.getPassword());
	
	assertEquals("login SuccessFul as Administrator", str);
}
  @Test void LoginShouldThrowNoSuchUserExistsForAdmin(){
  assertThrows(NoSuchUserExists.class, () -> {
  sweetCherryService.login("KetanMishra", null); }); }   

  
  @Test
  void LogoutShouldReturnStringSayingLogoutSuccessful() {
	    String expectedMsg = "You have been logged out successfully";
		String actualMsg = sweetCherryService.logout();

		assertTrue(expectedMsg.equalsIgnoreCase(actualMsg));
	}  
  
    @Test
	void testregisterCustomerShouldReturnUserDetailsObject() throws UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails();

		
		userDetails.setEmail("dummydata12@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyData@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhi");
		address.setHouseNo("345");
		address.setLandmark("Near landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);
		
		Optional<UserDetails> expected = Optional.of(userDetails);
		when(userDetailsRepository.save(userDetails)).thenReturn(expected.get());
		
		UserDetails actual = sweetCherryService.registerCustomer(userDetails);
		
		assertEquals(userDetails, actual);
  }
    @Test
	void testregisterCustomerShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.registerCustomer(null);
		});
    }

    @Test
    void testUpdatePasswordShouldReturnUpdatedPassword() throws UserNameAndPasswordDoNotMatchRegularExpression{
    	UserDetails userDetails = new UserDetails();
    	
    	userDetails.setEmail("dummydata12@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyData@123");
		userDetails.setUserId(1);
    	
		Optional<UserDetails> expected =Optional.of(userDetails);
	   // when(userDetailsRepository.updatePassword(1,"DummyData@123","KetanMishra@1234"));
		String str = sweetCherryService.modifyPassword(expected.get().getUserId(),expected.get().getPassword(),"KetanMishra@1234");
      
		assertEquals("Password changed successfully", str );
		
    }
    @Test
	void UpdatePasswordShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.modifyPassword(1, "oldPass@124", "newpassword123");
		});
}


  @Test void testUpdateCustomerProfileShouldReturnUserDetailsObject() throws UserNameAndPasswordDoNotMatchRegularExpression{ 
	  
	  UserDetails userDetails = new UserDetails();
  
  userDetails.setFirstName("Ketan");
  userDetails.setLastName("Mishra");
  userDetails.setEmail("KetanMishra@gmail.com");
  userDetails.setPassword("KetanM@1234");
  
  Role role = new Role(2, "user");
  
  userDetails.setRole(role);
  
  Address address = new Address(); address.setCity("Delhi");
  address.setHouseNo("756"); address.setLandmark("Near plaza");
  address.setPinCode("321494"); address.setState("Delhi");
  
  Set<Address> addressSet = new HashSet<>(); addressSet.add(address);
  userDetails.setAddress(addressSet);
  
  when(userDetailsRepository.save(userDetails)).thenReturn(userDetails);
  UserDetails actual = sweetCherryService.updateCustomerProfile(userDetails);
  
  assertEquals(actual, userDetails);
  
  
  }
    @Test
	void UpdateCustomerProfileThrowsUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.updateCustomerProfile(null);
		});
}
}