package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de vias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Vias extends  RetornoServicio {
	
	private Via[] vias;

	public Via[] getVias() {
		return vias;
	}

	public void setVias(Via[] vias) {
		this.vias = vias;
	}

}
