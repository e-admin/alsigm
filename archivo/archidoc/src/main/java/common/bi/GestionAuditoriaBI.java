package common.bi;

import java.util.Collection;
import java.util.List;

import se.usuarios.AppUser;
import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.TrazaVO;

import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

/**
 * Interface para el servicio de Auditoría
 */
public interface GestionAuditoriaBI {
	/**
	 * Obtiene el nivel de criticidad(log) del usuario conectado. El nivel es el
	 * menor entre: - El nivel de log general de la aplicación. - El menor de
	 * los niveles de log de los roles a los que el usuario pertenece.
	 * 
	 * @param user
	 *            Usuario del que deseamos conocer el nivel de criticidad.
	 * @return Nivel de log del usuario.
	 */
	public int getLogLevel(AppUser user);

	/**
	 * Obtiene el nivel global de criticidad de la aplicación.
	 * 
	 * @return Nivel de critificadad(log) de la aplicación
	 */
	public int getGlobalLogLevel();

	/**
	 * Obtiene un listado de los módulos existentes en la aplicación
	 * 
	 * @return Listaod de los módulos existentes
	 */
	public Collection getModules();

	/**
	 * Obtiene un listado de todas las acciones existentes en la aplicacion
	 * 
	 * @return Listado de las todas acciones existentes
	 */
	public Collection getActions();

	/**
	 * Obtiene un listado de las acciones existentes en un módulo de la
	 * aplicación
	 * 
	 * @return Listado de las acciones existentes en un módulo de la
	 *         aplicacación
	 */
	public Collection getActions(int module);

	// /**
	// * Obtiene un listado de los grupos de usuarios existentes para auditoria
	// * @return Listado de los grupos existentes para auditoria
	// */
	// public Collection getGroups();
	// /**
	// * Obtiene un listado de los usuarios existentes para auditoria
	// * @return Listado de los usuarios existentes para auditoria
	// */
	// public Collection getUsers();
	/**
	 * Obtiene un listado de las pistas de auditoría para el filtro de búsqueda
	 * aplicado
	 * 
	 * @param busqueda
	 *            Filtro de búsqueda aplicado
	 * @param pageInfo
	 *            Informacion de paginacion de la tabla de resultados
	 * @return Listado de las pistas de auditoría que cumplen el filtro.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getPistas(BusquedaPistasVO busqueda, PageInfo pageInfo)
			throws TooManyResultsException;

	/**
	 * Obtiene un listado de las acciones existentes en la aplicación con su
	 * nivel de log asociado.
	 * 
	 * @return Listado de las acciones existentes en la aplicacación
	 */
	public Collection getActionsWithLevels();

	/**
	 * Obtiene un listado de las acciones existentes en un modulo de la
	 * aplicación con su nivel de log asociado.
	 * 
	 * @return Listado de las acciones existentes en un modulo la aplicacación
	 */
	public Collection getActionsWithLevels(int module);

	/**
	 * Obtiene los niveles de log que existen en la aplicación
	 * 
	 * @return
	 */
	public Collection getLogLevels();

	/**
	 * Obtiene los datos de una pista de auditoria a partir de su identificador
	 * 
	 * @param id
	 *            Identificador de la pista de auditoria que deseamos recuperar
	 * @return Pista de auditoria asociada al id
	 */
	public TrazaVO getPista(String id);

	/**
	 * Obtiene los detalles de una pista de auditoria a partir del identificador
	 * de la pista
	 * 
	 * @param id
	 *            Identificador de la pista de la que deseamos recuperar los
	 *            detalles.
	 * @return Listado de los detalles de la pista de auditoria
	 */
	public List getDatosPista(String id);

	/**
	 * Establece el nivel de log de una accion.
	 * 
	 * @param module
	 *            modulo al que pertenece la accion
	 * @param action
	 *            Accion de la que deseamos establecer el nivel de log.
	 * @param logLevel
	 *            Nivel de log de la accion
	 */
	public void setActionLogLevel(int module, int action, int logLevel);

	/**
	 * Obtiene los usuarios existentes en la aplicacion con su nivel de
	 * criticidad
	 * 
	 * @return Listado de los usuarios existentes en la aplicacion
	 */
	public Collection getUsersWithLevels();

	/**
	 * Obtiene los grupos de usuarios existentes en la aplicacion con su nivel
	 * de criticidad
	 * 
	 * @return Listado de los grupos existentes en la aplicacion
	 */
	public Collection getGroupsWithLevels();

	/**
	 * Establece el nivel de log para un grupo de usuario
	 * 
	 * @param id
	 *            Identificador del grupo de usuario
	 * @param logLevel
	 *            nivel de log
	 */
	public void setGroupLogLevel(String id, int logLevel);

	/**
	 * Establece el nivel de log para un usuario
	 * 
	 * @param id
	 *            Identificador del usuario
	 * @param logLevel
	 *            nivel de log
	 */
	public void setUserLogLevel(String id, int logLevel);

	/**
	 * Obtiene un listado de los objetos de auditoria disponibles en la
	 * aplicacion
	 * 
	 * @return Listado de objetos auditables en la aplicacion
	 */
	public Collection getObjects();

	/**
	 * Obtiene todas las pistas de auditoria para un determinado objeto
	 * 
	 * @param type
	 *            Tipo del objeto
	 * @param id
	 *            Identificador del objeto
	 * @return Listado de las pistas asociadas a ese objeto
	 */
	public Collection getPistasXObject(int type, String id);

	/**
	 * Elimina un elemento del conjunto de elementos que están siendo auditados
	 * 
	 * @param tipo
	 *            Tipo de elemento auditado. En principio usuario, grupo o rol
	 * @param id
	 *            Identificador del elemento auditado
	 */
	public void removeTipoAuditado(int tipo, String[] id);

}
