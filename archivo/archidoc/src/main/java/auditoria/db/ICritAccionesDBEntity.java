package auditoria.db;

import java.util.Collection;

import auditoria.vos.CritAccionVO;

import common.db.IDBEntity;

/**
 * Entidad: <b>AANAUDITACCION</b>
 * 
 * @author IECISA
 * 
 */
public interface ICritAccionesDBEntity extends IDBEntity {
	/**
	 * Obtiene el nivel de criticidad para una acción de un módulo
	 * 
	 * @param module
	 *            Modulo al que pertenece la acción
	 * @param action
	 *            Acción
	 * @return
	 */
	public abstract int getActionLogLevel(int module, int action);

	/**
	 * Obtiene el nivel de criticidad para una acción
	 * 
	 * @param module
	 *            identificador del modulo o menor de 1 si no se quierre
	 *            restringir por modulo
	 * @param action
	 *            identificador de la Acción
	 * @return Accion seleccionada
	 */
	public abstract CritAccionVO getAction(int module, int action);

	/**
	 * Obtiene un listado de las acciones solicitadas con su nivel de log
	 * asociado.
	 * 
	 * @param actions
	 *            Acciones de las que deseamos obtener su nivel de log en la
	 *            aplicacion.
	 * @return Listado de las acciones solicitadas con su nivel
	 */
	public abstract Collection getActionsWithLevels(Collection actions);

	/**
	 * Establece el nivel de log de una accion.
	 * 
	 * @param moduel
	 *            modulo al que pertenece la accion
	 * @param action
	 *            Accion de la que deseamos establecer el nivel de log.
	 * @param logLevel
	 *            Nivel de log de la accion
	 */
	public abstract void setActionLogLevel(int module, int action, int logLevel);

	/**
	 * Crear el nivel de log de una accion.
	 * 
	 * @param module
	 *            Modulo al que pertenece la accion
	 * @param action
	 *            Accion de la que deseamos establecer el nivel de log.
	 * @param logLevel
	 *            Nivel de log de la accion
	 */
	public abstract CritAccionVO insertActionLogLevel(int module, int action,
			int logLevel);

	/**
	 * Realiza el borrado de una accion con su criticidad asocidad de la tabla
	 * de la base de datos
	 * 
	 * @param module
	 *            Module al que pertenece la accion o -1 si se desconoce el
	 *            módulo
	 * @param action
	 *            Accion que se desea borrar
	 */
	public abstract void deleteActionLogLevel(int module, int action);
}