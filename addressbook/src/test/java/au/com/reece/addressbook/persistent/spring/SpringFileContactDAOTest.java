package au.com.reece.addressbook.persistent.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringFileContactDAO.class, loader = AnnotationConfigContextLoader.class)
public class SpringFileContactDAOTest {

	@Autowired
	private SpringFileContactDAO springFileContactDAO;
	
	@Test
	public void testSpringFileContactDAO() {
		assertTrue(
				this.springFileContactDAO.getClass().toString().startsWith("class au.com.reece.addressbook.persistent.spring.SpringFileContactDAO"));
	}
	
	//Given the user access the springFileContactDAO
	//When the getContacts method called
	//Then a populated contact map return
	@Test
	public void whenGetContactsCalledThenPopulateMapReturn() {
		assertEquals("abc", this.springFileContactDAO.getFirst());
		//Given the user access the springFileContactDAO
		//When the getContacts method called
		Map<String, String> contacts = this.springFileContactDAO.getContacts();
		//Then the contact map is populated
		assertNotNull(contacts);
		System.out.println(contacts);
		assertFalse(contacts.isEmpty());
	}
}
