/*
 * Created on 24-ago-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author 79426599
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionInformation implements Serializable{

    private String user;

    private String userName = "";

    private String officeCode = "";
    
    private String officeName = "";
    
    private String officeEnabled = "";
    
    private String otherOffice = "";
    
    private String sessionId = "";
    
    private String caseSensitive = "";
    
	public SessionInformation() {
	}

	/**
	 * @return Returns the officeCode.
	 */
	public String getOfficeCode() {
		return officeCode;
	}
	/**
	 * @param officeCode The officeCode to set.
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	/**
	 * @return Returns the officeName.
	 */
	public String getOfficeName() {
		return officeName;
	}
	/**
	 * @param officeName The officeName to set.
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return Returns the user.
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the sessionId.
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId The sessionId to set.
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return Returns the otherOffice.
	 */
	public String getOtherOffice() {
		return otherOffice;
	}
	/**
	 * @param otherOffice The otherOffice to set.
	 */
	public void setOtherOffice(String otherOffice) {
		this.otherOffice = otherOffice;
	}

	/**
	 * @return the dataBaseType
	 */
	public String getCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * @param dataBaseType the dataBaseType to set
	 */
	public void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * @param officeEnabled the officeEnabled to set
	 */
	public void setOfficeEnabled(String officeEnabled) {
		this.officeEnabled = officeEnabled;
	}

	/**
	 * @return the officeEnabled
	 */
	public String getOfficeEnabled() {
		return officeEnabled;
	}
}
