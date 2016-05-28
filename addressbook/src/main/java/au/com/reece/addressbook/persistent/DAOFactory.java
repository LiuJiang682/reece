package au.com.reece.addressbook.persistent;

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
		FileContactDAO dao = new FileContactDAO(name);
		return dao;
	}

}
