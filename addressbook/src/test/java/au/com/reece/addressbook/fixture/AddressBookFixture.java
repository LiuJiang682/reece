package au.com.reece.addressbook.fixture;

import java.util.List;

import au.com.reece.addressbook.AddressBook;
import au.com.reece.addressbook.model.Contact;

/**
 * The test fixture for address book.
 *
 */
public class AddressBookFixture {

	public static AddressBook getPopulatedAddressBook() {
		AddressBook addressBook = new AddressBook();
		List<Contact> contacts = ContactFixture.getListOfContacts();
		
		addressBook.add(contacts.get(0));
		addressBook.add(contacts.get(1));
		addressBook.add(contacts.get(2));
		return addressBook;
	}

	
}
