package au.com.reece.addressbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.reece.addressbook.fixture.ContactFixture;
import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.model.ContactTest;
import au.com.reece.addressbook.persistent.FileContactDAO;

/**
 * In order to keep track customer contacts
 * As a Reece branch manager
 * I want to maintain multiply address books.
 */
public class MultiAddressBookTest {

	private File file;
	private MultiAddressBook addressBooks;
	private Path path;

	@Before
	public void setUp() {
		file = null;
		addressBooks = null;
		path = null;
	}
	
	/**
	 * Given user login the PC
	 * When the user start the Multi-Address book app
	 * Then the Multi-Address book app is loaded.
	 * @throws IOException 
	 */
	@Test
	public void whenMultiAddressBookFirstStartedThenTheAppShouldReturnDefautlAddressBook() throws IOException {
		//Give the app first start
		
		givenAddressBooks();
		
		//Then the default address book should loaded.
		assertNotNull(addressBooks);
		List<AddressBook> addressBooksList = addressBooks.getAddressBooks();
		assertNotNull(addressBooksList);
		assertTrue(1 == addressBooksList.size());
		assertEquals("default", addressBooksList.get(0).getName());
		 
		clearUpFile();
	}
	
	/**
	 * Given the multiAddressBook does not exist
	 * When the readAddressBookList method called
	 * Then an empty list should returned.
	 */
	@Test
	public void whenMultiAddressBookNotExistThenEmptyListShouldReturned() {
		clearUpFile();
		
		//When the readAddressBookList method called.
		List<String> addressBookNames = MultiAddressBook.readAddressBookList();
		
		//Then an empty list should return
		assertNotNull(addressBookNames);
		assertTrue(addressBookNames.isEmpty());
	}
	
	/**
	 * Given the multiAddressBook exist
	 * When the readAddressBookList method called
	 * Then an empty list should returned.
	 * @throws IOException 
	 */
	@Test
	public void whenMultiAddressBookExistThenFullListShouldReturned() throws IOException {
		//Given the mulitAddressBook file does exist
		File file = new File(MultiAddressBook.DEFAULT_ADDRESS_BOOK_LIST);
		file.createNewFile();
		
		//When the readAddressBookList method called.
		List<String> addressBookNames = MultiAddressBook.readAddressBookList();
		
		//Then an empty list should return
		assertNotNull(addressBookNames);
		assertTrue(addressBookNames.isEmpty());
	}
	
	/**
	 * Given the populated multiAddressBook exist
	 * When the readAddressBookList method called
	 * Then a populated list should returned.
	 * @throws IOException 
	 */
	@Test
	public void whenPopulateMultiAddressBookExistThenFullListShouldReturned() throws IOException {
		givenAddressBookExist();
		
		//When the readAddressBookList method called.
		List<String> addressBookNames = MultiAddressBook.readAddressBookList();
		
		//Then an empty list should return
		assertNotNull(addressBookNames);
		assertTrue(2 == addressBookNames.size());
		assertEquals("default", addressBookNames.get(0));
		assertEquals("abc", addressBookNames.get(1));
		
		clearUpFile();
	}
	
	/**
	 * Given the populated multiAddressBook exist
	 * When the readAddressBookList method called
	 * Then a populated list should returned.
	 * @throws IOException 
	 */
	@Test
	public void whenPopulateMultiAddressBookExistThenFullListAddressBooksShouldReturned() throws IOException {
		givenAddressBookExist();
		
		givenAddressBooks();
		List<AddressBook> listOfAddressBooks = addressBooks.getAddressBooks();
		
		//Then an empty list should return
		assertNotNull(listOfAddressBooks);
		assertTrue(2 == listOfAddressBooks.size());
		assertEquals("default", listOfAddressBooks.get(0).getName());
		assertEquals("abc", listOfAddressBooks.get(1).getName());
		
		clearUpFile();
	}
	
	/**
	 * Given the name provided
	 * When the writeAddressBookList method called
	 * Then the name will append to the address book list
	 * @throws IOException 
	 */
	@Test
	public void whenNameProvidedThenAddressBookListShouldPopulated() throws IOException {
		MultiAddressBook.writeAddressBookList("abc");
		
		List<String> list = MultiAddressBook.readAddressBookList();
		assertNotNull(list);
		assertTrue(1 == list.size());
		assertEquals("abc", list.get(0));
		
		clearUpFile();
	}
	
