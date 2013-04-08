package ieci.tecdoc.sgm.core.services.registro;

/**
 * Datos de un interesado
 * 
 * 
 */
public class PersonInfo {

	/**
	 * Identificador del interesado
	 */
	protected String personId = null;

	/**
	 * Nombre del interesado
	 */
	protected String personName = null;

	/**
	 * Identificador del interesado
	 */
	protected String domId = null;

	/**
	 * Direccion principal del interesado
	 */
	protected String direction = null;

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
}
