package au.com.reece.addressbook.persistent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import au.com.reece.addressbook.model.Contact;

/**
 * This class is the file persistence implementation
 * of ContactDAO interface.
 *
 */
public class FileContactDAO implements ContactDAO {

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

}
