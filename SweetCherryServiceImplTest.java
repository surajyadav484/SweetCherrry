package com.capgemini.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.exceptions.InvalidIdException;
import com.capgemini.exceptions.NoMatchingRegex;
import com.capgemini.exceptions.NoSuchUserExists;
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
		UserDetails actual = sweetCherryService.AllUserDetailsById(expected.getUserId());

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());

	}

	@Test
	void testFindUserByUserIdShouldThrowException() {
		assertThrows(InvalidIdException.class, () -> {
			sweetCherryService.AllUserDetailsById(-1);
		});
	}

	// Test case for register customer
	@Test
	void testRegisterCustomerShouldReturnUserDetailsObject() throws NoMatchingRegex {
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
		UserDetails actual = sweetCherryService.RegisterCustomer(userDetails);

		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void testRegisterCustomerShouldThrowNoMatchingRegex() {
		assertThrows(NoMatchingRegex.class, () -> {
			sweetCherryService.RegisterCustomer(null);
		});
	}

	@Test
	void testLogoutShouldDisplayMessageOfSuccessfulLogout() {

		String expectedMsg = "You have been logged out successfully";
		String actualMsg = sweetCherryService.Logout();

		assertTrue(expectedMsg.equalsIgnoreCase(actualMsg));
	}

	@Test
	void testLoginShouldReturnLoginSuccessfulAsUser() throws NoSuchUserExists, NoMatchingRegex {

		UserDetails userDetails = new UserDetails();

		userDetails.setEmail("Dummydataaa456@gmail.com");
		userDetails.setPassword("DummyData@1Dummy");
		userDetails.setFirstName("Dummy");
		userDetails.setLastName("Data");

		Role role = new Role(2, "user"); 
		userDetails.setRole(role);

		UserDetails expected = userRepository.save(userDetails);
		String actual = sweetCherryService.Login(expected.getEmail(), expected.getPassword());

		assertEquals(actual, "Login SuccessFul as User");

	}

	
	  @Test void LoginShouldThrowNoSuchUserExistsForAdmin(){
	  assertThrows(NoSuchUserExists.class, () -> {
	  sweetCherryService.Login("KetanMishra", null); }); }
	  
	  @Test void testLoginShouldReturnLoginSuccessfulAsAdmin() throws NoSuchUserExists, NoMatchingRegex {
	  
	  UserDetails userDetails = new UserDetails();
	  
	  userDetails.setEmail("KetanMishraaaa@gmail.com");
	  userDetails.setPassword("KetanMishra@123"); userDetails.setFirstName("Dummy");
	  userDetails.setLastName("Data");
	  
	  Role role = new Role(1,"admin") ; // 
	  userDetails.setRole(role);
	  
	  UserDetails expected = userRepository.save(userDetails);
	  String actual =sweetCherryService.Login(expected.getEmail(), expected.getPassword());
	  
	  assertEquals(actual,"Login SuccessFul as Administrator");
	  
	  }
	  
	  @Test void LoginShouldThrowNoSuchUserExists(){
	  assertThrows(NoSuchUserExists.class, () -> {
	  sweetCherryService.Login("SurajYadav", "KetanMishra"); }); }
	 

	@Test
	void UpdateCustomerProfileShouldReturnUpdatedCustomerProfile() throws NoMatchingRegex {
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
		UserDetails actual = sweetCherryService.UpdateCustomerProfile(expected);

		for(Address addresss : actual.getAddress()) {
			
			assertEquals(addresss.getCity(), "Delhi");
			assertEquals(addresss.getState(), "Delhi");
			assertEquals(addresss.getPinCode(), "321494");
			assertEquals(addresss.getHouseNo(), "756");
			assertEquals(addresss.getLandmark(), "Near plaza");
		}
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getPassword(), actual.getPassword());
	}

	@Test
	void UpdateCustomerProfileThrowsNoMatchingRegex() {
		assertThrows(NoMatchingRegex.class, () -> {
			sweetCherryService.UpdateCustomerProfile(null);
		});
	}

	@Test
	void UpdatePasswordShouldReturnUpdatedPassword() throws NoMatchingRegex {
     UserDetails userDetails = new UserDetails();
     
    userDetails.setFirstName("Ketan");
	userDetails.setLastName("Mishraa");
	userDetails.setEmail("KetanMishraa@gmail.com");
	userDetails.setPassword("KetanM@1234");
	
	UserDetails expected = userRepository.save(userDetails);
    String msg = sweetCherryService.modifyPassword(expected.getUserId(),expected.getPassword(),"KetanMishra@123");
    
    assertEquals(msg,"Password changed successfully");
    
	}

	@Test
	void UpdatePasswordShouldThrowNoMatchingRegex() {
		assertThrows(NoMatchingRegex.class, () -> {
			sweetCherryService.modifyPassword(1, "oldPass@124", "newpassword123");
		});
	}
}
