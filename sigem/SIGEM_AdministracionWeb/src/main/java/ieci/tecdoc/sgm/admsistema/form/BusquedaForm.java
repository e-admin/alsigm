package ieci.tecdoc.sgm.admsistema.form;

import org.apache.struts.action.ActionForm;

public class BusquedaForm extends ActionForm {

	private String busqueda;
	
	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}
	
	private final static long serialVersionUID = 0;

}