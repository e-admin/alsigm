package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena una lista de los datos de un registro
 * 
 */
public class Fields extends RetornoServicio {

	/**
	 * Lista de los datos de un registro
	 */
	private FieldInfo[] fields;

	/**
	 * @return
	 */
	public FieldInfo[] getFields() {
		return fields;
	}

	/**
	 * @param fields
	 */
	public void setFields(FieldInfo[] fields) {
		this.fields = fields;
	}

}
