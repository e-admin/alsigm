package com.ieci.tecdoc.common.isicres;

import java.util.ArrayList;
import java.util.List;


public class PersonInfo {
	private String sessionId = null;
	private String type = null;
	private String id = null;
	private String name = null;
	private String firstName = null;
	private String secondName = null;
	private String typeDoc = null;
	private String nif = null;
	private List addresses = new ArrayList();
	private List addressesTel = new ArrayList();

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the addresses
	 */
	public List getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List addresses) {
		this.addresses = addresses;
	}

	public void addAddress(PersonAddress pAddress) {
		this.addresses.add(pAddress);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif
	 *            the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @param secondName
	 *            the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * @return the typeDoc
	 */
	public String getTypeDoc() {
		return typeDoc;
	}

	/**
	 * @param typeDoc
	 *            the typeDoc to set
	 */
	public void setTypeDoc(String typeDoc) {
		this.typeDoc = typeDoc;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the addressesTel
	 */
	public List getAddressesTel() {
		return addressesTel;
	}
	
	/**
	 * @param addressesTel
	 *            the addressesTel to set
	 */
	public void setAddressesTel(List addressesTel) {
		this.addressesTel = addressesTel;
	}
	
	public void addAddressTel(PersonAddressTel pAddressTel) {
		this.addressesTel.add(pAddressTel);
	}
}
