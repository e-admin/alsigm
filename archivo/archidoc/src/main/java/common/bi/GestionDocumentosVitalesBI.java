package common.bi;

import java.util.List;

import common.exceptions.TooManyResultsException;
import common.vos.ResultadoRegistrosVO;
import common.vos.TypeDescVO;

import docvitales.exceptions.DocumentoVitalException;
import docvitales.exceptions.TipoDocumentoVitalEnUsoException;
import docvitales.vos.BusquedaDocumentosVitalesVO;
import docvitales.vos.DocumentoVitalExtVO;
import docvitales.vos.FormDocumentoVitalVO;
import docvitales.vos.InfoBDocumentoVitalExtVO;
import docvitales.vos.TipoDocumentoVitalVO;
import fondos.vos.DocumentoAntecedenteVO;
import fondos.vos.SerieDocAntVO;

/**
 * Interfaz de negocio de documentos vitales.
 */
public interface GestionDocumentosVitalesBI {

	// ========================================================================
	// DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Obtiene el número de documentos vitales a gestionar.
	 * 
	 * @return Número de documentos vitales a gestionar
	 */
	public int getCountDocumentosVitalesAGestionar();

	/**
	 * Realiza la búsqueda de documentos vitales.
	 * 
	 * @param busquedaVO
	 *            Criterios de búsqueda.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getDocumentosVitales(BusquedaDocumentosVitalesVO busquedaVO)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de documentos vitales vigentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idProc
	 *            Identificador de procedimiento. Si es nulo, ignora este
	 *            criterio.
	 * @return Lista de documentos vitales ({@link InfoBDocumentoVitalExtVO}.
	 */
	public List getDocumentosVitalesVigentes(String idTercero, String idProc);

	/**
	 * Obtiene la lista de documentos vitales a gestionar.
	 * 
	 * @return Lista de documentos vitales a gestionar(
	 *         {@link InfoBDocumentoVitalExtVO}).
	 */
	public List getDocumentosVitalesAGestionar();

	/**
	 * Obtiene el documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 * @return Documento vital.
	 */
	public DocumentoVitalExtVO getDocumentoVital(String id);

	/**
	 * Obtiene un documento vital.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 * @param estado
	 *            Estado del documento vital.
	 * @return Documento vital.
	 */
	public InfoBDocumentoVitalExtVO getDocumentoVital(String idTercero,
			String idTipoDocVit, int estado);

	/**
	 * Obtiene la lista de estados de los documentos vitales.
	 * 
	 * @return Lista de estados ({@link TypeDescVO}).
	 */
	public List getEstadosDocumentosVitales();

	/**
	 * Inserta un documento vital validado.
	 * 
	 * @param formDocVit
	 *            Información inicial del documento vital.
	 * @return Documento vital insertado y validado.
	 * @throws DocumentoVitalException
	 *             si hay un error en la creación.
	 */
	public InfoBDocumentoVitalExtVO insertDocumentoVitalValidado(
			FormDocumentoVitalVO formDocVit) throws DocumentoVitalException;

	/**
	 * Inserta un documento vital.
	 * 
	 * @param formDocVit
	 *            Información inicial del documento vital.
	 * @return Documento vital insertado.
	 */
	public InfoBDocumentoVitalExtVO insertDocumentoVital(
			FormDocumentoVitalVO formDocVit);

	/**
	 * Valida un documento vital.
	 * 
	 * @param documentoVital
	 *            Documento vital.
	 * @return Documento vital validado.
	 */
	public InfoBDocumentoVitalExtVO validarDocumentoVital(
			InfoBDocumentoVitalExtVO documentoVital);

	/**
	 * Rechaza un documento vital.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 */
	public void rechazarDocumentoVital(String id);

	/**
	 * Pasa un documento vital a histórico.
	 * 
	 * @param id
	 *            Identificador del documento vital.
	 */
	public void pasarAHistoricoDocumentoVital(String id);

	/**
	 * Comprueba la validez de los documentos vitales vigentes y los pasa a
	 * histórico si han caducado.
	 */
	public void pasarAHistoricoDocumentosVitalesCaducados();

	/**
	 * Elimina los documentos vitales históricos que no están referenciados en
	 * ningún expediente.
	 */
	public void eliminarDocumentosVitalesObsoletos();

	// ========================================================================
	// DOCUMENTOS ANTECEDENTES
	// ========================================================================

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero);

	/**
	 * Obtiene los documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador de tercero.
	 * @param idSerie
	 *            Identificador de la serie.
	 * @return Documentos antecedentes ({@link DocumentoAntecedenteVO}).
	 */
	public List getDocumentosAntecedentes(String idTercero, String idSerie);

	/**
	 * Obtiene las series que contienen documentos antecedentes de un tercero.
	 * 
	 * @param idTercero
	 *            Identificador del tercero.
	 * @return Lista de series ({@link SerieDocAntVO}).
	 */
	public List getSeriesDocumentosAntecedentes(String idTercero);

	// ========================================================================
	// TIPOS DE DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Obtiene la lista de tipos de documentos vitales.
	 * 
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales();

	/**
	 * Obtiene la lista de tipos de documentos vitales de un procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @return Lista de documentos vitales ({@link TipoDocumentoVitalVO}).
	 */
	public List getTiposDocumentosVitales(String idProc);

	/**
	 * Obtiene el tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO getTipoDocumentoVital(String id);

	/**
	 * Inserta un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 * @return Tipo de documento vital.
	 */
	public TipoDocumentoVitalVO insertTipoDocumentoVital(
			TipoDocumentoVitalVO tipo);

	/**
	 * Modifica un tipo de documento vital.
	 * 
	 * @param tipo
	 *            Tipo de documento vital.
	 */
	public void updateTipoDocumentoVital(TipoDocumentoVitalVO tipo);

	/**
	 * Elimina un tipo de documento vital.
	 * 
	 * @param id
	 *            Identificador del tipo de documento vital.
	 * @throws TipoDocumentoVitalEnUsoException
	 *             si el tipo está en uso.
	 */
	public void deleteTipoDocumentoVital(String id)
			throws TipoDocumentoVitalEnUsoException;

	/**
	 * Elimina tipos de documentos vitales.
	 * 
	 * @param ids
	 *            Lista de identificadores de tipos de documentos vitales.
	 * @return Información de la eliminación.
	 */
	public ResultadoRegistrosVO deleteTiposDocumentosVitales(String[] ids);

	// ========================================================================
	// RELACIONES ENTRE TIPOS DE DOCUMENTOS VITALES Y PROCEDIMIENTOS
	// ========================================================================

	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void insertTipoDocVitProc(String idProc, String idTipoDocVit);

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteTipoDocVit(String idProc, String idTipoDocVit);

	/**
	 * Elimina todas las relaciones de un tipo de documento vital con
	 * procedientos.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteTipoDocVitByIdTipoDocVit(String idTipoDocVit);

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void deleteTiposDocVitByIdProc(String idProc);

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void deleteTiposDocVit(String idProc, String[] idsTipoDocVit);

	// ========================================================================
	// USO DE DOCUMENTOS VITALES
	// ========================================================================

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
			String usuario);

	/**
	 * Elimina los vínculos de un expediente.
	 * 
	 * @param idExp
	 *            Identificador del expediente de backoffice.
	 * @param idSist
	 *            Identificador del sistema de backoffice.
	 */
	public void eliminaVinculosExpediente(String idExp, String idSist);

	/**
	 * @param idBdTerceros
	 * @param idTipoDocVit
	 */
	public void checkDocumentoVitalCreable(String idBdTerceros,
			String idTipoDocVit) throws DocumentoVitalException;

}