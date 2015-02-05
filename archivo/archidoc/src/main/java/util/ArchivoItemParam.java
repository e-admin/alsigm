/*
 * Created on 25-ene-2005
 *
 *  * Window - Preferences - Java - Code Style - Code Templates
 */
package util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import common.Constants;

/**
 * Tag para la creacion de menus de archivo
 * 
 * @author ABELRL
 * 
 */
public class ArchivoItemParam extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ArchivoItemParam.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// private final static String DEFAULT_ENCODE=1;

	ArchivoMenuItem parent;

	String name;
	String value;
	boolean encode = false;
	String codeencode = Constants.ENCODING_ISO_8859_1;

	/**
	 * @return Returns the codeEncode.
	 */
	public String getCodeencode() {
		return codeencode;
	}

	/**
	 * @param codeEncode
	 *            The codeEncode to set.
	 */
	public void setCodeencode(String codeEncode) {
		this.codeencode = codeEncode;
	}

	/**
	 * @return Returns the encode.
	 */
	public boolean isEncode() {
		return encode;
	}

	/**
	 * @param encode
	 *            The encode to set.
	 */
	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
		return super.doEndTag();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {
		// busqueda del padre
		parent = (ArchivoMenuItem) findAncestorWithClass(this,
				ArchivoMenuItem.class);

		try {

			StringBuffer tmp = new StringBuffer();

			if (isEncode()) {
				tmp.append(URLEncoder.encode(getValue(), getCodeencode()));
			} else {
				tmp.append(getValue());
			}

			parent.setParam(name, tmp.toString());

		} catch (IOException e) {
			logger.error(e);
		}

		return EVAL_PAGE;
	}

}
