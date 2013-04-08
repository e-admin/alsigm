package organizacion.model.vo;

import common.vos.BaseVO;

public class UserOrganoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* Identificador de Usuario */
	private String idUsuario;
	/* Nombre del Usuario */
	private String nombreUsuario;
	/* Identificador del Órgano */
	private String idOrgano;

	public UserOrganoVO() {
	}

	public UserOrganoVO(String idUser, String nameUser, String idOrgano,
			String idUsuarioExt, String sistemaExt) {
		this.idUsuario = idUser;
		this.nombreUsuario = nameUser;
		this.idOrgano = idOrgano;
	}

	/**
	 * @return el idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            el idUsuario a establecer
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return el nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            el nombreUsuario a establecer
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return el idOrgano
	 */
	public String getIdOrgano() {
		return idOrgano;
	}

	/**
	 * @param idOrgano
	 *            el idOrgano a establecer
	 */
	public void setIdOrgano(String idOrgano) {
		this.idOrgano = idOrgano;
	}
}