package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de bienes inmuebles
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class BienesInmuebles extends RetornoServicio{
	
	private BienInmueble[] bienesInmuebles;

	public void setBienesInmuebles(BienInmueble[] bienesInmuebles) {
		this.bienesInmuebles = bienesInmuebles;
	}

	public BienInmueble[] getBienesInmuebles() {
		return this.bienesInmuebles;
	}

}
