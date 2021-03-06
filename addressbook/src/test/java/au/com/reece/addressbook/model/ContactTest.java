package au.com.reece.addressbook.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import au.com.reece.addressbook.fixture.ContactFixture;

/**
 * Test the Contact class.
 *
 */
public class ContactTest {

	private static final int ZERO = 0;
	private static final int NOT_INIT = -1;
	public static final String TEST_PHONE_NUMBER = "0414123456";
	public static final String TEST_NAME = "John Smith";
	public static final String EMPTY = "";
	public static final long TEST_ID = ZERO;
	
	private long id;
	private String name;
	private String phoneNumber;
	private Contact contact;
	private Contact contact1;
	private Contact contact2;
	private int hashCode1;
	private int hashCode2;
	private String string1;
	private String string2;
	
	@Before
	public void setUp() {
		id = NOT_INIT;
		name = null;
		phoneNumber = null;
		contact = null;
		contact1 = null;
		contact2 = null;
		hashCode1 = NOT_INIT;
		hashCode2 = NOT_INIT;
		string1 = null;
		string2 = null;
	}

	@Test
	public void whenBothNameAndPhoneNumberAssignedThenConstructorShouldBeCorrectlyAssignField() {
		//Given the id, name and phone number.
		id = TEST_ID;
		name = TEST_NAME;
		phoneNumber = TEST_PHONE_NUMBER;
		 
		//When call constructor.
		contact = new Contact(id, name, phoneNumber);
		
		//Then all fields should correctly assigned.
		assertNotNull(contact);
		assertEquals(TEST_ID, contact.getId());
		assertEquals(TEST_NAME, contact.getName());
		assertEquals(TEST_PHONE_NUMBER, contact.getPhoneNumber());
	}

	
	@Test
	public void whenNameIsNullThenConstructorShouldRetunNameNull() {
		//Given null name and a test phone number.
		name = null;
		phoneNumber = TEST_PHONE_NUMBER;
		 
		//When call constructor.
		contact = new Contact(TEST_ID, name, phoneNumber);
		
		//Then name should returns null.
		assertNotNull(contact);
		assertEquals(TEST_ID, contact.getId());
		assertNull(contact.getName());
		assertEquals(TEST_PHONE_NUMBER, contact.getPhoneNumber());
	}
	
	@Test
	public void whenPhoneNumberIsNullThenConstructorShouldRetunPhoneNumberNull() {
		//Given a test name and null phone number.
		name = TEST_NAME;
		phoneNumber = null;
		 
		//When call constructor.
		contact = new Contact(TEST_ID, name, phoneNumber);
		
		//Then phone number should returns null.
		assertNotNull(contact);
		assertEquals(TEST_ID, contact.getId());
		assertEquals(TEST_NAME, contact.getName());
		assertNull(contact.getPhoneNumber());
	}
	
	@Test
	public void whenBothNameAndPhoneNumberAreNullThenConstructorShouldRetunBothFieldsNull() {
		//Given the name and phone number.
		name = null;
		phoneNumber = null;
		 
		//When call constructor.
		contact = new Contact(TEST_ID, name, phoneNumber);
		
		//Then all fields should return null.
		assertNotNull(contact);
		assertEquals(TEST_ID, contact.getId());
		assertNull(contact.getName());
		assertNull(contact.getPhoneNumber());
	}
	
	@Test
	public void whenSameObjectAsParameterThenEqualsShouldReturnsTrue() {
		// Given one contact object
		contact = ContactFixture.getDefaultContact();
		
		//When equals method called.
		boolean sameObject = contact.equals(contact);
		
		//Then the flage should be true.
		assertTrue(sameObject);
	}
	
	@Test
	public void whenNullAsParameterThenEqualsShouldReturnsFalse() {
		//Given one contact object and one null object.
		contact = ContactFixture.getDefaultContact();
		contact1 = null;
				
		//When equals method called.
		boolean c1SameAsC2 = contact.equals(contact1);
				
		//Then the flag should be false.
		assertFalse(c1SameAsC2);
	}
	
