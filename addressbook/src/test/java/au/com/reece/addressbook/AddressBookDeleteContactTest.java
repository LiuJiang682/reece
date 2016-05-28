package au.com.reece.addressbook;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import au.com.reece.addressbook.fixture.AddressBookFixture;
import au.com.reece.addressbook.model.Contact;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to delete contacts to address book 
 */
public class AddressBookDeleteContactTest {

	/**
	 * Given the address book exist
	 * When a name provided to address book
	 * Than the contact record should be deleted.
	 */
	@Test
	public void whenNameProvidedThanContactShouldDeleted() {
		//Given popluated address book
		AddressBook addressBook = AddressBookFixture.getPopulatedAddressBook();
		String name = "Will Pang";
		Contact contact = new Contact(name, null);
		
		//When delete method called
		boolean flag = addressBook.delete(contact, 5);
		
		//Then the record should removed
		assertTrue(flag);
	}
}
