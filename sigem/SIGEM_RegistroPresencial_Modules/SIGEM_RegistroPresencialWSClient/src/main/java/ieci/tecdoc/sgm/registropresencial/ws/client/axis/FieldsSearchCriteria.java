/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * @author 66575267
 * 
 */
public class FieldsSearchCriteria extends RetornoServicio {

	private FieldInfoSearchCriteria[] fields;

	public FieldInfoSearchCriteria[] getFields() {
		return fields;
	}

	public void setFields(FieldInfoSearchCriteria[] fields) {
		this.fields = fields;
	}

}
