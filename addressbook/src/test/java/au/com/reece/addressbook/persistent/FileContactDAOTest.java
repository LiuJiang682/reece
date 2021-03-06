package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.reece.addressbook.AddressBook;
import au.com.reece.addressbook.fixture.ContactFixture;
import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.model.ContactTest;

/**
 * Test the FileContactDAO class
 *
 */
public class FileContactDAOTest extends DefaultFileIntegrationTest {

	private FileContactDAO dao;
	
	@Before
	public void setUp() {
		dao = null;
	}
	
	@Test
	public void whenNullFileNameProvidedThenConstructorShouldThrowIllegalArgumentException()  {
		//Given the null file name
		String fileName = null;
		
		//When the constructor is called
		try {
			new FileContactDAO(fileName);
			fail("Program reached unexpected point.");
		}
		catch (IllegalArgumentException e) {
			//Then assert the IllegalArgumentException
			String message = e.getMessage();
			assertEquals("file name cannot be null!", message);
		}
	}

	@Test
	public void whenContactProvidedThenAddShouldBeReturnTrue()  {
		//Given the Contact provided
		Contact contact = ContactFixture.getDefaultContact();
		FileContactDAO dao = getFileContactDAO();
		
		//When the add operation performed
		boolean flag = dao.add(contact);
		
		//Then assert the flag
		assertTrue(flag);
	}
	
	@Test
	public void whenCloseCalledThenTrueShouldReturn()  {
		//Given the FileContactDAO provided
		dao = getFileContactDAO();
		
		//When the close operation performed
		boolean flag = dao.close();
		
		//Then assert the flag
		assertTrue(flag);
	}
	
	@Test
	public void whenGetAllContactCalledThenFullListShouldReturn()  {
		givenPopulatedDAO();
		
		//When getAllContacts called
		List<Contact> contacts = dao.getAllContacts(5);
		
		//Then the list should be fully populated
		assertNotNull(contacts);
	}
	
	@Test
	public void whenGetFileContentCalledThen3ContactShouldReturn() throws IOException {
		givenPopulatedDAO();
		
		//When getFileContents called
		List<String> contents = dao.getFileContent(5);
		
		//Then 3 Strings should return
		assertNotNull(contents);
		assertTrue(3 == contents.size());
	}
	
	@Test
	public void whenGetFileContentCalledWith2AsMaxShouldReturn2Strings()  {
		givenPopulatedDAO();
		
		//When getFileContents called with 2 as max size
		List<String> contents = dao.getFileContent(2);
		
		//Then 2 Strings should return
		assertNotNull(contents);
		assertTrue(2 == contents.size());
	}

	@Test
	public void when2StringsProvidedDoConversionShouldReturn2Contact() {
		givenPopulatedDAO();
		
		//And getFileContents called with 2 as max size
		List<String> contents = dao.getFileContent(2);
		//And 2 contact list provided
		List<Contact> contacts = new ArrayList<>(2);
		
		assertTrue(contacts.isEmpty());
		
		//When doContactConversion method called
		this.dao.doContactConversion(contacts, contents);
		
		//Then contacts should has 2 contacts in the list
		assertNotNull(contacts);
		assertTrue(2 == contacts.size());
		Contact contact1 = contacts.get(0);
		assertNotNull(contact1);
		assertEquals(0, contact1.getId());
		assertEquals("John Smith", contact1.getName());
		assertEquals("0414123456", contact1.getPhoneNumber());
		Contact contact2 = contacts.get(1);
		assertNotNull(contact2);
		assertEquals(1, contact2.getId());
		assertEquals("Richard Jones", contact2.getName());
		assertEquals("0414789012", contact2.getPhoneNumber());
	}
	
	@Test
	public void when2StringsProvidedWithOneInvalidIDDoConversionShouldReturn1Contact() {
		givenPopulatedDAO();
		String[] contents = {"id=abc,name=John Smith,phoneNumber=0414123456", "id=1,name=Richard Jones,phoneNumber=0414789012"};
		//And 2 contact list provided
		List<Contact> contacts = new ArrayList<>(2);
		
		assertTrue(contacts.isEmpty());
		
		//When doContactConversion method called
		this.dao.doContactConversion(contacts, Arrays.asList(contents));
		
		//Then contacts should has 2 contacts in the list
		assertNotNull(contacts);
		assertTrue(1 == contacts.size());
		Contact contact1 = contacts.get(0);
		assertNotNull(contact1);
		assertEquals(1, contact1.getId());
		assertEquals("Richard Jones", contact1.getName());
		assertEquals("0414789012", contact1.getPhoneNumber());
	}
	
