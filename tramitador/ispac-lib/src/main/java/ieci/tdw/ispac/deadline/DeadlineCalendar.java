package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import org.w3c.dom.Document;

public class DeadlineCalendar {
	
	private final Document mdeadlineXML;
	//private final ClientContext mcontext;
	private final EventManager meventmgr;
	//private final String mnumexp;
	
	public DeadlineCalendar(ClientContext context,EventManager eventmgr,Document deadlineXML,String numexp) 
	{
		mdeadlineXML=deadlineXML;
		//mcontext=context;
		meventmgr=eventmgr;
		//mnumexp=numexp;
	}
	public Integer getCalendarId() throws ISPACException
	{
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/calendar/ctcalendar"))
			return getCTCalendarId();
		
		if (XPathUtil.existNode(mdeadlineXML, "/deadline/calendar/rule"))
			return getRuleCalendarId();
		
		return null;
	}
	
	private Integer getCTCalendarId() throws ISPACException
	{
		int calendarId = XPathUtil.getInt(mdeadlineXML, "/deadline/calendar/ctcalendar/@id");
		
		return new Integer(calendarId);	
	}
	
	private Integer getRuleCalendarId() throws ISPACException
	{
		int ruleid = XPathUtil.getInt(mdeadlineXML, "/deadline/calendar/rule/@id");
		Integer calendarId=null;
		
		try
		{
			calendarId=(Integer)meventmgr.executeRule(ruleid);
		} catch (ClassCastException e)
		{
			throw new ISPACException("El valor obtenido por la ejecución de la regla"+
					" como calendario del plazo no es del tipo adecuado",e);
		}
	
		return calendarId;	
	}


}
