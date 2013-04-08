package common.view;

import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * Decorador para formatear la fecha
 */
public class DateDecorator implements DisplaytagColumnDecorator {

	/**
	 * FastDateFormat usado para formatear el objeto fecha.
	 */
	private FastDateFormat dateFormat = FastDateFormat
			.getInstance("dd/MM/yyyy");

	/**
	 * Transforma el objeto dado en su representación String. El objeto se
	 * supone que será una fecha.
	 * 
	 * @param columnValue
	 *            Object Objeto con el valor de la columna
	 * @return String Representacion textual del objeto
	 */
	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum mediaType) throws DecoratorException {
		Date date = (Date) columnValue;
		return this.dateFormat.format(date);
	}
}