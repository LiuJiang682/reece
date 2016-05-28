package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
//public class FileContactDAOTest extends DefaultFileIntegrationTest {
public class FileContactDAOTest {
	private File file;
	private FileContactDAO dao;
	
	@Before
	public void setUp() {
		file = null;
		dao = null;
	}

	@Test
	public void whenNullFileInputStreamProvidedThenConstructorShouldThrowIllegalArgumentException() {
		//Given the null FileInputStream and FileOutputStream
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		//When the constructor is called
		try {
			new FileContactDAO(fis, fos);
			fail("Program reached unexpected point.");
		}
		catch (IllegalArgumentException e) {
			//Then assert the IllegalArgumentException
			String message = e.getMessage();
			assertEquals("fis cannot be null!", message);
		}
	}
	
	@Test
	public void whenNullFileOutputStreamProvidedThenConstructorShouldThrowIllegalArgumentException()  {
		//Given the null FileInputStream and FileOutputStream
		FileInputStream fis = getFileInputStream();
		FileOutputStream fos = null;
		
		//When the constructor is called
		try {
			new FileContactDAO(fis, fos);
			fail("Program reached unexpected point.");
		}
		catch (IllegalArgumentException e) {
			//Then assert the IllegalArgumentException
			String message = e.getMessage();
			assertEquals("fos cannot be null!", message);
		}
	}

	@Test
	public void whenContactProvidedThenAddShouldBeReturnTrue()  {
		//Given the Contact provided
		Contact contact = new Contact(ContactTest.TEST_NAME, ContactTest.TEST_PHONE_NUMBER);
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
		assertEquals("John Smith", contact1.getName());
		assertEquals("0414123456", contact1.getPhoneNumber());
		Contact contact2 = contacts.get(1);
		assertNotNull(contact2);
		assertEquals("Richard Jones", contact2.getName());
		assertEquals("0414789012", contact2.getPhoneNumber());
	}
	
	@Test
	public void whenFullContactProvidedThenDeleteShouldReturnTrue() {
		//Given populated DAO and full contact
		givenPopulatedDAO();
		Contact contact = ContactFixture.getDefaultContact();
		
		//When delete method called
		boolean flag = this.dao.delete(contact, 5);
		
		//Then the flag should true
		assertTrue(flag);
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
		FileInputStream fis = getFileInputStream();
		FileOutputStream fos = getFileOutputStream();
		return new FileContactDAO(fis, fos);
	}

	private FileOutputStream getFileOutputStream() {
		FileOutputStream fos = null;
		try {
			getFile();
			fos = new FileOutputStream(file);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fos;
	}

	private FileInputStream getFileInputStream() {
		FileInputStream fis = null;
		try {
			getFile();
			fis = new FileInputStream(file);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	private void getFile() throws IOException  {
		if (null == file) {
			file = new File(AddressBook.DEFAULT_NAME);
			file.createNewFile();
		}
	}
	
	
}
