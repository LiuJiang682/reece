package au.com.reece.addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.com.reece.addressbook.fixture.ContactFixture;
import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.model.ContactTest;
import au.com.reece.addressbook.persistent.DefaultFileIntegrationTest;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to add new contacts to address book when new 
 * contact received
 */
public class AddressBookAddContactTest extends DefaultFileIntegrationTest {

	/**
	 * Given that a new contact is created and address book exist
	 * When I add the new contact into address book
	 * Then I should have the new contact in address book
	 * @throws IOException 
	 */
	@Test
	public void newContactShouldBeAddedToAddressBook() throws IOException {
		// Given a new contact and a default address book created.
		Contact contact = new Contact(ContactTest.TEST_ID, ContactTest.EMPTY, ContactTest.EMPTY);
		AddressBook addressBook = new AddressBook();
		
		// When I add the contact into default address book.
		boolean flag = addressBook.add(contact);
		
		//Then I should have the contact.
		assertTrue(flag);
		File file = new File(AddressBook.DEFAULT_NAME);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line1 = bufferedReader.readLine();
		assertNotNull(line1); 
		assertEquals("id=0,name=,phoneNumber=", line1);
		String line2 = bufferedReader.readLine();
		assertNull(line2);
		bufferedReader.close();
	}
	
	/**
	 * Given that a list of new contacts is created
	 * When I add the list into address book
	 * Then I should have the new list of contact in address book
	 */
	@Test
	public void newListofContactShouldBeAddedToAddressBook() {
		// Given a new list of contact and a default address book created.
		List<Contact> contacts = new ArrayList<Contact>();
		Contact contact = new Contact(ContactTest.TEST_ID, ContactTest.EMPTY, ContactTest.EMPTY);
		contacts.add(contact);
		Contact anthor = ContactFixture.getDefaultContact();
		contacts.add(anthor);
		AddressBook addressBook = new AddressBook();
		
		// When I add the contact into default address book.
		boolean flag = addressBook.add(contacts);
		
		//Then I should have the contact.
		assertTrue(flag);
	}
	
	/**
	 * One more scenario needs to test is the add operation
	 * failed on the middle of list and the operation should
	 * stop immediately. However, without any Mock framework
	 * it is hard to implements it.
	 */
}
