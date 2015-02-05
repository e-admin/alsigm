package solicitudes.consultas.decorators;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.DetalleConsultaVO;

import common.Messages;
import common.util.XmlFacade;

/**
 * Decorador para formatear las columna de manera adecuada en función de la
 * columna que se desea mostrar.
 */
public class DetalleConsultaDecorator implements DisplaytagColumnDecorator {

	/** Logger de la clase */
	private static Logger logger = Logger
			.getLogger(DetalleConsultaDecorator.class);

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

		ServletRequest request = pageContext.getRequest();
		int copiasSimples = 0;
		int copiasCertificadas = 0;
		StringBuffer salida = new StringBuffer();

		if (columnValue instanceof DetalleConsultaVO) {
			DetalleConsultaVO detalle = (DetalleConsultaVO) columnValue;

			if (detalle.getInformacion() != null
					&& detalle.getInformacion().trim().length() > 0) {
				try {
					XmlFacade xmlFacade = new XmlFacade(
							detalle.getInformacion());

					try {
						copiasSimples = Integer.parseInt(xmlFacade
								.get(DetalleConsultaVO.PATH_A_COPIASIMPLE));
					} catch (NumberFormatException nfe) {
						logger.warn(
								"Error obteniendo las copias simples del detalle "
										+ detalle.getIdudoc()
										+ " de la solicitud "
										+ detalle.getIdsolicitud(), nfe);
					}
					try {
						copiasCertificadas = Integer
								.parseInt(xmlFacade
										.get(DetalleConsultaVO.PATH_A_COPIACERTIFICADA));
					} catch (NumberFormatException nfe) {
						logger.warn(
								"Error obteniendo las copias certificadas del detalle "
										+ detalle.getIdudoc()
										+ " de la solicitud "
										+ detalle.getIdsolicitud(), nfe);
					}
				} catch (Exception e) {
					logger.warn("Error obteniendo el tratador de información",
							e);
				}
			}

			salida.append("<table><tr><td class=\"tdDatos\">");
			salida.append(copiasSimples);
			salida.append(" ");
			salida.append(Messages.getString(
					ConsultasConstants.CONSULTAS_COPIAS_SIMPLES,
					request.getLocale()));
			salida.append("</td></tr><tr><td class=\"tdDatos\">");
			salida.append(copiasCertificadas);
			salida.append(" ");
			salida.append(Messages.getString(
					ConsultasConstants.CONSULTAS_COPIAS_CERTIFICADAS,
					request.getLocale()));
			salida.append("</td></tr></table>");
		} else {
			if (columnValue != null) {
				salida.append((String) columnValue);
			}
		}

		return salida.toString();
	}
}