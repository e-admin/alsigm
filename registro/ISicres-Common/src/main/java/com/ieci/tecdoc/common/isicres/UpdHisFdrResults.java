package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

import com.ieci.tecdoc.common.invesicres.ScrModifreg;

/**
 * @author MABENITO
 */

public class UpdHisFdrResults implements Serializable {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/	
	private ScrModifreg scrModifReg = null;
	private String nameFld = null;
	private String value = null;
	private String oldvalue = null;
	private int sustituto;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /**
     *  
     */
	
	public UpdHisFdrResults() {
		super();
	}

	
    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/
	/**
	 * @return the scrModifReg
	 */
	public ScrModifreg getScrModifReg() {
		return scrModifReg;
	}

	/**
	 * @param scrModifReg the scrModifReg to set
	 */
	public void setScrModifReg(ScrModifreg scrModifReg) {
		this.scrModifReg = scrModifReg;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the oldvalue
	 */
	public String getOldvalue() {
		return oldvalue;
	}

	/**
	 * @param oldvalue the oldvalue to set
	 */
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	
	/**
	 * @return the nameFld
	 */
	public String getNameFld() {
		return nameFld;
	}


	/**
	 * @param nameFld the nameFld to set
	 */
	public void setNameFld(String nameFld) {
		this.nameFld = nameFld;
	}


	/**
	 * @return the sustituto
	 */
	public int getSustituto() {
		return sustituto;
	}


	/**
	 * @param sustituto the sustituto to set
	 */
	public void setSustituto(int sustituto) {
		this.sustituto = sustituto;
	}
	
	
    /**
     * toString methode: creates a String representation of the object
     * @return the String representation

     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("UpdHisFdrResults[");
        buffer.append("scrModifReg = ").append(scrModifReg);
        buffer.append(", value = ").append(value);
        buffer.append(", oldvalue = ").append(oldvalue);
        buffer.append("]");
        return buffer.toString();
    }


    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

	
	
	
}
