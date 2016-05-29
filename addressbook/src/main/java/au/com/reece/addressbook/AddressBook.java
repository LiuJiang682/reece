package au.com.reece.addressbook;

import java.util.List;

import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.persistent.ContactDAO;
import au.com.reece.addressbook.persistent.DAOFactory;
import au.com.reece.addressbook.utils.StringUtils;

/**
 * This is the main class of address book. It is like
 * a service class provides all required functionalities.
 *
 */
public class AddressBook {

	public static final String DEFAULT_NAME = "default";

	public static final String DEFAULT_DAO_TYPE_FILE = "file";
	
	private String name;
	private String daoType;
	private ContactDAO dao;
	
	// Default constructor
	public AddressBook() {
		this(null, null);
	}

	/**
	 * The real constructor.
	 * @param name the name for the address book. 
	 * 			Default name is "default"
	 * @param daoType the DAO type for address book.
	 * 			Default type is file.
	 * 			This parameter can be expand to an Enum type if 
	 * 			the DAO type becomes more than a few. 
	 */
	public AddressBook(String name, String daoType) {
		/**
		 * The StringUtils is a replacement
		 * for apache's StringUtils class since
		 * no framework is allow to use.
		 */
		if (StringUtils.isBlank(name)) {
			this.name = DEFAULT_NAME;
		}
		else {
			this.name = name;
		}
		
		if (StringUtils.isBlank(daoType)) {
			this.daoType = DEFAULT_DAO_TYPE_FILE;
		}
		else {
			this.daoType = daoType;
		}
		
		this.dao = DAOFactory.createDAO(this.name, this.daoType);
	}

	/**
	 * Add a new contact into the address book.
	 * @param contact new contact.
	 * @return true if everything works.
	 */
	public boolean add(Contact contact) {
		return this.dao.add(contact);
	}

	public List<Contact> getAllContacts(final int max) {
		return this.dao.getAllContacts(max);
	}

	public String getName() {
		return name;
	}

	public ContactDAO getDao() {
		return dao;
	}

	public Object getDaoType() {
		return daoType;
	}

	/**
	 * Adds a list of contacts into address book.
	 * @param contacts list of contacts
	 * @return true if everything goes well.
	 */
	public boolean add(List<Contact> contacts) {
		for (Contact contact : contacts) {
			boolean flag = this.add(contact);
			if (!flag) {
				return flag;
			}
		}
		return true;
	}

	/**
	 * Delete a contact from address book.
	 * @param contact the contact needs to delete.
	 * @return true if everything goes well.
	 */
	public boolean delete(Contact contact) {
		return this.dao.delete(contact);
	}

	/**
	 * Delete a list of contacts from address book.
	 * Returns any contact failed to delete.
	 * 
	 * @param contacts list of contact needs to delete.
	 * @return any contacts delete operation failed.
	 */
	public List<Contact> delete(List<Contact> contacts) {
		return this.dao.delete(contacts);
	}

}
