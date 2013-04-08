package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class OnLineHelpHrefTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	public static final String JSP_EXTENSION = "jsp";

	String fileName;
	String extension = "html";
	String queryString;
	String tipoObj;
	String idObj;
	
   
	public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context = request.getContextPath();
        String locale = request.getLocale().getLanguage();

	    String baseurl;
	    
	    try {
	    	baseurl = StaticContext.getInstance().getBaseUrl(context);
	    }
	    catch (ISPACException ie) {
	        pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
	        throw new JspException(ie.getMessage());
	    }
    	
    	StringBuffer href = new StringBuffer();
    	
    	//La ayuda esta en bbdd
    	if(StringUtils.isNotEmpty(tipoObj)){
    		
    		href.append(context+"/showHelp.do?tipoObj="+tipoObj);
    		if (StringUtils.isNotEmpty(idObj)) {
        		href.append("&idObj=").append(idObj);
    		}
    		if (StringUtils.isNotEmpty(getQueryString())) {
        		href.append("&").append(getQueryString());
    		}
    	}
    	else{
		    if (getExtension().equalsIgnoreCase(JSP_EXTENSION)) {
		    	
		    	href.append(context);
		    }
		    else {
		    	href.append(baseurl);
		    }
	    		
		    href.append("/help/")
	    		.append(locale)
	    		.append("/")
	    		.append(getFileName())
	    		.append(".")
	    		.append(getExtension());
	    	
	    	if (StringUtils.isNotEmpty(getQueryString())) {
	    		href.append("?").append(getQueryString());
	    	}
    	}

    	JspWriter out = pageContext.getOut();
    	try {
        	out.write(href.toString());
        }
    	catch (IOException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e.toString());
        }

        return EVAL_PAGE;
    }

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	 public String getTipoObj() {
			return tipoObj;
		}

		public void setTipoObj(String tipoObj) {
			this.tipoObj = tipoObj;
		}

		public String getIdObj() {
			return idObj;
		}

		public void setIdObj(String idObj) {
			this.idObj = idObj;
		}

}