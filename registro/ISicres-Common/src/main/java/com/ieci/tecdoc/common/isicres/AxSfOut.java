//
// FileName: AxSFIn.java
//
package com.ieci.tecdoc.common.isicres;

import java.util.ArrayList;
import java.util.HashMap;

import com.ieci.tecdoc.common.invesicres.ScrCa;


/**
 * 
 * 
 * @author lmvicente 
 * @version 
 * @since 
 * @creationDate 12-mar-2004
 */

public class AxSfOut extends AxSf {

    // Tipo asunto
    private ScrCa fld12 = null;
    // Nombre Tipo asunto
    private String fld12Name = null;
    
    public AxSfOut() {
		super();
	}
	
	public Object clone() {
	    AxSfOut newAxsf = new AxSfOut();
        newAxsf.setAttributesClasses(new HashMap(this.getAttributesClasses()));
        newAxsf.setAttributesValues(new HashMap(this.getAttributesValues()));
        newAxsf.setAttributesSQLTypes(new HashMap(this.getAttributesSQLTypes()));
        newAxsf.setAttributesSQLScale(new HashMap(this.getAttributesSQLScale()));
        newAxsf.setAttributesNames(new ArrayList(this.getAttributesNames()));
        newAxsf.setFormat(this.getFormat());
        newAxsf.setModified(this.isModified);
        newAxsf.setFld5(getFld5());
        newAxsf.setFld7(getFld7());
        newAxsf.setFld8(getFld8());
        newAxsf.setFld12(getFld12());
        newAxsf.setAxxf(getAxxf());
        return newAxsf;
    }
	
	/**
     * @return Returns the fld12.
     */
    public ScrCa getFld12() {
        return fld12;
    }
    /**
     * @param fld12 The fld12 to set.
     */
    public void setFld12(ScrCa fld12) {
        this.fld12 = fld12;
    }
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.toString());
        buffer.append("\n\t fld12 [");
        buffer.append(fld12);
        buffer.append("]");
        
        return buffer.toString();
    }

	/**
	 * @return the fld12Name
	 */
	public String getFld12Name() {
		return fld12Name;
	}

	/**
	 * @param fld12Name the fld12Name to set
	 */
	public void setFld12Name(String fld12Name) {
		this.fld12Name = fld12Name;
	}
	
}
