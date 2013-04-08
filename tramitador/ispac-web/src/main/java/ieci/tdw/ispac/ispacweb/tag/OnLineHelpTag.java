package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;

public class OnLineHelpTag extends TagSupport {
	
	public static final String JSP_EXTENSION = "jsp";

	String fileName;
	String extension = "html";
	String image;
	String titleKey;
	String queryString;
	String tipoObj;
	String idObj;
	
    public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context = request.getContextPath();
        String locale = request.getLocale().getLanguage();

		TagUtils tagUtils = TagUtils.getInstance();
    	String title = tagUtils.message(pageContext, null, Globals.LOCALE_KEY, getTitleKey());

	    String baseurl;
	    
	    try {
	    	baseurl = StaticContext.getInstance().getBaseUrl(context);
	    }
	    catch (ISPACException ie) {
	        pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
	        throw new JspException(ie.getMessage());
	    }
    	
    	StringBuffer link = new StringBuffer();
    	link.append("<a href='javascript: help();' ")
    		.append(" onclick=\"javascript: deactivateNeedToConfirm(); window.open('");
    	
    	//La ayuda esta en bbdd
    	if(StringUtils.isNotEmpty(tipoObj)){
    		link.append("showHelp.do?tipoObj="+tipoObj);
    		if (StringUtils.isNotEmpty(idObj)) {
        		link.append("&idObj=").append(idObj);
    		}
    		if (StringUtils.isNotEmpty(getQueryString())) {
        		link.append("&").append(getQueryString());
    		}
    	}
    	else{
	    	if (getExtension().equalsIgnoreCase(JSP_EXTENSION)) {
		    	
		    	link.append(context);
		    }
		    else {
		    	link.append(baseurl);
		    }
	    		
	    	link.append("/help/")
	    		.append(locale)
	    		.append("/")
	    		.append(getFileName())
	    		.append(".")
	    		.append(getExtension());
	    	
	    	if (StringUtils.isNotEmpty(getQueryString())) {
	    		link.append("?").append(getQueryString());
	    	}
    	}
    	
    	link.append("','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');\" class='help'> ");
	    
    	// Href que incluya el ispacbase junto con el skin
	    String imageRealPath = StaticContext.rewriteHref(pageContext, baseurl, getImage());
	    
    	link.append("<img src=' " )
    		.append(imageRealPath)
    		.append("' style='vertical-align:middle' border='0'/>")
    		.append(title)
    		.append("</a>");

    	JspWriter out = pageContext.getOut();
    	try {
        	out.write(link.toString());
        }
    	catch (IOException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw new JspException(e.toString());
        }

        return EVAL_BODY_INCLUDE;
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

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getTitleKey() {
		return titleKey;
	}
	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
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