package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class BaseUnidadAdministrativaVO extends BaseIsicresApiVO {

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getAcronimoUnidad() {
		return acronimoUnidad;
	}

	public void setAcronimoUnidad(String acronimoUnidad) {
		this.acronimoUnidad = acronimoUnidad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TipoUnidadAdministrativaVO getTipo() {
		return tipo;
	}

	public void setTipo(TipoUnidadAdministrativaVO tipo) {
		this.tipo = tipo;
	}

	public BaseUnidadAdministrativaVO getUnidadPadre() {
		return unidadPadre;
	}

	public void setUnidadPadre(BaseUnidadAdministrativaVO unidadPadre) {
		this.unidadPadre = unidadPadre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Members

	private static final long serialVersionUID = -1578955881871446768L;

	/**
	 * identificador único del vo
	 */
	protected String id;

	protected String codigoUnidad;

	protected String acronimoUnidad;

	protected String name;

	protected TipoUnidadAdministrativaVO tipo;

	protected BaseUnidadAdministrativaVO unidadPadre;

	protected Date fechaAlta;

	protected Date fechaBaja;

	protected boolean activa;

	protected String cif;

}
