/*
 * Created on 08-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;

/**
 * @author   juanin  To change the template for this generated type comment go to  Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EventManager
{
	private final IClientContext mcctx;
	private final RuleContextBuilder mctxbuilder;

	private Map parammap;

	private IRuleContext mrulectx;
	/**
	 * @throws ISPACException 
	 *
	 */
	public EventManager(IClientContext cctx) throws ISPACException
	{
		mcctx=cctx;
		mctxbuilder=new RuleContextBuilder(mcctx);
		mrulectx=null;
		parammap=null;
	}
	public EventManager(ClientContext cctx,Map parammap) throws ISPACException
	{
		mcctx=cctx;
		mctxbuilder=new RuleContextBuilder(mcctx);
		mrulectx=null;

		setDefaultContext(parammap);
	}

	public RuleContextBuilder getRuleContextBuilder()
	{
		return mctxbuilder;
	}

	public void setDefaultContext(Map parammap)
	{
	    this.parammap=parammap;
	    mctxbuilder.addContext(parammap);
	}

	public void newContext()
	throws ISPACException
	{
		mctxbuilder.newContext();
		mctxbuilder.addContext(parammap);
		mrulectx=null;
	}

	public IRuleContext getRuleContext()
	throws ISPACException
	{
		if (mrulectx==null)
			mrulectx=mctxbuilder.getRuleContext();

		return mrulectx;
	}

	public ArrayList getRules(int TypeObj,int nIdObj,int EventCode)
	throws ISPACException
	{
		ArrayList rules=new ArrayList();
		RuleFactory rulefactory=new RuleFactory(mcctx);
		CollectionDAO events=PEventoDAO.getEvents(mcctx.getConnection(),TypeObj,nIdObj,EventCode);

		while (events.next())
		{
			PEventoDAO event=(PEventoDAO)events.value();
			IRule rule=rulefactory.instanceRule(event);
			rules.add(rule);
		}
		return rules;
	}


	private IRule getRule(int nIdRule)
	throws ISPACException
	{
		RuleFactory rulefactory=new RuleFactory(mcctx);

		IRule rule=rulefactory.instanceRule(nIdRule);
		return rule;
	}

	public ArrayList processSystemEvents(int EventCode)
	throws ISPACException
	{
	    return processEvents(	EventsDefines.EVENT_OBJ_SYSTEM,
	            				EventsDefines.EVENT_OBJ_SYSTEM_SYSALL,
	            				EventCode);
	}

	public ArrayList processSystemEvents(int SystemTypeObj,int EventCode)
	throws ISPACException
	{
	    return processEvents(	EventsDefines.EVENT_OBJ_SYSTEM,
	            				SystemTypeObj,
	            				EventCode);
	}

	public ArrayList processEvents(int TypeObj,int nIdObj,int EventCode)
	throws ISPACException
	{
	
		//Informacion del evento procesado						
		mctxbuilder.addContext(RuleProperties.RCTX_OBJECTID, String.valueOf(nIdObj));
		mctxbuilder.addContext(RuleProperties.RCTX_OBJECTTYPE, String.valueOf(TypeObj));
		mctxbuilder.addContext(RuleProperties.RCTX_EVENT, String.valueOf(EventCode));
		//Al establecer nuevos datos en el contexto, se recalcula
		mrulectx=mctxbuilder.getRuleContext();
		
		ArrayList rules=getRules(TypeObj,nIdObj,EventCode);
		if (rules.size()<=0)
			return null;

		initRules(rules);

		if (!validateRules(rules))
		{
			throw new ISPACInfo(getRuleContext().getInfoMessage());
		}

		return executeRules(rules);
	}

	public ArrayList executeRules(int TypeObj,int nIdObj,int EventCode)
	throws ISPACException
	{
		ArrayList rules=getRules(TypeObj,nIdObj,EventCode);
		if (rules.size()<=0)
			return null;

		initRules(rules);

		return executeRules(rules);
	}

	public Object processRule(int nIdRule)
	throws ISPACException
	{
		IRule rule=getRule(nIdRule);
		init(rule);
		if (!validate(rule))
		{
			throw new ISPACInfo(getRuleContext().getInfoMessage());
		}
		return execute(rule);
	}

	public Object executeRule(int nIdRule)
	throws ISPACException
	{
		IRule rule=getRule(nIdRule);

		init(rule);
		return execute(rule);
	}
	public boolean validateRule(int nIdRule)
	throws ISPACException
	{
		IRule rule=getRule(nIdRule);
		init(rule);

		return validate(rule);
	}

	public boolean initRules(ArrayList rules)
	throws ISPACException
	{
		Iterator it=rules.iterator();
		while (it.hasNext())
		{
			IRule rule=(IRule)it.next();
			init(rule);
		}
		return true;
	}


	public boolean validateRules(int TypeObj,int nIdObj,int EventCode)
	throws ISPACException
	{
		ArrayList rules=getRules(TypeObj,nIdObj,EventCode);
		if (rules.size()<0)
			return true;

		initRules(rules);
		return validateRules(rules);
	}

	public boolean validateRules(ArrayList rules)
	throws ISPACException
	{
		Iterator it=rules.iterator();
		while (it.hasNext())
		{
			IRule rule=(IRule)it.next();
			if (!validate(rule))
				return false;
		}
		return true;
	}

	public ArrayList executeRules(ArrayList rules)
	throws ISPACException
	{
		ArrayList retobjs=new ArrayList();

		Iterator it=rules.iterator();
		while (it.hasNext())
		{
			IRule rule=(IRule)it.next();
			retobjs.add(execute(rule));
		}
		return retobjs;
	}

	private void init(IRule rule)
	throws ISPACException
	{
		try
		{
			rule.init(getRuleContext());
		}
		catch(ISPACRuleException ruleexcep)
		{
			throw ruleexcep;
		}
		catch(Exception e)
		{
			throw new ISPACException("Error ejecutando regla",e);
		}
	}

	private Object execute(IRule rule)
	throws ISPACException
	{
		try
		{
			return rule.execute(getRuleContext());
		}
		catch(ISPACRuleException ruleexcep)
		{
			throw ruleexcep;
		}
		catch(Exception e)
		{
			throw new ISPACException("Error ejecutando regla",e);
		}
	}

	private boolean validate(IRule rule)
	throws ISPACException
	{
		try
		{
			return rule.validate(getRuleContext());
		}
		catch(ISPACRuleException ruleexcep)
		{
			throw ruleexcep;
		}
		catch(Exception e)
		{
			throw new ISPACException("Error ejecutando regla",e);
		}
	}
}
