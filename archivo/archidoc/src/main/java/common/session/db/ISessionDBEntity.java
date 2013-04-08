package common.session.db;

import java.util.List;

import common.db.IDBEntity;
import common.session.vos.SessionVO;

/**
 * Entidad: <b>AASESION</b>
 * 
 * @author IECISA
 * 
 */
public interface ISessionDBEntity extends IDBEntity {
	/**
	 * Inserta una nueva linea en la base de datos
	 * 
	 * @param sesion
	 *            Informacion de la sesion
	 * @return Información de la sesion almacenada
	 */
	public abstract SessionVO insertSession(final SessionVO sesion);

	/**
	 * Realiza la actualización de la sesion dada en la base de datos
	 * 
	 * @param sesion
	 *            Sesion que deseamos actualizar
	 */
	public abstract void updateSession(SessionVO sesion);

	/**
	 * Realiza el borrado de una sesion dada por su identificador.
	 * 
	 * @param sesion
	 *            Identificador de la sesion.
	 */
	public abstract void deleteSession(String id);

	/**
	 * Obtiene una sesion a partir de su id
	 * 
	 * @param idSesion
	 *            Sesion que deseamos obtener
	 */
	public abstract SessionVO getSession(String idSesion);

	/**
	 * Realiza la actualización del keep-alive de una sesion dada en la base de
	 * datos
	 * 
	 * @param sesion
	 *            Sesion que deseamos actualizar
	 */
	public abstract void updateKeepAlive(SessionVO sesion);

	/**
	 * Obtiene una sesion a partir del id de usuario
	 * 
	 * @param idUser
	 *            id del usuario del que deseamos obtener la sesion
	 */
	public abstract SessionVO getSessionUser(String idUser);

	List getSessionesActivasActuales();
}