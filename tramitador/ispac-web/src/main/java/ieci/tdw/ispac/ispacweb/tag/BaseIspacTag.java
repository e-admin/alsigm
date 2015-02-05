package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Renders an HTML <base> element with an href 
 * attribute pointing to the absolute location of the enclosing JSP page. This 
 * tag is only valid when nested inside a head tag body. The presence 
 * of this tag allows the browser to resolve relative URL's to images,
 * CSS stylesheets  and other resources in a manner independent of the URL
 * used to call the ActionServlet.
 */

public class BaseIspacTag extends TagSupport {

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * The server name to use instead of request.getServerName().
     */
    protected String server = null;

    /**
     * Process the start of this tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
    	
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String context = request.getContextPath();
        String serverName = (getServer() == null) ? request.getServerName() : getServer();
        
        String baseurl;
        try {
        	baseurl = StaticContext.getInstance().getBaseUrl(context);
        }
        catch (ISPACException ie) {
            pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
            throw new JspException(ie.getMessage());
        }
        
        // Calcular la etiqueta base
        String href = StaticContext.rewriteHref(pageContext, baseurl, "");
        
        String baseTag = renderBaseElement(request.getScheme(),
                						   serverName,
                						   request.getServerPort(),
                						   href);

        JspWriter out = pageContext.getOut();
        try {
            out.write(baseTag);
        } catch (IOException e) {
            pageContext.setAttribute(Globals.EXCEPTION_KEY, e, PageContext.REQUEST_SCOPE);
            throw new JspException(messages.getMessage("common.io", e.toString()));
        }
        
        return EVAL_BODY_INCLUDE;
    }

    /**
     * Render a fully formed HTML &lt;base&gt; element and return it as a String.
     * @param scheme The scheme used in the url (ie. http or https).
     * @param serverName
     * @param port
     * @param uri  The portion of the url from the protocol name up to the query 
     * string.
     * @return String An HTML &lt;base&gt; element.
     * @since Struts 1.1
     */
    protected String renderBaseElement(String scheme,
    								   String serverName,
    								   int port,
    								   String uri) {
        
    	TagUtils tagUtils = TagUtils.getInstance();
        StringBuffer tag = new StringBuffer("<base href=\"");
        tag.append(scheme);
        tag.append("://");
        
        tag.append(serverName);
        if ("http".equals(scheme) && (port == 80)) {
            ;
        } else if ("https".equals(scheme) && (port == 443)) {
            ;
        } else {
            tag.append(":");
            tag.append(port);
        }
        
        tag.append(uri);
        tag.append("\"");
        
        if (tagUtils.isXhtml(this.pageContext)) {
            tag.append(" />");
        } else {
            tag.append(">");
        }
        
        return tag.toString();
    }
    
    /**
     * Returns the server.
     * @return String
     */
    public String getServer() {
        return this.server;
    }
    /**
     * Sets the server.
     * @param server The server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

}