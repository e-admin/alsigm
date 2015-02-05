package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

import java.util.Date;

public class SicresUserLocalizacionImpl {
	private int userId;
	private Date tmstamp;
	private String address;
	private String city;
	private String zip;
	private String country;
	private String telephone;
	private String fax;
	private String email;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getTmstamp() {
		return tmstamp;
	}
	public void setTmstamp(Date tmstamp) {
		this.tmstamp = tmstamp;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	
}
