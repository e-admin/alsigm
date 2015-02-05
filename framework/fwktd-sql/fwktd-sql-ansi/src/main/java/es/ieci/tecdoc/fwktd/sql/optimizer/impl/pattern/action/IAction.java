/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.action;

/**
 * Interfaz para las operaciones a realizar sobre el conjunto de sentencias
 * <code>IStatement</code> a optimizar.
 */
public interface IAction {

	/**
	 * Ejecuta la accion sobre las sentencias.
	 */
	void execute();
}
