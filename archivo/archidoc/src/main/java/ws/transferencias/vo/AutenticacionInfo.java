package ws.transferencias.vo;

import java.io.Serializable;

public class AutenticacionInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigoEntidad;
	private String usuario;
	private String password;

	public AutenticacionInfo() {
		super();
	}

	public AutenticacionInfo(String codigoEntidad, String usuario,
			String password) {
		super();
		this.codigoEntidad = codigoEntidad;
		this.usuario = usuario;
		this.password = password;
	}

	/**
	 * @return el idUsuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            el usuario a fijar
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return el password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            el password a fijar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return el codigoEntidad
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	/**
	 * @param codigoEntidad
	 *            el codigoEntidad a fijar
	 */
	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

}
