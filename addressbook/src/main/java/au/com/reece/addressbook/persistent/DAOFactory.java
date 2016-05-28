package au.com.reece.addressbook.persistent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * This factory products the DAO object for the address
 * book. 
 * It could refactor into a hash map with pre-loaded class
 * names if the DAO type is more a few so it is not anti-pattern.
 *
 */
public class DAOFactory {

	private static final String DAO_TYPE_FILE = "file";

	public static ContactDAO createDAO(String name, String daoType) {
		ContactDAO dao = null;
		
		/**
		 * Constructs the DAO object.
		 */
		switch (daoType) {
			case DAO_TYPE_FILE:
				dao = createFileDAO(name);
				break;
				
			default:
				throw new IllegalArgumentException("Unknown DAO type: " + daoType);
		}
		
		return dao;
	}

	protected static ContactDAO createFileDAO(String name) {
		FileContactDAO dao = null;
		
		try {
			File file = new File(name);
			file.createNewFile();
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(file, true);
			dao = new FileContactDAO(fis, fos);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
		return dao;
	}

}
