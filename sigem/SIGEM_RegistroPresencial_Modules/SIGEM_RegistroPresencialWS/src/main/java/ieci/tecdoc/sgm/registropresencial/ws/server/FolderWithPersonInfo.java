package ieci.tecdoc.sgm.registropresencial.ws.server;

/**
 * Información completa de un registro junto con los interesados.
 * 
 * 
 */
public class FolderWithPersonInfo extends Folder {

	/**
	 * Lista de interesados
	 */
	PersonInfo[] persons = null;

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
	 * Constructor por defecto
	 */
	public FolderWithPersonInfo() {

	}

	/**
	 * Constructor con parametros
	 * 
	 * @param folder
	 */
	public FolderWithPersonInfo(Folder folder) {
		setBookId(folder.getBookId());
		setDocumentos(folder.getDocumentos());
		setDocWithPage(folder.getDocWithPage());
		setErrorCode(folder.getErrorCode());
		setFields(folder.getFields());
		setFolderId(folder.getFolderId());
		setFolderNumber(folder.getFolderNumber());
		setReturnCode(folder.getReturnCode());
	}
}
