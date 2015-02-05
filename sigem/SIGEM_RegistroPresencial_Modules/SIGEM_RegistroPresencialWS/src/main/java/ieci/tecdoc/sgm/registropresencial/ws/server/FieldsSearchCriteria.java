/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena una lista de criterios de busqueda
 * 
 */
public class FieldsSearchCriteria extends RetornoServicio {

	/**
	 * Lista de criterios de busqueda
	 */
	private FieldInfoSearchCriteria[] fields;

	/**
	 * @return
	 */
	public FieldInfoSearchCriteria[] getFields() {
		return fields;
	}

	/**
	 * @param fields
	 */
	public void setFields(FieldInfoSearchCriteria[] fields) {
		this.fields = fields;
	}

}
