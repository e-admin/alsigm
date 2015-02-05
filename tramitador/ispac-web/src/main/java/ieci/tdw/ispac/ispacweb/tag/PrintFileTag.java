package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class PrintFileTag extends DefaultTextTag {

	protected String file = null;
	protected String delete = "true";
	protected String callback = null;
	
	public String getFile() {
		return (this.file);
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getDelete() {
		return (this.delete);
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public int doStartTag() throws JspException {
		if (this.file.equalsIgnoreCase("")) {
			ISPACInfo e = new ISPACInfo("printfile:No se ha indiado fichero");
			pageContext.setAttribute(Globals.EXCEPTION_KEY, e,
					PageContext.REQUEST_SCOPE);
			throw new JspException(e.toString());
		}

		try {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();

			// Tipo MIME del documento
			String mimeType = DocumentUtil.getDocumentMimeType(getSession(),
					Integer.parseInt(this.file));

			// URL del documento
			String url = DocumentUtil.generateURL(request, "download", getTicket(), 
					this.file, mimeType);

			StringBuffer htmlCode = new StringBuffer();
			htmlCode.append(DocumentUtil.generateHtmlCodeForPrinting(
					pageContext.getServletContext(), this.file, url, mimeType,
					callback));

			if (delete.equalsIgnoreCase("true")) {

				String sURL = DocumentUtil.generateDocumentDeleteURL(request,
						this.file);

				htmlCode.append("<script language='javascript'>");
				htmlCode.append("var xmlhttp = false;");
				htmlCode.append("try {");
				htmlCode
						.append("xmlhttp = new ActiveXObject('MSXML2.XMLHTTP');");
				htmlCode.append("} catch (e) {}");
				htmlCode.append("if (!xmlhttp) {");
				htmlCode.append("try {");
				htmlCode.append("xmlhttp = new XMLHttpRequest();");
				htmlCode.append("} catch (e) {}");
				htmlCode.append("}");
				htmlCode.append("if (xmlhttp) {");
				htmlCode.append("xmlhttp.open('GET', '" + sURL + "', false);");
				htmlCode.append("xmlhttp.send();");
				htmlCode.append("}");
				htmlCode.append("</script>");
			}

			JspWriter writer = pageContext.getOut();

			//writer.print(javascript.toString());
			writer.print(htmlCode.toString());

		} catch (Exception e) {
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}
}
