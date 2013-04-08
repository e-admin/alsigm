//
// FileName: AxSFIn.java
//
package com.ieci.tecdoc.common.isicres;

import java.util.ArrayList;
import java.util.HashMap;

import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOrg;

/**
 * @author lmvicente
 * @version @since @creationDate 12-mar-2004
 */

public class AxSfIn extends AxSf {

     // Registro original
    private ScrOrg fld13 = null;
    // Nombre Registro original
    private String fld13Name = null;

    // Tipo asunto
    private ScrCa fld16 = null;
    // Nombre asunto
    private String fld16Name = null;
    
    
	public AxSfIn() {
		super();
	}

	public Object clone() {
	    AxSfIn newAxsf = new AxSfIn();
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
        newAxsf.setFld13(getFld13());
        newAxsf.setFld16(getFld16());
        newAxsf.setAxxf(getAxxf());
        return newAxsf;
    }
	
	/**
     * @return Returns the fld13.
     */
    public ScrOrg getFld13() {
        return fld13;
    }
    /**
     * @param fld13 The fld13 to set.
     */
    public void setFld13(ScrOrg fld13) {
        this.fld13 = fld13;
    }
    /**
     * @return Returns the fld16.
     */
    public ScrCa getFld16() {
        return fld16;
    }
    /**
     * @param fld16 The fld16 to set.
     */
    public void setFld16(ScrCa fld16) {
        this.fld16 = fld16;
    }
   
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(super.toString());
        buffer.append("\n\t fld13 [");
        buffer.append(fld13);
        buffer.append("] fld16 [");
        buffer.append(fld16);
        buffer.append("]");
        
        return buffer.toString();
    }

	/**
	 * @return the fld13Name
	 */
	public String getFld13Name() {
		return fld13Name;
	}

	/**
	 * @param fld13Name the fld13Name to set
	 */
	public void setFld13Name(String fld13Name) {
		this.fld13Name = fld13Name;
	}

	/**
	 * @return the fld16Name
	 */
	public String getFld16Name() {
		return fld16Name;
	}

	/**
	 * @param fld16Name the fld16Name to set
	 */
	public void setFld16Name(String fld16Name) {
		this.fld16Name = fld16Name;
	}
    
}
