package ieci.tecdoc.sgm.core.services.registro;

/**
 * Datos de un registro con los documentos adjuntos.
 * 
 * 
 */
public class RegisterWithPagesInfo {

	/**
	 * Datos de un registro
	 */
	protected RegisterQueryInfo[] rqInfo = null;

	/**
	 * Lista de documentos adjuntos
	 */
	protected Document[] docInfo = null;

	/**
	 * @return
	 */
	public Document[] getDocInfo() {
		return docInfo;
	}

	/**
	 * @param docInfo
	 */
	public void setDocInfo(Document[] docInfo) {
		this.docInfo = docInfo;
	}

	/**
	 * @return
	 */
	public RegisterQueryInfo[] getRqInfo() {
		return rqInfo;
	}

	/**
	 * @param rqInfo
	 */
	public void setRqInfo(RegisterQueryInfo[] rqInfo) {
		this.rqInfo = rqInfo;
	}

}
