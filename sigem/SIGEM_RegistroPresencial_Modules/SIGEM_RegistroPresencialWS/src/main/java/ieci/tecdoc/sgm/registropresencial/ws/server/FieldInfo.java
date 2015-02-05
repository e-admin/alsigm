package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * Almacena uno de los datos de un registro
 *
 */
public class FieldInfo {

	/**
	 * Identificador del dato.
	 * 
	 * @value: 1-Número de registro
	 * @value: 2-Fecha del registro
	 * @value: 3-Usuario
	 * @value: 4- Fecha de trabajo
	 * @value: 5-Oficina de registro
	 * @value: 6-Estado
	 * @value: 7-Origen
	 * @value: 8-Destino
	 * @value: 9-Remitentes
	 * @value: 10-Nº de registro original
	 * @value: 11-Tipo de registro original
	 * @value: 12-Fecha de registro original
	 * @value: 13-Registro original
	 * @value: 14-Tipo de transporte
	 * @value: 15-Número de transporte
	 * @value: 16-Tipo de asunto
	 * @value: 17-Resumen
	 * @value: 18-Comentario
	 * @value: 19-Referencia del expediente.
	 */
	private String fieldId = null;

	/**
	 * Valor del campo
	 */
	private String value = null;

	
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
