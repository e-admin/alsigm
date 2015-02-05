package ieci.tecdoc.sgm.registro.form;

import org.apache.struts.action.ActionForm;

public class SolicitudesRegistroForm extends ActionForm{
	private String tramiteId;
	private String tramiteDescripcion;
	private String selTramiteId;
	
	public SolicitudesRegistroForm(){
		this.tramiteId = "";
		this.tramiteDescripcion = "";
		this.selTramiteId = "";
	}
	
	public SolicitudesRegistroForm(String tramiteId, String tramiteDescripcion){
		this.tramiteId = tramiteId;
		this.tramiteDescripcion = tramiteDescripcion;
	}

	public String getTramiteDescripcion() {
		return tramiteDescripcion;
	}

	public void setTramiteDescripcion(String tramiteDescripcion) {
		this.tramiteDescripcion = tramiteDescripcion;
	}

	public String getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getSelTramiteId() {
		return selTramiteId;
	}

	public void setSelTramiteId(String selTramiteId) {
		this.selTramiteId = selTramiteId;
	}
	
	private final static long serialVersionUID = 0;
}
