package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de construcciones
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Construcciones extends RetornoServicio{
	
	private Construccion[] construcciones;

	public Construccion[] getConstrucciones() {
		return construcciones;
	}

	public void setConstrucciones(Construccion[] construcciones) {
		this.construcciones = construcciones;
	}
}