	@Test
	public void whenFullContactProvidedThenDeleteShouldReturnTrue() throws IOException {
		//Given populated DAO and full contact
		givenPopulatedDAO();
		Contact contact = ContactFixture.getDefaultContact();
		
		//When delete method called
		boolean flag = this.dao.delete(contact);
		
		//Then the flag should true
		assertTrue(flag);
		File file = new File(AddressBook.DEFAULT_NAME);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line1 = bufferedReader.readLine();
		assertNotNull(line1); 
		assertEquals("id=1,name=Richard Jones,phoneNumber=0414789012", line1);
		String line2 = bufferedReader.readLine();
		assertNotNull(line2);
		assertEquals("id=2,name=Will Pang,phoneNumber=0414345678", line2);
		String line3 = bufferedReader.readLine();
		assertNull(line3);
		bufferedReader.close();
	}
	
	@Test
	public void whenContactsProvidedThenDeleteShouldReturnTrue() throws IOException {
		//Given populated DAO and full contact
		givenPopulatedDAO();
		List<Contact> contacts = new ArrayList<>();
		contacts.add(ContactFixture.getWillsContact());
		contacts.add(ContactFixture.getRichardsContact());
		
		//When delete method called
		List<Contact> undeleted = this.dao.delete(contacts);
		
		//Then the flag should true
		assertNotNull(undeleted);
		assertTrue(undeleted.isEmpty());
		File file = new File(AddressBook.DEFAULT_NAME);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String line1 = bufferedReader.readLine();
		assertNotNull(line1); 
		assertEquals("id=0,name=John Smith,phoneNumber=0414123456", line1);
		String line2 = bufferedReader.readLine();
		assertNull(line2);
		bufferedReader.close();
	}
	
	@Test
	public void whenContactsProvidedThenDeleteShouldReturnTruePartialDeleted() throws IOException {
		//Given populated DAO and full contact
		givenPopulatedDAO();
		List<Contact> contacts = new ArrayList<>();
		contacts.add(ContactFixture.getWillsContact());
		contacts.add(new Contact(ContactTest.TEST_ID, ContactTest.EMPTY, ContactTest.EMPTY));
		
		//When delete method called
		List<Contact> undeleted = this.dao.delete(contacts);
		
		//Then the flag should true
		assertNotNull(undeleted);
		assertTrue(1 == undeleted.size());
		Contact undeletedContact = undeleted.get(0);
		assertEquals(ContactTest.EMPTY, undeletedContact.getName());
		assertEquals(ContactTest.EMPTY, undeletedContact.getPhoneNumber());
		
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
	
	@Test
	public void whenIDProvidedThenRecordShouldBeUpdated() throws IOException {
		//Given populated DAO and updated contact
		givenPopulatedDAO();
		Contact orginalContact = ContactFixture.getWillsContact();
		Contact updatedContact = new Contact(orginalContact.getId(), orginalContact.getName(), 
				"0412000888");
		//When the update method called
		boolean flag = dao.update(updatedContact);
		//Then the flag should be true
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
		assertNotNull(line3);
		assertEquals("id=2,name=Will Pang,phoneNumber=0412000888", line3);
		String line4 = bufferedReader.readLine();
		assertNull(line4);
		bufferedReader.close();
	}
	
	@Test
	public void whenIDNotExistThenRecordShouldBeInserted() throws IOException {
		//Given populated DAO and updated contact
		givenPopulatedDAO();
		Contact updatedContact = new Contact(6, "Pamela Anderson",
				"0412000888");
		//When the update method called
		boolean flag = dao.update(updatedContact);
		//Then the flag should be true
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
		assertNotNull(line3);
		assertEquals("id=2,name=Will Pang,phoneNumber=0414345678", line3);
		String line4 = bufferedReader.readLine();
		assertNotNull(line4);
		assertEquals("id=6,name=Pamela Anderson,phoneNumber=0412000888", line4);
		String line5 = bufferedReader.readLine();
		assertNull(line5);
		bufferedReader.close();
	}
	
	private void givenPopulatedDAO()  {
		//Given the FileContactDAO provided
		dao = getFileContactDAO();
				
		//And populated with contacts
		givenFileContactDAOContacts();
	}
	
	private void givenFileContactDAOContacts() {
		List<Contact> contacts = ContactFixture.getListOfContacts();
		this.dao.add(contacts.get(0));
		this.dao.add(contacts.get(1));
		this.dao.add(contacts.get(2));
	}

	private FileContactDAO getFileContactDAO()  {
		return new FileContactDAO(AddressBook.DEFAULT_NAME);
	}
	
	/**
	 * More tests should be added for the negative test cases
	 * on file related operation, however, with any mocking
	 * framework, it is very different to simulate the exception
	 * raising during file operation.
	 */
}
