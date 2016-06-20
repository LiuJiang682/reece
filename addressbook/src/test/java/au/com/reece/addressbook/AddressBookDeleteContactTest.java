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

import au.com.reece.addressbook.fixture.AddressBookFixture;
import au.com.reece.addressbook.fixture.ContactFixture;
import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.persistent.DefaultFileIntegrationTest;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to delete contacts to address book 
 */
public class AddressBookDeleteContactTest extends DefaultFileIntegrationTest {

	/**
	 * Given the address book exist
	 * When a name provided to address book
	 * Than the contact record should be deleted.
	 * @throws IOException 
	 */
	@Test
	public void whenNameProvidedThanContactShouldDeleted() throws IOException {
		//Given populated address book
		AddressBook addressBook = AddressBookFixture.getPopulatedAddressBook();
		Contact contact = ContactFixture.getWillsContact();
		
		//When delete method called
		boolean flag = addressBook.delete(contact);
		
		//Then the record should removed
		assertTrue(flag);
		File file = new File(AddressBook.DEFAULT_NAME);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line1 = bufferedReader.readLine();
		assertNotNull(line1); 
		assertEquals("id=0,name=John Smith,phoneNumber=0414123456", line1);
		String line2 = bufferedReader.readLine();
		assertNotNull(line2);
		assertEquals("id=1,name=Richard Jones,phoneNumber=0414789012", line2);
		String line3 = bufferedReader.readLine();
		assertNull(line3);
		bufferedReader.close();
	}
	
	/**
	 * Given the address book exist
	 * When a list of name provided to address book
	 * Than the contact records should be deleted.
	 */
	@Test
	public void whenNameListProvidedThenContactsShouldBeDeleted() {
		//Given populated address book
		AddressBook addressBook = AddressBookFixture.getPopulatedAddressBook();
		List<Contact> contacts = new ArrayList<>();
		contacts.add(ContactFixture.getWillsContact());
		contacts.add(ContactFixture.getRichardsContact());
		
		//When the delete method called
		List<Contact> undeleted = addressBook.delete(contacts);
		
		//Then assert the result
		assertNotNull(undeleted);
		assertTrue(undeleted.isEmpty());
	}
}
