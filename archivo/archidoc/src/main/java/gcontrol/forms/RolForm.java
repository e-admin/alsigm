package gcontrol.forms;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en la gestion de roles
 */
public class RolForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String[] rolesSeleccionados = null;
	String idRol = null;
	String nombre;
	String descripcion;
	String[] permisoRol = null;
	int modulo = -1;
	String[] usuarioSeleccionado = null;
	String nombreUsuario = null;

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
	 * @return Returns the modulo.
	 */
	public int getModulo() {
		return modulo;
	}

	/**
	 * @param modulo
	 *            The modulo to set.
	 */
	public void setModulo(int modulo) {
		this.modulo = modulo;
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
	 * @return Returns the rolesSeleccionados.
	 */
	public String[] getRolesSeleccionados() {
		return rolesSeleccionados;
	}

	/**
	 * @param rolesSeleccionados
	 *            The rolesSeleccionados to set.
	 */
	public void setRolesSeleccionados(String[] rolesSeleccionados) {
		this.rolesSeleccionados = rolesSeleccionados;
	}

	/**
	 * @return Returns the permisoRol.
	 */
	public String[] getPermisoRol() {
		return permisoRol;
	}

	/**
	 * @param permisoRol
	 *            The permisoRol to set.
	 */
	public void setPermisoRol(String[] permisoRol) {
		this.permisoRol = permisoRol;
	}

	public String[] getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(String[] usuariosSeleccionados) {
		this.usuarioSeleccionado = usuariosSeleccionados;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
