package es.ieci.tecdoc.isicres.admin.core.beans;

import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistrador;

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
