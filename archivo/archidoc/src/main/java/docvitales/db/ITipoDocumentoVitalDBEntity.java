package docvitales.db;

import java.util.List;

import common.db.IDBEntity;

import docvitales.vos.TipoDocumentoVitalVO;

/**
 * Interfaz para acceder a la tabla de tipos de documentos vitales. <br>
 * Entidad: <b>ADPCDOCUMENTOVIT</b>
 */
public interface ITipoDocumentoVitalDBEntity extends IDBEntity {
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
	 */
	public void deleteTipoDocumentoVital(String id);

}