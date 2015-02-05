/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.command;

import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.action.IAction;

/**
 * Command para la optimización de sentencias SQL
 */
public interface ISqlOptimizationCommand {

	/**
	 * Realiza la optimización
	 */
	public void execute();

	void addAction(IAction anAction);
}
