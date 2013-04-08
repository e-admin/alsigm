package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

/**
 * Clase helper para la conversión de instancias de
 * <code>XMLGregorianCalendar</code> a/de <code>java.util.Date</code>.
 * 
 * @see XMLGregorianCalendar
 * 
 * @author IECISA
 * 
 */
public class XMLGregorianCalendarHelper {

	/**
	 * Convierte una fecha de tipo <code>XMLGregorianCalendar</code> en su
	 * representación como <code>java.util.Date</code>.
	 * 
	 * @see XMLGregorianCalendar
	 * 
	 * @param calendar
	 *            fecha a convertir
	 * @return
	 */
	public static Date toDate(XMLGregorianCalendar calendar) {
		Date result=null;
		
		if (calendar!=null){
			result=calendar.toGregorianCalendar().getTime();
		}
		
		return result;
	}

	/**
	 * Convierte una fecha de tipo <code>java.util.Date</code> en su
	 * representación XML como <code>XMLGregorianCalendar</code>.
	 * 
	 * @see XMLGregorianCalendar
	 * 
	 * @param date
	 *            fecha a convertir
	 * @return
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		Assert.notNull(date);

		XMLGregorianCalendar xmlGregorianCalendar = null;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);

			xmlGregorianCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			logger.error("Imposible traducir la fecha " + date
					+ " a XMLGregorianCalendar");
		}

		return xmlGregorianCalendar;
	}

	// Members
	protected static final Logger logger = Logger
			.getLogger(XMLGregorianCalendarHelper.class);
}
