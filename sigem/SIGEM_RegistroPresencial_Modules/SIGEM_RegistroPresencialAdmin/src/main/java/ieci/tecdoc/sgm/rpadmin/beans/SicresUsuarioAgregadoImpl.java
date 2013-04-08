package ieci.tecdoc.sgm.rpadmin.beans;

import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistrador;

public class SicresUsuarioAgregadoImpl extends UsuarioRegistrador {
	
	private boolean agregado;
	
	/**
	 * @return the agregado
	 */
	public boolean isAgregado() {
		return agregado;
	}

	/**
	 * @param agregado the agregado to set
	 */
	public void setAgregado(boolean agregado) {
		this.agregado = agregado;
	}	
}
