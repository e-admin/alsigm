package ieci.tecdoc.sgm.core.services.registro;

/**
 * Datos de un registro con documentación e interesados
 * 
 * @see ieci.tecdoc.sgm.core.services.registro.RegisterWithPagesInfo
 */
public class RegisterWithPagesInfoPersonInfo extends RegisterWithPagesInfo {

	/**
	 * Listado de interesados asociados a un registro
	 */
	protected PersonInfo[] persons = null;

	/**
	 * @return
	 */
	public PersonInfo[] getPersons() {
		return persons;
	}

	/**
	 * @param persons
	 */
	public void setPersons(PersonInfo[] persons) {
		this.persons = persons;
	}

	/**
	 * @param register
	 */
	public RegisterWithPagesInfoPersonInfo(RegisterWithPagesInfo register) {
		setDocInfo(register.getDocInfo());
		setRqInfo(register.getRqInfo());
	}
}
