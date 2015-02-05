/*
 * Created on 07-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXTransition
{
	private final int mnIdNode;
	private final int mnIdFlow;

	/**
	 * 
	 */
	public TXTransition(int nIdNode,int nIdFlow)
	{
		super();
		mnIdNode=nIdNode;
		mnIdFlow=nIdFlow;
	}

	public boolean test(EventManager eventmgr)
	throws ISPACException
	{
		return eventmgr.validateRules(EventsDefines.EVENT_OBJ_FLOW,
									mnIdFlow,
									EventsDefines.EVENT_EXEC_START);
	}
	/**
	 * @return Returns the IdFlow.
	 */
	public int getIdFlow()
	{
		return mnIdFlow;
	}
	/**
	 * @return Returns the IdNode.
	 */
	public int getIdNode()
	{
		return mnIdNode;
	}
}
