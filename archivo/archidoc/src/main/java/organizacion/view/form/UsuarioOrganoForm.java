package organizacion.view.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import organizacion.model.vo.UserOrganoVO;

import common.forms.CustomForm;

/**
 * Formulario para la recogida de datos en las operaciones implicadas en la
 * gestión de usuarios
 */
public class UsuarioOrganoForm extends CustomForm {

	private static final long serialVersionUID = 3713256412546854750L;

	String idUsuario;
	String nombreUsuario;
	String idOrgano;

	String nombreOrgano;
	String nombreUsuarioABuscar;
	String tipoSistema;
	String idUsrSisExtGestorSeleccionado;
	String[] usuariosABorrar = null;
	String[] usuariosSeleccionados = null;
	String searchTokenNombre = null;
	String origen = null;

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
	 * @return el nombreUsuarioABuscar
	 */
	public String getNombreUsuarioABuscar() {
		return nombreUsuarioABuscar;
	}

	/**
	 * @return el tipoSistema
	 */
	public String getTipoSistema() {
		return tipoSistema;
	}

	/**
	 * @param tipoSistema
	 *            el tipoSistema a establecer
	 */
	public void setTipoSistema(String tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	/**
	 * @param nombreUsuarioABuscar
	 *            el nombreUsuarioABuscar a establecer
	 */
	public void setNombreUsuarioABuscar(String nombreUsuarioABuscar) {
		this.nombreUsuarioABuscar = nombreUsuarioABuscar;
	}

	/**
	 * @return el idUsrSisExtGestorSeleccionado
	 */
	public String getIdUsrSisExtGestorSeleccionado() {
		return idUsrSisExtGestorSeleccionado;
	}

	/**
	 * @param idUsrSisExtGestorSeleccionado
	 *            el idUsrSisExtGestorSeleccionado a establecer
	 */
	public void setIdUsrSisExtGestorSeleccionado(
			String idUsrSisExtGestorSeleccionado) {
		this.idUsrSisExtGestorSeleccionado = idUsrSisExtGestorSeleccionado;
	}

	/**
	 * @return el usuariosABorrar
	 */
	public String[] getUsuariosABorrar() {
		return usuariosABorrar;
	}

	/**
	 * @param usuariosABorrar
	 *            el usuariosABorrar a establecer
	 */
	public void setUsuariosABorrar(String[] usuariosABorrar) {
		this.usuariosABorrar = usuariosABorrar;
	}

	/**
	 * @return el nombreOrgano
	 */
	public String getNombreOrgano() {
		return nombreOrgano;
	}

	/**
	 * @param nombreOrgano
	 *            el nombreOrgano a establecer
	 */
	public void setNombreOrgano(String nombreOrgano) {
		this.nombreOrgano = nombreOrgano;
	}

	/**
	 * @return el searchTokenNombre
	 */
	public String getSearchTokenNombre() {
		return searchTokenNombre;
	}

	/**
	 * @param searchTokenNombre
	 *            el searchTokenNombre a establecer
	 */
	public void setSearchTokenNombre(String searchTokenNombre) {
		this.searchTokenNombre = searchTokenNombre;
	}

	/**
	 * @return el usuariosSeleccionados
	 */
	public String[] getUsuariosSeleccionados() {
		return usuariosSeleccionados;
	}

	/**
	 * @param usuariosSeleccionados
	 *            el usuariosSeleccionados a establecer
	 */
	public void setUsuariosSeleccionados(String[] usuariosSeleccionados) {
		this.usuariosSeleccionados = usuariosSeleccionados;
	}

	/**
	 * @return el origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            el origen a establecer
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public void set(UserOrganoVO userOrganoVO) {
		if (userOrganoVO != null) {
			setIdUsuario(userOrganoVO.getIdUsuario());
			setNombreUsuario(userOrganoVO.getNombreUsuario());
			setIdOrgano(userOrganoVO.getIdOrgano());
		}
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(UserOrganoVO userOrganoVO) {
		if (userOrganoVO != null) {
			userOrganoVO.setIdUsuario(this.idUsuario);
			userOrganoVO.setNombreUsuario(this.nombreUsuario);
			userOrganoVO.setIdOrgano(this.idOrgano);
		}
	}

	/**
	 * Valida el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		return errors;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	public void clear() {
		this.idUsuario = null;
		this.nombreUsuario = null;
		this.idOrgano = null;
	}
}