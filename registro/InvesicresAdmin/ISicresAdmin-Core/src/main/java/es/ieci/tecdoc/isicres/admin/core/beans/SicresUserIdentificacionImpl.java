package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

import java.util.Date;

public class SicresUserIdentificacionImpl {
	private int userId;
	private Date tmstamp;
	private String firstName;
	private String secondName;
	private String surname;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	
	
}
