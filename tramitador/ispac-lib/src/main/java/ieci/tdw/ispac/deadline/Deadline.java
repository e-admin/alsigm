package ieci.tdw.ispac.deadline;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.Set;

/**
 * Información de plazos
 */
public class Deadline extends GregorianCalendar
{
    private static final long serialVersionUID = 1L;
    public int idCalendario;
    public static final int DEADLINE_DAY = 1;
	public static final int DEADLINE_BUSINESSDAY = 2;
	public static final int DEADLINE_MONTH = 3;
	public static final int DEADLINE_YEAR = 4;
	
	int munits;
	int mmagnitude;
	Date mbasedate;
	
	Set mWeekHolydays;
	Set mHolydays;

	/**
	 *  
	 */
	public Deadline(int units,int magnitude,Date basedate,Set weekHolydays)
	{
		munits=units;
		mmagnitude=magnitude;
		mbasedate=cleanTime(basedate);
		mWeekHolydays=weekHolydays;
		mHolydays=new HashSet();
	}
	
	public void addHolydays(Set holydays)
	{
		mHolydays.addAll(holydays);				
	}
		
	protected Date cleanTime(Date date)
	{
		Calendar cal=new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}

		
	public Date getFinalDate() throws ISPACException
	{
		setTime(mbasedate);

		switch(munits)
		{
		case DEADLINE_DAY:  
			return calcDays();
		case DEADLINE_BUSINESSDAY:  
			return calcBussinessDays();
		case DEADLINE_MONTH:  
			return calcMonths();
		case DEADLINE_YEAR:
		    return calcYears();
		}
		return null;
	}
	
	protected Date calcDays() throws ISPACException
	{
		add(Calendar.DATE,mmagnitude);
//Se comprobaba si la fecha limite resultante coincidia con un dia no habil, se iba incrementando hasta coincidir con un dia habil. Se considera
//que no se debe realizar asi, se debe dejar tal cual tras el calculo inicial. Ej. 12/09/2007 se incrementa en 10 dias naturales -> 22/09/2007, al ser este dia un sabado y considerado como no habil,
//mediante esta comprobacion se iba desplazando la fecha limite hasta terminar en 24/09/2007 que seria lunes. Pero se considera que esto no es asi.
//		while(skipHolidays())
//		{
//			add(Calendar.DATE,1);
//		}
		return getTime();
	}
	
	protected Date calcBussinessDays() throws ISPACException
	{
		while (mmagnitude>0)
		{
			add(Calendar.DATE,1);
			if (!skipHolidays(this))
				mmagnitude--;
		}
		return getTime();
	}
	
	/**
	 * @return
	 */
//	private boolean skipHolidays()
//	{
//		if (mWeekHolydays.contains(new Integer(get(DAY_OF_WEEK))))
//			return true; 
//		
//		if (mHolydays.contains(getTime()))
//			return true;
//			
//		return false;
//	}
	private boolean skipHolidays(GregorianCalendar calendar)
	{
		if (mWeekHolydays.contains(new Integer(calendar.get(DAY_OF_WEEK))))
			return true; 
		
		if (mHolydays.contains(calendar.getTime()))
			return true;
			
		return false;
	}
	protected Date calcMonths() throws ISPACException
	{
		add(Calendar.MONTH,mmagnitude);
//		Se comprobaba si la fecha limite resultante coincidia con un dia no habil, se iba incrementando hasta coincidir con un dia habil. Se considera
//		que no se debe realizar asi, se debe dejar tal cual tras el calculo inicial.		
//		while(skipHolidays())
//		{
//			add(Calendar.DATE,1);
//		}
		return getTime();
	}

	
	protected Date calcYears() throws ISPACException
	{
		add(Calendar.YEAR, mmagnitude);
//		Se comprobaba si la fecha limite resultante coincidia con un dia no habil, se iba incrementando hasta coincidir con un dia habil. Se considera
//		que no se debe realizar asi, se debe dejar tal cual tras el calculo inicial.
//		while(skipHolidays())
//		{
//			add(Calendar.DATE,1);
//		}
		return getTime();
	}		

	public int getUnits() {
		return munits;
	}
	
	public int getMagnitude() {
		return mmagnitude;
	}
	
	public Date getBaseDate(){
		return mbasedate;
	}
	
	public int calcPassNaturalDays(Date fechaInicio) throws ISPACException {
		Date today = new Date();
		GregorianCalendar todayGregorianCalendar = new GregorianCalendar();
		todayGregorianCalendar.setTime(today);
		GregorianCalendar basedateGregorianCalendar = new GregorianCalendar();
		basedateGregorianCalendar.setTime(fechaInicio);
		//calculo el total de dias
		return calculateDays( basedateGregorianCalendar.getTime(), todayGregorianCalendar.getTime());
	}
	
	private int calculateDays(Date initialDate, Date finalDate){
		long diffMillis = finalDate.getTime() - initialDate.getTime();
		long diffDays = diffMillis / (1000 * 60 * 60 * 24);
		return new Long(diffDays).intValue();
	}

	public int getIdCalendario() {
		return idCalendario;
	}

	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}
	
	
}
