package ieci.tecdoc.mvc.taglibs;

import ieci.tecdoc.mvc.error.ErrorBean;
import ieci.tecdoc.mvc.util.MvcDefs;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.util.PropertyMessageResources;
import org.apache.struts.util.ResponseUtils;


/**
 * @author ruben
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ErrorTag extends TagSupport {
    // ------------------------------------------------------------- Properties
   private static Logger logger = Logger.getLogger(ErrorTag.class);

   protected Object  bean = null;

   public Object getBean()
   {
      return bean;
   }

   public void setBean(Object bean)
   {
      this.bean = bean;
   }

    // --------------------------------------------------------- Public Methods

    /**
         * Render the appropriately encoded URI.
         *
         * @exception javax.servlet.jsp.JspException if a JSP exception has occurred
         */
    public int doStartTag() throws JspException {
        // Print this element to our output writer
        StringBuffer results = new StringBuffer("");
       if ( this.bean ==null){
          if(logger.isDebugEnabled())
             logger.debug("bean es null ");
          ResponseUtils.write(pageContext, results.toString());
          return (SKIP_BODY );
       }else{
          if(logger.isDebugEnabled())
             logger.debug("bean no es null "+this.bean );
          ErrorBean error=null;
          try{
             error = (ErrorBean)this.bean;
             if(logger.isDebugEnabled())
                logger.debug("error: "+error);

          }catch(Exception e){
             logger.error("El bean pasado al tag de errores no es un ErrorBean",e );
             ResponseUtils.write(pageContext, results.toString());
             return (SKIP_BODY );
          }
          if ( error.getShowMessage() ){
             if(logger.isDebugEnabled())
                logger.debug("tengo el showmessage a true empiezo a pintar ");
             results.append("<script>\n" +
             "function cerrar(){\n" +
             " this.document.getElementById('errorDiv').style.visibility='hidden';\n" +
             " this.history.back(-1);\n" +
             "}\n" +
             "</script><div class=\"errors\" style=\"visibility:visible;\" id=\"errorDiv\" >");
             results.append("<table class=\"tableGeneralErrors\" >\n" +
             "<tr>\n" +
             "<td width=\"100%\" height=\"18px;\" class=\"blue\">\n");
             Enumeration obj = (Enumeration)pageContext.getAttributeNamesInScope(PageContext.APPLICATION_SCOPE ) ;
             PropertyMessageResources errors = (PropertyMessageResources)pageContext.getAttribute("errors",PageContext.APPLICATION_SCOPE );
             PropertyMessageResources messages = (PropertyMessageResources)pageContext.getAttribute("org.apache.struts.action.MESSAGE",PageContext.APPLICATION_SCOPE );

             String errorText = errors.getMessage("message.error.text") ;
                if(logger.isDebugEnabled())
                   logger.debug("obj: "+errorText );

             if(logger.isDebugEnabled())
                logger.debug("error.getStatus(): "+error.getStatus());

             results.append(errorText);
             results.append("</td>\n" +
             "</tr>\n" +
             "<tr>\n" +
             "<td width=\"100%\" height=\"18px;\">\n" +
             "&nbsp;\n" +
             "</td>\n" +
             "</tr>\n" +
             "<tr>\n" +
             "<td width=\"100%\" >\n");
             results.append(error.getException() );
             results.append("<br></td>\n");
             if ( error.getStatus().equals(MvcDefs.EXCEPTION_HANDLE_DEVELOPMENT ) )
             {
                for ( int i = 0 ; i < error.getStack().length ; i++)
                {
                   results.append("<tr>\n");
                   results.append("<td width=\"100%\" >\n");
                   results.append(error.getStack()[i] );
                   results.append("</td>\n");
                   results.append("</tr>\n");
                }
             }
             results.append("</td>\n" +
             "</tr>\n" +
             "<tr>\n" +
             "<td width=\"100%\" align=\"right\" height=\"18px;\">\n");
             HashMap action = (HashMap)error.getActions();
             Set actionSet = action.keySet() ;
             Iterator it= actionSet.iterator();
             String href=null;
             while ( it.hasNext() )
             {
                String thisAction = it.next().toString();
                href = thisAction + "?" + ((HashMap)action.get(thisAction)).entrySet().iterator().next() ;
             }
             if(logger.isDebugEnabled())
                logger.debug("href: "+href);
             results.append("<a href="+href+">Volver</a>\n" +
             "<a style=\"cursor:hand;\" onclick=\"cerrar();\">Cerrar</a>\n" +
             "</td>\n" +
             "</tr>"+
             "</table>"+
             "</div>");

             ResponseUtils.write(pageContext, results.toString());
          }
       }



        // Skip the body of this tag
        return (SKIP_BODY );
    }

    /**
         * Ignore the end of this tag.
         *
         * @exception javax.servlet.jsp.JspException if a JSP exception has occurred
         */
    public int doEndTag() throws JspException {
		// Prepare the textual content and ending element of this hyperlink
		StringBuffer results = new StringBuffer();
		results.append("");

		// Render the remainder to the output stream
		ResponseUtils.write(pageContext, results.toString());

		return (EVAL_PAGE);
    }

    /**
         * Release any acquired resources.
         */
    public void release() {
        super.release();
        bean = null;
    }
}
