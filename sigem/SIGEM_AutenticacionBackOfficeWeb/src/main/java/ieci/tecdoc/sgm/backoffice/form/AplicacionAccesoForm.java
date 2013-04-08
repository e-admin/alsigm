package ieci.tecdoc.sgm.backoffice.form;

import org.apache.struts.action.ActionForm;

public class AplicacionAccesoForm extends ActionForm{
	private String aplicacionId;
	
	public String getAplicacionId() {
		return aplicacionId;
	}

	public void setAplicacionId(String aplicacionId) {
		this.aplicacionId = aplicacionId;
	}
	
	private final static long serialVersionUID = 0;
}
