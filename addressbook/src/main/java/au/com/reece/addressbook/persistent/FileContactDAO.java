package au.com.reece.addressbook.persistent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

	private static final int ONE = 1;

	private static final int ZERO = 0;

	private static final String PHONE_NUMBER_TAG = "phoneNumber=";

	private static final String NAME_TAG = "name=";

	private static final String EMPTY = "";

	private static final String FIELD_SEPARATOR = ",";

	private static final String CHAR_SET_UTF_8 = "UTF-8";

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private FileInputStream fis;
	private FileOutputStream fos;
	private String fileName;
	private File file;
	
	public FileContactDAO(final String fileName) {
		if (StringUtils.isBlank(fileName)) {
			throw new IllegalArgumentException("file name cannot be null!");
		}
		
		this.fileName = fileName;
		try {
			file = new File(this.fileName);
			file.createNewFile();
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(file);
			this.fis = fis;
			this.fos = fos;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
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

	/**
	 * This method returns a list of contacts up to the size
	 * of max.
	 * @param max the max size of list.
	 */
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

	/**
	 * This method deletes one record from file. 
	 * 
	 * @param contact the contact need to delete.
	 */
	@Override
	public boolean delete(Contact contact) {
		boolean success = true;
		boolean deleted = false;
		try {
			String tempFileName = fileName + System.currentTimeMillis();
			File tempFile = new File(tempFileName); 
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, CHAR_SET_UTF_8));
			
			String lineToDelete = contact.toString();
			String currentLine;
			while(null != (currentLine = bufferedReader.readLine())) {
				String trimmedLine = currentLine.trim();
				if (trimmedLine.equals(lineToDelete)) {
					deleted = true;
					continue;
				}
				writer.write(currentLine + LINE_SEPARATOR);
			}
			
			//Done! Clean up resource. 
			writer.flush();
			writer.close();
			bufferedReader.close();
			fis.close();
			
			// Replace the old file with temp file.
			success = file.delete();
			if (success) {
				success = tempFile.renameTo(file);
			}
			// Points the file and inputStream to
			// the new file.
			if (success) {
				file = new File(fileName);
				fis = new FileInputStream(file);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success && deleted;
	}

	@Override
	public List<Contact> delete(List<Contact> contacts) {
		List<Contact> undeleted = new ArrayList<>();
		
		for (Contact contact : contacts) {
			boolean success = this.delete(contact);
			if (!success) {
				undeleted.add(contact);
			}
		}
		return undeleted;
	}
}
