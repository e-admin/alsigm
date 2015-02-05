package gcontrol.vos;

import common.vos.BaseVO;

/**
 * Clase que almacena la información de un permiso de un rol de usuario.
 */
public class PermisoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String idRol = null;
	private int tipoModulo = -1;
	private int perm = -1;

	/**
	 * Constructor
	 * 
	 * @param idRol
	 *            Identificador de rol
	 * @param tipoModulo
	 *            Modulo al que esta asociado el rol
	 * @param perm
	 *            Permiso generico
	 */
	public PermisoVO(String idRol, int tipoModulo, int perm) {
		super();
		this.idRol = idRol;
		this.tipoModulo = tipoModulo;
		this.perm = perm;
	}

	/**
	 * Constructor
	 */
	public PermisoVO() {
		super();
	}

	/**
	 * @return Returns the idRol.
	 */
	public String getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol
	 *            The idRol to set.
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return Returns the perm.
	 */
	public int getPerm() {
		return perm;
	}

	/**
	 * @param perm
	 *            The perm to set.
	 */
	public void setPerm(int perm) {
		this.perm = perm;
	}

	/**
	 * @return Returns the tipoModulo.
	 */
	public int getTipoModulo() {
		return tipoModulo;
	}

	/**
	 * @param tipoModulo
	 *            The tipoModulo to set.
	 */
	public void setTipoModulo(int tipoModulo) {
		this.tipoModulo = tipoModulo;
	}
}
