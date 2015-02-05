package com.ieci.tecdoc.common.isicres;

public class PersonAddress {
	private String Id = "";
	private String Dom = "";
	private String City = "";
	private String Zip = "";
	private String Province = "";
	private String Preference = "";
	private String toDelete = "";

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		City = city;
	}

	/**
	 * @return the dom
	 */
	public String getDom() {
		return Dom;
	}

	/**
	 * @param dom
	 *            the dom to set
	 */
	public void setDom(String dom) {
		Dom = dom;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @return the preference
	 */
	public String getPreference() {
		return Preference;
	}

	/**
	 * @param preference
	 *            the preference to set
	 */
	public void setPreference(String preference) {
		Preference = preference;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return Province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		Province = province;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return Zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		Zip = zip;
	}

	/**
	 * @return the toDelete
	 */
	public String getToDelete() {
		return toDelete;
	}

	/**
	 * @param toDelete
	 *            the toDelete to set
	 */
	public void setToDelete(String toDelete) {
		this.toDelete = toDelete;
	}

}
