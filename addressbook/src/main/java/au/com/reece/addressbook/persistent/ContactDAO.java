package au.com.reece.addressbook.persistent;

import au.com.reece.addressbook.model.Contact;

public interface ContactDAO {

	public boolean add(Contact contact);
	public boolean close();
}
