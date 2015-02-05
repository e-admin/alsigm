package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de provincias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Provincias extends RetornoServicio{
	
	private Provincia[] provincias;

	public Provincia[] getProvincias() {
		return provincias;
	}

	public void setProvincias(Provincia[] provincias) {
		this.provincias = provincias;
	}
	
}
