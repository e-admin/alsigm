package ieci.tdw.ispac.deadline;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.xml.XmlObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class CalendarDef extends XmlObject {
	
	private static final long serialVersionUID = 1L;
	
	private static final Properties PROPERTIES = new Properties();
	private static final String KEY = "ID";
	
	HashSet mholydays = new HashSet();
	//HashSet mweekholydays = new HashSet();
	List mweekholydays = new ArrayList();
	String name;
	
	static {
		PROPERTIES.addProperties(new Property [] {
				new Property(1, "NOMBRE"),
				new Property(2, "HOLYDAYS"),
				new Property(3, "WEEKHOLYDAYS")
		});
	}
	public CalendarDef() throws ISPACException {
		super(PROPERTIES, KEY);
		//addWeekHolyday("SABADO");
		//addWeekHolyday("DOMINGO");
	}	
	
	
	public CalendarDef(String xml) throws ISPACException {
		super(PROPERTIES, KEY);
		parseCalendarDef(xml);
	}
	
	private void parseCalendarDef(String calendarConfig) throws ISPACException{
		Document doc=XMLDocUtil.newDocument(calendarConfig);
		loadName(doc);
		loadHolydays(doc);
		loadWeekHolydays(doc);
	}
	
	private void loadName(Document doc) throws ISPACException {
		NodeList nodelist=XPathUtil.getNodeList(doc,"/calendar/name/text()");
		
		for (int i = 0; i < nodelist.getLength(); i++)
		{
			String name = nodelist.item(i).getNodeValue();
			this.name = name;
		}
	}
	
	public HolydayDef addHolyday(String name, String date){
		HolydayDef holydayDef = new HolydayDef();
		holydayDef.setName(name);
		holydayDef.setDate(date);
		mholydays.add(holydayDef);
		return holydayDef;
	}
	
	public void removeHolyday(String date){
		List holydays = getHolydays();
		for (Iterator iter = holydays.iterator(); iter.hasNext();) {
			HolydayDef holyday = (HolydayDef) iter.next();
			if (holyday.getDate().equalsIgnoreCase(date)){
				mholydays.remove(holyday);
			}
		}
	}
	
	public void loadHolydays(Document doc) throws ISPACException
	{	
		NodeList nodelist=XPathUtil.getNodeList(doc,"/calendar/holydays/anual/holyday");
		for (int i = 0; i < nodelist.getLength(); i++)
		{

//			HolydayDef holydayDef = new HolydayDef();
			String sName = XPathUtil.get( nodelist.item(i), "name/text()");
			String sDate = XPathUtil.get( nodelist.item(i), "date/text()");
			addHolyday(sName, sDate);
//			holydayDef.setName(sName);
//			holydayDef.setDate(sDate);
//			mholydays.add(holydayDef);

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
	
	public String getXmlValues() {
	
		StringBuffer str = new StringBuffer();

		str.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		str.append("<calendar>");
		str.append("<holydays>");
		str.append("<week>");
		for (Iterator iter = mweekholydays.iterator(); iter.hasNext();) {
			Integer day = (Integer) iter.next();
			str.append("<day>");str.append(getWeekHolyday(day.intValue()));str.append("</day>");
		}
		str.append("</week>");
		str.append("<anual>");
		for (Iterator iter = mholydays.iterator(); iter.hasNext();) {
			HolydayDef holyday = (HolydayDef) iter.next();
			str.append(holyday.getXmlValues());
		}
		str.append("</anual>");
		str.append("</holydays>");
		str.append("</calendar>");
		return str.toString();
		
	}
	
	private String getWeekHolyday(int dayOrder)
	{
		
		switch (dayOrder) {
		case Calendar.MONDAY:
			return "LUNES";
			
		case Calendar.TUESDAY:
			return "MARTES";

		case Calendar.WEDNESDAY:
			return "MIERCOLES";

		case Calendar.THURSDAY:
			return "JUEVES";

		case Calendar.FRIDAY:
			return "VIERNES";

		case Calendar.SATURDAY:
			return "SABADO";

		case Calendar.SUNDAY:
			return "DOMINGO";

		default:
			return "El dia de la semana '"+dayOrder+"' no existe.";
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
			mweekholydays.add(new Integer(Calendar.TUESDAY));
			return;
		}
		if (sday.compareToIgnoreCase("MIERCOLES")==0)
		{
			mweekholydays.add(new Integer(Calendar.WEDNESDAY));
			return;
		}
		if (sday.compareToIgnoreCase("JUEVES")==0)
		{
			mweekholydays.add(new Integer(Calendar.THURSDAY));
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
		throw new ISPACException("Error en la configuración del calendario."+
				"El dia de la semana '"+sday+"' no existe.");
	}
	
	
	
	public void addWeekEnd(String[] dias) throws ISPACException {
		
		if(dias!=null) {
			mweekholydays.clear();
			for ( int i = 0; i < dias.length; i++ ) {
				addWeekHolyday(getWeekHolyday(Integer.parseInt(dias[i])));
			}	
		}
		else {
			mweekholydays.clear();
		}
	}
	
	public String getNombre(){
		return name;
	}
	
	public static List createList(Object [] objs) {
		List list = null;
		
		if (objs != null) {
			list = new ArrayList();
			
			for (int i = 0; i < objs.length; i++) {
				list.add(objs[i]);
			}
		}
		
		return list;
	}
	
	public List getHolydays(){
		HolydayDef[] holydays = (HolydayDef[]) mholydays.toArray(new HolydayDef[mholydays.size()]); //defino un vector de HolydayDef
		
		//almacena una lista de HolydayDef
		
		return createList(holydays);
	}
	
	
	public List getAllWeekDays () throws ISPACException{
		
		List result = new ArrayList();
		
		for ( int i=2; i<=7; i++ ) {
			Integer integ = new Integer(i);
			WeekDef def = new WeekDef(integ.toString(), getWeekHolyday(i));
			result.add(def);
		}
		result.add(new WeekDef("1", getWeekHolyday(1)));

	
		return result;		
	}
	
	/*
	
	public List getWeekEndDays() throws ISPACException{

		List result = new ArrayList();
		if (!mweekholydays.isEmpty()){
			Iterator it = mweekholydays.iterator();
			while (it.hasNext()){
				Integer day = (Integer) it.next();
				WeekDef def = new WeekDef(getWeekHolyday(day.intValue()));
				result.add(def);
			}
		}
		
		return result;
		
	}
	
	*/
	
	
	public String[] getWeekEndDays() throws ISPACException{

		String [] dias = new String [mweekholydays.size()];
		int i = 0;
		
		if (!mweekholydays.isEmpty()){
			Iterator it = mweekholydays.iterator();
			while (it.hasNext()){
				Integer day = (Integer) it.next();
				dias[i] = day.toString();
				i++;
			}
		}
		
		return dias;
	
	}

	
	
	/*
	
	public String[] getWeekEndDays() throws ISPACException{
		
		//WeekDef[] prueba = new WeekDef[mweekholydays.size()];
		
		//pone los dos valores a null es un error.
		
		//El toArray necesita la clase y el tamaño, y lo que se hace es pasar mweekholydays a weekDef[]
		//mirar s/map/table    y camibar weekdef
		//deberia guardar 7=domingo
		//HashSet s = (HashSet) mweekholydays.clone();
		
		
		String dia = null;
		String [] vdias = new String[mweekholydays.size()];
		
		int contador = 0;
		//asi funciona y saca los dias en numeros.
		for (Iterator iter = mweekholydays.iterator(); iter.hasNext();) {
			Integer day = (Integer) iter.next();			
			String sday = getDayByInteger(day);			
			//vdias[contador++] = "<day>"+sday+"</day>";
			vdias[contador++] = sday;
		}
		

	//	WeekDef[] weekEndDays = new WeekDef[mweekholydays.size()];
		
		//WeekDef[] weekEndDays = (WeekDef[]) mweekholydays.toArray(new WeekDef[mweekholydays.size()]); 
		//return createList(vdias);
		
		return vdias;
	}*/

}
