package salas.form;

import salas.SalasConsultaConstants;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.RegistroConsultaSalaVO;
import salas.vos.UsuarioSalasConsultaVO;

import common.Constants;
import common.forms.CustomForm;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Formulario para la gestión de registros de consulta
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class RegistroConsultaSalaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del Registro de consulta de sala
	 */
	private String idRegistro = null;

	/**
	 * Identificador del usuario de consulta
	 */
	private String idUsrCSala = null;

	/**
	 * Fecha de entrada a la sala
	 */
	private String fechaEntrada = null;

	/**
	 * Fecha de salida de la sala
	 */
	private String fechaSalida = null;

	/**
	 * Número de documento identificativo
	 */
	private String numDocIdentificacion = null;

	/**
	 * Nombre y Apellidos del usuario de consulta
	 */
	private String nombreApellidos = null;

	/**
	 * Identificador del usuario con acceso a la aplicación
	 */
	private String idScaUsr = null;

	/**
	 * Identificador del archivo sobre el que se realiza la consulta
	 */
	private String idArchivo = null;

	/**
	 * Ruta de la mesa asignada al usuario dentro de la sala
	 */
	private String mesaAsignada = null;

	/**
	 * Para indicar si queremos salas con equipo informatico o no
	 */
	private String equipoInformatico = null;

	/**
	 * Para búsqueda de registros y usuarios
	 */
	private String searchTokenNombreApellidos = null;
	private String numeroDoc_like = null;
	private String searchTokenNumDoc = null;
	private String searchTokenMesa = null;
	private String searchTokenArchivo = null;
	private String usuarioSeleccionado = null;

	private String searchTokenNombre = null;
	private String searchTokenApellidos = null;
	private Integer searchTokenTipoDoc = null;
	private boolean registrado = false;

	/**
	 * Para la selección de la mesa
	 */
	private String elementoSeleccionado = null;
	private String tipoSeleccionado = null;

	/**
	 * Para la selección de registros
	 */
	private String[] registrosSeleccionados = null;

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getIdUsrCSala() {
		return idUsrCSala;
	}

	public void setIdUsrCSala(String idUsrCSala) {
		this.idUsrCSala = idUsrCSala;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getNumDocIdentificacion() {
		return numDocIdentificacion;
	}

	public void setNumDocIdentificacion(String numDocIdentificacion) {
		this.numDocIdentificacion = numDocIdentificacion;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public String getIdScaUsr() {
		return idScaUsr;
	}

	public void setIdScaUsr(String idScaUsr) {
		this.idScaUsr = idScaUsr;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getMesaAsignada() {
		return mesaAsignada;
	}

	public void setMesaAsignada(String mesaAsignada) {
		this.mesaAsignada = mesaAsignada;
	}

	public String getSearchTokenNombreApellidos() {
		return searchTokenNombreApellidos;
	}

	public void setSearchTokenNombreApellidos(String searchTokenNombreApellidos) {
		this.searchTokenNombreApellidos = searchTokenNombreApellidos;
	}

	public String getNumeroDoc_like() {
		return numeroDoc_like;
	}

	public void setNumeroDoc_like(String numeroDocLike) {
		numeroDoc_like = numeroDocLike;
	}

	public String getSearchTokenNumDoc() {
		return searchTokenNumDoc;
	}

	public void setSearchTokenNumDoc(String searchTokenNumDoc) {
		this.searchTokenNumDoc = searchTokenNumDoc;
	}

	public String getSearchTokenMesa() {
		return searchTokenMesa;
	}

	public void setSearchTokenMesa(String searchTokenMesa) {
		this.searchTokenMesa = searchTokenMesa;
	}

	public String getSearchTokenArchivo() {
		return searchTokenArchivo;
	}

	public void setSearchTokenArchivo(String searchTokenArchivo) {
		this.searchTokenArchivo = searchTokenArchivo;
	}

	public String getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(String usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public String getEquipoInformatico() {
		return equipoInformatico;
	}

	public void setEquipoInformatico(String equipoInformatico) {
		this.equipoInformatico = equipoInformatico;
	}

	public String getElementoSeleccionado() {
		return elementoSeleccionado;
	}

	public void setElementoSeleccionado(String elementoSeleccionado) {
		this.elementoSeleccionado = elementoSeleccionado;
	}

	public String getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(String tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	public String[] getRegistrosSeleccionados() {
		return registrosSeleccionados;
	}

	public void setRegistrosSeleccionados(String[] registrosSeleccionados) {
		this.registrosSeleccionados = registrosSeleccionados;
	}

	public String getSearchTokenNombre() {
		return searchTokenNombre;
	}

	public void setSearchTokenNombre(String searchTokenNombre) {
		this.searchTokenNombre = searchTokenNombre;
	}

	public String getSearchTokenApellidos() {
		return searchTokenApellidos;
	}

	public void setSearchTokenApellidos(String searchTokenApellidos) {
		this.searchTokenApellidos = searchTokenApellidos;
	}

	public Integer getSearchTokenTipoDoc() {
		return searchTokenTipoDoc;
	}

	public void setSearchTokenTipoDoc(Integer searchTokenTipoDoc) {
		this.searchTokenTipoDoc = searchTokenTipoDoc;
	}

	public boolean isRegistrado() {
		return registrado;
	}

	public void setRegistrado(boolean registrado) {
		this.registrado = registrado;
	}

	public void set(RegistroConsultaSalaVO registroConsultaSalaVO) {
		reset();
		if (registroConsultaSalaVO != null) {
			this.idRegistro = registroConsultaSalaVO.getId();
			this.idUsrCSala = registroConsultaSalaVO.getIdScaUsr();
			if (registroConsultaSalaVO.getFechaEntrada() != null)
				this.fechaEntrada = DateUtils.formatDate(registroConsultaSalaVO
						.getFechaEntrada());
			if (registroConsultaSalaVO.getFechaSalida() != null)
				this.fechaSalida = DateUtils.formatDate(registroConsultaSalaVO
						.getFechaSalida());
			this.numDocIdentificacion = registroConsultaSalaVO
					.getNumDocIdentificacion();
			this.nombreApellidos = registroConsultaSalaVO.getNombreApellidos();
			this.idScaUsr = registroConsultaSalaVO.getIdScaUsr();
			this.idArchivo = registroConsultaSalaVO.getIdArchivo();
			this.mesaAsignada = registroConsultaSalaVO.getMesaAsignada();
		}
	}

	public void setUsuarioConsultaSala(
			UsuarioSalasConsultaVO usuarioConsultaSalaVO) {
		this.setIdUsrCSala(usuarioConsultaSalaVO.getId());
		this.setNumDocIdentificacion(usuarioConsultaSalaVO
				.getNumDocIdentificacion());
		this.setNombreApellidos(usuarioConsultaSalaVO.getNombre()
				+ Constants.STRING_SPACE + usuarioConsultaSalaVO.getApellidos());
		this.setIdScaUsr(usuarioConsultaSalaVO.getIdscausr());
	}

	public void populateRegistroConsultaSala(
			BusquedaRegistroConsultaSalaVO registroConsultaSalaVO) {
		registroConsultaSalaVO.setNombreApellidos(this
				.getSearchTokenNombreApellidos());
		registroConsultaSalaVO.setNumeroDoc_like(this.getNumeroDoc_like());
		registroConsultaSalaVO.setNumDocIdentificacion(this
				.getSearchTokenNumDoc());
		registroConsultaSalaVO.setIdArchivo(this.getSearchTokenArchivo());
		registroConsultaSalaVO.setMesaAsignada(this.getSearchTokenMesa());

		CustomDateRange rangeEntrada = CustomDateFormat
				.getDateRange(
						getMapFormValues(SalasConsultaConstants.ATTR_FECHA_COMP_ENTRADA),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FORMATO_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_A_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_M_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_D_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_S_ENTRADA)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_FORMATO_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_A_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_M_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_D_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_S_ENTRADA)),
						new CustomDate(
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_FORMATO_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_A_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_M_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_D_ENTRADA),
								getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_S_ENTRADA)));

		registroConsultaSalaVO
				.setFechaIniEntrada(rangeEntrada.getInitialDate());
		registroConsultaSalaVO.setFechaFinEntrada(rangeEntrada.getFinalDate());

		if (isRegistrado()) {
			registroConsultaSalaVO.setRegistrado(isRegistrado());
			CustomDateRange rangeSalida = CustomDateFormat
					.getDateRange(
							getMapFormValues(SalasConsultaConstants.ATTR_FECHA_COMP_SALIDA),
							new CustomDate(
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FORMATO_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_A_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_M_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_D_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_S_SALIDA)),
							new CustomDate(
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_FORMATO_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_A_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_M_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_D_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_INI_S_SALIDA)),
							new CustomDate(
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_FORMATO_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_A_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_M_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_D_SALIDA),
									getMapFormValues(SalasConsultaConstants.ATTR_FECHA_FIN_S_SALIDA)));

			registroConsultaSalaVO.setFechaIniSalida(rangeSalida
					.getInitialDate());
			registroConsultaSalaVO
					.setFechaFinSalida(rangeSalida.getFinalDate());
		}
	}

	public void populateUsuarioConsultaSala(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO) {
		busquedaUsuarioSalaConsultaVO.setNombre(this.getSearchTokenNombre());
		busquedaUsuarioSalaConsultaVO.setApellidos(this
				.getSearchTokenApellidos());
		busquedaUsuarioSalaConsultaVO.setTipoDocIdentificacion(this
				.getSearchTokenTipoDoc());
		busquedaUsuarioSalaConsultaVO.setNumDocIdentificacion(this
				.getSearchTokenNumDoc());
		busquedaUsuarioSalaConsultaVO.setVigente(Constants.TRUE_STRING);
	}

	public void populate(RegistroConsultaSalaVO registroConsultaSalaVO) {
		registroConsultaSalaVO.setId(this.idRegistro);
		registroConsultaSalaVO.setIdUsrCSala(this.idUsrCSala);
		if (StringUtils.isNotEmpty(this.fechaEntrada))
			registroConsultaSalaVO.setFechaEntrada(DateUtils
					.getDate(this.fechaEntrada));
		if (StringUtils.isNotEmpty(this.fechaSalida))
			registroConsultaSalaVO.setFechaSalida(DateUtils
					.getDate(this.fechaSalida));

		registroConsultaSalaVO
				.setNumDocIdentificacion(this.numDocIdentificacion);
		registroConsultaSalaVO.setNombreApellidos(this.nombreApellidos);
		registroConsultaSalaVO.setIdScaUsr(this.idScaUsr);
		registroConsultaSalaVO.setIdArchivo(this.idArchivo);
		registroConsultaSalaVO.setMesaAsignada(this.mesaAsignada);
	}

	public void reset() {
		this.idRegistro = null;
		this.idUsrCSala = null;
		this.fechaEntrada = null;
		this.fechaSalida = null;
		this.numDocIdentificacion = null;
		this.nombreApellidos = null;
		this.idScaUsr = null;
		this.idArchivo = null;
		this.mesaAsignada = null;
	}
}