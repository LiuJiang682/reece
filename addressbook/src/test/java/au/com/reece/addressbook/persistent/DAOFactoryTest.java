package au.com.reece.addressbook.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import au.com.reece.addressbook.persistent.DAOFactory;
import org.junit.Test;

/**
 * Test the DAOFactory class.
 *
 */
public class DAOFactoryTest {

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
		String name = "default";
		String daoType = "file";
		
		//When the createDAO Method called
		ContactDAO dao = DAOFactory.createDAO(name, daoType);
		
		//Then assert the contact dao is current type.
		assertNotNull(dao);
	}
}
