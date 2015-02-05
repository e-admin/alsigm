package ieci.tdw.ispac.services.ws.server.dto;

import java.io.Serializable;

public class InteresadoExpediente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String IND_POSTAL = "P";
	public static final String IND_TELEMATIC = "T";
	public static final String IND_PRINCIPAL = "S";
	
	private String thirdPartyId;
	private String indPrincipal;
	private String nifcif;
	private String name;
	private String postalAddress;
	private String postalCode;
	private String placeCity;
	private String regionCountry;
	private String telematicAddress;
	private String notificationAddressType;
	private String phone;
	private String mobilePhone;
	
	public InteresadoExpediente() {
		super();
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	/**
	 * @return Returns the indPrincipal.
	 */
	public String getIndPrincipal() {
		return indPrincipal;
	}
	/**
	 * @param indPrincipal The indPrincipal to set.
	 */
	public void setIndPrincipal(String indPrincipal) {
		this.indPrincipal = indPrincipal;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return Returns the nifcif.
	 */
	public String getNifcif() {
		return nifcif;
	}
	/**
	 * @param nifcif The nifcif to set.
	 */
	public void setNifcif(String nifcif) {
		this.nifcif = nifcif;
	}

	/**
	 * @return Returns the placeCity.
	 */
	public String getPlaceCity() {
		return placeCity;
	}
	/**
	 * @param placeCity The placeCity to set.
	 */
	public void setPlaceCity(String placeCity) {
		this.placeCity = placeCity;
	}

	/**
	 * @return Returns the postalCode.
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode The postalCode to set.
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return Returns the regionCountry.
	 */
	public String getRegionCountry() {
		return regionCountry;
	}
	/**
	 * @param regionCountry The regionCountry to set.
	 */
	public void setRegionCountry(String regionCountry) {
		this.regionCountry = regionCountry;
	}

	/**
	 * @return Returns the notificationAddressType.
	 */
	public String getNotificationAddressType() {
		return notificationAddressType;
	}
	/**
	 * @param notificationAddressType The notificationAddressType to set.
	 */
	public void setNotificationAddressType(String notificationAddressType) {
		this.notificationAddressType = notificationAddressType;
	}

	/**
	 * @return Returns the postalAddress.
	 */
	public String getPostalAddress() {
		return postalAddress;
	}
	/**
	 * @param postalAddress The postalAddress to set.
	 */
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * @return Returns the telematicAddress.
	 */
	public String getTelematicAddress() {
		return telematicAddress;
	}
	/**
	 * @param telematicAddress The telematicAddress to set.
	 */
	public void setTelematicAddress(String telematicAddress) {
		this.telematicAddress = telematicAddress;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}