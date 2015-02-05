package common.tags.calendar;

/**
 * Clase para almacenar cada intervalo de la leyenda
 */
public class CalendarLegend {

	/**
	 * Inicio del intervalo de color
	 */
	private String inicio = null;

	/**
	 * Fin del intervalo de color
	 */
	private String fin = null;

	/**
	 * Color del intervalo
	 */
	private String color = null;

	/**
	 * Indica si es el último intervalo de la leyenda
	 */
	private boolean ultimo = false;

	/**
	 * @param inicio
	 *            Inicio del intervalo
	 * @param fin
	 *            Fin del intervalo
	 * @param color
	 *            Color del intervalo
	 */
	public CalendarLegend(String inicio, String fin, String color) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.color = color;
		this.ultimo = false;
	}

	/**
	 * @param inicio
	 *            Inicio del intervalo
	 * @param fin
	 *            Fin del intervalo
	 * @param color
	 *            Color del intervalo
	 * @param ultimo
	 *            Indica si es el último intervalo
	 */
	public CalendarLegend(String inicio, String fin, String color,
			boolean ultimo) {
		super();
		this.inicio = inicio;
		this.fin = fin;
		this.color = color;
		this.ultimo = ultimo;
	}

	/**
	 * @return el color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            el color a establecer
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return el fin
	 */
	public String getFin() {
		return fin;
	}

	/**
	 * @param fin
	 *            el fin a establecer
	 */
	public void setFin(String fin) {
		this.fin = fin;
	}

	/**
	 * @return el inicio
	 */
	public String getInicio() {
		return inicio;
	}

	/**
	 * @param inicio
	 *            el inicio a establecer
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return el ultimo
	 */
	public boolean isUltimo() {
		return ultimo;
	}

	/**
	 * @return el ultimo
	 */
	public boolean getUltimo() {
		return ultimo;
	}

	/**
	 * @param ultimo
	 *            el ultimo a establecer
	 */
	public void setUltimo(boolean ultimo) {
		this.ultimo = ultimo;
	}
}