	/**
	 * Given user login the PC
	 * When the user add a new address book
	 * Then a new address book is created
	 * @throws IOException 
	 */
	@Test
	public void whenNewAddressBookNameAddedThenNewAddressBookShouldReturn() throws IOException {
		//Given a new address book name
		String addressBookName = "MLC";
		givenAddressBooks();
		
		//When add the new name to MultiAddressBook
		addressBooks.createNewAddressBook(addressBookName);
		
		//Then new address book created
		List<AddressBook> addressBooksList = addressBooks.getAddressBooks();
		assertNotNull(addressBooksList);
		assertTrue(2 == addressBooksList.size());
		
		List<String> addressBookNames = MultiAddressBook.readAddressBookList();
		assertNotNull(addressBookNames);
		assertTrue(2 == addressBookNames.size());
		assertEquals("default", addressBookNames.get(0));
		assertEquals("MLC", addressBookNames.get(1));
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When add method called
	 * Then the contact should be able to add the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenAddressBookNameAndContactProvidedThenContactShouldCreated() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		
		//When add method called
		boolean flag = addressBooks.add("abc", contact);
		
		//Then a contact should created
		assertTrue(flag);
		File abc = new File("abc");
		Path path = abc.toPath();
		BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
		String line1 = reader.readLine();
		assertEquals("name=John Smith,phoneNumber=0414123456", line1);
		String line2 = reader.readLine();
		assertNull(line2);
		abc.delete();
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenAddressBookNameAndContactProvidedThenContactShouldDeleted() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		File abc = givenABCContent(contact);
		
		//When delete method called
		boolean deleteFlag = addressBooks.delete("abc", contact);
		
		//Then a contact should created
		assertTrue(deleteFlag);
		BufferedReader reader1 = Files.newBufferedReader(path, Charset.defaultCharset());
		String line3 = reader1.readLine();
		assertNull(line3);
		abc.delete();
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenAddressBookNameAndContactProvidedThenContactListShouldReturn() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		File abc = givenABCContent(contact);
		
		//When delete method called
		List<Contact> contacts = addressBooks.getAllContacts("abc", 5);
		
		//Then a list of contacts should return
		assertNotNull(contacts);
		assertTrue(1 == contacts.size());
		assertEquals(ContactTest.TEST_NAME, contacts.get(0).getName());
		assertEquals(ContactTest.TEST_PHONE_NUMBER, contacts.get(0).getPhoneNumber());
		abc.delete();
		
		clearUpFile();
	}

	
	
	/**
	 * Given address book name and a contact
	 * When add method called
	 * Then the contact should be able to add the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNullAddressBookNameAndContactProvidedThenContactShouldCreatedToDefault() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		
		File abc = givenDefaultContent(contact);
		abc.delete();
		
		clearUpFile();
	}
	
	/**
	 * Given NO address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNullAddressBookNameAndContactProvidedThenContactShouldDeleted() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		File def = givenDefaultContent(contact);
		
		//When delete method called
		boolean deleteFlag = addressBooks.delete(null, contact);
		
		//Then a contact should created
		assertTrue(deleteFlag);
		BufferedReader reader1 = Files.newBufferedReader(path, Charset.defaultCharset());
		String line3 = reader1.readLine();
		assertNull(line3);
		def.delete();
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNullAddressBookNameAndContactProvidedThenContactListShouldReturn() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		File def = givenDefaultContent(contact);
		
		//When delete method called
		List<Contact> contacts = addressBooks.getAllContacts(null, 5);
		
		//Then a list of contacts should return
		assertNotNull(contacts);
		assertTrue(1 == contacts.size());
		assertEquals(ContactTest.TEST_NAME, contacts.get(0).getName());
		assertEquals(ContactTest.TEST_PHONE_NUMBER, contacts.get(0).getPhoneNumber());
		def.delete();
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When add method called
	 * Then the contact should be able to add the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNoExistAddressBookNameAndContactProvidedThenContactShouldNOTCreatedToDefault() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		
		//When add method called
		boolean flag = addressBooks.add("ddd", contact);
		
		//Then a contact should created
		assertFalse(flag);
		
		clearUpFile();
	}
	
	/**
	 * Given no exist address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNoExistAddressBookNameAndContactProvidedThenContactShouldDeleted() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		Contact contact = ContactFixture.getDefaultContact();
		givenAddressBooks();
		
		//When delete method called
		boolean deleteFlag = addressBooks.delete("ddd", contact);
		
		//Then a contact should created
		assertFalse(deleteFlag);
		
		clearUpFile();
	}
	
	/**
	 * Given address book name and a contact
	 * When delete method called
	 * Then the contact should be able to delete the correct address book
	 * @throws IOException 
	 */
	@Test
	public void whenNoExistAddressBookNameAndContactProvidedThenContactListShouldReturn() throws IOException {
		//Given the address name and a contact
		givenAddressBookExist();
		givenAddressBooks();
		
		//When delete method called
		List<Contact> contacts = addressBooks.getAllContacts("ddd", 5);
		
		//Then a list of contacts should return
		assertNotNull(contacts);
		assertTrue(contacts.isEmpty());
		
		clearUpFile();
	}
	
