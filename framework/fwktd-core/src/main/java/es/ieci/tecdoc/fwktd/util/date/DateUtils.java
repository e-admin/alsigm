package es.ieci.tecdoc.fwktd.util.date;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidades de tratamiento de fechas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DateUtils {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(DateUtils.class);

	/**
	 * Constructor.
	 */
	private DateUtils() {
	}

	/**
	 * Clona una fecha.
	 * 
	 * @param date
	 *            Objeto fecha original.
	 * @return Objeto fecha clonado.
	 */
	public static Date cloneDate(Date date) {

		Date clonedDate = null;

		if (date != null) {
			clonedDate = new Date(date.getTime());
		}

		return clonedDate;
	}

	/**
	 * Convierte una fecha al formato XMLGregorianCalendar.
	 *
	 * @param date
	 *            Fecha.
	 * @return XMLGregorianCalendar.
	 */
	public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {

		if (date == null) {
			return null;
		}

		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);

			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

		} catch (DatatypeConfigurationException e) {
			logger.error("Error en la transformación de la fecha [" + date
					+ "]", e);
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Convierte una fecha del formato XMLGregorianCalendar a Date
	 *
	 * @param xmlGregorianCalendar
	 *            Fecha
	 * @return Date
	 */
	public static Date toDate(XMLGregorianCalendar xmlGregorianCalendar) {

		if (xmlGregorianCalendar == null) {
			return null;
		}

		return xmlGregorianCalendar.toGregorianCalendar().getTime();
	}

}
