package gcontrol.vos;

import common.vos.BaseVO;

/**
 * Clase que almacena la información del rol de un usuario.
 */
public class RolVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private int tipoModulo = 0;
	private String descripcion = null;

	private boolean permitidaModificacionPermisos = false;

	/**
	 * Constructor
	 */
	public RolVO() {
		super();
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public boolean equals(Object obj) {
		return getId().equalsIgnoreCase(((RolVO) obj).getId());
	}

	/**
	 * @return Returns the permitidaModificacionPermisos.
	 */
	public boolean getPermitidaModificacionPermisos() {
		return permitidaModificacionPermisos;
	}

	/**
	 * @param permitidaModificacionPermisos
	 *            The permitidaModificacionPermisos to set.
	 */
	public void setPermitidaModificacionPermisos(
			boolean permitidaModificacionPermisos) {
		this.permitidaModificacionPermisos = permitidaModificacionPermisos;
	}
}
