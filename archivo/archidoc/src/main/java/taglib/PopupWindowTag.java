/*
 * Created on 13-abr-2005
 *
 */
package taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author LUISANVE
 * 
 */
public class PopupWindowTag extends BodyTagSupport {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String title = null;
	String preferredWidth = null;
	String preferredHeight = null;

	String name = null;

	public String getPreferredHeight() {
		return preferredHeight;
	}

	public void setPreferredHeight(String preferredHeight) {
		this.preferredHeight = preferredHeight;
	}

	public String getPreferredWidth() {
		return preferredWidth;
	}

	public void setPreferredWidth(String preferredWidth) {
		this.preferredWidth = preferredWidth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	String getPopupName() {
		if (name == null)
			name = "popup_" + this.getId();
		return name;
	}

	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			String contextPath = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
			StringBuffer buffer = new StringBuffer();
			buffer.append("<script language='JavaScript' src='");
			buffer.append(contextPath).append("/js/sigia_core.js'");
			buffer.append(" type='text/JavaScript'></script>");
			out.println(buffer.toString());
			out.println("<script>");
			out.print(getPopupName());
			out.print("= new PopupWindow(");
			return EVAL_BODY_INCLUDE;
		} catch (IOException ioe) {
			return SKIP_BODY;
		}
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print("<a href='javascript:GraphicToolkit.showWindow('");
			out.print(getBodyContent().toString());
			out.println("</script>");
		} catch (IOException ioe) {
			return SKIP_BODY;
		}

		return EVAL_PAGE;
	}

}
