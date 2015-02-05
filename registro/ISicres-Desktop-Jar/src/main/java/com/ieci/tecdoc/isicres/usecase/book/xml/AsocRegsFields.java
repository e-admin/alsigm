package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.Map;

/**
 * 
 * @author 66575267
 * 
 * @date 01/06/2009
 * 
 */
public class AsocRegsFields {

	private String fieldName = null;
	private String fieldLabel = null;
	private Integer fieldType = null;
	private Integer fieldValidation = null;
	private Map operators = null;
	private String fieldId = null;

	public AsocRegsFields(String fieldName, String fieldLabel,
			Integer fieldType, Integer fieldValidation, String fieldId) {
		this.fieldName = fieldName;
		this.fieldLabel = fieldLabel;
		this.fieldType = fieldType;
		this.fieldValidation = fieldValidation;
		this.fieldId = fieldId;
	}

	/**
	 * @return Returns the fieldLabel.
	 */
	public String getFieldLabel() {
		return fieldLabel;
	}

	/**
	 * @param fieldLabel
	 *            The fieldLabel to set.
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
	 * @param fieldName
	 *            The fieldName to set.
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
	 * @param fieldType
	 *            The fieldType to set.
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
	 * @param fieldValidation
	 *            The fieldValidation to set.
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
	 * @param operators
	 *            The operators to set.
	 */
	public void setOperators(Map operators) {
		this.operators = operators;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

}
