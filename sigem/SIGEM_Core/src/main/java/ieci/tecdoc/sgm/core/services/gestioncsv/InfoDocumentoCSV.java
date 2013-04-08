/**
 * 
 */
package ieci.tecdoc.sgm.core.services.gestioncsv;

import java.util.Date;

/**
 * @author IECISA
 * 
 * Información de un documento con su CSV generado
 *
 */
public class InfoDocumentoCSV extends InfoDocumentoCSVForm{
	
	/**
	 * Identificador del documento
	 */
	private String id;
	
	/**
	 * Código CSV asignado al documento
	 */
	private String csv;
	
	/**
	 * Fecha de generación del CSV del documento
	 */
	private Date fechaCSV;
	
	/**
	 * Nombre de la aplicación a la que pertenece el documento
	 */
	private String nombreAplicacion;

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el csv
	 */
	public String getCsv() {
		return csv;
	}

	/**
	 * @param csv el csv a fijar
	 */
	public void setCsv(String csv) {
		this.csv = csv;
	}

	/**
	 * @return el fechaCSV
	 */
	public Date getFechaCSV() {
		return fechaCSV;
	}

	/**
	 * @param fechaCSV el fechaCSV a fijar
	 */
	public void setFechaCSV(Date fechaCSV) {
		this.fechaCSV = fechaCSV;
	}

	/**
	 * @return el nombreAplicacion
	 */
	public String getNombreAplicacion() {
		return nombreAplicacion;
	}

	/**
	 * @param nombreAplicacion el nombreAplicacion a fijar
	 */
	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}

	
	

}
