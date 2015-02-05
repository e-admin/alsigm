package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/** 
 * Etiqueta JSP que establece el lenguaje seleccionado
 * para los textos en JavaScript. 
 *
 */
public class JavascriptLanguageTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_LANGUAGE = "es";
	
    /**
     * Idioma seleccionado
     */
    private String language = null;

    
	/**
	 * Obtiene el idioma seleccionado.
	 * @return Código de idioma.
	 */
	public String getLanguage() {
		return language;
	}

    /**
     * Establece el idioma. 
     * @param language Código de idioma.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Se encarga de escribir el código javascript para establecer el idioma.
     * 
     * @return código indicando si el método terminó con éxito [EVAL_PAGE]
     * @throws JspException en caso de que se produzca algún error
     */
    public int doEndTag() throws JspException {

        try {

            JspWriter out = pageContext.getOut();

            // Obtener el idioma establecido en el tag
            String currentLanguage = getLanguage();
            if (StringUtils.isBlank(currentLanguage)) {
            	
            	// Establecer el idioma establecido en la sesión
            	currentLanguage = getSessionLanguage();
            	
            	// Si no hay idioma seleccionado, establecer el de por defecto
            	if (StringUtils.isBlank(currentLanguage)) {
            		currentLanguage = DEFAULT_LANGUAGE;
            	}
            }
            
            out.write("<script language=\"JavaScript\">\n");
            out.write("//<!--\n");
            out.write("var ispacLanguage = '" + currentLanguage + "';\n");
            out.write("//-->\n");
            out.write("</script>\n");

        } catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }
    
    private String getSessionLanguage() {
    	String lang = null;
    	
    	Locale locale = (Locale) pageContext.getSession().getAttribute(org.apache.struts.Globals.LOCALE_KEY);
    	if (locale != null) {
    		lang = locale.getLanguage();
    	}
    	
    	return lang;
    }
}
