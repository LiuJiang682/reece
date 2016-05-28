package au.com.reece.addressbook.persistent;

import java.util.List;

import au.com.reece.addressbook.model.Contact;

public interface ContactDAO {

	public boolean add(final Contact contact);
	public boolean close();
	public List<Contact> getAllContacts(final int max);
	public boolean delete(final Contact contact, final int listLimit);
}
