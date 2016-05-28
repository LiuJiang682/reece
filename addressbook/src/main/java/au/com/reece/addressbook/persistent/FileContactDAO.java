package au.com.reece.addressbook.persistent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.reece.addressbook.model.Contact;
import au.com.reece.addressbook.utils.StringUtils;

/**
 * This class is the file persistence implementation
 * of ContactDAO interface.
 *
 */
public class FileContactDAO implements ContactDAO {

	private static final int DEFAULT_MAX_SIZE = 3000;

	private static final int ONE = 1;

	private static final int ZERO = 0;

	private static final String PHONE_NUMBER_TAG = "phoneNumber=";

	private static final String NAME_TAG = "name=";

	private static final String EMPTY = "";

	private static final String FIELD_SEPARATOR = ",";

	private static final String CHAR_SET_UTF_8 = "UTF-8";

	private static final String LINE_SEPARATOR = "\n";
	
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public FileContactDAO(final FileInputStream fis, final FileOutputStream fos) {
		if (null == fis) {
			throw new IllegalArgumentException("fis cannot be null!");
		}
		if (null == fos) {
			throw new IllegalArgumentException("fos cannot be null!");
		}
		this.fis = fis;
		this.fos = fos;
	}

	/**
	 * This operation adds a new contact into the persistent layer.
	 * 
	 * @param contact the new contact.
	 */
	@Override
	public boolean add(Contact contact) {
		boolean success = true;
		
		String contactString = contact.toString();
		contactString += LINE_SEPARATOR;
		byte[] data = contactString.getBytes();
		try {
			fos.write(data);
			fos.flush();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public FileOutputStream getFos() {
		return fos;
	}

	/**
	 * This operation closes all input/output streams.
	 */
	@Override
	public boolean close() {
		if (null != fis) {
			try {
				fis.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (null != fos) {
			try {
				fos.close();
			}
			catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Contact> getAllContacts(final int max) {
		List<Contact> contacts = new ArrayList<>(max);
		
		
		List<String> contents = getFileContent(max);
		doContactConversion(contacts, contents);

		return contacts;
	}

	protected void doContactConversion(List<Contact> contacts, List<String> contents) {
		for (String content : contents) {
			String[] strings = content.split(FIELD_SEPARATOR);
			String name = strings[ZERO].trim();
			String phoneNumber = strings[ONE].trim(); 
			name = name.replace(NAME_TAG, EMPTY); 
			phoneNumber = phoneNumber.replace(PHONE_NUMBER_TAG, EMPTY); 
			Contact contact = new Contact(name, phoneNumber);
			contacts.add(contact);
		}
	}

	protected List<String> getFileContent(final int max) {
		List<String> contents = new ArrayList<>(max);
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, CHAR_SET_UTF_8));
			String line = bufferedReader.readLine();
			while((!StringUtils.isBlank(line)) && (contents.size() < max)){
				contents.add(line);
				line = bufferedReader.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

	@Override
	public boolean delete(Contact contact, int listLimit) {
		int size = listLimit;
		if (size <= 0) {
			size = DEFAULT_MAX_SIZE;
		}
		List<Contact> allContacts = getAllContacts(listLimit);
		boolean hasMoreRecord = size == allContacts.size();
		do {
			boolean successRemoved = allContacts.remove(contact);
			if (successRemoved) {
				//Found the exactly matched.
				
			}
		}
		while(hasMoreRecord);
		return false;
	}
}
