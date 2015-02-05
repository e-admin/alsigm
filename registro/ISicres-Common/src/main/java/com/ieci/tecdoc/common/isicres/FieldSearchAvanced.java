/**
 * @author MABENITO
 * 
 */

package com.ieci.tecdoc.common.isicres;

import java.io.Serializable;

import com.ieci.tecdoc.common.conf.FieldConf;

public class FieldSearchAvanced implements Serializable {

	private FieldConf fieldConf;
	private String value;
	private boolean isValidated;
	private int validation;
	private String caseSensitive;
	private int fldType;
	private int rowId;
		
	/**
	 * @return the caseSensitive
	 */
	public String getCaseSensitive() {
		return caseSensitive;
	}


	/**
	 * @param caseSensitive the caseSensitive to set
	 */
	public void setCaseSensitive(String caseSensitive) {
		this.caseSensitive = caseSensitive;
	}


	public FieldSearchAvanced() {
		super();
	}


	/**
	 * @return the fieldConf
	 */
	public FieldConf getFieldConf() {
		return fieldConf;
	}


	/**
	 * @param fieldConf the fieldConf to set
	 */
	public void setFieldConf(FieldConf fieldConf) {
		this.fieldConf = fieldConf;
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
	 * @return the isValidated
	 */
	public boolean isValidated() {
		return isValidated;
	}


	/**
	 * @param isValidated the isValidated to set
	 */
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}


	/**
	 * @return the fldType
	 */
	public int getFldType() {
		return fldType;
	}


	/**
	 * @param fldType the fldType to set
	 */
	public void setFldType(int fldType) {
		this.fldType = fldType;
	}


	/**
	 * @return the validation
	 */
	public int getValidation() {
		return validation;
	}


	/**
	 * @param validation the validation to set
	 */
	public void setValidation(int validation) {
		this.validation = validation;
	}


	public int getRowId() {
		return rowId;
	}


	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
}
