package com.capgemini.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoSuchUserExists;
import com.capgemini.exceptions.UserNameAndPasswordDoNotMatchRegularExpression;
import com.capgemini.model.Address;
import com.capgemini.model.Role;
import com.capgemini.model.UserDetails;
import com.capgemini.repository.UserDetailsRepository;

@SpringBootTest
class SweetCherryServiceImplTest {

	@Autowired
	private SweetCherryService sweetCherryService;
	@Autowired
	private UserDetailsRepository userRepository;
	
	
	@Test
	void testGetAllAdminAndUserDetails() throws NoSuchUserExists {
		UserDetails userDetails = new UserDetails();
		
		userDetails.setEmail("dummyydata123@gmail.com");
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

		userRepository.save(userDetails);
		
		List<UserDetails> expected = userRepository.findAll();
		List<UserDetails> actual = sweetCherryService.allDetailsOfAdminAndUser();

		// Asserts that the supplied condition is true
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0).getEmail(), actual.get(0).getEmail());
		assertEquals(expected.get(0).getPassword() , actual.get(0).getPassword());
		
	}
                
	@Test
	void testFindUserByUserIdShouldReturnUserObject() throws InvalidIdException {
		UserDetails userDetails = new UserDetails();

		userDetails.setEmail("dummydata123@gmail.com");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");
		userDetails.setPassword("DummyDataqwerty@123");

		Address address = new Address();
		Set<Address> addressSet = new HashSet<Address>();

		address.setCity("Delhhi");
		address.setHouseNo("345");
		address.setLandmark("Neaar landmark");
		address.setPinCode("300123");
		address.setState("Delhhhi");
		addressSet.add(address);

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = sweetCherryService.allUserDetailsById(expected.getUserId());

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());

	}

	@Test
	void testFindUserByUserIdShouldThrowException() {
		assertThrows(InvalidIdException.class, () -> {
			sweetCherryService.allUserDetailsById(-1);
		});
	}

	// Test case for register customer
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

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = sweetCherryService.registerCustomer(userDetails);

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void testregisterCustomerShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.registerCustomer(null);
		});
	}

	@Test
	void testLogoutShouldDisplayMessageOfSuccessfulLogout() {

		String expectedMsg = "You have been logged out successfully";
		String actualMsg = sweetCherryService.logout();

		assertTrue(expectedMsg.equalsIgnoreCase(actualMsg));
	}

	@Test
	void testLoginShouldReturnLoginSuccessfulAsUser() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {

		UserDetails userDetails = new UserDetails();

		userDetails.setEmail("Dummydataaa456@gmail.com");
		userDetails.setPassword("DummyData@1Dummy");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");

		Role role = new Role(2, "user"); 
		userDetails.setRole(role);

		UserDetails expected = userRepository.save(userDetails);
		String actual = sweetCherryService.login(expected.getEmail(), expected.getPassword());

		assertEquals("login SuccessFul as User",actual);

	}

	
	  @Test void LoginShouldThrowNoSuchUserExistsForAdmin(){
	  assertThrows(NoSuchUserExists.class, () -> {
	  sweetCherryService.login("KetanMishra", null); }); }
	  
	  @Test void testLoginShouldReturnLoginSuccessfulAsAdmin() throws NoSuchUserExists, UserNameAndPasswordDoNotMatchRegularExpression {
	  
	  UserDetails userDetails = new UserDetails();
	  
	  userDetails.setEmail("KetanMishraaaa@gmail.com");
	  userDetails.setPassword("KetanMishra@123"); userDetails.setFirstName("Dummy");
	  userDetails.setLastName("Data");
	  
	  Role role = new Role(1,"admin") ; // 
	  userDetails.setRole(role);
	  
	  UserDetails expected = userRepository.save(userDetails);
	  String actual =sweetCherryService.login(expected.getEmail(), expected.getPassword());
	  
	  assertEquals("login SuccessFul as Administrator",actual);
	  
	  }
	  
	  @Test void LoginShouldThrowNoSuchUserExists(){
	  assertThrows(NoSuchUserExists.class, () -> {
	  sweetCherryService.login("KetanMishra","Password12"); }); }
	 

	@Test
	void updateCustomerProfileShouldReturnUpdatedCustomerProfile() throws UserNameAndPasswordDoNotMatchRegularExpression {
		UserDetails userDetails = new UserDetails();

		userDetails.setFirstName("Ketan");
		userDetails.setLastName("Mishra");
		userDetails.setEmail("KetanMishra@gmail.com");
		userDetails.setPassword("KetanM@1234");

		Role role = new Role(2, "user");

		userDetails.setRole(role);

		Address address = new Address();
		address.setCity("Delhi");
		address.setHouseNo("756");
		address.setLandmark("Near plaza");
		address.setPinCode("321494");
		address.setState("Delhi");

		Set<Address> addressSet = new HashSet<>();
		addressSet.add(address);
		userDetails.setAddress(addressSet);

		UserDetails expected = userRepository.save(userDetails);
		UserDetails actual = sweetCherryService.updateCustomerProfile(expected);

		for(Address addresss : actual.getAddress()) {
			
			assertEquals("Delhi",addresss.getCity());
			assertEquals("Delhi",addresss.getState());
			assertEquals("321494",addresss.getPinCode());
			assertEquals("756",addresss.getHouseNo());
			assertEquals("Near plaza",addresss.getLandmark());
		}
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void updateCustomerProfileThrowsUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.updateCustomerProfile(null);
		});
	}

	@Test
	void UpdatePasswordShouldReturnUpdatedPassword() throws UserNameAndPasswordDoNotMatchRegularExpression {
     UserDetails userDetails = new UserDetails();
     
    userDetails.setFirstName("Ketan");
	userDetails.setLastName("Mishraa");
	userDetails.setEmail("KetanMishraa@gmail.com");
	userDetails.setPassword("KetanM@1234");
	
	UserDetails expected = userRepository.save(userDetails);
    String msg = sweetCherryService.modifyPassword(expected.getUserId(),expected.getPassword(),"KetanMishra@123");
    
    assertEquals("Password changed successfully",msg);
    
	}

	@Test
	void UpdatePasswordShouldThrowUserNameAndPasswordDoNotMatchRegularExpression() {
		assertThrows(UserNameAndPasswordDoNotMatchRegularExpression.class, () -> {
			sweetCherryService.modifyPassword(1, "oldPass@124", "newpassword123");
		});
	}
	
}
