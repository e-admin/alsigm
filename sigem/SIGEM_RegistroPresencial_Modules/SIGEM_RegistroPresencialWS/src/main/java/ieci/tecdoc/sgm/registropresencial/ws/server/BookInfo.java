/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena la información basica del libro de registro
 * 
 */
public class BookInfo extends RetornoServicio {

	/**
	 * Identificador del libro
	 */
	protected String id;

	/**
	 * Identificador del archivador asociado al libro
	 */
	protected String idocArchId;

	/**
	 * Estado en el que se encuentra el libro
	 * 
	 * @value: 0.- Abierto
	 * @value: 1.- Cerrado
	 */
	protected String state;

	/**
	 * Tipo de libro
	 * 
	 * @value: 1.- Entrada
	 * @value: 2.- Salida
	 */
	protected String type;

	/**
	 * Nombre del libro
	 */
	protected String name;

	/**
	 * Comentarios o información adicional del libro
	 */
	protected String remarks;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the idocArchId
	 */
	public String getIdocArchId() {
		return idocArchId;
	}

	/**
	 * @param idocArchId
	 *            the idocArchId to set
	 */
	public void setIdocArchId(String idocArchId) {
		this.idocArchId = idocArchId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
