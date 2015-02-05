/*
 * Created on 23-jun-2004
 *
 */
package ieci.tdw.ispac.deadline;


import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author juanin
 */
public class DeadlineFactory
{
	HashSet mholydays;
	HashSet mweekholydays;
	int idCalendario;
	
	public DeadlineFactory(ClientContext ctx, int idCalendario)
	throws ISPACException
	{
		IItemCollection itemcol = ctx.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_CALENDARIOS,
				"WHERE ID=" + idCalendario);
		IItem itemCal = null;
		if (itemcol.next())
			itemCal = itemcol.value();
		if (itemCal == null)
			throw new ISPACInfo("exception.calendar.notFound");
		String calendarioConfigXML = itemCal.getString("CALENDARIO");
		mholydays=new HashSet();
		mweekholydays=new HashSet();

		Document doc=XMLDocUtil.newDocument(calendarioConfigXML);
		loadHolydays(doc);
		loadWeekHolydays(doc);
		this.idCalendario = idCalendario;
	}	
	
//	//Añadido para poder ser llamado sin estar asociado a expediente ni envento alguno
//	public DeadlineFactory(String calendarConfig)
//	throws ISPACException
//	{
//		mholydays=new HashSet();
//		mweekholydays=new HashSet();
//
//		Document doc=XMLDocUtil.newDocument(calendarConfig);
//		loadHolydays(doc);
//		loadWeekHolydays(doc);
//	}	
	
//	public int getIdCalendario() {
//		return idCalendario;
//	}
//
//	public void setIdCalendario(int idCalendario) {
//		this.idCalendario = idCalendario;
//	}

	public Deadline createDeadline(Document deadlineXML, Date baseDate) throws ISPACException {
		
		int nUnits = getInt(deadlineXML, "units");
		int nMagnitude = getInt(deadlineXML, "magnitude");
		
		Deadline deadline = new Deadline(nUnits, nMagnitude, baseDate,
				mweekholydays);
		
		deadline.addHolydays(mholydays);
		deadline.setIdCalendario(idCalendario);
		
		return deadline;
	}
	
	public Deadline createDeadline(ClientContext cctx,EventManager eventmgr,
			Document deadlineXML,String numexp)
		throws ISPACException
	{
		int nUnits = getInt(deadlineXML, "units");
		int nMagnitude = getInt(deadlineXML, "magnitude");

		DeadlineBaseDate deadlinebasedate = new DeadlineBaseDate(cctx, eventmgr, deadlineXML,
				numexp);

		Deadline deadline = new Deadline(nUnits, nMagnitude, deadlinebasedate.getBaseDate(),
				mweekholydays);
		
		deadline.addHolydays(mholydays);
		deadline.setIdCalendario(idCalendario);
		
		return deadline;
	}
	
	//Añadido para poder ser llamado sin estar asociado a expediente ni envento alguno
	public Deadline createDeadline(int units, int magnitude, Date baseDate){
	    Deadline deadline = new Deadline(units, magnitude, baseDate, mweekholydays);
	    deadline.addHolydays(mholydays);
	    deadline.setIdCalendario(idCalendario);
	    return deadline;
	}	
	
	public int getInt(Document doc,String property) throws ISPACException
	{
		return XPathUtil.getInt(doc,"/deadline/" + property + "/text()");
	}

	public Date getDate(Document doc,String property) throws ISPACException
	{
		return XPathUtil.getDate(doc,"/deadline/" + property + "/text()");
	}

	public String get(Document doc,String property) throws ISPACException
	{
		return XPathUtil.get(doc,"/deadline/" + property + "/text()");
	}
	
	
	public void loadHolydays(Document doc) throws ISPACException
	{	
		NodeList nodelist=XPathUtil.getNodeList(doc,"/calendar/holydays/anual/holyday/date/text()");
		
		for (int i = 0; i < nodelist.getLength(); i++)
		{
			String sdate = nodelist.item(i).getNodeValue();
			mholydays.add(TypeConverter.toDate(sdate));
		}
	}
	
	public void loadWeekHolydays(Document doc)
	throws ISPACException
	{	
		NodeList nodelist=XPathUtil.getNodeList(doc,"/calendar/holydays/week/day/text()");
		
		for (int i = 0; i < nodelist.getLength(); i++)
		{
			String sday = nodelist.item(i).getNodeValue();
			addWeekHolyday(sday);
		}
	}

	private void addWeekHolyday(String sday)
	throws ISPACException
	{
		
		if (sday.compareToIgnoreCase("LUNES")==0)
		{
			mweekholydays.add(new Integer(Calendar.MONDAY));
			return;
		}
		if (sday.compareToIgnoreCase("MARTES")==0)
		{
			mweekholydays.add(new Integer(Calendar.THURSDAY));
			return;
		}
		if (sday.compareToIgnoreCase("MIERCOLES")==0)
		{
			mweekholydays.add(new Integer(Calendar.WEDNESDAY));
			return;
		}
		if (sday.compareToIgnoreCase("JUEVES")==0)
		{
			mweekholydays.add(new Integer(Calendar.TUESDAY));
			return;
		}
		if (sday.compareToIgnoreCase("VIERNES")==0)
		{
			mweekholydays.add(new Integer(Calendar.FRIDAY));
			return;
		}
		if (sday.compareToIgnoreCase("SABADO")==0)
		{
			mweekholydays.add(new Integer(Calendar.SATURDAY));
			return;
		}
		if (sday.compareToIgnoreCase("DOMINGO")==0)
		{
			mweekholydays.add(new Integer(Calendar.SUNDAY));
			return;
		}
		
		throw new ISPACException("Error en la configuración del calendario." +
				"El dia de la semana '" + sday + "' no existe.");
	}
}
