package es.ieci.tecdoc.fwktd.dir3.api.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class EstadoActualizacionDcoVO extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1763557309106784979L;
	/*
	 * =======================================================================
	 * Datos identificativos
	 *
	 * El campo "id" heredado de la clase Entity es el código único.
	 * =======================================================================
	 */
	private Date fechaActualizacion;
	private String estado;


	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
