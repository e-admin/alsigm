package ieci.tdw.ispac.ispacweb.tag;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Etiqueta JSP que se encarga de mostrar un calendario.
 */
public class CalendarTag extends TagSupport {

    /**
     * Idioma en el que se mostrará el formulario.
     */
    private String language = "es";

    /**
     * Flag que indica si es posible seleccionar una fecha anterior a la actual.
     */
    private boolean enablePast = false;

    /**
     * Coordenada X en la que se mostrará el calendario.
     */
    private int fixedX = -1;

    /**
     * Coordenada Y en la que se mostrará el calendario.
     */
    private int fixedY = -1;

    /**
     * Imagen que se muestra como icono del calendario.
     */
    private String image = "popcalendar.gif";

    /**
     * Id del formulario asociado al calendario.
     */
    private String formId = "defaultForm";

    /**
     * Nombre del campo del formulario que guardará el valor de la fecha
     * seleccionada en el calendario.
     */
    private String componentId;

    /**
     * Patrón en el se devolverá la fecha.
     */
    private String format;

    /**
     * Nombre del fichero javascript que recoge toda la lógica del calendario.
     */
    private String scriptFile = "calendar.js";

    /**
     * Guarda el nombre del fichero javascript que contiene la lógica de negocio
     * del calendario.
     * 
     * @param scriptFile
     *            el nombre del fichero javascript que contiene la lógica de
     *            negocio del calendario
     */
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * @return Devuelve el nombre del fichero javascript que contiene la lógica de negocio
     * del calendario.
     */
    public String getScriptFile()
    {
        return scriptFile;
    }

    /**
     * Guarda el nombre del campo del formulario que recoje la fecha
     * seleccionada en el calendario.
     * 
     * @param componentId
     *            el nombre del campo del formulario que recoje la fecha
     *            seleccionada en el calendario
     */
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    /**
     * Guarda el patrón empleado para la visualización de la fecha seleccionada
     * en el calendario.
     * 
     * @param format
     *            el patrón empleado para la visualización de la fecha
     *            seleccionada en el calendario
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Guarda el atributo <code>id</code> del formulario asociado al
     * calendario.
     * 
     * @param formId
     *            el atributo <code>id</code> del formulario asociado al
     *            calendario
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * Establece si se permite seleccionar una fecha anterior a la fecha actual.
     * 
     * @param enablePast
     *            flag que indica si es posible seleccionar una fecha anterior a
     *            la fecha actual
     */
    public void setEnablePast(boolean enablePast) {
        this.enablePast = enablePast;
    }

    /**
     * Guarda la coordenada X en la que se mostrará el calendario.
     * 
     * @param fixedX
     *            la coordenada X en la que se mostrará el calendario
     */
    public void setFixedX(int fixedX) {
        this.fixedX = fixedX;
    }

    /**
     * Guarda la coordenada Y en la que se mostrará el calendario.
     * 
     * @param fixedY
     *            la coordenada Y en la que se mostrará el calendario
     */
    public void setFixedY(int fixedY) {
        this.fixedY = fixedY;
    }

    /**
     * Guarda el nombre de la imagen que se mostrará como icono del calendario.
     * 
     * @param image
     *            el nombre de la imagen que se mostrará como icono del
     *            calendario
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Guarda el idioma utilizado para la visualización del calendario.
     * 
     * @param language
     *            el idioma utilizado para la visualización del calendario
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Se encarga de escribir una etiqueta <code>img</code> con un enlace a un
     * método javascript que se encarga de mostrar el calendario teniendo en
     * cuenta los parámetros de configuración establecidos.
     * 
     * @return un código que indica si el método concluyó con éxito [EVAL_PAGE]
     * @throws JspException
     *             en caso de que se produzca algún error
     */
    public int doEndTag() throws JspException {

        try {

            JspWriter out = pageContext.getOut();

            //Ejecutar el calendario.
            String client = "document.forms[\"" + getFormId() + "\"].elements[\"" + getComponentId() + "\"]";
            
            out.write("<img id='calgif' src='" + getImage() + "' ");
            out.write("onclick='showCalendar(this, " + client + ", ");
            int val = (isEnablePast()) ? 1 : 0;

            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            
            out.write("\"" + getFormat() + "\",\"" + getLanguage(request) + "\"," + val + ","
                    + getFixedX() + "," + getFixedY() + ")' />\n");

        } catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

	/**
	 * @return Returns the componentId.
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * @return Returns the enablePast.
	 */
	public boolean isEnablePast() {
		return enablePast;
	}

	/**
	 * @return Returns the fixedX.
	 */
	public int getFixedX() {
		return fixedX;
	}

	/**
	 * @return Returns the fixedY.
	 */
	public int getFixedY() {
		return fixedY;
	}

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @return Returns the formId.
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @return Returns the image.
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return language;
	}
	
	public String getLanguage(HttpServletRequest request) {
		
		ClientContext cct = (ClientContext) request.getAttribute("ClientContext");
		if (cct != null) {
			
			try {
				return cct.getAppLanguage();
			}
			catch (ISPACException ie) {}
		}
		
		return getLanguage();
	}
    
}