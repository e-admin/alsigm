package taglib.js;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag que oculta un elmento del interfaz mediante javascript
 * 
 */
public class HideElementTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String htmlElementID = null;

	public String getHtmlElementID() {
		return htmlElementID;
	}

	public void setHtmlElementID(String htmlElementID) {
		this.htmlElementID = htmlElementID;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println("<script>");
			out.print("xDisplay('");
			out.print(htmlElementID);
			out.println("', 'none'); ");
			out.println("</script>");
		} catch (IOException ioe) {
		}
		return EVAL_PAGE;
	}
}