	/**
	 * Given app loaded
	 * When the getAllUniqueContactAsOne method called
	 * Then the contact list with all unique contact from multiply
	 *      address book returns as one list.
	 * 
	 * @return list of unique contacts.
	 * @throws IOException 
	 */
	@Test
	public void whenGetAllUniqueContentAsOneCalledThenListShouldReturn() throws IOException {
		//Given multiply address book exist and populated
		givenAddressBookExist();
		givenAddressBooks();
		addressBooks.add(null, ContactFixture.getDefaultContact());
		addressBooks.add(null, ContactFixture.getRichardsContact());
		addressBooks.add(null, ContactFixture.getWillsContact());
		addressBooks.add("abc", ContactFixture.getDefaultContact());
		addressBooks.add("abc", new Contact("David Goldsmith", "0414666777"));
		
		//When getAllUniqueContentAsOne method called
		List<Contact> contacts = addressBooks.getAllUniqueContentAsOne();
		
		//Then the list should contains 4 contacts
		assertNotNull(contacts);
		assertTrue(4 == contacts.size());
		Contact contact1 = contacts.get(0);
		assertEquals("Richard Jones", contact1.getName());
		assertEquals("0414789012", contact1.getPhoneNumber());
		Contact contact2 = contacts.get(1);
		assertEquals("Will Pang", contact2.getName());
		assertEquals("0414345678", contact2.getPhoneNumber());
		Contact contact3 = contacts.get(2);
		assertEquals("David Goldsmith", contact3.getName());
		assertEquals("0414666777", contact3.getPhoneNumber());
		Contact contact4 = contacts.get(3);
		assertEquals("John Smith", contact4.getName());
		assertEquals("0414123456", contact4.getPhoneNumber());
	}

	private File givenDefaultContent(Contact contact) throws IOException {
		boolean flag = addressBooks.add(null, contact);
		assertTrue(flag);
		File def = new File("default");
		path = def.toPath();
		BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
		String line1 = reader.readLine();
		assertEquals("name=John Smith,phoneNumber=0414123456", line1);
		String line2 = reader.readLine();
		assertNull(line2);
		return def;
	}
	
	private void givenAddressBooks() throws IOException {
		addressBooks = new MultiAddressBook();
	}

	private void givenAddressBookExist() throws IOException {
		file = new File(MultiAddressBook.DEFAULT_ADDRESS_BOOK_LIST);
		file.createNewFile(); 
		Path path = file.toPath();
		BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.WRITE);
		writer.write("default" + FileContactDAO.LINE_SEPARATOR);
		writer.write("abc" + FileContactDAO.LINE_SEPARATOR);
		writer.flush();
		writer.close();
	}
	
	private void clearUpFile() {
		File file = new File(MultiAddressBook.DEFAULT_ADDRESS_BOOK_LIST);
		if (file.exists()) {
			file.delete();
		}
	}
	
	private File givenABCContent(Contact contact) throws IOException {
		boolean flag = addressBooks.add("abc", contact);
		assertTrue(flag);
		File abc = new File("abc");
		path = abc.toPath();
		BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
		String line1 = reader.readLine();
		assertEquals("name=John Smith,phoneNumber=0414123456", line1);
		String line2 = reader.readLine();
		assertNull(line2);
		return abc;
	}
}
