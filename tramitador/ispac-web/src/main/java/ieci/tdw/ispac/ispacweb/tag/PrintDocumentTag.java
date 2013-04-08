package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class PrintDocumentTag extends DefaultTextTag {

	protected String document = null;
	protected String callback = null;
	protected boolean inline = false;
	
	public String getDocument() {
		return (this.document);
	}
	public void setDocument(String document) {	
		this.document = document;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public boolean isInline() {
		return inline;
	}
	public void setInline(boolean inline) {
		this.inline = inline;
	}
	
	public int doStartTag() throws JspException {
		if (this.document.equalsIgnoreCase("")) {
			ISPACInfo e = new ISPACInfo(
					"printdocument:No se ha indicado documento");
			pageContext.setAttribute(Globals.EXCEPTION_KEY, e,
					PageContext.REQUEST_SCOPE);
			throw new JspException(e.toString());
		}

		try {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();

			// Tipo MIME del documento
			String mimeType = DocumentUtil.getDocumentMimeType(getSession(),
					Integer.parseInt(this.document));

			// URL del documento
			String url = DocumentUtil.generateURL(request, "download", getTicket(), 
					this.document, mimeType);

			String htmlCode = null;
			if (inline) {
				htmlCode = "printDocument('" + url + "', '" + mimeType + "', '"
					+ this.document + "', " 
					+ (StringUtils.isNotBlank(callback) ? "'" + callback + "'" : null) + ");";
			} else {
				htmlCode = DocumentUtil.generateHtmlCodeForPrinting(
						pageContext.getServletContext(), this.document, url,
						mimeType, this.callback);
			}

			JspWriter writer = pageContext.getOut();

			writer.print(htmlCode);
			
		} catch (Exception e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}

}
