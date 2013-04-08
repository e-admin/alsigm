package common.lock.db;

import common.db.IDBEntity;
import common.lock.vos.LockVO;

/**
 * Interfaz para todas las definiciones de los bloqueos, así como de las
 * operaciones que se pueden realizar sobre ellos. <br>
 * Entidad: <b>AGOBJBLOQUEO</b>
 */
public interface ILockDBEntity extends IDBEntity {

	/**
	 * Obtiene la información de un bloqueo.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Información del bloqueo.
	 */
	public LockVO getBloqueo(String idObj, int tipoObj, int modulo,
			String idUsuario);

	/**
	 * Obtiene la información de un bloqueo y bloquea el registro en base de
	 * datos hasta cerrar la transacción.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @return Información del bloqueo.
	 */
	public LockVO getBloqueoForUpdate(String idObj, int tipoObj, int modulo);

	/**
	 * Inserta un nuevo bloqueo en la base de datos
	 * 
	 * @param bloqueo
	 *            Información del bloqueo.
	 */
	public void insertBloqueo(final LockVO bloqueo);

	/**
	 * Realiza la actualizacion de un bloqueo.
	 * 
	 * @param bloqueo
	 *            Información del bloqueo.
	 */
	public void updateBloqueo(LockVO bloqueo);

	/**
	 * Elimina un bloqueo en la base de datos.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 * @param idUsuario
	 *            Identificador del usuario que ha realizado el bloqueo.
	 */
	public void deleteBloqueo(String idObj, int tipoObj, int modulo,
			String idUsuario);

	/**
	 * Elimina un bloqueo en la base de datos.
	 * 
	 * @param idObj
	 *            Identificador del objeto.
	 * @param tipoObj
	 *            Tipo de objeto.
	 * @param modulo
	 *            Módulo donde se ha bloqueado.
	 */
	public void deleteBloqueo(String idObj, int tipoObj, int modulo);

	LockVO getBloqueo(String idObj, int tipoObj, int modulo);
}