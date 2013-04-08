package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispacweb.util.PathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Tag que renderiza los mensajes de error en un alert de javascript. Es una
 * copia de <html:errors/>de struts, solo que en lugar de presentarlo en el jsp,
 * lanza los errores en una ventana
 *
 * @author IECISA
 * @version 1.0
 * @see org.apache.struts.util.MessageResources
 * @see javax.servlet.jsp.tagext.TagSupport
 * @since JDK1.4.1_02
 *
 */
public class ErrorsTag extends TagSupport {

    // --- Properties
	/*
	 * nombre de la página html que contiene la nueva ventana con el error
	 */
	public static final String ERROR_WINDOW = "errorWindowTag.jsp";

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;

    /**
     * The default locale on our server.
     *
     * @deprecated Use Locale.getDefault() directly.
     */
    protected static Locale defaultLocale = Locale.getDefault();

    /**
     * The line ending string.
     */
    protected static String lineEnd = "\\n";

    /**
     * The session attribute key for our locale.
     */
    protected String locale = Globals.LOCALE_KEY;

    /**
     * The message resources for this package.
     */
    protected static MessageResources messages = MessageResources
            .getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * The request attribute key for our error messages (if any).
     */
    protected String name = Globals.ERROR_KEY;

    /**
     * The name of the property for which error messages should be returned, or
     * <code>null</code> to return all errors.
     */
    protected String property = null;

    /**
     * Funcion get
     *
     * @return valor del atributo
     */
    public String getBundle() {
        return (this.bundle);
    }

    /**
     * Funcion set
     *
     * @param bundle
     *            nuevo valor del atributo
     */
    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    // --- Public Methods

