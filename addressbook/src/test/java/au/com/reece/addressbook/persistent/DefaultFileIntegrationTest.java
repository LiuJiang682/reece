package au.com.reece.addressbook.persistent;

import java.io.File;

import org.junit.AfterClass;

import au.com.reece.addressbook.AddressBook;

public class DefaultFileIntegrationTest {

	private static final CharSequence EMPTY = "";

	@AfterClass
	public static void clearUp() {
		File file = new File(AddressBook.DEFAULT_NAME); 
		String parentString = file.getAbsolutePath().replace(AddressBook.DEFAULT_NAME, EMPTY);
		File parent = new File(parentString);
		File[] toBeDeleted = parent.listFiles();
		if (null != toBeDeleted) {
			for (File f : parent.listFiles()) {
				if (f.getName().startsWith(AddressBook.DEFAULT_NAME)) {
					f.delete(); 
				}
			}
		}
	}
}
