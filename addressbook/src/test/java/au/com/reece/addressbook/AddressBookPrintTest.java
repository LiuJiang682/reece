package au.com.reece.addressbook;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import au.com.reece.addressbook.fixture.AddressBookFixture;
import au.com.reece.addressbook.model.Contact;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to print all contacts to address book 
 */
public class AddressBookPrintTest {

	/**
	 * Given the address book is populated
	 * When I print all contacts in the address book
	 * Then a list of contacts should return
	 */
	@Test
	public void contractListShouldReturnsFromAddressBook() {
		//Given populated address book
		AddressBook  addressBook = AddressBookFixture.getPopulatedAddressBook();
		
		//When getAllContacts method called
		List<Contact> contacts = addressBook.getAllContacts(5);
		
		//Then the list should has all contacts
		assertNotNull(contacts);
		assertTrue(3 == contacts.size());
	}
}
