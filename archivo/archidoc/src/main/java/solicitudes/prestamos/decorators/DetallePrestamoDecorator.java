package solicitudes.prestamos.decorators;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import solicitudes.prestamos.vos.DetallePrestamoVO;

import common.Constants;
import common.util.XmlFacade;

/**
 * Decorador para formatear las columna de manera adecuada en función de la
 * columna que se desea mostrar.
 */
public class DetallePrestamoDecorator implements DisplaytagColumnDecorator {

	/** Logger de la clase */
	private static Logger logger = Logger
			.getLogger(DetallePrestamoDecorator.class);

	/**
	 * Transforma el objeto dado en su representación String. Comprueba la fecha
	 * inicial de reserva para en caso positivo colorear de rojo la columna.
	 * 
	 * @param columnValue
	 *            Object Objeto con el valor de la columna
	 * @return String Representacion textual del objeto
	 */
	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum arg2) throws DecoratorException {

		StringBuffer salida = new StringBuffer();
		String observaciones = Constants.STRING_EMPTY;

		if (columnValue != null) {
			DetallePrestamoVO detalle = (DetallePrestamoVO) columnValue;

			if (detalle.getInformacion() != null
					&& detalle.getInformacion().trim().length() > 0) {
				try {
					XmlFacade xmlFacade = new XmlFacade(
							detalle.getInformacion());
					observaciones = xmlFacade
							.get(DetallePrestamoVO.PATH_A_OBSERVACIONES);
				} catch (Exception e) {
					logger.warn("Error obteniendo el tratador de información",
							e);
				}
			}
		}

		salida.append(observaciones);

		return salida.toString();
	}
}