package ieci.tecdoc.sgm.terceros.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class ListaDireccionesElectronicas extends RetornoServicio implements Serializable {
	
    private DireccionElectronica[] direccionesElectronicas;

	public ListaDireccionesElectronicas() {
		super();
	}

	public DireccionElectronica[] getDireccionesElectronicas() {
		return direccionesElectronicas;
	}

	public void setDireccionesElectronicas(DireccionElectronica[] direccionesElectronicas) {
		this.direccionesElectronicas = direccionesElectronicas;
	}
}
