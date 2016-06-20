package au.com.reece.addressbook.persistent.spring;

import java.util.HashMap;
import java.util.Map;

public class AddressRepository {

	private Map<String, String> repository = new HashMap<>();
	
	public Map<String, String> getRepository() {
		return this.repository;
	}

}
