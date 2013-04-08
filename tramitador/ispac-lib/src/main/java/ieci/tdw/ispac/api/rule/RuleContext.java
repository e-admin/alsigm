/*
 * Created on 02-jun-2004
 *
 */
package ieci.tdw.ispac.api.rule;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;

/**
 * @author   juanin  <?xml version="1.0" encoding="ISO-8859-1"?>  <ispac_context>  <procedureid>42</procedureid>  <processid>434</processid>  <numexp>EXP/0001</numexp>  <stageid>3</stageid>  <taskid>6</taskid>  </ispac_context>
 */

public class RuleContext implements IRuleContext
{
	private Document mContextXML;
	private String mmessage;
	private final IClientContext mcctx;
	private final IItem item;

	/**
	 *
	 */
	public RuleContext(Document docRuleContext, IClientContext cctx, IItem item)
	{
		this.mContextXML = docRuleContext;
		this.mcctx = cctx;
		this.item = item;
	}

	public Document getDocContext()
	throws ISPACRuleException
	{
		return mContextXML;
	}

	public IClientContext getClientContext()
	{
		return mcctx;
	}

	public String getInfoMessage()
	{
		return mmessage;
	}

	public void setInfoMessage(String message)
	{
		mmessage=message;
	}

	public IItem getItem() {
		return item;
	}

	public int getProcedureId()
		throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_PROCEDURE);
		//return getInt("procedureid");
	}
	public int getStageProcedureId()
		throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_STAGEPCD);
		//return getInt("stageprocedureid");
	}

	public int getTaskProcedureId()
		throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_TASKPCD);
		//return getInt("taskprocedureid");
	}

	public int getProcessId()
		throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_PROCESS);
		//return getInt("processid");
	}

	public int getSubProcessId()
	throws ISPACRuleException
	{
		return getInt(RuleProperties.RCTX_SUBPROCESS);
	}
	
	
	public String getNumExp()
	throws ISPACRuleException
	{
	    return get(RuleProperties.RCTX_NUMEXP);
		//return get("numexp");
	}

	public int getStageId()
	throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_STAGE);
		//return getInt("stageid");
	}

	public int getTaskId()
	throws ISPACRuleException
	{
	    return getInt(RuleProperties.RCTX_TASK);
		//return getInt("taskid");
	}

	public int getInt(String property)
		throws ISPACRuleException
	{
		try
		{
			return XPathUtil.getInt(mContextXML,"/ispac_context/"+property+"/text()");
		}
		catch (ISPACException e)
		{
			throw new ISPACRuleException(e);
		}
	}

	private int getIntNullSafe(String property) throws ISPACRuleException{
	    String value = get(property);
	    if (StringUtils.isBlank(value)){
	    	return 0;
	    }else{
	    	return Integer.parseInt(value);
	    }
	}	
	
	public Date getDate(String property)
		throws ISPACRuleException
	{
		try
		{
			return XPathUtil.getDate(mContextXML,"/ispac_context/"+property+"/text()");
		}
		catch (ISPACException e)
		{
			throw new ISPACRuleException(e);
		}
	}

	public String get(String property)
		throws ISPACRuleException
	{
		try
		{
			//return XPathUtil.get(mContextXML,"/ispac_context/"+property+"/text()");
		 	return StringEscapeUtils.unescapeXml(XPathUtil.get(mContextXML,"/ispac_context/"+property+"/text()")); 
		}
		catch (ISPACException e)
		{
			throw new ISPACRuleException(e);
		}
	}

	public int getEvent() throws ISPACRuleException {
		//return getInt(RuleProperties.RCTX_EVENT);
		return getIntNullSafe(RuleProperties.RCTX_EVENT);

	}

	public int getObjectId() throws ISPACRuleException {
	    //return getInt(RuleProperties.RCTX_OBJECTID);
		return getIntNullSafe(RuleProperties.RCTX_OBJECTID);
	}

	public int getObjectType() throws ISPACRuleException {
	    //return getInt(RuleProperties.RCTX_OBJECTTYPE);
		return getIntNullSafe(RuleProperties.RCTX_OBJECTTYPE);
	}
	

}
