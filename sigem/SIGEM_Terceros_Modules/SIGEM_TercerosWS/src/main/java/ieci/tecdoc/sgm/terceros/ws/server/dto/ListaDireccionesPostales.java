package ieci.tecdoc.sgm.terceros.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class ListaDireccionesPostales extends RetornoServicio implements Serializable {
	
    private DireccionPostal[] direccionesPostales;

	public ListaDireccionesPostales() {
		super();
	}

	public DireccionPostal[] getDireccionesPostales() {
		return direccionesPostales;
	}

	public void setDireccionesPostales(DireccionPostal[] direccionesPostales) {
		this.direccionesPostales = direccionesPostales;
	}
}
