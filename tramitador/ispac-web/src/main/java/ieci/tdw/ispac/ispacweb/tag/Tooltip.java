package ieci.tdw.ispac.ispacweb.tag;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

/**
 * 
 * Tag para añadir tooltips a elementos del formulario <br>
 * Usa tooltip.css <br>
 * <br>
 * Sintaxis:<br>
 *	&lt;ispac:tooltip elementId='k' headerKey='k' bodyKey='k'/&gt;<br>
 *<br>
 *	Todos los parámetros son claves del fichero ApplicationResources.properties<br>
 *	<br>
 *	elementId:	Identificador del elemento<br>
 *	headerKey:	Texto con la cabecera del tooltip<br> 
 *	bodyKey:	Cuerpo del tooltip<br>
 *
 */

public class Tooltip extends TagSupport {
	private static final long serialVersionUID = 5491979318291824309L; // Compiler generated

	private String elementId;
	private String headerKey;
	private String bodyKey;
	
	public int doStartTag() throws JspException {
		
		TagUtils tagUtils = TagUtils.getInstance();
	  	
		String[] args = new String[3];
//		args[0] = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, elementId);
		args[0] = elementId;
		args[1] = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, headerKey);
		args[2] = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, bodyKey);
		
		MessageFormat sOut = new MessageFormat (
			"<div class=\"tooltip for_{0}\">" +
				"<h1>{1}</h1>" +
				"<p>{2}</p>"+
			"</div>"
		);

		String sText = sOut.format(args);
		JspWriter out = pageContext.getOut();
		try {
			out.println(sText);
		} catch (IOException e) {
			throw new JspException();
		}
		return (EVAL_BODY_INCLUDE);
	}

	public void setBodyKey(String bodyKey) {
		this.bodyKey = bodyKey;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}
}