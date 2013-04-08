package ieci.tecdoc.sgm.terceros.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class ListaTerceros extends RetornoServicio implements Serializable {

	/** Lista de terceros. */
	Tercero[] terceros = null;

	/**
	 * Constructor.
	 *
	 */
	public ListaTerceros() {
		super();
	}
	
	/**
	 * Obtiene la lista de terceros.
	 * @return Terceros.
	 */
	public Tercero[] getTerceros() {
		return terceros;
	}

	/**
	 * Establece la lista de terceros.
	 * @param terceros Terceros.
	 */
	public void setTerceros(Tercero[] terceros) {
		this.terceros = terceros;
	}
	
}
