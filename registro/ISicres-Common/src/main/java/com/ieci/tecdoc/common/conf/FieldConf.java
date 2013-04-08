package com.ieci.tecdoc.common.conf;

import java.io.Serializable;

/**
 * @author 79426599
 *
 */
public class FieldConf implements Serializable {

    private String fieldLabel = "";
    private int fieldId = 0;
    private int fieldCheck = 0;
    
	public FieldConf() {
	}

	
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t FieldConf fieldLabel[");
        buffer.append(fieldLabel);
        buffer.append("] fieldId [");
        buffer.append(fieldId);
        buffer.append("] fieldCheck [");
        buffer.append(fieldCheck);
        buffer.append("]");
        
        return buffer.toString();
    }


	/**
	 * @return the fieldCheck
	 */
	public int getFieldCheck() {
		return fieldCheck;
	}


	/**
	 * @param fieldCheck the fieldCheck to set
	 */
	public void setFieldCheck(int fieldCheck) {
		this.fieldCheck = fieldCheck;
	}


	/**
	 * @return the fieldId
	 */
	public int getFieldId() {
		return fieldId;
	}


	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}


	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}


	/**
	 * @param fieldLabel the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
}
