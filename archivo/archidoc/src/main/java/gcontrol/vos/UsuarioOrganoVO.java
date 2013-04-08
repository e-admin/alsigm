package gcontrol.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Clase que almacena la información de la relación entre un usuario y un
 * órgano.
 */
public class UsuarioOrganoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del usuario. */
	private String idUsuario = null;

	/** Identificador del órgano. */
	private String idOrgano = null;

	/** Fecha de inicio del período de validez. */
	private Date fechaIni = null;

	/** Fecha de fin del período de validez. */
	private Date fechaFin = null;

	/**
	 * Constructor
	 */
	public UsuarioOrganoVO() {
		super();
	}

	/**
	 * @return Returns the fechaFin.
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            The fechaFin to set.
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return Returns the fechaIni.
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni
	 *            The fechaIni to set.
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return Returns the idOrgano.
	 */
	public String getIdOrgano() {
		return idOrgano;
	}

	/**
	 * @param idOrgano
	 *            The idOrgano to set.
	 */
	public void setIdOrgano(String idOrgano) {
		this.idOrgano = idOrgano;
	}

	/**
	 * @return Returns the idUsuario.
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            The idUsuario to set.
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
}
