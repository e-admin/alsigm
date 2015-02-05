/*
 * Created on 13-abr-2005
 *
 */
package taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SetOnCloseListenerTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String type = null;

	Map listenerProperties = new HashMap();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	void setListenerPorperty(String property, String value) {
		listenerProperties.put(property, value);
	}

	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	private void flushCodeLine(JspWriter out, StringBuffer buff)
			throws IOException {
		out.println(buff.toString());
		buff.setLength(0);
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			PopupWindowTag parentTag = (PopupWindowTag) findAncestorWithClass(
					this, PopupWindowTag.class);
			StringBuffer buffer = new StringBuffer();
			buffer.append("popup_").append(parentTag.getId())
					.append("_onCloseListener");
			String closeListenerName = buffer.toString();
			buffer.setLength(0);
			buffer.append("var ").append(closeListenerName).append("= new ")
					.append(this.type).append("();");
			flushCodeLine(out, buffer);
			for (Iterator i = listenerProperties.keySet().iterator(); i
					.hasNext();) {
				String property = (String) i.next();
				buffer.append(closeListenerName).append(".setProperty('")
						.append(property).append("','");
				buffer.append(listenerProperties.get(property)).append("');");
				flushCodeLine(out, buffer);
			}
			buffer.append(parentTag.getPopupName()).append(
					".setOnCloseListener(");
			buffer.append(closeListenerName).append(");");
			flushCodeLine(out, buffer);
			out.println(buffer.toString());
		} catch (IOException ioe) {
		}
		return EVAL_PAGE;
	}

}
