/*
 * Created on 24-ene-2005
 *
 */
package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author ABELRL
 * 
 */
public class ArchivoMenuItem extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ArchivoMenuItem.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArchivoMenuTag parent;
	HashMap parametros = new HashMap();

	String value;
	String target;

	public String getParam(String name) {
		return (String) parametros.get(name);
	}

	public void setParam(String name, String value) {
		parametros.put(name, value);
	}

	/**
	 * @return Returns the target.
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            The target to set.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();

			StringBuffer tmp = new StringBuffer();
			String contextPath = this.pageContext.getServletContext()
					.getServletContextName();
			if (!getTarget().equalsIgnoreCase("submit")) {
				tmp.append("<input type=\"button\" value=\"" + getValue()
						+ "\" ");
				tmp.append("onclick=\"window.location='");
				tmp.append("/" + contextPath + getTarget());
				StringBuffer strParametros = new StringBuffer();
				Iterator it = parametros.keySet().iterator();
				String key;
				int i = 0;
				while (it.hasNext()) {

					if (i == 0)
						strParametros.append("?");
					else
						strParametros.append("&");

					key = (String) it.next();
					value = (String) getParam(key);
					strParametros.append(key + "=" + value);
					i++;
				}

				tmp.append(strParametros.toString());
				tmp.append("';\">");
			} else {
				tmp.append("<input type=\"submit\" value=\"" + getValue()
						+ "\" >");
			}

			out.println(tmp);

			parametros.clear();
			target = "";

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

		// busqueda del padre
		parent = (ArchivoMenuTag) findAncestorWithClass(this,
				ArchivoMenuTag.class);

		return EVAL_PAGE;
	}
}
