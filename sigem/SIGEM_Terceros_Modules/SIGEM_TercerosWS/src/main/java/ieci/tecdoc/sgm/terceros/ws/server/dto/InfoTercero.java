package ieci.tecdoc.sgm.terceros.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

import java.io.Serializable;

public class InfoTercero extends RetornoServicio implements Serializable {

	/** Tercero. */
	Tercero tercero = null;

	/**
	 * Constructor.
	 *
	 */
	public InfoTercero() {
		super();
	}
	
	/**
	 * Obtiene la información del tercero.
	 * @return Tercero.
	 */
	public Tercero getTercero() {
		return tercero;
	}

	/**
	 * Establece la información del tercero.
	 * @param tercero Tercero.
	 */
	public void setTercero(Tercero tercero) {
		this.tercero = tercero;
	}
}