	@Test
	public void whenObjectAsParameterThenEqualsShouldReturnsFalse() {
		//Given one contact object and one null object.
		contact = ContactFixture.getDefaultContact();
		Object contact2 = new Object();
				
		//When equals method called.
		boolean c1SameAsC2 = contact.equals(contact2);
				
		//Then the flag should be false.
		assertFalse(c1SameAsC2);
	}
	
	@Test
	public void whenTwoContactsHaveSameIDThenEqualsShouldBeSymmetric() {
		givenTwoContactHaveSameContent();
		
		//When equals method called.
		boolean c1SameAsC2 = contact1.equals(contact2);
		boolean c2SameAsC1 = contact2.equals(contact1);
		
		//Then both flags should be true.
		assertTrue(c1SameAsC2);
		assertTrue(c2SameAsC1);
	}
	
	@Test
	public void whenTwoContactsHaveAllNullFieldsThenEqualsShouldBeSymmetric() {
		givenTwoContactHaveAllNullField();
		
		//When equals method called.
		boolean c1SameAsC2 = contact1.equals(contact2);
		boolean c2SameAsC1 = contact2.equals(contact1);
		
		//Then both flags should be true.
		assertTrue(c1SameAsC2);
		assertTrue(c2SameAsC1);
	}
	
	@Test
	public void whenTwoContactsHaveDifferentIDThenEqualsShouldReturnFalse() {
		//Given two contact objects with different contents.
		contact1 = ContactFixture.getDefaultContact();
		contact2 = new Contact(1, EMPTY, EMPTY);
				
		//When equals method called.
		boolean c1SameAsC2 = contact1.equals(contact2);
		boolean c2SameAsC1 = contact2.equals(contact1);
				
		//Then both flags should be false.
		assertFalse(c1SameAsC2);
		assertFalse(c2SameAsC1);
	}
	
	@Test
	public void whenTwoContactsHaveDifferentNullFieldsAndDifferentIDThenEqualsShouldReturnFalse() {
		contact1 = new Contact(TEST_ID, TEST_NAME, null);
		contact2 = new Contact(1, null, TEST_PHONE_NUMBER);
				
		//When equals method called.
		boolean c1SameAsC2 = contact1.equals(contact2);
		boolean c2SameAsC1 = contact2.equals(contact1);
				
		//Then both flags should be false.
		assertFalse(c1SameAsC2);
		assertFalse(c2SameAsC1);
	}
	
	@Test
	public void whenTwoContactsHaveDifferentNullFieldsAndSameIDThenEqualsShouldReturnTrue() {
		contact1 = new Contact(TEST_ID, TEST_NAME, null);
		contact2 = new Contact(TEST_ID, null, TEST_PHONE_NUMBER);
				
		//When equals method called.
		boolean c1SameAsC2 = contact1.equals(contact2);
		boolean c2SameAsC1 = contact2.equals(contact1);
				
		//Then both flags should be false.
		assertTrue(c1SameAsC2);
		assertTrue(c2SameAsC1);
	}
	
	@Test
	public void whenTwoContactsHaveSameContentThenHashCodeShouldReturnSame() {
		//Given two contact objects with same contents.
		givenTwoContactHaveSameContent();
		
		//When hash code called and returns same value.
		whenHashCodeCalledAndReturnsSame();
	}
	
	@Test
	public void whenTwoContactsHaveNullFieldsThenHashCodeShouldBeSame() {
		//Given two contact objects with same contents.
		givenTwoContactHaveAllNullField();
						
		//When hash code called and returns same value.
		whenHashCodeCalledAndReturnsSame();
	}
	
	@Test
	public void whenTwoContactsHaveDifferentContentsAndIDThenHashCodeShouldReturnDifferent() {
		//Given two contact objects with different contents.
		contact1 = ContactFixture.getDefaultContact();
		contact2 = new Contact(1, EMPTY, EMPTY);
		
		//when hash code called and returns different hash code.
		whenHashCodeCalledAndReturnsDifferent();
	}
	
	@Test
	public void whenTwoContactsHaveOneDifferentNullFieldsThenHashCodeShouldReturnDifferent() {
		// Given two contact object with different null field.
		contact1 = new Contact(ZERO, TEST_NAME, null);
		contact2 = new Contact(1, null, TEST_PHONE_NUMBER);
		
		//when hash code called and returns different hash code.
		whenHashCodeCalledAndReturnsDifferent();
	}
	
