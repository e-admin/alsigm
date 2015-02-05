package se.autenticacion;

import common.model.UserInfo;

public class UserInfoImpl implements UserInfo {

	String organizationUserId;
	String externalUserId;
	String address;
	String email;
	String name;
	String surname;
	String description;

	/**
	 * @param address
	 * @param email
	 * @param name
	 * @param surname
	 */
	public UserInfoImpl(String externalUserId, String organizationUserId,
			String address, String email, String name, String surname,
			String description) {
		super();
		this.externalUserId = externalUserId;
		this.organizationUserId = organizationUserId;
		this.address = address;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.description = description;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the surname.
	 */
	public String getSurname() {
		return surname;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.model.UserInfo#getExternalUserId()
	 */
	public String getExternalUserId() {
		return externalUserId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see common.model.UserInfo#getOrganizationUserId()
	 */
	public String getOrganizationUserId() {
		return organizationUserId;
	}

	public String getDescription() {
		return description;
	}
}
