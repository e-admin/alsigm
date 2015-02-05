package ieci.tecdoc.sgm.registro.form;

import org.apache.struts.action.ActionForm;

public class TipoAccesoForm extends ActionForm{
	private String selTipoAcceso;
	private int accesoId;
	private String accesoDescription;
	
	public TipoAccesoForm(){
		this.selTipoAcceso = "";
		this.accesoId = -1;
		this.accesoDescription = "";
	}
	
	public TipoAccesoForm(int id, String description){
		this.accesoId = id;
		this.accesoDescription = description;
	}

	public String getAccesoDescription() {
		return accesoDescription;
	}

	public void setAccesoDescription(String accesoDescription) {
		this.accesoDescription = accesoDescription;
	}

	public int getAccesoId() {
		return accesoId;
	}

	public void setAccesoId(int accesoId) {
		this.accesoId = accesoId;
	}

	public String getSelTipoAcceso() {
		return selTipoAcceso;
	}

	public void setSelTipoAcceso(String selTipoAcceso) {
		this.selTipoAcceso = selTipoAcceso;
	}
	
	private final static long serialVersionUID = 0;
}