	@Test
	public void whenTwoContactsHaveOneDifferentNullFieldsSameIDThenHashCodeShouldReturnDifferent() {
		// Given two contact object with different null field.
		contact1 = new Contact(ZERO, TEST_NAME, null);
		contact2 = new Contact(ZERO, null, TEST_PHONE_NUMBER);
		
		//when hash code called and returns different hash code.
		whenHashCodeCalledAndReturnsSame();
	}
	
	@Test
	public void whenTwoContactsHaveSameContentThenToStringShouldReturnSame() {
		//Give tow contact objects with same content.
		givenTwoContactHaveSameContent();
		
		//When the toString is called.
		whenToStringCalled(); 
		
		//Then the string content should be same.
		assertStringNotNull();
		assertEquals(string1, string2);
		assertStringContent();
		assertEquals("id=0,name=John Smith,phoneNumber=0414123456", string1);
		assertEquals("id=0,name=John Smith,phoneNumber=0414123456", string2);
	}
	
	@Test
	public void whenTwoContactsHaveAllNullFieldsToStringShouldReturnSame() {
		givenTwoContactHaveAllNullField();
		
		//When the toString is called.
		whenToStringCalled(); 
		
		//Then the string content should be same.
		assertStringNotNull();
		assertEquals(string1, string2);
		assertStringContent();
		assertEquals("id=0,name=null,phoneNumber=null", string1);
		assertEquals("id=0,name=null,phoneNumber=null", string2);
	}
	
	@Test
	public  void whenTwoContactsHaveDifferentNullFieldToStringShouldReturnDifferent() {
		// Given two contact object with different null field.
		givenTwoContactsWithDifferentNullField();
		
		//When the toString is called.
		whenToStringCalled(); 
				
		//Then the string content should be same.
		assertStringNotNull();
		assertFalse(string1.equals(string2));
		assertStringContent();
		assertEquals("id=0,name=John Smith,phoneNumber=null", string1);
		assertEquals("id=0,name=null,phoneNumber=0414123456", string2);
	}

	private void assertStringContent() {
		assertTrue(string1.contains("id="));
		assertTrue(string1.contains("name="));
		assertTrue(string1.contains("phoneNumber="));
		assertTrue(string2.contains("id="));
		assertTrue(string2.contains("name="));
		assertTrue(string2.contains("phoneNumber="));
	}

	private void assertStringNotNull() {
		assertNotNull(string1);
		assertNotNull(string2);
	}

	private void whenToStringCalled() {
		string1 = contact1.toString(); 
		string2 = contact2.toString();
	}

	private void givenTwoContactsWithDifferentNullField() {
		contact1 = new Contact(ZERO, TEST_NAME, null);
		contact2 = new Contact(ZERO, null, TEST_PHONE_NUMBER);
	}

	private void whenHashCodeCalledAndReturnsDifferent() {
		//When hash code method called.
		whenHashCodeCalled();
				
		// assert 
		assertHashCodeModified();
		assertFalse(hashCode1 == hashCode2);
	}

	private void whenHashCodeCalledAndReturnsSame() {
		//When hash code called.
		whenHashCodeCalled();
		
		//Then both hash code should be same.
		assertHashCodeModified();
		assertTrue(hashCode1 == hashCode2);
	}
	
	private void assertHashCodeModified() {
		assertTrue(NOT_INIT != hashCode1);
		assertTrue(NOT_INIT != hashCode2);
	}

	private void whenHashCodeCalled() {
		//When hashCode method called.
		hashCode1 = contact1.hashCode();
		hashCode2 = contact2.hashCode();
	}


	private void givenTwoContactHaveAllNullField() {
		contact1 = new Contact(ZERO, null, null);
		contact2 = new Contact(ZERO, null, null);
	}


	private void givenTwoContactHaveSameContent() {
		contact1 = ContactFixture.getDefaultContact();
		contact2 = ContactFixture.getDefaultContact();
	}
	
}
