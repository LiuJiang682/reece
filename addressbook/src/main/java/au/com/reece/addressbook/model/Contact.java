package au.com.reece.addressbook.model;

import java.io.Serializable;

public class Contact implements Serializable {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -3398400133652065212L;

	private String name;
	private String phoneNumber;
	
	public Contact(final String name, final String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
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
		
		// Parameter obj is the a Contact object.
		Contact contact = (Contact) obj;
		if ((null == this.name) &&
				(null == this.phoneNumber) && 
				(null == contact.name) &&
				(null == contact.phoneNumber)) 
			return true;
		boolean isSameName = (null != this.name && this.name.equals(contact.getName()));
		boolean isSamePhoneNumber = (null != this.phoneNumber && this.phoneNumber.equals(contact.getPhoneNumber()));
		
		return isSameName && isSamePhoneNumber;
	}
	
	@Override
	public int hashCode() {
		// Not sure HashCodeBuilder is allow to use, if it 
		// is allow, use the line below:
		// return HashCodeBuilder.reflectionHashCode(this);
		int hash = 7;
		hash = 31 * hash + (null == this.name ? 0 : this.name.hashCode());
		hash = 31 * hash + (null == this.phoneNumber ? 0 : this.phoneNumber.hashCode());
		return hash;
	}
	
	@Override
	public String toString() {
		// Not sure ToStringBuilder is allow to use, if it 
		// is allow, use the line below:
		// return ToStringBuilder.reflectionToString(this);
		StringBuilder buf = new StringBuilder("name=");
		buf.append(name);
		buf.append(",");
		buf.append("phoneNumber=");
		buf.append(phoneNumber);
		return buf.toString();
	}
}
