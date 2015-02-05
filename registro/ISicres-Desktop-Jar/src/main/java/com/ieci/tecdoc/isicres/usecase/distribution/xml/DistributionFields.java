/*
 * Created on 01-jun-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.isicres.usecase.distribution.xml;

import java.util.Map;

/**
 * @author 79426599
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DistributionFields {

	private String fieldName = null;
	private String fieldLabel = null;
	private Integer fieldType = null;
	private Integer fieldValidation = null;
	private Map operators = null;
	
	public DistributionFields(String fieldName, String fieldLabel, Integer fieldType, Integer  fieldValidation) {
		this.fieldName = fieldName;
		this.fieldLabel = fieldLabel;
		this.fieldType = fieldType;
		this.fieldValidation = fieldValidation;
	}

	/**
	 * @return Returns the fieldLabel.
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}
	/**
	 * @param fieldLabel The fieldLabel to set.
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	/**
	 * @return Returns the fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return Returns the fieldType.
	 */
	public Integer getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType The fieldType to set.
	 */
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * @return Returns the fieldValidation.
	 */
	public Integer getFieldValidation() {
		return fieldValidation;
	}
	/**
	 * @param fieldValidation The fieldValidation to set.
	 */
	public void setFieldValidation(Integer fieldValidation) {
		this.fieldValidation = fieldValidation;
	}
	/**
	 * @return Returns the operators.
	 */
	public Map getOperators() {
		return operators;
	}
	/**
	 * @param operators The operators to set.
	 */
	public void setOperators(Map operators) {
		this.operators = operators;
	}
}
