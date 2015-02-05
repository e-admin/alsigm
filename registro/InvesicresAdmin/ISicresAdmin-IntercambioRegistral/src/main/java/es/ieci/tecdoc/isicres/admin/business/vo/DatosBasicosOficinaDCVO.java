package es.ieci.tecdoc.isicres.admin.business.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public class DatosBasicosOficinaDCVO extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * =======================================================================
	 * Datos identificativos
	 *
	 * El campo "id" heredado de la clase Entity es el código único.
	 * =======================================================================
	 */


	
	/**
	 * Denominación de la oficina.
	 */
	private String nombre;

	/**
	 * Código externo utilizado por la entidad pública que aporta los datos para
	 * el alta de la oficina en el Directorio Común.
	 */
	private String idExternoFuente;


	/*
	 * =======================================================================
	 * Datos de dependencia jerárquica.
	 * =======================================================================
	 */

	/**
	 * Unidad responsable (de la que depende la oficina).
	 */
	private String idUnidadResponsable;

	/**
	 * Denominación de la unidad responsable (de la que depende la oficina).
	 */
	private String nombreUnidadResponsable;

	/**
	 * Código del nivel de administración (Estatal, Autonómica o Local)
	 */
	private String nivelAdministracion;

	/**
	 * Descripción del nivel de administración.
	 */
	private String descripcionNivelAdministracion;


	/*
	 * =======================================================================
	 * Datos de operativos.
	 * =======================================================================
	 */

	/**
	 * Designa si una oficina se encuentra adherida a la plataforma de
	 * intercambio registral del SIR.
	 */
	private String indicadorAdhesionSIR;

	/**
	 * Designa si la oficina tiene competencias de REGISTRO.
	 */
	private String indicadorOficinaRegistro;

	/**
	 * Designa si la oficina tiene competencias de información.
	 */
	private String indicadorOficinaInformacion;

	/**
	 * Designa si la oficina tiene competencias de tramitación.
	 */
	private String indicadorOficinaTramitacion;

	/**
	 * Designa si se trata de una oficina de registro que realiza el servicio
	 * vía medios electrónicos (Registro Electrónico).
	 */
	private String indicadorRegistroElectronico;

	/**
	 * Designa si una oficina puede registrar documentos destinados a Unidades
	 * de cualquier ámbito de la Administración pública.
	 */
	private String indicadorIntercambioSinRestriccion;

	/**
	 * Designa si una oficina de ámbito local puede registrar documentos destinados a Unidades de la AGE.
	 */
	private String indicadorIntercambioLocalEstatal;

	/**
	 * Designa si una oficina de ámbito local puede registrar documentos
	 * destinados a Unidades de su Administración Autonómica, es decir de la
	 * Comunidad Autónoma donde opera la oficina.
	 */
	private String indicadorIntercambioLocalAutonomicoRestringido;

	/**
	 * Designa si una oficina de ámbito local puede registrar documentos
	 * destinados a Unidades de cualquier Administración Autonómica.
	 */
	private String indicadorIntercambioLocalAutonomicoGeneral;

	/**
	 * Designa si una oficina de ámbito local puede registrar documentos
	 * destinados a otras entidades locales de la Comunidad Autónoma donde opera
	 * la oficina.
	 */
	private String indicadorIntercambioLocalLocalRestringido;

	/**
	 * Designa si una oficina de ámbito local puede registrar documentos
	 * destinados a cualquier otra entidad local.
	 */
	private String indicadorIntercambioLocalLocalGeneral;

	/**
	 * Designa si una oficina de un ayuntamiento puede registrar documentos
	 * destinados a otros ayuntamientos de su misma Comunidad Autónoma.
	 */
	private String indicadorIntercambioAytoAytoRestringido;


	/*
	 * =======================================================================
	 * Datos de vigencia.
	 * =======================================================================
	 */

	/**
	 * Estado de la entidad.
	 */
	private String estado;

	/**
	 * Descripción del estado.
	 */
	private String descripcionEstado;

	/**
	 * Fecha de creación oficial.
	 */
	private Date fechaCreacion;

	/**
	 * Fecha de extinción final.
	 */
	private Date fechaExtincion;

	/**
	 * Fecha de anulación.
	 */
	private Date fechaAnulacion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdExternoFuente() {
		return idExternoFuente;
	}

	public void setIdExternoFuente(String idExternoFuente) {
		this.idExternoFuente = idExternoFuente;
	}

	public String getIdUnidadResponsable() {
		return idUnidadResponsable;
	}

	public void setIdUnidadResponsable(String idUnidadResponsable) {
		this.idUnidadResponsable = idUnidadResponsable;
	}

	public String getNombreUnidadResponsable() {
		return nombreUnidadResponsable;
	}

	public void setNombreUnidadResponsable(String nombreUnidadResponsable) {
		this.nombreUnidadResponsable = nombreUnidadResponsable;
	}

	public String getNivelAdministracion() {
		return nivelAdministracion;
	}

	public void setNivelAdministracion(String nivelAdministracion) {
		this.nivelAdministracion = nivelAdministracion;
	}

	public String getDescripcionNivelAdministracion() {
		return descripcionNivelAdministracion;
	}

	public void setDescripcionNivelAdministracion(
			String descripcionNivelAdministracion) {
		this.descripcionNivelAdministracion = descripcionNivelAdministracion;
	}

	public String getIndicadorAdhesionSIR() {
		return indicadorAdhesionSIR;
	}

	public void setIndicadorAdhesionSIR(String indicadorAdhesionSIR) {
		this.indicadorAdhesionSIR = indicadorAdhesionSIR;
	}

	public String getIndicadorOficinaRegistro() {
		return indicadorOficinaRegistro;
	}

	public void setIndicadorOficinaRegistro(String indicadorOficinaRegistro) {
		this.indicadorOficinaRegistro = indicadorOficinaRegistro;
	}

	public String getIndicadorOficinaInformacion() {
		return indicadorOficinaInformacion;
	}

	public void setIndicadorOficinaInformacion(String indicadorOficinaInformacion) {
		this.indicadorOficinaInformacion = indicadorOficinaInformacion;
	}

	public String getIndicadorOficinaTramitacion() {
		return indicadorOficinaTramitacion;
	}

	public void setIndicadorOficinaTramitacion(String indicadorOficinaTramitacion) {
		this.indicadorOficinaTramitacion = indicadorOficinaTramitacion;
	}

	public String getIndicadorRegistroElectronico() {
		return indicadorRegistroElectronico;
	}

	public void setIndicadorRegistroElectronico(String indicadorRegistroElectronico) {
		this.indicadorRegistroElectronico = indicadorRegistroElectronico;
	}

	public String getIndicadorIntercambioSinRestriccion() {
		return indicadorIntercambioSinRestriccion;
	}

	public void setIndicadorIntercambioSinRestriccion(
			String indicadorIntercambioSinRestriccion) {
		this.indicadorIntercambioSinRestriccion = indicadorIntercambioSinRestriccion;
	}

	public String getIndicadorIntercambioLocalEstatal() {
		return indicadorIntercambioLocalEstatal;
	}

	public void setIndicadorIntercambioLocalEstatal(
			String indicadorIntercambioLocalEstatal) {
		this.indicadorIntercambioLocalEstatal = indicadorIntercambioLocalEstatal;
	}

	public String getIndicadorIntercambioLocalAutonomicoRestringido() {
		return indicadorIntercambioLocalAutonomicoRestringido;
	}

	public void setIndicadorIntercambioLocalAutonomicoRestringido(
			String indicadorIntercambioLocalAutonomicoRestringido) {
		this.indicadorIntercambioLocalAutonomicoRestringido = indicadorIntercambioLocalAutonomicoRestringido;
	}

	public String getIndicadorIntercambioLocalAutonomicoGeneral() {
		return indicadorIntercambioLocalAutonomicoGeneral;
	}

	public void setIndicadorIntercambioLocalAutonomicoGeneral(
			String indicadorIntercambioLocalAutonomicoGeneral) {
		this.indicadorIntercambioLocalAutonomicoGeneral = indicadorIntercambioLocalAutonomicoGeneral;
	}

	public String getIndicadorIntercambioLocalLocalRestringido() {
		return indicadorIntercambioLocalLocalRestringido;
	}

	public void setIndicadorIntercambioLocalLocalRestringido(
			String indicadorIntercambioLocalLocalRestringido) {
		this.indicadorIntercambioLocalLocalRestringido = indicadorIntercambioLocalLocalRestringido;
	}

	public String getIndicadorIntercambioLocalLocalGeneral() {
		return indicadorIntercambioLocalLocalGeneral;
	}

	public void setIndicadorIntercambioLocalLocalGeneral(
			String indicadorIntercambioLocalLocalGeneral) {
		this.indicadorIntercambioLocalLocalGeneral = indicadorIntercambioLocalLocalGeneral;
	}

	public String getIndicadorIntercambioAytoAytoRestringido() {
		return indicadorIntercambioAytoAytoRestringido;
	}

	public void setIndicadorIntercambioAytoAytoRestringido(
			String indicadorIntercambioAytoAytoRestringido) {
		this.indicadorIntercambioAytoAytoRestringido = indicadorIntercambioAytoAytoRestringido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaExtincion() {
		return fechaExtincion;
	}

	public void setFechaExtincion(Date fechaExtincion) {
		this.fechaExtincion = fechaExtincion;
	}

	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}


}
