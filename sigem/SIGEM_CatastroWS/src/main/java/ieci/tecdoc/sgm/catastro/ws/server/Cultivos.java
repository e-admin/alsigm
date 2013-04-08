package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de cultivos
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Cultivos extends RetornoServicio{
	
	private Cultivo[] cultivos;

	public Cultivo[] getCultivos() {
		return cultivos;
	}

	public void setCultivos(Cultivo[] cultivos) {
		this.cultivos = cultivos;
	}
}
