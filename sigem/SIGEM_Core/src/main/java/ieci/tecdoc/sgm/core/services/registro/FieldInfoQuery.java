package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de Atributos para consulta
 * 
 */
public class FieldInfoQuery {
	
	/**
	 * Identificador del Atributo
	 */
	protected String fieldId = null;

	/**
	 * Valor del atributo
	 */
	protected String value = null;

	/**
	 * Operador de busqueda del atributo
	 */
	protected String operator = null;

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

	/**
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

}
