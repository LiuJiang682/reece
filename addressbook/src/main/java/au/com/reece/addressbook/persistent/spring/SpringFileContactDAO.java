package au.com.reece.addressbook.persistent.spring;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@EnableAutoConfiguration
@EnableConfigurationProperties
public class SpringFileContactDAO {

//	@Value("${first}")
//	private String first;
	
	@Autowired
	private AddressRepository contacts;
	
	public String getFirst() {
		return "abc";
	}
	
	public Map<String, String> getContacts() {
		return contacts.getRepository();
//		return null;
	}


	@Bean
	@ConfigurationProperties
	public AddressRepository getAddressRepository() {
		return new AddressRepository();
	}

}
