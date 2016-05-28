package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import au.com.reece.addressbook.AddressBook;
import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.model.ContactTest;

/**
 * Test the FileContactDAO class
 *
 */
public class FileContactDAOTest extends DefaultFileIntegrationTest {

	private File file;

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
	public void whenNullFileOutputStreamProvidedThenConstructorShouldThrowIllegalArgumentException() {
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
	public void whenContactProvidedThenAddShouldBeReturnTrue() {
		//Given the Contact provided
		Contact contact = new Contact(ContactTest.TEST_NAME, ContactTest.TEST_PHONE_NUMBER);
		FileContactDAO dao = getFileContactDAO();
		
		//When the add operation performed
		boolean flag = dao.add(contact);
		
		//Then assert the flag
		assertTrue(flag);
	}
	
	@Test
	public void whenCloseCalledThenTrueShouldReturn() {
		//Given the FileContactDAO provided
		FileContactDAO dao = getFileContactDAO();
		
		//When the close operation performed
		boolean flag = dao.close();
		
		//Then assert the flag
		assertTrue(flag);
	}
	
	private FileContactDAO getFileContactDAO() {
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

	private void getFile() throws IOException {
		if (null == file) {
			file = new File(AddressBook.DEFAULT_NAME);
			file.createNewFile();
		}
	}
	
	
}
