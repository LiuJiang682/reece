package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test the FileContactDAO class
 *
 */
public class FileContactDAOTest {

	@Test
	public void whenNullNameProvidedThenConstructorShouldThrowIllegalArgumentException() {
		//Given the name is null
		String name = null;
		
		//When the constructor is called
		try {
			new FileContactDAO(name);
			fail("Program reached unexpected point.");
		}
		catch (IllegalArgumentException e) {
			//Then IllegalArgumentException should raised.
			String message = e.getMessage();
			assertEquals("name cannot be null", message);
		}
	}
}
