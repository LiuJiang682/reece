package au.com.reece.addressbook.persistent.spring;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AddressRepositoryTest {

	//Given user can access address repository class
	//When the constructor called
	//Then the Map should be constructed
	@Test
	public void whenConstructorCalledThemMapExist() {
		//Given user can access address repository class
		//When the constructor called
		AddressRepository addressRepository = new AddressRepository();
		//Then the map should exist
		assertNotNull(addressRepository.getRepository());
	}
}
