package docvitales.db;

import common.db.IDBEntity;

/**
 * Interfaz para acceder a la tabla que relaciona los tipos de documentos
 * vitales con los procedimientos. <br/>
 * Entidad: <b>ADPCDOCUMENTOVIT</b>
 */
public interface ITipoDocVitProcedimientoDBEntity extends IDBEntity {
	/**
	 * Crea una relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void insert(String idProc, String idTipoDocVit);

	/**
	 * Elimina todas las relaciones de un tipo de documento vital con
	 * procedientos.
	 * 
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void deleteByIdTipoDocVit(String idTipoDocVit);

	/**
	 * Elimina todas las relaciones entre tipos de documentos vitales con un
	 * procedimiento.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 */
	public void deleteByIdProc(String idProc);

	/**
	 * Elimina la relación entre un procedimiento y un tipo de documento vital.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idTipoDocVit
	 *            Identificador del tipo de documento vital.
	 */
	public void delete(String idProc, String idTipoDocVit);

	/**
	 * Elimina la relación entre un procedimiento y una lista de tipos de
	 * documentos vitales.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param idsTipoDocVit
	 *            Lista de identificadores de tipos de documentos vitales.
	 */
	public void delete(String idProc, String[] idsTipoDocVit);
}