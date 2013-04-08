package solicitudes.consultas.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.DetalleConsultaVO;

import common.util.XmlFacade;

/**
 * Obtiene un HTML a partir del XML de la informacion de una consulta.
 */
public class GetInformacionFromXMLTag extends TagSupport {
	/** UID de la clase */
	private static final long serialVersionUID = 2005101901L;

	/** Logger de la clase */
	protected static final Logger logger = Logger
			.getLogger(GetInformacionFromXMLTag.class);

	/**
	 * Constructor por defecto de la clase
	 */
	public GetInformacionFromXMLTag() {
	}

	public int doEndTag() throws JspException {
		// Obtenemos el flujo de salida
		JspWriter out = super.pageContext.getOut();

		try {
			out.write(this.generateInformation());
		} catch (IOException ioe) {
			logger.error("Error generando el HTML de información: "
					+ ioe.getMessage());

			throw new JspException(ioe.toString());
		}

		return EVAL_PAGE;
	}

	/**
	 * Genera un HTML con la informacion pasada
	 * 
	 * @return
	 */
	private String generateInformation() {
		StringBuffer sb = new StringBuffer();
		int copiasSimples = 0;
		int copiasCertificadas = 0;
		String xml = null;

		DetalleConsultaVO consulta = (DetalleConsultaVO) ((HttpServletRequest) pageContext
				.getRequest()).getSession().getAttribute(
				ConsultasConstants.DETALLE_CONSULTA_KEY);
		if (consulta != null)
			xml = consulta.getInformacion();

		if (xml != null && xml.trim().length() > 0) {
			XmlFacade xmlFacade = new XmlFacade(xml);

			try {
				copiasSimples = Integer.parseInt(xmlFacade
						.get(DetalleConsultaVO.PATH_A_COPIASIMPLE));
			} catch (NumberFormatException nfe) {
				logger.warn("Error obteniendo las copias simples del xml "
						+ xml, nfe);
			}
			try {
				copiasCertificadas = Integer.parseInt(xmlFacade
						.get(DetalleConsultaVO.PATH_A_COPIACERTIFICADA));
			} catch (NumberFormatException nfe) {
				logger.warn("Error obteniendo las copias certificadas del xml "
						+ xml, nfe);
			}
		}

		sb.append("<table><tr><td class=\"tdDatos\">");
		sb.append(copiasSimples);
		sb.append(" copias simples</td></tr><tr><td class=\"tdDatos\">");
		sb.append(copiasCertificadas);
		sb.append(" copias certificadas</td></tr></table>");

		return sb.toString();
	}
}
