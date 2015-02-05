/*
 * 
 */
package es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.command;

import java.util.ArrayList;
import java.util.Collection;

import es.ieci.tecdoc.fwktd.sql.optimizer.impl.pattern.action.IAction;

/**
 * 
 * @author IECISA
 * 
 */
public class SqlOptimizationCommand implements ISqlOptimizationCommand {

	public void execute() {
		for (IAction action : actions) {
			action.execute();
		}
	}

	public Collection<IAction> getActions() {
		return actions;
	}

	public void addAction(IAction anAction) {
		actions.add(anAction);
	}

	// Members

	protected Collection<IAction> actions = new ArrayList<IAction>();
}