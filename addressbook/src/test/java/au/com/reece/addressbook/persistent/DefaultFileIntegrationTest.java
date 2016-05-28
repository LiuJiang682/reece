package au.com.reece.addressbook.persistent;

import java.io.File;

import org.junit.AfterClass;

import au.com.reece.addressbook.AddressBook;

public class DefaultFileIntegrationTest {

	@AfterClass
	public static void clearUp() {
		File file = new File(AddressBook.DEFAULT_NAME);
		if (file.exists()) {
			file.delete();
		}
	}
}
