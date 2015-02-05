package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de mapas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Mapas  extends RetornoServicio  {
	
	private Mapa[] mapas;

	public Mapa[] getMapas() {
		return mapas;
	}

	public void setMapas(Mapa[] mapas) {
		this.mapas = mapas;
	}

}
