package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de coordenadas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Coordenadas extends RetornoServicio{
	
	private Coordenada[] coordenadas;

	public Coordenada[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenada[] coordenadas) {
		this.coordenadas = coordenadas;
	}

}
