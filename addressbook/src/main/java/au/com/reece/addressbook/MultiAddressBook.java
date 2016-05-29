package au.com.reece.addressbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.persistent.FileContactDAO;
import au.com.reece.addressbook.utils.StringUtils;

/**
 * The main class for multi address book.
 *
 */
public class MultiAddressBook {

	private static final int MAX_ALL_CONTACTS = 500000;
	private static final String EMPTY = "";
	public static final String DEFAULT_ADDRESS_BOOK_LIST = "addressBookList";
	
	private List<AddressBook> addressBooks;

	/**
	 * Constructor -- It does 3 things:
	 *  1. Read the file for existing address books
	 *  2. Depends on the list is populated or not, it does 2 things:
	 *  	a. Create a default address book if the list is empty.
	 *  		It also populate the list when it created the default
	 *  		address book.
	 *  	b. Load the address books if the list is not empty.
	 * @throws IOException
	 */
	public MultiAddressBook() throws IOException {
		this.addressBooks = new ArrayList<>();
		List<String> names = readAddressBookList();

		if (names.isEmpty()) {
			// The first the application is loaded.
			AddressBook address = new AddressBook();
			addressBooks.add(address);
			writeAddressBookList(address.getName());
		}
		else {
			// Load the address books
			for (String name : names) {
				AddressBook address = new AddressBook(name, AddressBook.DEFAULT_DAO_TYPE_FILE);
				addressBooks.add(address);
			}
		}
	}

	protected static void writeAddressBookList(String name) throws IOException  {
		File file;
		BufferedWriter writer = null;
		
		try {
			file = new File(MultiAddressBook.DEFAULT_ADDRESS_BOOK_LIST);
			file.createNewFile(); 
			Path path = file.toPath();
			writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
			writer.write(name + FileContactDAO.LINE_SEPARATOR);
			writer.flush();
		}
		finally {
			if (null != writer) {
				writer.close();
			}
			file = null;
			writer = null;
		}
	}

	protected static List<String> readAddressBookList() {
		List<String> lines = new ArrayList<>();
		try {
			BufferedReader reader = Files.newBufferedReader(
					FileSystems.getDefault().getPath(EMPTY, DEFAULT_ADDRESS_BOOK_LIST), Charset.defaultCharset());

			String line = null;
			while ((line = reader.readLine()) != null)
				lines.add(line);
			reader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return lines;
	}

	public List<AddressBook> getAddressBooks() {
		return this.addressBooks;
	}

	/**
	 * Create a new address.
	 * 
	 * @param addressBookName the name
	 * @throws IOException
	 */
	public void createNewAddressBook(String addressBookName) throws IOException {
		AddressBook addressBook = new AddressBook(addressBookName, AddressBook.DEFAULT_DAO_TYPE_FILE);
		addressBooks.add(addressBook);
		writeAddressBookList(addressBookName);
	}

	/**
	 * Add a contact to named address books
	 * @param name the address book name
	 * @param contact the contact
	 * @return true if everything goes well.
	 */
	public boolean add(String name, Contact contact) {
		boolean success = true;
		boolean foundAddressBook = false;
		
		String addressBookName = doNameCheckOrUseDefault(name);
		
		for (AddressBook addressBook : addressBooks) {
			if (addressBookName.equals(addressBook.getName())) {
				foundAddressBook = true;
				success = addressBook.add(contact);
			}
		}
		return success && foundAddressBook;
	}

	/**
	 * Delete a contact from a named address book
	 * @param name the address book name
	 * @param contact the contact to delete
	 * @return true if everything goes well.
	 */
	public boolean delete(String name, Contact contact) {
		boolean success = true;
		boolean foundAddressBook = false;
		
		String addressBookName = doNameCheckOrUseDefault(name);
		
		for (AddressBook addressBook : addressBooks) {
			if (addressBookName.equals(addressBook.getName())) {
				foundAddressBook = true;
				success = addressBook.delete(contact);
			}
		}
		return success && foundAddressBook;
	}

	/**
	 * Retrieve the contacts of named address book.
	 * 
	 * @param name the address book name.
	 * @param max max of the list.
	 * @return a list of contacts
	 */
	public List<Contact> getAllContacts(String name, int max) {
		List<Contact> contacts = new ArrayList<>();
		String addressBookName = doNameCheckOrUseDefault(name);
		
		for (AddressBook addressBook : addressBooks) {
			if (addressBookName.equals(addressBook.getName())) {
				contacts = addressBook.getAllContacts(max);
			}
		}
		
		return contacts;
	}

	/**
	 * Retrieve all unique contacts from all address books.
	 * @return a list of unique contacts.
	 */
	public List<Contact> getAllUniqueContentAsOne() {
		List<Contact> contacts = new ArrayList<>();
		HashSet<Contact> uniqueContacts = new HashSet<>();
		for (AddressBook addressBook : addressBooks) {
			uniqueContacts.addAll(addressBook.getAllContacts(MAX_ALL_CONTACTS));
		}
		contacts.addAll(uniqueContacts);
		
		return contacts;
	}
	
	private String doNameCheckOrUseDefault(String name) {
		String addressBookName = name;
		if (StringUtils.isBlank(addressBookName)) {
			addressBookName = AddressBook.DEFAULT_NAME;
		}
		
		return addressBookName;
	}

}
