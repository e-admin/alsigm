package com.ieci.tecdoc.isicres.usecase.book.xml;


/**
 * @author 79426599
 *
 */
public class XMLFieldsInfo {

	private String fieldName = null;
	private String fieldLabel = null;
	private String stringFieldName[] = {"CODE", "ACRON", "NAME", "TRANSPORT", "MATTER"};
	private String stringFieldLabel[] = {"0", "1", "2", "3", "2"};
	
	public XMLFieldsInfo(int type, String fieldName) {
		this.fieldName = fieldName;
		this.fieldLabel = stringFieldLabel[type];
		
	}
	
	public XMLFieldsInfo(int type) {
		this.fieldName = stringFieldName[type];
		this.fieldLabel = stringFieldLabel[type];
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
}
