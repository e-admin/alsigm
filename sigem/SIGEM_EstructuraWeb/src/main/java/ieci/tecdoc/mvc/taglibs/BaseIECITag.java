package ieci.tecdoc.mvc.taglibs;

import ieci.tecdoc.mvc.util.MvcDefs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;


public class BaseIECITag extends TagSupport
{

    //~ Static fields/initializers ---------------------------------------------

    /** The message resources for this package. */
    protected static MessageResources messages = MessageResources.getMessageResources(MvcDefs.TOKEN_PACKAGE+
                                                                                      ".LocalStrings");
    private   static Logger             logger = Logger.getLogger(BaseIECITag.class);

    //~ Methods ----------------------------------------------------------------

    /**
     * Process the start of this tag.
     *
     * @return $paramType$ DOCUMENT ME!
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag()
                   throws JspException
    {

        //String       default_skin = null;
        String       server       = null;
        String       port         = null;
        String       protocol     = null;
        //String       base_dir     = null;
        //String       idioma       = null;
        String       context      = null;
        StringBuffer tag          = null;
        
        //default_skin = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_DEFAULT_SKIN);
        protocol     = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_PROTOCOL);
        //base_dir     = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_BASE_DIR);
        //idioma       = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_IDIOMA);
        server       = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_HTTP_SERVER);
        port         = (String)pageContext.getServletContext().getAttribute(MvcDefs.PLUGIN_HTTP_PORT);
        context      = (String)((HttpServletRequest)pageContext.getRequest()).getContextPath();
        
        if (protocol == null) {
              protocol = pageContext.getRequest().getScheme ();
        }
      
	      if (server == null) {
	        server = pageContext.getRequest().getServerName ();
	      }
      
	      if (port == null) {
	        port = String.valueOf (pageContext.getRequest().getServerPort ());
	      }
        
        tag          = new StringBuffer(protocol+"://"+server);

        if(logger.isDebugEnabled())
        {

            //logger.debug("default_skin: "+default_skin);
            logger.debug("protocol: "+protocol);
            //logger.debug("base_dir: "+base_dir);
            //logger.debug("idioma: "+idioma);
            logger.debug("server: "+server);
            logger.debug("port: "+port);
            logger.debug("context: "+context);

        }

        if((port != null) && !port.equals(""))
            tag.append(":"+port);
        //tag.append(context).append(base_dir).append(idioma)
               //.append(MvcDefs.CHAR_SLASH).append(default_skin).append(MvcDefs.CHAR_SLASH);
        
        tag.append(context).append(MvcDefs.CHAR_SLASH);

        String    baseTag = renderBaseElement(tag);
        JspWriter out = pageContext.getOut();

        try
        {

            out.write(baseTag);

        }
         catch(IOException e)
        {

            pageContext.setAttribute(Globals.EXCEPTION_KEY, e,
                                     PageContext.REQUEST_SCOPE);
            throw new JspException(messages.getMessage("common.io", e.toString()));

        }

        return EVAL_BODY_INCLUDE;

    }

    protected String renderBaseElement(StringBuffer tag)
    {

        String base = tag.toString ();
        tag.insert(0, "<base href=\"");

        tag.append(" \"/>");

        tag.append ("<link rel=\"icon\" href=\"include/images/favicon.ico\" type=\"image/x-icon\"/>");
        tag.append ("<link rel=\"shortcut icon\" href=\"" + base + "include/images/favicon.ico\" type=\"image/x-icon\"></link>");
        tag.append ("<link rel=\"shortcut icon\" href=\"include/images/favicon.ico\" type=\"image/x-icon\"></link>");

        if(logger.isDebugEnabled())
            logger.debug("tag = "+tag);

        return tag.toString();

    }

}
