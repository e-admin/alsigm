package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena una lista de interesados
 * 
 */
public class ListaPersonInfo extends RetornoServicio {

	/**
	 * Conjunto de interesados
	 */
	PersonInfo[] personsInfo = null;

	/**
	 * @return
	 */
	public PersonInfo[] getPersonsInfo() {
		return personsInfo;
	}

	/**
	 * @param personsInfo
	 */
	public void setPersonsInfo(PersonInfo[] personsInfo) {
		this.personsInfo = personsInfo;
	}

}
