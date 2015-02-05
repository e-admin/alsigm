package ieci.tecdoc.sgm.geolocalizacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de tipos de vias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class TiposVia extends RetornoServicio{
	
	private TipoVia[] tiposVia;

	public TipoVia[] getTiposVia() {
		return tiposVia;
	}

	public void setTiposVia(TipoVia[] tiposVia) {
		this.tiposVia = tiposVia;
	}
	
}
