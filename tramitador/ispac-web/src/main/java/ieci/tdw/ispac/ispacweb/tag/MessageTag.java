package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Tag que visualiza un mensaje en una ventana
 */
public class MessageTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	/**
     * The message resources for this package
     */
    protected static MessageResources messages =
    MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * The request attribute key for our error messages.
     */
    protected String name = "infoAlert";
    protected String width = "380";
    protected String height = "240";
    protected String page = "message.jsp";
    /**
     * Si noRemove =true no eliminaremos de la sesion el inforAlert
     */
    protected String noRemove="false";
    /*Si Location es distinto de "" quiere decir que el mensaje
     * no lo va a mostrar la ventana actual*/
    protected String location="";
    /*Si generateTagScript por defecto es true en caso
     * contrario no generamos <script></script>*/
    protected String generateTagScript="true";

    protected String jsExecute="";


    public String getJsExecute() {
		return jsExecute;
	}
	public void setJsExecute(String jsExecute) {
		this.jsExecute = jsExecute;
	}
	public String getGenerateTagScript() {
		return generateTagScript;
	}
	public void setGenerateTagScript(String generateTagScript) {
		this.generateTagScript = generateTagScript;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName()
    {
        return (this.name);
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getWidth()
    {
        return (this.width);
    }
    public void setWidth(String width)
    {
        this.width = width;
    }

    public String getHeight()
    {
        return (this.height);
    }
    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getPage()
    {
        return (this.page);
    }
    public void setPage(String page)
    {
        this.page = page;
    }

    public int doStartTag() throws JspException {
    	TagUtils tagUtils = TagUtils.getInstance();
    	HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    	if(request.getSession().getAttribute(getName())!=null &&
    		(request.getSession().getAttribute(getName()) instanceof ISPACInfo
    				|| request.getSession().getAttribute(getName()) instanceof ISPACException)){


    		StringBuffer message = new StringBuffer();
    	    String extraInfo = null;
    	    String msg = null;
    	    ISPACException ie = ((ISPACException)request.getSession().getAttribute(getName()));
    	    if(StringUtils.equalsIgnoreCase("false", noRemove)){
    	    	request.getSession().removeAttribute(getName());
    	    }
			msg = ie.getExtendedMessage(request.getLocale());
			extraInfo = ie.getExtraInfo();
			if (extraInfo != null){
				String aux = ie.getExtendedExtraInfo(request.getLocale());
				if (aux != null)
					extraInfo = aux;
			}

			if (msg == null){
				msg = ie.getMessage();
			}
	        message.append(msg);
	        String finalMessage =  msg;
	        if(extraInfo!=null){
	        	finalMessage = finalMessage + ": " + extraInfo;
	        }

	       if(finalMessage!=null && finalMessage.length()>0){
	    	   StringBuffer code = new StringBuffer();
	    	   String codeString="";
	    	   if(generateTagScript.equalsIgnoreCase("TRUE")){
	    		  codeString+="<script> ";
	    	   }
	    	   //El código se lanza cuando la página haya sido totalmente recargada evento ready de jquery
	    	   codeString+="$(document).ready(function() {";
	    	   if(StringUtils.isNotEmpty(location)){
	    		   codeString+=location+".";
	    	   }

	    	   // Codificar las comillas simples
	    	   finalMessage = StringUtils.replace(finalMessage, "'", "\\'");

	    	   codeString+="jAlert('"+finalMessage+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.alert")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.ok")+"', '"+tagUtils.message(pageContext, null, Globals.LOCALE_KEY, "common.message.cancel")+"'";
	    	  if(StringUtils.isNotBlank(jsExecute)){
	    		  codeString+=",function(r) {if(r) {if("+jsExecute+"){"+jsExecute+"();}}}" ;
	    	  }


	    	  //cerramos el jAlert
	    	  codeString+=");";
	    	   //Cerramos el evento onReady
	    	   codeString+="});";

	    	   if(generateTagScript.equalsIgnoreCase("TRUE")){
	    		  codeString+="</script> ";
	    	   }
	    	code.append(codeString);

	        JspWriter writer = pageContext.getOut();
	        try {
	            writer.print(code.toString());
	        }
	        catch (IOException ioe) {
	            TagUtils.getInstance().saveException(pageContext, ioe);
	            throw new JspException(messages.getMessage("write.io", ioe.toString()));
	        }
	       }
    	}
    	return (EVAL_BODY_INCLUDE);


    }

    /**
     * Recupera el contenido de un fichero
     */
    public String getFileString(String fichero) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(fichero));
        StringBuffer s2 = new StringBuffer();
        String s;
        while ((s = in.readLine()) != null)
        {
            s2.append(s);
        }
        in.close();

        return s2.toString();
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
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

        StringBuffer tag = new StringBuffer("<base href='");
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
        tag.append("'");

        if (TagUtils.getInstance().isXhtml(this.pageContext)) {
            tag.append(" />");
        } else {
            tag.append(">");
        }

        return tag.toString();
    }

//	private String getMessage(HttpServletRequest request, String key) {
//
//	   MessageResources resources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
//	   return (resources.getMessage(request.getLocale(), key));
//	}
	public String getNoRemove() {
		return noRemove;
	}
	public void setNoRemove(String noRemove) {
		this.noRemove = noRemove;
	}

}