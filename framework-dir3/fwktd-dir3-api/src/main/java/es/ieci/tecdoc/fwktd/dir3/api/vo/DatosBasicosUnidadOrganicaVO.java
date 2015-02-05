package es.ieci.tecdoc.fwktd.dir3.api.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Datos básicos de una unidad orgánica.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DatosBasicosUnidadOrganicaVO extends Entity {

	private static final long serialVersionUID = 9177726544679259698L;

	/*
	 * =======================================================================
	 * Datos identificativos
	 *
	 * El campo "id" heredado de la clase Entity es el código único.
	 * =======================================================================
	 */

	/**
	 * Denominación de la unidad.
	 */
	private String nombre;

	/**
	 * Código del nivel de administración (Estatal, Autonómica o Local)
	 */
	private String nivelAdministracion;

	/**
	 * Descripción del nivel de administración.
	 */
	private String descripcionNivelAdministracion;

	/**
	 * Indicador de si la unidad pertenece a una Entidad de Derecho Público.
	 */
	private String indicadorEntidadDerechoPublico;

	/**
	 * Código externo utilizado por la entidad pública que aporta los datos al
	 * Directorio Común.
	 */
	private String idExternoFuente;

	/*
	 * =======================================================================
	 * Datos de dependencia jerárquica
	 * =======================================================================
	 */

	/**
	 *
	 * Unidad superior jerárquica (de la que depende directamente la unidad).
	 */
	private String idUnidadOrganicaSuperior;

	/**
	 * Denominación de la unidad superior jerárquica (de la que depende
	 * directamente la unidad).
	 */
	private String nombreUnidadOrganicaSuperior;

	/**
	 * Unidad raíz (máximo nivel jerárquico en la entidad pública a la que
	 * pertenece la unidad).
	 */
	private String idUnidadOrganicaPrincipal;

	/**
	 * Denominación de la unidad raíz (máximo nivel jerárquico en la entidad
	 * pública a la que pertenece la unidad).
	 */
	private String nombreUnidadOrganicaPrincipal;

	/**
	 * Unidad raíz que representa a la Administración a la que está vinculada
	 * una Entidad de Derecho Público.
	 */
	private String idUnidadOrganicaEntidadDerechoPublico;

	/**
	 * Denominación de la unidad raíz que representa a la Administración a la
	 * que está vinculada una Entidad de Derecho Público.
	 */
	private String nombreUnidadOrganicaEntidadDerechoPublico;

	/**
	 * Nivel jerárquico en la estructura de la entidad pública (secuencial).
	 */
	private Integer nivelJerarquico;

	/*
	 * =======================================================================
	 * Datos de vigencia
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
	private Date fechaAltaOficial;

	/**
	 * Fecha de supresión oficial.
	 */
	private Date fechaBajaOficial;

	/**
	 * Fecha de extinción final.
	 */
	private Date fechaExtincion;

	/**
	 * Fecha de anulación.
	 */
	private Date fechaAnulacion;

	/**
	 * Constructor.
	 */
	public DatosBasicosUnidadOrganicaVO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getIndicadorEntidadDerechoPublico() {
		return indicadorEntidadDerechoPublico;
	}

	public void setIndicadorEntidadDerechoPublico(
			String indicadorEntidadDerechoPublico) {
		this.indicadorEntidadDerechoPublico = indicadorEntidadDerechoPublico;
	}

	public String getIdExternoFuente() {
		return idExternoFuente;
	}

	public void setIdExternoFuente(String idExternoFuente) {
		this.idExternoFuente = idExternoFuente;
	}

	public String getIdUnidadOrganicaSuperior() {
		return idUnidadOrganicaSuperior;
	}

	public void setIdUnidadOrganicaSuperior(String idUnidadOrganicaSuperior) {
		this.idUnidadOrganicaSuperior = idUnidadOrganicaSuperior;
	}

	public String getNombreUnidadOrganicaSuperior() {
		return nombreUnidadOrganicaSuperior;
	}

	public void setNombreUnidadOrganicaSuperior(String nombreUnidadOrganicaSuperior) {
		this.nombreUnidadOrganicaSuperior = nombreUnidadOrganicaSuperior;
	}

	public String getIdUnidadOrganicaPrincipal() {
		return idUnidadOrganicaPrincipal;
	}

	public void setIdUnidadOrganicaPrincipal(String idUnidadOrganicaPrincipal) {
		this.idUnidadOrganicaPrincipal = idUnidadOrganicaPrincipal;
	}

	public String getNombreUnidadOrganicaPrincipal() {
		return nombreUnidadOrganicaPrincipal;
	}

	public void setNombreUnidadOrganicaPrincipal(
			String nombreUnidadOrganicaPrincipal) {
		this.nombreUnidadOrganicaPrincipal = nombreUnidadOrganicaPrincipal;
	}

	public String getIdUnidadOrganicaEntidadDerechoPublico() {
		return idUnidadOrganicaEntidadDerechoPublico;
	}

	public void setIdUnidadOrganicaEntidadDerechoPublico(
			String idUnidadOrganicaEntidadDerechoPublico) {
		this.idUnidadOrganicaEntidadDerechoPublico = idUnidadOrganicaEntidadDerechoPublico;
	}

	public String getNombreUnidadOrganicaEntidadDerechoPublico() {
		return nombreUnidadOrganicaEntidadDerechoPublico;
	}

	public void setNombreUnidadOrganicaEntidadDerechoPublico(
			String nombreUnidadOrganicaEntidadDerechoPublico) {
		this.nombreUnidadOrganicaEntidadDerechoPublico = nombreUnidadOrganicaEntidadDerechoPublico;
	}

	public Integer getNivelJerarquico() {
		return nivelJerarquico;
	}

	public void setNivelJerarquico(Integer nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
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

	public Date getFechaAltaOficial() {
		return fechaAltaOficial;
	}

	public void setFechaAltaOficial(Date fechaAltaOficial) {
		this.fechaAltaOficial = fechaAltaOficial;
	}

	public Date getFechaBajaOficial() {
		return fechaBajaOficial;
	}

	public void setFechaBajaOficial(Date fechaBajaOficial) {
		this.fechaBajaOficial = fechaBajaOficial;
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
