package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de municipios
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Municipios extends RetornoServicio{
	
	private Municipio[] municipios;

	public Municipio[] getMunicipios() {
		return municipios;
	}

	public void setMunicipios(Municipio[] municipios) {
		this.municipios = municipios;
	}

}
