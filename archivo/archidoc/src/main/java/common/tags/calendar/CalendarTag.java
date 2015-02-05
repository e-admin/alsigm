package common.tags.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import transferencias.TransferenciasConstants;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.tags.calendar.config.CalendarTagConfig;
import common.util.DateUtils;

/**
 * Clase para mostrar un calendario laboral
 */
public class CalendarTag extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Log log = null;

	static {
		log = LogFactory.getLog(CalendarTag.class);
	}

	// private JspContext jspContext;

	/**
	 * Bean de configuración del calendario
	 */
	private CalendarTagConfig config;

	/**
	 * Ámbito del bean de configuración del calendario
	 */
	private String scope = null;

	/**
	 * Nombre del bean de configuración del calendario
	 */
	private String name = null;

	/* Clases CSS por defecto */
	private String styleClass = "calendarioPrevisiones";
	private String styleClassMes = "calendarioPrevisionesMes";
	private String styleClassCabeceraMes = "calendarioPrevisionesCabeceraMes";
	private String styleClassResumenMes = "calendarioPrevisionesResumenMes";
	private String styleClassDiaCabecera = "calendarioPrevisionesDiaCabecera";
	private String styleClassDia = "calendarioPrevisionesDia";
	private String styleClassDiaOculto = "calendarioPrevisionesDiaOculto";

	private String urlRequestParameter = null;

	/**
	 * @return Devuelve styleClass.
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass
	 *            Establece styleClass.
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return Devuelve styleClassCabeceraMes.
	 */
	public String getStyleClassCabeceraMes() {
		return styleClassCabeceraMes;
	}

	/**
	 * @param styleClassCabeceraMes
	 *            Establece styleClassCabeceraMes.
	 */
	public void setStyleClassCabeceraMes(String styleClassCabeceraMes) {
		this.styleClassCabeceraMes = styleClassCabeceraMes;
	}

	/**
	 * @return Devuelve styleClassDia.
	 */
	public String getStyleClassDia() {
		return styleClassDia;
	}

	/**
	 * @param styleClassDia
	 *            Establece styleClassDia.
	 */
	public void setStyleClassDia(String styleClassDia) {
		this.styleClassDia = styleClassDia;
	}

	/**
	 * @return Devuelve styleClassDiaCabecera.
	 */
	public String getStyleClassDiaCabecera() {
		return styleClassDiaCabecera;
	}

	/**
	 * @param styleClassDiaCabecera
	 *            Establece styleClassDiaCabecera.
	 */
	public void setStyleClassDiaCabecera(String styleClassDiaCabecera) {
		this.styleClassDiaCabecera = styleClassDiaCabecera;
	}

	/**
	 * @return Devuelve styleClassDiaOculto.
	 */
	public String getStyleClassDiaOculto() {
		return styleClassDiaOculto;
	}

	/**
	 * @param styleClassDiaOculto
	 *            Establece styleClassDiaOculto.
	 */
	public void setStyleClassDiaOculto(String styleClassDiaOculto) {
		this.styleClassDiaOculto = styleClassDiaOculto;
	}

	/**
	 * @return Devuelve styleClassMes.
	 */
	public String getStyleClassMes() {
		return styleClassMes;
	}

	/**
	 * @param styleClassMes
	 *            Establece styleClassMes.
	 */
	public void setStyleClassMes(String styleClassMes) {
		this.styleClassMes = styleClassMes;
	}

	/**
	 * @return el styleClassResumenMes
	 */
	public String getStyleClassResumenMes() {
		return styleClassResumenMes;
	}

	/**
	 * @param styleClassResumenMes
	 *            el styleClassResumenMes a establecer
	 */
	public void setStyleClassResumenMes(String styleClassResumenMes) {
		this.styleClassResumenMes = styleClassResumenMes;
	}

	/**
	 * @return Devuelve el nombre del bean en el scope especificado.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Establece el nombre del bean en el scope especificado.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Devuelve el ámbito del bean de configuración.
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            Establece el ámbito del bean de configuración.
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return el urlRequestParameter
	 */
	public String getUrlRequestParameter() {
		return urlRequestParameter;
	}

	/**
	 * @param urlRequestParameter
	 *            el urlRequestParameter a establecer
	 */
	public void setUrlRequestParameter(String urlRequestParameter) {
		this.urlRequestParameter = urlRequestParameter;
	}

	public int doStartTag() throws JspTagException {
		try {

			// Si no se indica el scope mostrar un error
			if ((scope == null) || (scope.equals(Constants.STRING_EMPTY))) {
				log.warn("scope no especificado");
				pageContext.getOut().print("scope no especificado");
				return SKIP_BODY;
			}

			// Si no se indica el name mostrar un error
			if ((name == null) || (name.equals(Constants.STRING_EMPTY))) {
				log.warn("name no especificado");
				pageContext.getOut().print("name no especificado");
				return SKIP_BODY;
			}

			// Obtener el bean que contiene la configuración del calendario
			config = null;

			// Si a la propiedad de ámbito no se le pone valor, habrá que
			// buscarlo en todos los ámbitos
			if (getScope() != null) {
				if (getScope().equals("page")) {
					config = (CalendarTagConfig) pageContext
							.getAttribute(TransferenciasConstants.CALENDAR_CONFIG_KEY);
				} else if (getScope().equals("request")) {
					HttpServletRequest request = (HttpServletRequest) pageContext
							.getRequest();
					config = (CalendarTagConfig) request
							.getAttribute(TransferenciasConstants.CALENDAR_CONFIG_KEY);
				} else if (getScope().equals("session")) {
					HttpSession session = pageContext.getSession();
					config = (CalendarTagConfig) session
							.getAttribute(TransferenciasConstants.CALENDAR_CONFIG_KEY);
				} else if (scope.equals("application")) {
					ServletContext context = pageContext.getServletContext();
					config = (CalendarTagConfig) context
							.getAttribute(getName());
				} else {
					config = (CalendarTagConfig) pageContext
							.getAttribute(TransferenciasConstants.CALENDAR_CONFIG_KEY);
				}
			}

			// Si no se encuentra el error no generar nada
			if (config == null) {
				log.warn("bean no encontrado");
				pageContext.getOut().print("bean no encontrado");
				return SKIP_BODY;
			}

			if (!config.isOk()) {
				log.warn("La configuración del tag es incorrecta");
				pageContext.getOut().print(
						"La configuración del tag es incorrecta");
				return SKIP_BODY;
			}

			// Obtener la URL de los meses
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			String url = (String) request.getAttribute(urlRequestParameter);

			// Obtener el año a mostrar
			int year = config.getYear().intValue();

			// Obtener las fechas con tratamiento especial
			HashMap monthsMap = (HashMap) config.getMonths();

			// Obtener si hay que mostrar un mes o todo el año
			int numMonths = 1;
			int initialMonth = 0;
			int finalMonth = 11;
			if (config.getMonth() != null) {
				initialMonth = config.getMonth().intValue() - 1;
				finalMonth = config.getMonth().intValue() - 1;
			} else {
				numMonths = config.getNumMonths().intValue();
			}

			// Generar el div principal
			pageContext.getOut().print("<div id=\"" + config.getId() + "\">\n");

			// Obtener los mensajes de meses y días
			String[] monthNames = config.getMonthNames();
			String[] dayNames = config.getDayNames();

			// Obtener la fecha de hoy
			Calendar calendarToday = new GregorianCalendar();
			calendarToday.setTime(new Date());

			// Obtener los colores y el intervalo de colores
			String[] colores = ConfigConstants.getInstance()
					.getColoresCalendarioPrevisiones();
			int coloresInterval = ConfigConstants.getInstance()
					.getIntervalCalendarioPrevisiones();

			// Generar la tabla principal
			pageContext
					.getOut()
					.print("\t<table class=\""
							+ styleClass
							+ "\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");

			for (int month = initialMonth; month <= finalMonth; month++) {
				// Generar cada uno de los meses
				HashMap monthMap = (HashMap) monthsMap.get(String
						.valueOf(month));

				HashMap mapDates = new HashMap();
				Integer numPrevisiones = new Integer(0);
				Integer numUnidades = new Integer(0);
				Integer numPosiblesDias = new Integer(0);
				if (monthMap != null) {
					mapDates = (HashMap) monthMap
							.get(TransferenciasConstants.DIAS_OCUPADOS_MES_KEY);
					numPrevisiones = (Integer) monthMap
							.get(TransferenciasConstants.NUM_PREVISIONES_MES_KEY);
					numUnidades = (Integer) monthMap
							.get(TransferenciasConstants.NUM_UNIDADES_INSTALACION_MES_KEY);
					numPosiblesDias = (Integer) monthMap
							.get(TransferenciasConstants.NUM_DIAS_OCUPADOS_MES_KEY);
				}

				// Generar un div para cada línea de meses
				if ((month % numMonths) == 0) {
					pageContext.getOut().print("\t\t<tr>\n");
				}

				// Generar cada mes
				pageContext.getOut().print("\t\t\t<td align=\"center\">\n");
				pageContext.getOut().print(
						"\t\t\t\t<table class=\"" + styleClassMes + "\">\n");

				// Generar el div para la cabecera del mes
				pageContext.getOut().print(
						"\t\t\t\t\t<tr><th class=\"" + styleClassCabeceraMes
								+ "\" colspan=\"7\">");
				// Generar un enlace si hay previsiones para el mes actual
				if (numPrevisiones.intValue() > 0)
					pageContext.getOut().print(
							"<a href=\"" + url + month + "\">");
				pageContext.getOut().print(monthNames[month]);
				if (numPrevisiones.intValue() > 0)
					pageContext.getOut().print("</a>");
				pageContext.getOut().print("</th></tr>\n");
				pageContext.getOut().print(
						"\t\t\t\t\t<tr><th class=\"" + styleClassResumenMes
								+ "\" colspan=\"7\">");
				pageContext.getOut().print(
						Messages.getString(
								Constants.ETIQUETA_CALENDAR_PREVISIONES,
								request.getLocale())
								+ ": " + numPrevisiones + "<br/>");
				pageContext.getOut().print(
						Messages.getString(
								Constants.ETIQUETA_CALENDAR_UNIDADES,
								request.getLocale())
								+ ": " + numUnidades + "<br/>");
				pageContext.getOut().print(
						Messages.getString(
								Constants.ETIQUETA_CALENDAR_DIAS_POSIBLES,
								request.getLocale())
								+ ": " + numPosiblesDias);
				pageContext.getOut().print("</th></tr>\n");

				// Generar los textos de los días
				pageContext.getOut().print("\t\t\t\t\t<tr>\n");
				for (int dia = 0; dia < 7; dia++) {
					pageContext.getOut().print(
							"\t\t\t\t\t\t<th class=\"" + styleClassDiaCabecera
									+ "\">");
					// Generar el encabezado de día
					pageContext.getOut().print(dayNames[dia]);
					pageContext.getOut().print("</th>\n");
				}
				pageContext.getOut().print("\t\t\t\t\t</tr>\n");

				Calendar calendar = new GregorianCalendar();
				// Establecer como fecha de inicio del mes 1/mes/anio
				calendar.set(year, month, 1);

				// Obtener el día inicial para el primer día de mes
				int initialIndex = calendar.get(Calendar.DAY_OF_WEEK);
				initialIndex--;
				if (initialIndex == 0) // Si es el domingo entonces es el primer
										// día de la semana
					initialIndex = 7;

				// Guardar en una variable el día del mes que se está procesando
				int day = 1;
				// Guardar en una variable los días del mes que se está
				// procesando
				int actualMonthDays = DateUtils.getMonthDays(month, year);

				for (int position = 1; position <= 42; position++) {
					// Si es el primer día de la línea generar un inicio de
					// línea
					if ((position % 7 == 1)) {
						// Generar una línea de mes
						pageContext.getOut().print("\t\t\t\t\t<tr>\n");
					}

					// Si la posicion es menor que el día de inicio o mayor que
					// los días del mes generar un día oculto
					if ((position < initialIndex) || (day > actualMonthDays)) {
						pageContext.getOut().print(
								"\t\t\t\t\t\t<td class=\"" + styleClassDia
										+ Constants.STRING_SPACE
										+ styleClassDiaOculto + "\">");
						pageContext.getOut().print("&nbsp;</td>\n");
					} else {

						// Variables auxiliares
						String color = "";
						String style = styleClassDia;

						// Obtener la fecha del día
						Calendar calendarDay = GregorianCalendar.getInstance();
						calendarDay.set(GregorianCalendar.YEAR, year);
						calendarDay.set(GregorianCalendar.MONTH, month);
						calendarDay.set(GregorianCalendar.DATE, day);

						// Comprobar si es una fecha especial en el calendario
						CalendarCell calendarCell = calendarDate(day, month,
								year, mapDates);
						if (calendarCell != null) {
							try {
								int index = (calendarCell.getNumPrevisiones() - 1)
										/ coloresInterval;
								if (index > colores.length - 1)
									index = colores.length - 1;

								color = " style=\"background-color: #"
										+ colores[index] + ";\" ";
							} catch (Exception e) {
							}
						}

						// Generar la celda
						pageContext.getOut().print(
								"\t\t\t\t\t\t<td class=\"" + style + "\""
										+ color + ">" + day + "</td>\n");

						day++;
					}

					if ((position % 7 == 0)) {
						// Fin generar una línea de mes
						pageContext.getOut().print("\t\t\t\t\t</tr>\n");
					}
				}

				// Generar la terminación del mes
				pageContext.getOut().print("\t\t\t\t</table>\n");
				pageContext.getOut().print("\t\t\t</td>\n");

				// Generar la terminación de la línea de meses
				if ((month % numMonths) == (numMonths - 1)) {
					pageContext.getOut().print("\t\t</tr>\n");
				}
			}

			pageContext.getOut().print("\t</table>\n");

			// Generar la terminación del div principal
			pageContext.getOut().print("</div>\n");
		} catch (Exception e) {
			log.error("Error en la tag calendario", e);
		}
		return SKIP_BODY;
	}

	/**
	 * Permite obtener para una fecha si es una fecha especial
	 * 
	 * @param dia
	 *            Dia de la fecha
	 * @param mes
	 *            Mes de la fecha
	 * @param anio
	 *            Año de la fecha
	 * @param mapDates
	 *            Map con las fiestas
	 * @return Objeto con los datos de la fecha o null si no se encuentra
	 */
	private CalendarCell calendarDate(int dia, int mes, int anio,
			HashMap mapDates) throws Exception {
		if (mapDates == null)
			return null;

		String key = CalendarTagConfig.getMapDateCode(dia, mes, anio);
		return (CalendarCell) mapDates.get(key);
	}
}
