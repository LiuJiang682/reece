package au.com.reece.addressbook.model;

import java.io.Serializable;

public class Contact implements Serializable {

	private static final String DELIM = ",";

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -3398400133652065212L;

	private long id;
	private String name;
	private String phoneNumber;
	
	public Contact(long id, final String name, final String phoneNumber) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		// Not sure EqualsBuilder is allow to use, if it 
		// is allow, use the line below:
		//return EqualsBuilder.reflectionEquals(this, obj);
		if (this == obj)
			return true;
		if ((null == obj) 
				|| (obj.getClass() != this.getClass())) {
			return false;
		}
		
		Contact contact = (Contact) obj;
		return this.id == contact.getId();
		//Deprecated -- since introduced id field.
		// Parameter obj is the a Contact object.
//		Contact contact = (Contact) obj;
//		if ((null == this.name) &&
//				(null == this.phoneNumber) && 
//				(null == contact.name) &&
//				(null == contact.phoneNumber)) 
//			return true;
//		boolean isSameName = (null != this.name && this.name.equals(contact.getName()));
//		boolean isSamePhoneNumber = (null != this.phoneNumber && this.phoneNumber.equals(contact.getPhoneNumber()));
//		
//		return isSameName && isSamePhoneNumber;
	}
	
	@Override
	public int hashCode() {
		return new Long(id).hashCode();
		//Deprecated -- since introduced id field
		// Not sure HashCodeBuilder is allow to use, if it 
		// is allow, use the line below:
		// return HashCodeBuilder.reflectionHashCode(this);
//		int hash = 7;
//		hash = 31 * hash + (null == this.name ? 0 : this.name.hashCode());
//		hash = 31 * hash + (null == this.phoneNumber ? 0 : this.phoneNumber.hashCode());
//		return hash;
	}
	
	@Override
	public String toString() {
		// Not sure ToStringBuilder is allow to use, if it 
		// is allow, use the line below:
		// return ToStringBuilder.reflectionToString(this);
		StringBuilder buf = new StringBuilder("id=");
		buf.append(id);
		buf.append(DELIM);
		buf.append("name=");
		buf.append(name);
		buf.append(DELIM);
		buf.append("phoneNumber=");
		buf.append(phoneNumber);
		return buf.toString();
	}
}
