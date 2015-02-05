package ieci.tecdoc.sgm.core.services.registro;

/**
 * Datos de los campos de un registro
 * 
 * 
 */
public class RegisterQueryInfo {

	/**
	 * Nombre del campo
	 */
	protected String folderName = null;

	/**
	 * Valor de un campo
	 */
	protected String value = null;

	/**
	 * @return
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
