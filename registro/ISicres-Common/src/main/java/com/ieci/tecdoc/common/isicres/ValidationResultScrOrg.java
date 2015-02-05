package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

/**
 * @author LMVICENTE
 * @creationDate 24-jun-2004 10:41:16
 * @version
 * @since
 */
public class ValidationResultScrOrg implements Serializable {

    private String scrOrgName = null;
    private String scrOrgFatherName = null;
    private String scrOrgParentAcron = null;
    private String scrOrgCode = null;
    private Integer scrOrgId = null;

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public ValidationResultScrOrg() {
        super();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    /**
     * @return Returns the scrOrgFatherName.
     */
    public String getScrOrgFatherName() {
        return scrOrgFatherName;
    }

    /**
     * @return Returns the scrOrgId.
     */
    public Integer getScrOrgId() {
        return scrOrgId;
    }
    /**
     * @param scrOrgId The scrOrgId to set.
     */
    public void setScrOrgId(Integer scrOrgId) {
        this.scrOrgId = scrOrgId;
    }
    /**
     * @param scrOrgFatherName
     *            The scrOrgFatherName to set.
     */
    public void setScrOrgFatherName(String scrOrgFatherName) {
        this.scrOrgFatherName = scrOrgFatherName;
    }

    /**
     * @return Returns the scrOrgName.
     */
    public String getScrOrgName() {
        return scrOrgName;
    }

    /**
     * @param scrOrgName
     *            The scrOrgName to set.
     */
    public void setScrOrgName(String scrOrgName) {
        this.scrOrgName = scrOrgName;
    }

    /**
     * @return Returns the scrOrgParentName.
     */
    public String getScrOrgParentAcron() {
        return scrOrgParentAcron;
    }

    /**
     * @param scrOrgParentName
     *            The scrOrgParentName to set.
     */
    public void setScrOrgParentAcron(String scrOrgParentAcron) {
        this.scrOrgParentAcron = scrOrgParentAcron;
    }

    /**
     * @return Returns the scrOrgCode.
     */
    public String getScrOrgCode() {
        return scrOrgCode;
    }
    /**
     * @param scrOrgCode The scrOrgCode to set.
     */
    public void setScrOrgCode(String scrOrgCode) {
        this.scrOrgCode = scrOrgCode;
    }
    
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation
     * @author info.vancauwenberge.tostring plugin

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ValidationResultScrOrg[");
        buffer.append("scrOrgName = ").append(scrOrgName);
        buffer.append(", scrOrgFatherName = ").append(scrOrgFatherName);
        buffer.append(", scrOrgParentAcron = ").append(scrOrgParentAcron);
        buffer.append(", scrOrgCode = ").append(scrOrgCode);
        buffer.append(", scrOrgId = ").append(scrOrgId);
        buffer.append("]");
        return buffer.toString();
    }
}

