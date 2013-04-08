package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de portales
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Portales extends RetornoServicio{
	
	private Portal[] portales;

	public Portal[] getPortales() {
		return portales;
	}

	public void setPortales(Portal[] portales) {
		this.portales = portales;
	}

}
