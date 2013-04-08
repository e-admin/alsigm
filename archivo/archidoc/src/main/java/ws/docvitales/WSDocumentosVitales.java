package ws.docvitales;

import docvitales.webservice.vos.DocumentoAntecedente;
import docvitales.webservice.vos.DocumentoVital;
import docvitales.webservice.vos.FormDocumentoVital;
import docvitales.webservice.vos.InfoBDocumentoVital;
import docvitales.webservice.vos.Serie;

/**
 * Interfaz del servicio web de acceso a los documentos vitales.
 */
public interface WSDocumentosVitales {

	/**
	 * Obtiene la lista de series que contienen documentos antecedentes de un
	 * tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series.
	 */
	public Serie[] getSeriesTercero(String idTercero) throws Exception;

	/**
	 * Recupera la lista de documentos antecedentes de un tercero pertenecientes
	 * a una serie determinada.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Lista de documentos antecedentes.
	 */
	public DocumentoAntecedente[] getDocumentosAntecedentes(String idTercero,
			String idSerie) throws Exception;

	/**
	 * Recupera la lista de documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de documentos antecedentes.
	 */
	public DocumentoAntecedente[] getDocumentosAntecedentesTercero(
			String idTercero) throws Exception;

	/**
	 * Obtiene la ficha ISAD(G) de una unidad documental.
	 * 
	 * @param idDocAnt
	 *            Identificador de la unidad documental.
	 * @return Ficha ISAD(G).
	 */
	public String getISADG(String idDocAnt) throws Exception;

	/**
	 * Recupera la lista de documentos vitales de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de documentos vitales.
	 */
	public InfoBDocumentoVital[] getDocumentosVitalesTercero(String idTercero)
			throws Exception;

	/**
	 * Recupera la lista de documentos vitales de un tercero asociados a un
	 * procedimiento.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales.
	 */
	public InfoBDocumentoVital[] getDocumentosVitalesTerProc(String idTercero,
			String idProc) throws Exception;

	/**
	 * Recupera un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @return Documento vital.
	 */
	public DocumentoVital getDocumentoVital(String id) throws Exception;

	/**
	 * Crea un documento vital.
	 * 
	 * @param formDocVit
	 *            Información del documento vital.
	 * @return Identificador del documento vital creado.
	 */
	public InfoBDocumentoVital altaDocumentoVital(FormDocumentoVital formDocVit)
			throws Exception;

	/**
	 * Vincula un expediente a un documento vital.
	 * 
	 * @param idDocVit
	 *            Identificador del documento vital.
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 * @param usuario
	 *            Nombre completo del usuario de backoffice.
	 */
	public void vinculaExpediente(String idDocVit, String idExp, String idSist,
			String usuario) throws Exception;

	/**
	 * Elimina los vínculos de un expediente.
	 * 
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 */
	public void eliminaVinculosExpediente(String idExp, String idSist)
			throws Exception;
}