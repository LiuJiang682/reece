package au.com.reece.addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to have an address book on my PC
 */
public class AddressBookConstructorTest {

	private AddressBook addressBook;
	
	@Before
	public void setUp() {
		addressBook = null;
	}

	/**
	 * Given user provided a null address book name,
	 * When PC loaded
	 * Then the default address book should be ready
	 * with default address book
	 */
	@Test
	public void whenUserProvidedNullAddressBookNameThenDefaultAddressBookShouldReturn() {
		//Given user provided null address book name
		String addressBookName = null;
		
		//When address book called
		addressBook = new AddressBook(addressBookName, null);
		
		//Then the default address book should return
		thenAssertDefaultAddressBook();
	}
	
	/**
	 * Given user provided a address book name,
	 * When PC loaded
	 * Then the correct address book should be ready
	 */
	@Test
	public void whenUserProvidedAddressBookNameThenCorrectAddressBookShouldReturn() {
		//Given user provided null address book name
		String addressBookName = "abc";
		
		//When address book called
		addressBook = new AddressBook(addressBookName, null);
		
		thenAssertCorrectAddressBook();
	}

	/**
	 * Given user provided a null address book name and a null dao type,
	 * When PC loaded
	 * Then the default address book should be ready
	 * with default address book and default dao type
	 */
	@Test
	public void whenUserProvidedNullAddressBookNameNullDaoTypeThenDefaultAddressBookShouldReturn() {
		//Given user provided null address book name and null DAO type
		String addressBookName = null;
		String daoType = null;
		
		//When address book called
		addressBook = new AddressBook(addressBookName, daoType);
		
		//Then the default address book should return
		thenAssertDefaultAddressBook();
		assertEquals(AddressBook.DEFAULT_DAO_TYPE_FILE, addressBook.getDaoType());
	}
	
	/**
	 * Given user provided an address book name and a dao type,
	 * When PC loaded
	 * Then the default address book should be ready
	 * with a correct address book and correct dao type
	 */
	@Test
	public void whenUserProvidedAddressBookNameDaoTypeThenCorrectAddressBookShouldReturn() {
		//Given user provided an address book name and a DAO type
		String addressBookName = "abc";
		String daoType = "file";
		
		//When address book called
		addressBook = new AddressBook(addressBookName, daoType);
		
		//Then the a correct address book should return
		thenAssertCorrectAddressBook();
		assertEquals(daoType, addressBook.getDaoType());
	}

	/**
	 * Given user provided an address book name and a dao type,
	 * When PC loaded
	 * Then the default address book should be ready
	 * with a correct address book and correct dao type
	 */
	@Test
	public void whenUserProvidedNullAddressBookNameDaoTypeThenDefaultAddressBookShouldReturn() {
		//Given user provided null address book name and a DAO type
		String addressBookName = null;
		String daoType = "file";
		
		//When address book called
		addressBook = new AddressBook(addressBookName, daoType);
		
		//Then the a correct address book should return
		thenAssertDefaultAddressBook();
		assertEquals(daoType, addressBook.getDaoType());
	}
	
	/**
	 * Given user provided an address book name and a dao type,
	 * When PC loaded
	 * Then the default address book should be ready
	 * with a correct address book and correct dao type
	 */
	@Test
	public void whenUserProvidedNullAddressNullBookNameDaoTypeThenDefaultAddressBookShouldReturnWithDAO() {
		//Given user provided null address book name and a DAO type
		String addressBookName = null;
		String daoType = null;
		
		//When address book called
		addressBook = new AddressBook(addressBookName, daoType);
		
		//Then the a correct address book should return
		thenAssertDefaultAddressBook();
		assertEquals(AddressBook.DEFAULT_DAO_TYPE_FILE, addressBook.getDaoType());
		assertNotNull(addressBook.getDao());
	}
	
	/**
	 * Given user login to the PC
	 * When PC loaded
	 * Then the default address book should be ready 
	 * with default address book
	 */
	@Test
	public void whenUserNotProvidedAddressBookNameThenDefaultAddressBookShouldReturn() {
		//Given user not provided address book name
		
		//When address book called
		addressBook = new AddressBook();
		
		//Then the default address book should return
		thenAssertDefaultAddressBook();
	}

	private void thenAssertDefaultAddressBook() {
		thenAssertAddressBookNotNull();
		assertEquals(AddressBook.DEFAULT_NAME, addressBook.getName());
	}

	private void thenAssertAddressBookNotNull() {
		assertNotNull(addressBook);
	}
	
	private void thenAssertCorrectAddressBook() {
		thenAssertAddressBookNotNull();
		assertEquals("abc", addressBook.getName());
	}
}
