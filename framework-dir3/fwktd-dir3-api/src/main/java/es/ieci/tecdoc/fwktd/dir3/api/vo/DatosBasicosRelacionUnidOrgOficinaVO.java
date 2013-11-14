package es.ieci.tecdoc.fwktd.dir3.api.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;
/**
 * Datos básicos de la relación entre la oficina y la unid. orgánica
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DatosBasicosRelacionUnidOrgOficinaVO extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = -7443550436672001424L;

	/*
	 * =======================================================================
	 * Datos identificativos
	 *
	 * El campo "id" heredado de la clase Entity es el código único.
	 * =======================================================================
	 */

	/**
	 * Código de la unidad orgánica
	 */
	private String codigoUnidadOrganica;

	/**
	 * Denominación de la unidad orgánica
	 */
	private String denominacionUnidadOrganica;

	/**
	 * Código de la oficina
	 */
	private String codigoOficina;

	/**
	 * Denominación de la oficina
	 */
	private String denominacionOficina;

	/**
	 * Estado de la relación
	 *
	 */
	private String estadoRelacion;

	public String getCodigoUnidadOrganica() {
		return codigoUnidadOrganica;
	}

	public void setCodigoUnidadOrganica(String codigoUnidadOrganica) {
		this.codigoUnidadOrganica = codigoUnidadOrganica;
	}

	public String getDenominacionUnidadOrganica() {
		return denominacionUnidadOrganica;
	}

	public void setDenominacionUnidadOrganica(String denominacionUnidadOrganica) {
		this.denominacionUnidadOrganica = denominacionUnidadOrganica;
	}

	public String getCodigoOficina() {
		return codigoOficina;
	}

	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public String getDenominacionOficina() {
		return denominacionOficina;
	}

	public void setDenominacionOficina(String denominacionOficina) {
		this.denominacionOficina = denominacionOficina;
	}

	public String getEstadoRelacion() {
		return estadoRelacion;
	}

	public void setEstadoRelacion(String estadoRelacion) {
		this.estadoRelacion = estadoRelacion;
	}

}
