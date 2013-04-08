package es.ieci.tecdoc.isicres.api.audit.business.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class IsicresAuditoriaValorModificadoVO extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6433383786237986210L;
	
	private String fieldName;
	
	private String oldValue;
	
	private String newValue;

	public String getFieldName() {
		return fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	

}
