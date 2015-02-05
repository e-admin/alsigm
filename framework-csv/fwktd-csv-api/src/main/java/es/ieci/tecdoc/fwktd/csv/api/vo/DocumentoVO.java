package es.ieci.tecdoc.fwktd.csv.api.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * Información de un documento.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoVO extends Entity {

	private static final long serialVersionUID = 3673503978664698730L;

	/**
	 * Nombre del documento.
	 */
	private String nombre = null;

	/**
	 * Descripción del documento.
	 */
	private String descripcion = null;

	/**
	 * Tipo MIME del documento.
	 */
	private String tipoMIME = null;

	/**
	 * Fecha de creación del documento.
	 */
	private Date fechaCreacion = null;

	/**
	 * Fecha de caducidad del documento.
	 */
	private Date fechaCaducidad = null;

	/**
	 * Código Seguro de Verificación.
	 */
	private String csv = null;

	/**
	 * Fecha de generación del CSV.
	 */
	private Date fechaCSV = null;

	/**
	 * Indica la disponibilidad del documento en la aplicación externa.
	 */
	private Boolean disponible = Boolean.TRUE;

	/**
	 * Aplicación externa.
	 */
	private AplicacionVO aplicacion = null;

	/**
	 * Constructor.
	 */
	public DocumentoVO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoMIME() {
		return tipoMIME;
	}

	public void setTipoMIME(String tipoMIME) {
		this.tipoMIME = tipoMIME;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public Date getFechaCSV() {
		return fechaCSV;
	}

	public void setFechaCSV(Date fechaCSV) {
		this.fechaCSV = fechaCSV;
	}

	public Boolean isDisponible() {
		return disponible;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}
}
