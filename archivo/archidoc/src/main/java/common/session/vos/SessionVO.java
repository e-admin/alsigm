package common.session.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Clase que contiene toda la informacion para el mantenimiento de sesiones de
 * los usuarios
 */
public class SessionVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador de la sesion */
	private String id = null;
	/** Identificador del usuario */
	private String idUsuario = null;
	/** Keep-alive */
	private Date keepAlive = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(Date keepAlive) {
		this.keepAlive = keepAlive;
	}
}
