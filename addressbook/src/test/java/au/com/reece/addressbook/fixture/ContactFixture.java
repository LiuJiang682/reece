package au.com.reece.addressbook.fixture;

import java.util.ArrayList;
import java.util.List;

import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.model.ContactTest;

public class ContactFixture {

	public static List<Contact> getListOfContacts() {
		List<Contact> contacts = new ArrayList<>(3);
		Contact contact = new Contact(ContactTest.TEST_NAME, ContactTest.TEST_PHONE_NUMBER);
		contacts.add(contact);
		Contact contact1 = new Contact("Richard Jones", "0414789012");
		contacts.add(contact1);
		Contact contact2 = new Contact("Will Pang", "0414345678");
		contacts.add(contact2);
		return contacts;
	}
	
	public static Contact getDefaultContact() {
		return new Contact(ContactTest.TEST_NAME, ContactTest.TEST_PHONE_NUMBER);
	}
	
	public static Contact getWillsContact() {
		return new Contact("Will Pang", "0414345678");
	}

	public static Contact getRichardsContact() {
		return new Contact("Richard Jones", "0414789012");
	}
}
