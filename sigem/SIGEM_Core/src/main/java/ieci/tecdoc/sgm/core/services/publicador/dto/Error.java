package ieci.tecdoc.sgm.core.services.publicador.dto;

import java.io.Serializable;

/**
 * Información del error producido en el publicador.
 *
 */
public class Error implements Serializable {

	/**
	 * Identificador del hito.
	 */
	private int idHito = 0;
	
	/**
	 * Identificador de la aplicación
	 */
	private int idAplicacion = 0;
	
	/**
	 * Identificador del sistema. 
	 */	
	private String idSistema = null;
	
	/**
	 * Identificador del objeto (número de expediente).
	 */
	private String idObjeto = null;
	
	/**
	 * Descripción del error.
	 */
	private String descripcion = null;
	
	/**
	 * Identificador del error.
	 */
	private int idError = 0;

	
	/**
	 * Constructor.
	 */
	public Error() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param idHito
	 * @param idAplicacion
	 * @param idSistema
	 * @param idObjeto
	 * @param descripcion
	 * @param idError
	 */
	public Error(int idHito, int idAplicacion, String idSistema,
			String idObjeto, String descripcion, int idError) {
		super();
		this.idHito = idHito;
		this.idAplicacion = idAplicacion;
		this.idSistema = idSistema;
		this.idObjeto = idObjeto;
		this.descripcion = descripcion;
		this.idError = idError;
	}

	public int getIdHito() {
		return idHito;
	}

	public void setIdHito(int idHito) {
		this.idHito = idHito;
	}

	public int getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(String idSistema) {
		this.idSistema = idSistema;
	}

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdError() {
		return idError;
	}

	public void setIdError(int idError) {
		this.idError = idError;
	}

}
