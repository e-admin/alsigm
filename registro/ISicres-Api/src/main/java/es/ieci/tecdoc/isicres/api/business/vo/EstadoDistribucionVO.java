package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.Date;

import es.ieci.tecdoc.isicres.api.business.vo.enums.EstadoDistribucionEnum;

public class EstadoDistribucionVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 8494150725120656726L;

	/**
	 * identificador único del estado de la distribucion 1 pendiente 2 Aceptado
	 * 3 Archivado 4 Rechazado 5 Redistribuido
	 * 
	 */

	protected EstadoDistribucionEnum estado;

	protected String id;

	/**
	 * descripcion del estado
	 */
	protected String descripcion;

	/**
	 * fecha en la que se actualizó el estado de la distribución
	 */
	protected Date fechaEstado;

	/**
	 * usuario responsable del estado de la distribución
	 */
	protected String usuarioEstado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return getIdEstado().getName();
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getUsuarioEstado() {
		return usuarioEstado;
	}

	public void setUsuarioEstado(String usuarioEstado) {
		this.usuarioEstado = usuarioEstado;
	}

	public EstadoDistribucionEnum getIdEstado() {
		return estado;
	}

	public void setIdEstado(EstadoDistribucionEnum idEstado) {
		this.estado = idEstado;
	}

}
