/**
 *
 */
package ieci.tecdoc.sgm.gestioncsv.ws.server.dto;

import java.util.Date;

/**
 * @author IECISA
 *
 * Información de un documento para generar su CSV
 *
 */
public class InfoDocumentoCSVFormDTO {

	/**
	 * Nombre del documento
	 */
	private String nombre;

	/**
	 * Tipo MIME del documento
	 */
	private String tipoMime;

	/**
	 * Fecha de creación del documento
	 */
	private Date fechaCreacion;

	/**
	 * Fecha de caducidad del documento
	 */
	private Date fechaCaducidad;

	/**
	 * Código de la aplicación a la que pertenece el documento
	 */
	private String codigoAplicacion;

	/**
	 * Disponibilidad del documento
	 */
	private boolean disponible;

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el tipoMime
	 */
	public String getTipoMime() {
		return tipoMime;
	}

	/**
	 * @param tipoMime el tipoMime a fijar
	 */
	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	/**
	 * @return el fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion el fechaCreacion a fijar
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return el fechaCaducidad
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * @param fechaCaducidad el fechaCaducidad a fijar
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * @return el codigoAplicacion
	 */
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	/**
	 * @param codigoAplicacion el codigoAplicacion a fijar
	 */
	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	/**
	 * @return el disponible
	 */
	public boolean isDisponible() {
		return disponible;
	}

	/**
	 * @param disponible el disponible a fijar
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}



}
