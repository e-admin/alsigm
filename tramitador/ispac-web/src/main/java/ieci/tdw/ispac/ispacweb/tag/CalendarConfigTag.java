package ieci.tdw.ispac.ispacweb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Etiqueta JSP encargada de la configuración de la etiqueta
 * <code>CalendarTag</code>.
 * 
 * @see ieci.tdw.ispac.ispacweb.tag.CalendarTag
 */
public class CalendarConfigTag extends TagSupport {

    /**
     * Idioma en el que se mostrará el calendario.
     */
    private String language = "es";

    /**
     * Flag que indica se se permite seleccionar una fecha anterior a la fecha
     * actual.
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
     * Día de la semana en el que se desea que comienza el calendario. 0 =
     * domingo y 1 = lunes.
     */
    private int startAt = 1;

    /**
     * Flag que indica si se desea mostrar el número de la semana.
     */
    private boolean showWeekNumber = false;

    /**
     * Flag que indica si se desea mostrar el día de hoy resaltado.
     */
    private boolean showToday = true;

    /**
     * Path relativo a <html:base/>en el que se encuentran las imágenes usadas
     * por el calendario.
     */
    private String imgDir = "/";

    /**
     * noop
     */
    private String dayName = "";

    /**
     * Fichero javascript que contiene la lógica de negocio del calendario.
     */
    private String scriptFile = "calendar.js";

    /**
     * Cambia el valor de la propiedad <code>dayName</code>.
     * 
     * @param dayName
     *            el nuevo valor de la propiedad <code>dayName</code>
     */
    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    /**
     * Establece si se permite seleccionar una fecha anterior a la fecha actual.
     * 
     * @param enablePast
     *            <code>true</code> si se permite seleccionar una fecha
     *            anterior a la actual
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
     * Guarda el path relativo en el que se encuentran las imágenes empleadas
     * por el calendario.
     * 
     * @param imgDir
     *            el path relativo en el que se encuentran las imágenes
     *            empleadas por el calendario
     */
    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    /**
     * Guarda el código del idioma en el que se mostrará el calendario. Sólo
     * están soportados tres idiomas : castellano, inglés y alemán. Por esta
     * razón, los únicos códigos que tendrán efecto son <code>es, en y de</code>.
     * 
     * @param language
     *            código del idioma en que se mostrará el calendario
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Guarda el nombre del fichero de javascript que contiene la lógica de
     * negocio del calendario.
     * 
     * @param scriptFile
     *            el nombre del fichero de javascript que contiene la lógica de
     *            negocio del calendario
     */
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * Establece si se desea que la fecha actual se muestre resaltada.
     * 
     * @param showToday
     *            <code>true</code> si se desea que la fecha actual se muestre
     *            resaltada
     */
    public void setShowToday(boolean showToday) {
        this.showToday = showToday;
    }

    /**
     * Establece si se desea que aparezca el número de la semana.
     * 
     * @param showWeekNumber
     *            <code>true</code> si se desean mostrar los números de semana
     */
    public void setShowWeekNumber(boolean showWeekNumber) {
        this.showWeekNumber = showWeekNumber;
    }

    /**
     * Establece el día de la semana en el que se desea que comienza el
     * calendario. 0 = domingo y 1 = lunes.
     * 
     * @param startAt
     *            dia de la semana en el que se quiere que comience el
     *            calendario
     */
    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    /**
     * Se encarga de escribir el código javascript con los parámetros de
     * configuración que debe usar el calendario.
     * 
     * @return código indicando si el método terminó con éxito [EVAL_PAGE]
     * @throws JspException
     *             en caso de que se produzca algún error
     */
    public int doEndTag() throws JspException {

        try {

            JspWriter out = pageContext.getOut();

            //Configurar el calendario.
            out.write("<script language=\"JavaScript\">\n");
            out.write("<!--\n");
            out.write("var language = '" + getLanguage() + "';\n");
            int val = (enablePast) ? 1 : 0;
            out.write("var enablePast = " + val + "; \n");
            out.write("var fixedX = " + getFixedX() + "; \n");
            out.write("var fixedY = " + getFixedY() + "; \n");
            out.write("var startAt = " + getStartAt() + "; \n");
            val = (isShowWeekNumber()) ? 1 : 0;
            out.write("var showWeekNumber = " + val + "; \n");
            val = (isShowToday()) ? 1 : 0;
            out.write("var showToday = " + val + ";\n");
            out.write("var imgDir = '" + getImgDir() + "';\n");
            out.write("var dayName = '" + getDayName() + "';\n");
            out.write("-->\n");
            out.write("</script>\n");
            out.write("<script language=\"JavaScript\" " + "src=\""
                    + getScriptFile() + "\" type=\"text/javascript\">");
            out.write("</script>\n");

        } catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

	/**
	 * @return Returns the dayName.
	 */
	public String getDayName() {
		return dayName;
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
	 * @return Returns the imgDir.
	 */
	public String getImgDir() {
		return imgDir;
	}

	/**
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return Returns the scriptFile.
	 */
	public String getScriptFile() {
		return scriptFile;
	}

	/**
	 * @return Returns the showToday.
	 */
	public boolean isShowToday() {
		return showToday;
	}

	/**
	 * @return Returns the showWeekNumber.
	 */
	public boolean isShowWeekNumber() {
		return showWeekNumber;
	}

	/**
	 * @return Returns the startAt.
	 */
	public int getStartAt() {
		return startAt;
	}
    
}