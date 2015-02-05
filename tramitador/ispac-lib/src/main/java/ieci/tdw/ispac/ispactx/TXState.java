/*
 * Created on 07-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXState extends ArrayList
{
    private static final long serialVersionUID = 1L;
    
    private int mnIdNode;
	private boolean mbStartNode;
	/**
	 * 
	 */
	public TXState(int nIdNode)
	{
		super();
		mnIdNode=nIdNode;
		mbStartNode=true;
	}
	
	public void addTransition(int nIdNodeEnd,int nIdFlow)
	{
		TXTransition transition=new TXTransition(nIdNodeEnd,nIdFlow);
		add(transition);
	}
	
	public boolean isStartNode()
	{
		return mbStartNode;
	}
	
	public boolean isParentNode(int nIdNodeEnd)
	{
		Iterator it=iterator();
		while(it.hasNext())
		{
			TXTransition transition=(TXTransition)it.next();
			if (transition.getIdNode()==nIdNodeEnd)
				return true;
		}
		return false;
	}
	
	public void setStart(boolean bStartNode)
	{
		mbStartNode=bStartNode;
	}
	/**
	 * @return Returns the IdNode.
	 */
	public int getIdNode()
	{
		return mnIdNode;
	}
}
