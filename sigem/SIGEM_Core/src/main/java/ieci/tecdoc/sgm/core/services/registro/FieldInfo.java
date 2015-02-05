package ieci.tecdoc.sgm.core.services.registro;

/**
 * Informacion de un atributo
 * 
 */
public class FieldInfo {

	/**
	 * Identificador del atributo
	 */
	protected String fieldId = null;

	/**
	 * Valor del atributo
	 */
	protected String value = null;

	/**
	 * @return
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
