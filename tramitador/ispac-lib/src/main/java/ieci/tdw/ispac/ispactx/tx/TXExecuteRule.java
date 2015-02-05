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
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispactx.ITXAction;
import ieci.tdw.ispac.ispactx.TXTransactionDataContainer;

import java.util.HashMap;
import java.util.Map;


/**
 * @author   juanin
 */
public class TXExecuteRule implements ITXAction
{
	private final int mnIdRule;
	private final int mnIdTask;
	private final int mnIdStage;
	private final int mnIdProc;
	private final Map mparammap;
	private final IRuleContextParams mrctxparam;
	
	private Object mresult;
	

	public TXExecuteRule(int nIdRule, Map parameters)
	{
		super();
		mnIdRule=nIdRule;
		mnIdStage=0;
		mnIdTask=0;
		mnIdProc=0;
		
		if (parameters==null)
		    mparammap=new HashMap();
		else
		    mparammap=parameters;
		
		mrctxparam=null;
	}
	
	public TXExecuteRule(int nIdRule,int nIdProc,int nIdStage,int nIdTask, Map parameters)
	{
		super();
		mnIdRule=nIdRule;
		mnIdProc=nIdProc;
		mnIdStage=nIdStage;
		mnIdTask=nIdTask;
		if (parameters==null)
		    mparammap=new HashMap();
		else
		    mparammap=parameters;
		
		mrctxparam=null;
	}
	
	public TXExecuteRule(int nIdRule,IRuleContextParams rctxparam)
	{
		super();
		mnIdRule=nIdRule;
		mnIdStage=0;
		mnIdTask=0;
		mnIdProc=0;
		mparammap=new HashMap();
		
		mrctxparam=rctxparam;
	}
	
	public void run(ClientContext cs, TXTransactionDataContainer dtc, ITXTransaction itx)
			throws ISPACException
	{
		EventManager eventmgr=new EventManager(cs);
		TXTramiteDAO task=null;
		TXFaseDAO stage=null;
		TXProcesoDAO exped=null;

		// Se construye el contexto de ejecución de scripts.
		if (mnIdTask!=0)
		{
			task=dtc.getTask(mnIdTask);
			eventmgr.getRuleContextBuilder().addContext(task);
		}
		
		if (mnIdStage!=0)
		{
			stage=dtc.getStage(mnIdStage);
			eventmgr.getRuleContextBuilder().addContext(stage);
		}
		
		if (mnIdProc!=0)
		{
			exped=dtc.getProcess(mnIdProc);
			eventmgr.getRuleContextBuilder().addContext(exped);
		}
		
		RuleContextBuilder rulebuilder=eventmgr.getRuleContextBuilder();
		rulebuilder.addContext(mparammap);
		
		if (mrctxparam!=null)
		    rulebuilder.addContext(mrctxparam);
		
//		Iterator it=mparammap.entrySet().iterator();
//		while (it.hasNext())
//        {
//		    Map.Entry var = (Map.Entry) it.next();
//		    rulebuilder.addContext(var.getKey().toString(),var.getValue().toString());
//        }
		
		mresult=eventmgr.processRule(mnIdRule);
	}

	public void lock(ClientContext cs, TXTransactionDataContainer dtc) throws ISPACException
	{
		
	}

	public Object getResult(String nameResult)
	{
		return mresult;
	}
}
