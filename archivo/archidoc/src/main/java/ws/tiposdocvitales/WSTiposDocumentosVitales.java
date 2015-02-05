package ws.tiposdocvitales;

import docvitales.webservice.vos.TipoDocumentoVital;

/**
 * Interfaz del servicio web de acceso a los tipos de documentos vitales
 */
public interface WSTiposDocumentosVitales {
	/**
	 * Obtiene la lista de tipos de documentos vitales.
	 * 
	 * @return Lista de documentos vitales.
	 */
	public TipoDocumentoVital[] getTiposDocVit() throws Exception;

	/**
	 * Obtiene la lista de tipos de documentos vitales de un procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales.
	 */
	public TipoDocumentoVital[] getTiposDocVitPorProc(String idProc)
			throws Exception;

	/**
	 * Obtiene el tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVital getTipoDocVit(String id) throws Exception;

	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void addRelTipoDocVitProc(String idProc, String idTipoDocVit)
			throws Exception;

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void removeRelTipoDocVitProc(String idProc, String idTipoDocVit)
			throws Exception;

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void removeRelsPorProc(String idProc) throws Exception;

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void removeRelsPorTiposDocProc(String idProc, String[] idsTipoDocVit)
			throws Exception;
}