package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import au.com.reece.addressbook.AddressBook;

/**
 * Test the DAOFactory class.
 *
 */
public class DAOFactoryTest extends DefaultFileIntegrationTest {
	
	@Test
	public void whenDaoTypeIsNullThenDAOFactoryThrowIllegalArgumentException() {
		//Given the name and dao type are both null
		String name = null;
		String daoType = "";
		
		//When the createDAO method called
		try {
			DAOFactory.createDAO(name, daoType);
			fail("Program reach unexpected point.");
		}
		catch (IllegalArgumentException e) {
			//Then the createDAO method should raise exception
			String message = e.getMessage();
			assertEquals("Unknown DAO type: ", message);
		}
	}
	
	@Test
	public void whenDaoTypeIsFileThenDAOFactoryShouldBeReturnContactDAO() {
		//Given the name and dao type
		String name = AddressBook.DEFAULT_NAME;
		String daoType = "file";
		
		//When the createDAO Method called
		ContactDAO dao = DAOFactory.createDAO(name, daoType);
		
		//Then assert the contact dao is current type.
		assertNotNull(dao);
	}
	
	@Test
	public void whenNameProvidedThenCreateFileDAOShouldBeReturnFileContactDAO() {
		//Given the name
		String name = "default";
		
		//When the createFileDAO method called
		ContactDAO dao = DAOFactory.createFileDAO(name);
		
		//Then assert the contact dao
		assertNotNull(dao);
		assertTrue(dao instanceof FileContactDAO);
	}
	
	/**
	 * There are should be more negative test for createFileDAO,
	 * however, it is too difficult to run unit test on file 
	 * related operations with a mocking framework.
	 */
}
