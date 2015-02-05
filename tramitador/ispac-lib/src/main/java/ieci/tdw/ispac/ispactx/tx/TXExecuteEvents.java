/*
 * Created on 01-oct-2004
 *
 */
package ieci.tdw.ispac.ispactx.tx;

import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.api.rule.RuleContextBuilder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;


/**
 * @author   juanin
 */
public class TXExecuteEvents implements ITXAction
{
    private final int mTypeObj;
    private final int mnIdObj;
    private final int mEventCode;
	private final IRuleContextParams mrctxobj;
	
	private Object mresult;
	
	public TXExecuteEvents(int TypeObj,int nIdObj,int EventCode,IRuleContextParams rctxobj)
	{
	    mTypeObj=TypeObj;
	    mnIdObj=nIdObj;
	    mEventCode=EventCode;
	    mrctxobj=rctxobj;
	}

	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx)
			throws ISPACException
	{
		EventManager eventmgr=new EventManager(cs);

		// Se construye el contexto de ejecución de scripts.
		RuleContextBuilder rulebuilder=eventmgr.getRuleContextBuilder();
		rulebuilder.addContext(mrctxobj);
			
		mresult=eventmgr.processEvents(mTypeObj,mnIdObj,mEventCode);

	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException
	{
		
	}

	public Object getResult(String nameResult)
	{
		return mresult;
	}
}
