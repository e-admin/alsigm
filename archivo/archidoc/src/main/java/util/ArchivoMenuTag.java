/*
 * Created on 24-ene-2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package util;

import org.apache.log4j.Logger;

import common.HTMLConstants;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author ABELRL
 * 
 */
public class ArchivoMenuTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ArchivoMenuTag.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		try {

			JspWriter out = pageContext.getOut();
			out.println(HTMLConstants.getEndTag(HTMLConstants.TAG_DIV));

		} catch (IOException e) {
			logger.error(e);
		}
		return EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();

		try {
			out.println(HTMLConstants.getStartTag(HTMLConstants.TAG_DIV));
		} catch (IOException e) {
			logger.error(e);
		}

		return EVAL_PAGE;
	}
}
