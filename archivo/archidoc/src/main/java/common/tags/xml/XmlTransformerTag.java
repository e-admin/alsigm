package common.tags.xml;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Transforma un XML utilizando una hoja XSL.<br/>
 */
public class XmlTransformerTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(XmlTransformerTag.class);

	/** Nombre del parámetro que contiene el documento XML a transformar. */
	private String xmlParamName = null;

	/** Nombre del parámetro que contiene el documento XSL. */
	private String xslParamName = null;

	/**
	 * Establece el nombre del parámetro que contiene el documento XML a
	 * transformar.
	 * 
	 * @param xmlParamName
	 *            Nombre del parámetro que contiene el documento XML a
	 *            transformar.
	 */
	public void setXmlParamName(String xmlParamName) {
		this.xmlParamName = xmlParamName;
	}

	/**
	 * Establece el nombre del parámetro que contiene el documento XSL.
	 * 
	 * @param xslParamName
	 *            Nombre del parámetro que contiene el documento XSL.
	 */
	public void setXslParamName(String xslParamName) {
		this.xslParamName = xslParamName;
	}

	/**
	 * Se encarga de escribir la URL del action en función del valor almacenado
	 * en el parámetro de sesión: SecurityGlobals.REQUESTED_URL.
	 * 
	 * @return un código que indica si el método concluyó con éxito [EVAL_PAGE].
	 * @throws JspException
	 *             en caso de que se produzca algún error.
	 */
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();

			// Documento XML
			String xml = (String) pageContext.findAttribute(xmlParamName);

			// Documento XSL
			String xsl = (String) pageContext.findAttribute(xslParamName);

			// Transformación XSL
			if (!StringUtils.isEmpty(xml) && !StringUtils.isEmpty(xsl)) {
				ByteArrayOutputStream html = new ByteArrayOutputStream();
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory
						.newTransformer(new StreamSource(new StringReader(xsl)));
				transformer.transform(new StreamSource(new StringReader(xml)),
						new StreamResult(html));

				out.print(html.toString());
			}
		} catch (Exception e) {
			logger.error(
					"Error en la transformaci\u00F3n del XML en la p\u00E1gina JSP",
					e);
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}
}
