package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class EditTemplateTag extends TagSupport {

	private static final long serialVersionUID = 1352535303954375019L;

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(EditTemplateTag.class);
	
	protected String template = null;

	// Atributos del tag

	public String getTemplate() {
		return (this.template);
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int doStartTag() throws JspException {
		
		if (this.template.equals("")) {
			ISPACInfo e = new ISPACInfo(
					"edittemplate:No se ha indicado plantilla");
			pageContext.setAttribute(Globals.EXCEPTION_KEY, e,
					PageContext.REQUEST_SCOPE);
			throw new JspException(e.toString());
		}

		JspWriter writer = pageContext.getOut();

		try {
			HttpServletRequest request = 
				(HttpServletRequest) pageContext.getRequest();

			// URL del documento
			String url = DocumentUtil.generateURL(request, "file", getTicket(),
					this.template, null);

			// Tipo MIME de la plantilla
			String mimeType = MimetypeMapping.getFileMimeType(this.template);
			
			String htmlCode = DocumentUtil.generateHtmlCode(
					pageContext.getServletContext(), request.getContextPath(), url, 
					mimeType, null, false);

			writer.print(htmlCode);
			
		} catch (Exception e) {
			logger.error("Error al editar la plantilla", e);
			TagUtils.getInstance().saveException(pageContext, e);
			throw new JspException(e.toString());
		}

		return (EVAL_BODY_INCLUDE);
	}

	private String getTicket() {
		String ticket = null;
		
		HttpServletRequest request = 
			(HttpServletRequest) pageContext.getRequest();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("user")) {
				ticket = cookies[i].getValue();
				break;
			}
		}

		return ticket;
	}

	protected SessionAPI getSession() {

	  	SessionAPI sessionAPI = null;

	  	try	{
	  		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	  		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
	  		sessionAPI = SessionAPIFactory.getSessionAPI(request, response);
	    } catch( ISPACException e) {
	    	logger.warn("Error al obtener el SessionAPI", e);
	    }

	    return sessionAPI;
	}
	
}
