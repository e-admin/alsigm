/*
 * Created on 05-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXStateTable 
{
	private HashMap mstates=new HashMap();
		
	/**
	 * 
	 */
	public TXStateTable()
	{
		super();
	}

	protected TXState getState(int nIdNode)
	{
		return (TXState)mstates.get(new Integer(nIdNode));
	}

	protected TXState addState(int nIdNode)
	{
		TXState state = new TXState(nIdNode);
		mstates.put(new Integer(nIdNode),state);
		
		return state;
	}

	public void addTransition(int nIdNodeStart, int nIdNodeEnd,int nIdFlow)
	{
		TXState state=getState(nIdNodeStart);
		
		if (state == null)
			state = addState(nIdNodeStart);

		state.addTransition(nIdNodeEnd,nIdFlow);
		markNode(nIdNodeEnd);
	}
	
	public void markNode(int nIdNode)
	{
		TXState state=getState(nIdNode);
		if (state == null)
			state = addState(nIdNode);
			
		state.setStart(false);
	}
	
	public Set getStartStages()
	{
		HashSet set=new HashSet();
		
		Iterator it=mstates.entrySet().iterator();
		while (it.hasNext())
		{
			TXState state=(TXState)((Map.Entry)it.next()).getValue();
			if (state.isStartNode())
				set.add(new Integer(state.getIdNode()));
		}
		return set;
	}
	

	public Set nextStages(EventManager eventmgr,int nIdNode)
	throws ISPACException
	{
		HashSet set=new HashSet();

		TXState state=getState(nIdNode);
		if (state==null)
			return set;
		
		Iterator it=state.iterator();
		while (it.hasNext())
		{
			TXTransition transition=(TXTransition)it.next();
			if (transition.test(eventmgr))
				set.add(new Integer(transition.getIdNode()));
		}
		return set;
	}
	
	public Set previousStages(int nIdNode)
	throws ISPACException
	{
		HashSet set=new HashSet();

		Iterator it=mstates.entrySet().iterator();
		while (it.hasNext())
		{
			TXState state=(TXState)((Map.Entry)it.next()).getValue();
			if (state.isParentNode(nIdNode))
				set.add(new Integer(state.getIdNode()));
		}
		return set;
	}
}