    /**
     * Render the specified error messages if there are any.
     *
     * @exception JspException
     *                if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

    	HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    	Exception e = (Exception) request.getAttribute(org.apache.struts.Globals.EXCEPTION_KEY);
    	if (e == null) { return (EVAL_BODY_INCLUDE); }

        //Render the error messages appropriately
        StringBuffer results = new StringBuffer();

        // sólo abriremos la ventana cuando haya un mensaje que escribir
        if (e.getMessage() == null || e.getMessage().equals("")) {
            

            results.append("La acción ha lanzado la excepción: "+StringEscapeUtils.escapeJava(e.toString()));
        } else {
        	results.append(StringEscapeUtils.escapeJava(e.getMessage()));
        }

        
        // Calculo de la etiqueta base
        String ispacbase =(String)pageContext.getServletContext().getAttribute("ispacbase");
        
        StringBuffer javascript = new StringBuffer();
        javascript.append("<script language='javascript'>\n")
        		  .append("<!--\n")
        		  .append("var x = screen.width;  // ancho de la pantalla\n")
        		  .append("var y = screen.height; // alto de la pantalla\n")
        		  .append("var ancho = 400;\n")
        		  .append("var alto = 200;\n")
        		  .append("var pos1 = Math.round(y/2) - Math.round(alto/2); // posicion del top según alto pantalla\n")
        		  .append("var pos2 = Math.round(x/2) - Math.round(ancho/2);// posicion del left según ancho pantalla\n")
        		  .append("var sProps = 'top='+pos1+',left='+pos2+',resizable=no,scrollbars=no,width='+ancho+',height='+alto;\n")
        		  .append("var oWin = new Object();\n")
        		  .append("oWin = window.open('"+ispacbase+"/loading.jsp','errorsWindow',sProps);\n")
        		  .append("oWin.focus();\n")
        		  .append("oWin.document.write(\"");

        String plantillaHtml = null;
        
        String pathApp = PathUtils.getRealPath(pageContext.getServletContext(), "/");
        
    	if (!StringUtils.isEmpty(ispacbase)) {
    		pathApp = pathApp + ispacbase + "/";
    	}

        try {
            plantillaHtml = this.getFileString(pathApp + ERROR_WINDOW);
        }
        catch (java.io.IOException ioe) {
            //System.out.println( "formateoMensaje " + ioe.toString());
            throw new JspException(messages
                    .getMessage("formateoMensaje.io", ioe.toString()));
        }

        // Calculo de la etiqueta base //
        String skin =(String)pageContext.getServletContext().getAttribute("skin");
        String serverName = request.getServerName();
        String href = request.getContextPath();
        if (ispacbase == null || ispacbase.equalsIgnoreCase(""))
        	href = href +"/"+skin+"/";
        else
        	href = href +"/"+ispacbase+"/"+skin+"/";

        String baseTag =
            renderBaseElement(
                request.getScheme(),
                serverName,
                request.getServerPort(),
                href);

        JspWriter out = pageContext.getOut();
        try {
            out.write(baseTag);
        } catch (IOException ie) {
            pageContext.setAttribute(Globals.EXCEPTION_KEY, ie, PageContext.REQUEST_SCOPE);
            throw new JspException(messages.getMessage("common.io", ie.toString()));
        }
        // Calculo de la etiqueta base
        plantillaHtml = StringUtils.replaceOnce(plantillaHtml, "$base", baseTag);
        
        // Añadir el título
        plantillaHtml = StringUtils.replaceOnce(plantillaHtml, "$title", getMessage(request, "pageError.head.title"));
        
        // Añadir la cabecera
        plantillaHtml = StringUtils.replaceOnce(plantillaHtml, "$head", getMessage(request, "pageError.title"));
        
        // Añadir la etiqueta de volver
        plantillaHtml = StringUtils.replaceOnce(plantillaHtml, "$back", getMessage(request, "pageError.back"));
        
        // Se reemplaza la key por la cadena con la descripcion de los errores
        plantillaHtml = StringUtils.replaceOnce(plantillaHtml, "$errors", results.toString());

        javascript.append(plantillaHtml);

        javascript.append("\");\n");
        String context =request.getContextPath();
        String url = (String)pageContext.getRequest().getAttribute("refresh");
        javascript.append("top.document.location.href = '"+ context +"/"+ url +"'");
        javascript.append("//-->\n");
        javascript.append("</script>\n");

        JspWriter writer = pageContext.getOut();
        try {
            writer.print(javascript.toString());
        } catch (IOException ioe) {
            TagUtils.getInstance().saveException(pageContext, ioe);
            throw new JspException(messages
                    .getMessage("write.io", ioe.toString()));
        }

        return (EVAL_BODY_INCLUDE);

    }

    /**
     * Funcion de recuperacion del contenido de un fichero
     *
     * @param fichero
     *            Cadena representativa a la ruta física del fichero
     * @return Cadena representativa del contenido del fichero
     * @throws IOException
     *             Excepcion lanzada en caso de error
     */
    public String getFileString(String fichero) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fichero));
        StringBuffer s2 = new StringBuffer();
        String s;
        while ((s = in.readLine()) != null) {
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
        bundle = Globals.MESSAGES_KEY;
        locale = Globals.LOCALE_KEY;
        name = Globals.ERROR_KEY;
        property = null;
    }

    // --- Getters and Setters

    /**
     * Funcion get
     *
     * @return valor del atributo
     */
    public String getLocale() {
        return (this.locale);
    }

    /**
     * Funcion set
     *
     * @param bundle
     *            nuevo valor del atributo
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Funcion get
     *
     * @return valor del atributo
     */
    public String getName() {
        return (this.name);
    }

    /**
     * Funcion set
     *
     * @param bundle
     *            nuevo valor del atributo
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Funcion get
     *
     * @return valor del atributo
     */
    public String getProperty() {
        return (this.property);
    }

    /**
     * Funcion set
     *
     * @param bundle
     *            nuevo valor del atributo
     */
    public void setProperty(String property) {
        this.property = property;
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
    protected String renderBaseElement(
        String scheme,
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
    
	private String getMessage(HttpServletRequest request, String key) {
		
	   MessageResources resources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
	   return (resources.getMessage(request.getLocale(), key));
	}	

}