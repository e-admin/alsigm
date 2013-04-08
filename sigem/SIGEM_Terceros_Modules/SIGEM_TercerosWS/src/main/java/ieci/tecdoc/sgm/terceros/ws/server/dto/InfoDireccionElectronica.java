package ieci.tecdoc.sgm.terceros.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class InfoDireccionElectronica extends RetornoServicio implements Serializable {
	
    private DireccionElectronica direccionElectronica;

	public InfoDireccionElectronica() {
		super();
	}

	public DireccionElectronica getDireccionElectronica() {
		return direccionElectronica;
	}

	public void setDireccionElectronica(DireccionElectronica direccionElectronica) {
		this.direccionElectronica = direccionElectronica;
	}
}
