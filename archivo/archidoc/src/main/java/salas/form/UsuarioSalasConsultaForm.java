package salas.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;

import common.Constants;
import common.Messages;
import common.db.DBUtils;
import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UsuarioSalasConsultaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	private String id = null;
	private Integer tipoDocIdentificacion = null;
	private String numDocIdentificacion = null;
	private String nombre = null;
	private String apellidos = null;
	private String nacionalidad = null;
	private String telefonos = null;
	private String email = null;
	private String direccion = null;
	private Boolean vigente = null;
	private String idscausr = null;
	private String fechaAlta = null;

	private List listaArchivos = new ArrayList();

	private String[] idsArchivo = new String[0];

	private String filtroNombreUsuarioExterno = null;

	private String idUsrSisExtGestorSeleccionado = null;
	private String nombreUsuarioABuscar = null;
	private String vigenteSearch = null;
	private Boolean accesoAplicacion = null;
	private String idArchivo = null;
	private String fechaExp = null;
	private Integer numVeces;

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el tipoDocIdentificacion
	 */
	public Integer getTipoDocIdentificacion() {
		return tipoDocIdentificacion;
	}

	/**
	 * @param tipoDocIdentificacion
	 *            el tipoDocIdentificacion a fijar
	 */
	public void setTipoDocIdentificacion(Integer tipoDocIdentificacion) {
		this.tipoDocIdentificacion = tipoDocIdentificacion;
	}

	/**
	 * @return el numDocIdentificacion
	 */
	public String getNumDocIdentificacion() {
		return numDocIdentificacion;
	}

	/**
	 * @param numDocIdentificacion
	 *            el numDocIdentificacion a fijar
	 */
	public void setNumDocIdentificacion(String numDocIdentificacion) {
		this.numDocIdentificacion = numDocIdentificacion;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            el apellidos a fijar
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return el nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad
	 *            el nacionalidad a fijar
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return el telefonos
	 */
	public String getTelefonos() {
		return telefonos;
	}

	/**
	 * @param telefonos
	 *            el telefonos a fijar
	 */
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	/**
	 * @return el email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            el email a fijar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return el direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            el direccion a fijar
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return el vigente
	 */
	public Boolean getVigente() {
		return vigente;
	}

	/**
	 * @param vigente
	 *            el vigente a fijar
	 */
	public void setVigente(Boolean vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return el idscausr
	 */
	public String getIdscausr() {
		return idscausr;
	}

	/**
	 * @param idscausr
	 *            el idscausr a fijar
	 */
	public void setIdscausr(String idscausr) {
		this.idscausr = idscausr;
	}

	public void populate(UsuarioSalasConsultaVO vo) throws Exception {
		vo.setId(getId());
		vo.setTipoDocIdentificacion(getTipoDocIdentificacion());
		vo.setNumDocIdentificacion(getNumDocIdentificacion());
		vo.setNombre(getNombre());
		vo.setApellidos(getApellidos());
		vo.setNacionalidad(getNacionalidad());
		vo.setTelefonos(getTelefonos());
		vo.setEmail(getEmail());
		vo.setDireccion(getDireccion());

		String vigente = DBUtils.getStringValue(false);
		if (getVigente() != null && getVigente().booleanValue()) {
			vigente = DBUtils.getStringValue(getVigente().booleanValue());
		}

		vo.setVigente(vigente);

		if (getAccesoAplicacion() != null
				&& Boolean.TRUE.equals(getAccesoAplicacion())) {
			vo.setIdscausr(getIdscausr());
		} else {
			vo.setIdscausr(null);
		}

		vo.setIdsArchivos(getIdsArchivo());
	}

	public void populate(BusquedaUsuarioSalaConsultaVO vo) throws Exception {

		if (getTipoDocIdentificacion() != null
				&& getTipoDocIdentificacion().intValue() != 0) {
			vo.setTipoDocIdentificacion(getTipoDocIdentificacion());
		}

		if (StringUtils.isNotEmpty(getNumDocIdentificacion())) {
			vo.setNumDocIdentificacion(getNumDocIdentificacion());
		}

		if (StringUtils.isNotEmpty(getNombre())) {
			vo.setNombre(getNombre());
		}

		if (StringUtils.isNotEmpty(getApellidos())) {
			vo.setApellidos(getApellidos());
		}

		if (StringUtils.isNotEmpty(getVigenteSearch())) {
			vo.setVigente(getVigenteSearch());
		}

		if (StringUtils.isNotEmpty(getIdArchivo())) {
			vo.setIdsArchivo(new String[] { getIdArchivo() });
		}

		CustomDateRange rangeAlta = CustomDateFormat
				.getDateRange(
						getMapFormValues(SalasConsultaConstants.ATTR_FECHA_COMP_ALTA),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FORMATO_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_A_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_M_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_D_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_S_ALTA)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_FORMATO_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_A_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_M_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_D_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_S_ALTA)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_FORMATO_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_A_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_M_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_D_ALTA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_S_ALTA)));

		vo.setFechaIniAlta(rangeAlta.getInitialDate());
		vo.setFechaFinAlta(rangeAlta.getFinalDate());
	}

	public CustomDateRange getRangoFechaExp() {
		CustomDateRange rangeExp = CustomDateFormat
				.getDateRange(
						getMapFormValues(SalasConsultaConstants.ATTR_FECHA_COMP_EXP),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FORMATO_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_A_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_M_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_D_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_S_EXP)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_FORMATO_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_A_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_M_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_D_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_S_EXP)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_FORMATO_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_A_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_M_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_D_EXP),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_S_EXP)));

		return rangeExp;
	}

	public void set(UsuarioSalasConsultaVO vo) throws Exception {
		setId(vo.getId());

		if (vo.getTipoDocIdentificacion() != null
				&& vo.getTipoDocIdentificacion().intValue() > 0) {
			setTipoDocIdentificacion(vo.getTipoDocIdentificacion());
		}
		setNumDocIdentificacion(vo.getNumDocIdentificacion());
		setNombre(vo.getNombre());
		setApellidos(vo.getApellidos());
		setNacionalidad(vo.getNacionalidad());
		setTelefonos(vo.getTelefonos());
		setEmail(vo.getEmail());
		setDireccion(vo.getDireccion());
		setVigente(new Boolean(DBUtils.getBooleanValue(vo.getVigente())));

		if (StringUtils.isNotEmpty(vo.getIdscausr())) {
			setAccesoAplicacion(Boolean.TRUE);
			setIdscausr(vo.getIdscausr());
		} else {
			setAccesoAplicacion(Boolean.FALSE);
		}
		setListaArchivos(vo.getListaArchivos());

	}

	public ActionErrors validate(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (this.getTipoDocIdentificacion() == null
				|| this.getTipoDocIdentificacion().intValue() == 0) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SalasConsultaConstants.ETIQUETA_TIPO_DOCUMENTO,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getNumDocIdentificacion())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SalasConsultaConstants.ETIQUETA_NUM_DOCUMENTO,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getNombre())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(SalasConsultaConstants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getApellidos())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(
									SalasConsultaConstants.ETIQUETA_APELLIDOS,
									request.getLocale())));
		}

		// Si es nuevo, comprobar que se han seleccionado archivos.
		/*
		 * if(StringUtils.isEmpty(getId())){
		 * if(ArrayUtils.isEmpty(getIdsArchivo())){
		 * errors.add(ActionMessages.GLOBAL_MESSAGE, new
		 * ActionError(Constants.ERROR_REQUIRED,
		 * Messages.getString(SalasConsultaConstants
		 * .ETIQUETA_ARCHIVOS_ASOCIADOS, request.getLocale())));
		 * 
		 * } }
		 */

		return errors;

	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setIdsArchivo(String[] idsArchivo) {
		this.idsArchivo = idsArchivo;
	}

	public String[] getIdsArchivo() {
		return idsArchivo;
	}

	public void setIdUsrSisExtGestorSeleccionado(
			String idUsrSisExtGestorSeleccionado) {
		this.idUsrSisExtGestorSeleccionado = idUsrSisExtGestorSeleccionado;
	}

	public String getIdUsrSisExtGestorSeleccionado() {
		return idUsrSisExtGestorSeleccionado;
	}

	public void setNombreUsuarioABuscar(String nombreUsuarioABuscar) {
		this.nombreUsuarioABuscar = nombreUsuarioABuscar;
	}

	public String getNombreUsuarioABuscar() {
		return nombreUsuarioABuscar;
	}

	public void setVigenteSearch(String vigenteSearch) {
		this.vigenteSearch = vigenteSearch;
	}

	public String getVigenteSearch() {
		return vigenteSearch;
	}

	public void setAccesoAplicacion(Boolean accesoAplicacion) {
		this.accesoAplicacion = accesoAplicacion;
	}

	public Boolean getAccesoAplicacion() {
		return accesoAplicacion;
	}

	public void setFiltroNombreUsuarioExterno(String filtroNombreUsuarioExterno) {
		this.filtroNombreUsuarioExterno = filtroNombreUsuarioExterno;
	}

	public String getFiltroNombreUsuarioExterno() {
		return filtroNombreUsuarioExterno;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(String fechaExp) {
		this.fechaExp = fechaExp;
	}

	public Integer getNumVeces() {
		return numVeces;
	}

	public void setNumVeces(Integer numVeces) {
		this.numVeces = numVeces;
	}

	public void reset() {
		id = null;
		tipoDocIdentificacion = null;
		numDocIdentificacion = null;
		nombre = null;
		apellidos = null;
		nacionalidad = null;
		telefonos = null;
		email = null;
		direccion = null;
		vigente = null;
		idscausr = null;
		listaArchivos = new ArrayList();
		idsArchivo = new String[0];
		filtroNombreUsuarioExterno = null;
		idUsrSisExtGestorSeleccionado = null;
		nombreUsuarioABuscar = null;
		vigenteSearch = null;
		accesoAplicacion = null;
		idArchivo = null;
	}

}
