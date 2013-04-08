/*
 * Created on 28-abr-2006
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
public class ValidationResultCode implements Serializable {
    private Integer fldid = null;
    private String code = null;
    private String description = null;
    private Integer fldIdAdd = null;
    private String codeAdd = null;
    private String descriptionAdd = null;


    public ValidationResultCode() {
    }
    
    
    /**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the codeAdd.
	 */
	public String getCodeAdd() {
		return codeAdd;
	}
	/**
	 * @param codeAdd The codeAdd to set.
	 */
	public void setCodeAdd(String codeAdd) {
		this.codeAdd = codeAdd;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the descriptionAdd.
	 */
	public String getDescriptionAdd() {
		return descriptionAdd;
	}
	/**
	 * @param descriptionAdd The descriptionAdd to set.
	 */
	public void setDescriptionAdd(String descriptionAdd) {
		this.descriptionAdd = descriptionAdd;
	}
	/**
	 * @return Returns the fldid.
	 */
	public Integer getFldid() {
		return fldid;
	}
	/**
	 * @param fldid The fldid to set.
	 */
	public void setFldid(Integer fldid) {
		this.fldid = fldid;
	}
	/**
	 * @return Returns the fldidAdd.
	 */
	public Integer getFldIdAdd() {
		return fldIdAdd;
	}
	/**
	 * @param fldidAdd The fldidAdd to set.
	 */
	public void setFldIdAdd(Integer fldIdAdd) {
		this.fldIdAdd = fldIdAdd;
	}
	
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ValidationResultCode[");
        buffer.append("fldid = ").append(fldid);
        buffer.append(", code = ").append(code);
        buffer.append(", description = ").append(description);
        buffer.append(", fldIdAdd = ").append(fldIdAdd);
        buffer.append(", codeAdd = ").append(codeAdd);
        buffer.append(", descriptionAdd = ").append(descriptionAdd);
        buffer.append("]");
        return buffer.toString();
    }
	
}
