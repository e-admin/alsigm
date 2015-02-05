package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * 
 * Almacena datos de interesados en un registro
 * 
 */
public class PersonInfo {

	/**
	 * Identificador del interesado. (0 si no está normalizado)
	 */
	private String personId = null;

	/**
	 * Nombre del interesado.
	 */
	private String personName = null;

	/**
	 * Identificador del domicilio.
	 */
	private String domId = null;

	/**
	 * Direccion del interesado
	 */
	private String direction = null;

	/**
	 * @return
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return
	 */
	public String getDomId() {
		return domId;
	}

	/**
	 * @param domId
	 */
	public void setDomId(String domId) {
		this.domId = domId;
	}

	/**
	 * @return
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
	 * @return
	 */
	public String getPersonName() {
		return personName;
	}

	/**
	 * @param personName
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
