package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

public class ISPACRewriteTag extends BodyTagSupport {

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources
            .getMessageResources(Constants.Package + ".LocalStrings");

    protected String id = null;
    protected String toScope = null;
    protected String href = null;
    protected String action = null;
    protected String page = null;

    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException
    {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
    	
    	TagUtils tagUtils = TagUtils.getInstance();
        //String skin = (String) pageContext.getServletContext().getAttribute("skin");
        
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        //String baseurl = request.getContextPath();
        String context = request.getContextPath();
        
        String url;
        
        if (getHref() != null) {
        	
            try {
            	url = StaticContext.getInstance().getBaseUrl(context);
            }
            catch (ISPACException ie) {
                pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
                throw new JspException(ie.getMessage());
            }
            
        	// Href que incluya el ispacbase junto con el skin
            //baseurl = rewriteHref(baseurl, ispacbase, skin);
        	url = StaticContext.rewriteHref(pageContext, url, getHref());
        }
        else if (getPage() != null) {
        	
        	// Página JSP que incluya el contexto de la aplicación junto con el ispacbase
        	url = StaticContext.rewritePage(pageContext, context, getPage());      		
        }
        else {
        	// Url (protocol, server name, port number, server path)
        	// que incluya hasta el contexto de la aplicación
        	// para generar la url completa del action
        	//baseurl = rewriteAction((request.getRequestURL()).toString(), context);
            url = StaticContext.rewriteAction((request.getRequestURL()).toString(), context, getAction());	
        }

        if (getId() == null) {
        	
	        JspWriter out = pageContext.getOut();
	        try {
	            out.write(url);
	        }
	        catch (IOException e) {
	            pageContext.setAttribute(Globals.EXCEPTION_KEY, e, PageContext.REQUEST_SCOPE);
	            throw new JspException(messages.getMessage("common.io", e.toString()));
	        }
            return EVAL_PAGE;
        }

        // Expose this value as a scripting variable
        int inScope = PageContext.PAGE_SCOPE;
        try {
			if (getToScope() != null) {
				inScope = tagUtils.getScope(getToScope());
			}
		}
        catch (JspException e) {
			// toScope was invalid name so we default to PAGE_SCOPE
		}

        // Establecer en el ámbito especificado el id con la url generada 
        pageContext.setAttribute(getId(), url, inScope);

        return (EVAL_PAGE);
    }

    /*
    private String rewriteHref(String baseurl, String ispacbase, String skin) {
        
    	if ((ispacbase == null) || 
           	(ispacbase.equalsIgnoreCase(""))) {
            	
    		return baseurl = baseurl + "/" + skin + "/" + getHref();
    	}

        return baseurl = baseurl + "/" + ispacbase + "/" + skin + "/" + getHref();
    }
    */

    /*
    private String rewriteAction(String url, String context) {
    	
    	int lastIndex= url.lastIndexOf(context) + context.length();
        String urlAction = url.substring(0, lastIndex);
    	
        return urlAction + "/" + getAction();
    }
    */

    /**
     * Returns the href.
     *
     * @return String
     */
    public String getHref()
    {
        return this.href;
    }
    /**
     * Sets the href.
     *
     * @param href The href to set
     */
    public void setHref(String href)
    {
        this.href = href;
    }

    /**
     * Returns the action.
     *
     * @return String
     */
    public String getAction()
    {
        return this.action;
    }
    /**
     * Sets the action.
     *
     * @param action The action to set
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * Returns the id.
     *
     * @return String
     */
    public String getId()
    {
        return this.id;
    }
    /**
     * Sets the id.
     *
     * @param id The id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Returns the scope within which the newly defined bean will be created.
     * 
     * @return String
     */
    public String getToScope() {
        return (this.toScope);
    }
    /**
     * Sets the scope within which the newly defined bean will be created.
     *
     * @param toScope The scope within which the newly defined bean will be created to set
     */
    public void setToScope(String toScope) {
        this.toScope = toScope;
    }

    /**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
     * Release all allocated resources.
     */
    public void release() {

        super.release();

        id = null;
        href = null;
        action = null;
        page = null;
        toScope = "page";
    }
    
}