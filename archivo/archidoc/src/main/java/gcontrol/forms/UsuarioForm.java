package gcontrol.forms;

import java.util.Date;

import common.forms.CustomForm;
import common.model.UserInfo;
import common.util.DateUtils;

/**
 * Formulario para la recogida de datos en las operaciones implicadas en la
 * gestión de usuarios
 */
public class UsuarioForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String tipoUsuario = null;
	String nombre = null;
	String apellidos = null;
	String email = null;
	String direccion = null;
	String fechaMaxVigencia = null;
	String descripcion = null;
	String sistemaExterno = null;
	// id del usuario en el sistema de usuarios externo
	String idUsrsExtGestor = null;
	// id del usuario en el sistema de organizacion
	String idUsrSistOrg = null;
	String nombreUsuarioABuscar = null;
	String deshabilitado = null;

	/** Identificador del órgano. */
	String idOrganoInterno = null;

	/** Fecha de inicio del período de validez. */
	String fechaIniPeriodoValidez = null;

	/** Fecha de fin del período de validez. */
	String fechaFinPeriodoValidez = null;

	/** roles a anadir **/
	String[] rolesSeleccionados = null;

	/** grupos a anadir **/
	String[] gruposSeleccionados = null;

	String idOrganoExterno;

	String idUsrSisExtGestorSeleccionado;

	String idUsuarioSeleccionado = null;

	String idModuloSeleccionado = null;

	String[] usuariosABorrar = null;

	String searchTokenOrgano = null;
	String searchTokenNombre = null;
	String searchTokenApellidos = null;

	// Estos se utlizan para la búsqueda de órganos.
	String codigoOrgano = null;
	String nombreOrgano = null;

	/**
	 * @return Returns the idOrganoExterno.
	 */
	public String getIdOrganoExterno() {
		return idOrganoExterno;
	}

	/**
	 * @param idOrganoExterno
	 *            The idOrganoExterno to set.
	 */
	public void setIdOrganoExterno(String idOrganoExterno) {
		this.idOrganoExterno = idOrganoExterno;
	}

	/**
	 * @return Returns the desahibilitado.
	 */
	public String getDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param desahibilitado
	 *            The desahibilitado to set.
	 */
	public void setDeshabilitado(String desahibilitado) {
		this.deshabilitado = desahibilitado;
	}

	/**
	 * @return Returns the idUsrSisExtGestorSeleccionado.
	 */
	public String getIdUsrSisExtGestorSeleccionado() {
		return idUsrSisExtGestorSeleccionado;
	}

	/**
	 * @param idUsrSisExtGestorSeleccionado
	 *            The idUsrSisExtGestorSeleccionado to set.
	 */
	public void setIdUsrSisExtGestorSeleccionado(
			String idUsrSisExtGestorSeleccionado) {
		this.idUsrSisExtGestorSeleccionado = idUsrSisExtGestorSeleccionado;
	}

	/**
	 * @return Returns the usuariosABorrar.
	 */
	public String[] getUsuariosABorrar() {
		return usuariosABorrar;
	}

	/**
	 * @param usuariosABorrar
	 *            The usuariosABorrar to set.
	 */
	public void setUsuariosABorrar(String[] usuariosABorrar) {
		this.usuariosABorrar = usuariosABorrar;
	}

	/**
	 * @return Returns the idModuloSeleccionado.
	 */
	public String getIdModuloSeleccionado() {
		return idModuloSeleccionado;
	}

	/**
	 * @param idModuloSeleccionado
	 *            The idModuloSeleccionado to set.
	 */
	public void setIdModuloSeleccionado(String idModuloSeleccionado) {
		this.idModuloSeleccionado = idModuloSeleccionado;
	}

	/**
	 * @return Returns the gruposAEliminar.
	 */
	// public String[] getGruposAEliminar() {
	// return gruposAEliminar;
	// }
	/**
	 * @param gruposAEliminar
	 *            The gruposAEliminar to set.
	 */
	// public void setGruposAEliminar(String[] gruposAEliminar) {
	// this.gruposAEliminar = gruposAEliminar;
	// }
	/**
	 * @return Returns the gruposSeleccionados.
	 */
	public String[] getGruposSeleccionados() {
		return gruposSeleccionados;
	}

	/**
	 * @param gruposSeleccionados
	 *            The gruposSeleccionados to set.
	 */
	public void setGruposSeleccionados(String[] gruposSeleccionados) {
		this.gruposSeleccionados = gruposSeleccionados;
	}

	/**
	 * @return Returns the rolesAEliminar.
	 */
	// public String[] getRolesAEliminar() {
	// return rolesAEliminar;
	// }
	/**
	 * @param rolesAEliminar
	 *            The rolesAEliminar to set.
	 */
	// public void setRolesAEliminar(String[] rolesAEliminar) {
	// this.rolesAEliminar = rolesAEliminar;
	// }
	/**
	 * @return Returns the idUsuarioSeleccionado.
	 */
	public String getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	/**
	 * @param idUsuarioSeleccionado
	 *            The idUsuarioSeleccionado to set.
	 */
	public void setIdUsuarioSeleccionado(String idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}

	/**
	 * @return Returns the fechaFinPeriodoValidez.
	 */
	public String getFechaFinPeriodoValidez() {
		return fechaFinPeriodoValidez;
	}

	/**
	 * @param fechaFinPeriodoValidez
	 *            The fechaFinPeriodoValidez to set.
	 */
	public void setFechaFinPeriodoValidez(String fechaFinPeriodoValidez) {
		this.fechaFinPeriodoValidez = fechaFinPeriodoValidez;
	}

	/**
	 * @return Returns the fechaIniPeriodoValidez.
	 */
	public String getFechaIniPeriodoValidez() {
		return fechaIniPeriodoValidez;
	}

	/**
	 * @param fechaIniPeriodoValidez
	 *            The fechaIniPeriodoValidez to set.
	 */
	public void setFechaIniPeriodoValidez(String fechaIniPeriodoValidez) {
		this.fechaIniPeriodoValidez = fechaIniPeriodoValidez;
	}

	/**
	 * @return Returns the idOrgano.
	 */
	public String getIdOrganoInterno() {
		return idOrganoInterno;
	}

	/**
	 * @param idOrgano
	 *            The idOrgano to set.
	 */
	public void setIdOrganoInterno(String idOrgano) {
		this.idOrganoInterno = idOrgano;
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
	 * @return Returns the nombreUsuarioABuscar.
	 */
	public String getNombreUsuarioABuscar() {
		return nombreUsuarioABuscar;
	}

	/**
	 * @param nombreUsuarioABuscar
	 *            The nombreUsuarioABuscar to set.
	 */
	public void setNombreUsuarioABuscar(String nombreUsuarioABuscar) {
		this.nombreUsuarioABuscar = nombreUsuarioABuscar;
	}

	/**
	 * @return Returns the idUsrSistOrg.
	 */
	public String getIdUsrSistOrg() {
		return idUsrSistOrg;
	}

	/**
	 * @param idUsrSistOrg
	 *            The idUsrSistOrg to set.
	 */
	public void setIdUsrSistOrg(String idUsrSistOrg) {
		this.idUsrSistOrg = idUsrSistOrg;
	}

	/**
	 * @return Returns the idUsrsExtGestor.
	 */
	public String getIdUsrsExtGestor() {
		return idUsrsExtGestor;
	}

	/**
	 * @param idUsrsExtGestor
	 *            The idUsrsExtGestor to set.
	 */
	public void setIdUsrsExtGestor(String idUsrsExtGestor) {
		this.idUsrsExtGestor = idUsrsExtGestor;
	}

	/**
	 * @param direccion
	 *            The direccion to set.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return Returns the sistemaExterno.
	 */
	public String getSistemaExterno() {
		return sistemaExterno;
	}

	/**
	 * @param sistemaExterno
	 *            The sistemaExterno to set.
	 */
	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}

	/**
	 * @return Returns the tipoUsuario.
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * @param tipoUsuario
	 *            The tipoUsuario to set.
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	/**
	 * @return Returns the apellidos.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            The apellidos to set.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return Returns the descripción.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripción
	 *            The descripción to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the dirección.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the fechaMaxVigencia.
	 */
	public Date getFechaMaxVigenciaDate() {
		return DateUtils.getDate(fechaMaxVigencia);
	}

	/**
	 * @return Returns the fechaMaxVigencia.
	 */
	public String getFechaMaxVigencia() {
		return fechaMaxVigencia;
	}

	/**
	 * @param fechaMaxVigencia
	 *            The fechaMaxVigencia to set.
	 */
	public void setFechaMaxVigencia(String fechaMaxVigencia) {
		this.fechaMaxVigencia = fechaMaxVigencia;
	}

	public String getCodigoOrgano() {
		return codigoOrgano;
	}

	public void setCodigoOrgano(String codigoOrgano) {
		this.codigoOrgano = codigoOrgano;
	}

	public String getNombreOrgano() {
		return nombreOrgano;
	}

	public void setNombreOrgano(String nombreOrgano) {
		this.nombreOrgano = nombreOrgano;
	}

	/**
	 * @return Returns the habilitado.
	 */
	// public boolean getHabilitado() {
	// return habilitado;
	// }
	// /**
	// * @param habilitado The habilitado to set.
	// */
	// public void setHabilitado(boolean habilitado) {
	// this.habilitado = habilitado;
	// }
	public boolean getHabilitado() {
		String desah = getDeshabilitado();
		if (desah != null)
			return getDeshabilitado().equalsIgnoreCase("false");
		return true;
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

	public String getSearchTokenApellidos() {
		return searchTokenApellidos;
	}

	public void setSearchTokenApellidos(String searchTokenApellidos) {
		this.searchTokenApellidos = searchTokenApellidos;
	}

	public String getSearchTokenNombre() {
		return searchTokenNombre;
	}

	public void setSearchTokenNombre(String searchTokenNombre) {
		this.searchTokenNombre = searchTokenNombre;
	}

	public String getSearchTokenOrgano() {
		return searchTokenOrgano;
	}

	public void setSearchTokenOrgano(String searchTokenOrgano) {
		this.searchTokenOrgano = searchTokenOrgano;
	}

	public void populateByUserInfo(UserInfo userInfo,
			String idSistGestorUsuarios, boolean isUsuarioConSistIntOrganizacion) {
		if (userInfo != null) {
			setEmail(userInfo.getEmail());
			setDireccion(userInfo.getAddress());
			setNombre(userInfo.getName());
			setApellidos(userInfo.getSurname());
			setIdUsrsExtGestor(userInfo.getExternalUserId());
			if (!isUsuarioConSistIntOrganizacion) {
				setIdUsrSistOrg(userInfo.getOrganizationUserId());
			}

			setSistemaExterno(idSistGestorUsuarios);
		}
	}

	public void resetInBuscarUsuario() {
		setNombreUsuarioABuscar(null);
		setIdUsrSisExtGestorSeleccionado(null);

	}

	public void resetInChangedTipoUsuario() {
		nombre = null;
		apellidos = null;
		email = null;
		direccion = null;
		// habilitado = false;
		fechaMaxVigencia = null;
		descripcion = null;
		sistemaExterno = null;
		// id del usuario en el sistema de usuarios externo
		idUsrsExtGestor = null;
		// id del usuario en el sistema de organizacion
		idUsrSistOrg = null;
		nombreUsuarioABuscar = null;

		/** Identificador del órgano externo. */
		idOrganoExterno = null;

		/** Identificador del órgano interno. */
		idOrganoInterno = null;

		/** Fecha de inicio del período de validez. */
		fechaIniPeriodoValidez = null;

		/** Fecha de fin del período de validez. */
		fechaFinPeriodoValidez = null;
		setCodigoOrgano(null);
		setNombreOrgano(null);
	}

	public void resetInVerGrupos() {
		gruposSeleccionados = null;
	}

	public void resetInVerRoles() {
		rolesSeleccionados = null;
	}

	public void resetInNuevoUsuario() {
		setTipoUsuario(null);
		setNombreUsuarioABuscar(null);
		setEmail(null);
		setDireccion(null);
		setNombre(null);
		setApellidos(null);
		setIdUsrsExtGestor(null);
		setIdUsrSistOrg(null);
		setIdOrganoInterno(null);
		setSistemaExterno(null);
		setFechaFinPeriodoValidez(null);
		setFechaIniPeriodoValidez(null);
		setFechaMaxVigencia(null);
		setCodigoOrgano(null);
		setNombreOrgano(null);
	}

	public void resetBeforeDelete() {
		setUsuariosABorrar(null);
	}

	public void clear() {
		tipoUsuario = null;
		nombre = null;
		apellidos = null;
		email = null;
		direccion = null;
		fechaMaxVigencia = null;
		descripcion = null;
		sistemaExterno = null;
		idUsrsExtGestor = null;
		idUsrSistOrg = null;
		nombreUsuarioABuscar = null;
		deshabilitado = null;
		idOrganoInterno = null;
		fechaIniPeriodoValidez = null;
		fechaFinPeriodoValidez = null;
		rolesSeleccionados = null;
		gruposSeleccionados = null;
		idOrganoExterno = null;
		idUsrSisExtGestorSeleccionado = null;
		idUsuarioSeleccionado = null;
		idModuloSeleccionado = null;
		usuariosABorrar = null;
		searchTokenOrgano = null;
		searchTokenNombre = null;
		searchTokenApellidos = null;
		codigoOrgano = null;
		nombreOrgano = null;
	}
}
