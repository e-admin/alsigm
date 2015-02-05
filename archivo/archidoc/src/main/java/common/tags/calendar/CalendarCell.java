package common.tags.calendar;

import java.util.Date;

/**
 * Clase para representar una celda para las custom tag calendario y el tag mes
 */
public class CalendarCell {

	/**
	 * Día normal
	 */
	public static final int DATE_TYPE_NORMAL = 0;

	/**
	 * Día con más de un evento
	 */
	public static final int DATE_TYPE_MULTIPLE = 1;

	/**
	 * Fecha que representa la celda
	 */
	private Date date;

	/**
	 * Descripción de la celda
	 */
	private String description;

	/**
	 * Color de la celda
	 */
	private String color;

	/**
	 * Indicador de celda seleccionable
	 */
	private boolean selectionable = true;

	/**
	 * Número de previsiones de la celda
	 */
	private int numPrevisiones = 0;

	/**
	 * Crea un objeto celda de calendario
	 * 
	 * @param date
	 *            Fecha de la celda
	 * @param description
	 *            Descripción de la celda
	 * @param color
	 *            Color de la celda
	 * @param selectionable
	 *            Indica si la celda es o no seleccionable
	 */
	public CalendarCell(Date date, String description, String color,
			boolean selectionable) {
		super();
		this.date = date;
		this.description = description;
		this.color = color;
		this.selectionable = selectionable;
	}

	/**
	 * Crea un objeto celda de calendario
	 * 
	 * @param date
	 *            Fecha de la celda
	 * @param description
	 *            Descripción de la celda
	 * @param color
	 *            Color de la celda
	 * @param selectionable
	 *            Indica si la celda es o no seleccionable
	 * @param numPrevisiones
	 *            Número de previsiones
	 */
	public CalendarCell(Date date, String description, String color,
			boolean selectionable, int numPrevisiones) {
		super();
		this.date = date;
		this.description = description;
		this.color = color;
		this.selectionable = selectionable;
		this.numPrevisiones = numPrevisiones;
	}

	/**
	 * Devuelve el color de la celda
	 * 
	 * @return Color de la celda.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color de la celda
	 * 
	 * @param color
	 *            Color de la celda.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Devuelve la fecha de la celda
	 * 
	 * @return Fecha de la celda.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Establece la fecha de la celda
	 * 
	 * @param date
	 *            Fecha de la celda.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Devuelve la descripción de la fecha
	 * 
	 * @return Descripción de la celda.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Establece la descripción de la fecha
	 * 
	 * @param description
	 *            Descripción de la celda.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Devuelve si la celda es seleccionable
	 * 
	 * @return Devuelve si la celda es seleccionable.
	 */
	public boolean isSelectionable() {
		return selectionable;
	}

	/**
	 * Establece si la celda es seleccionable
	 * 
	 * @param selectionable
	 *            Establece si la celda es seleccionable
	 */
	public void setSelectionable(boolean selectionable) {
		this.selectionable = selectionable;
	}

	/**
	 * @return el numPrevisiones
	 */
	public int getNumPrevisiones() {
		return numPrevisiones;
	}

	/**
	 * @param numPrevisiones
	 *            el numPrevisiones a establecer
	 */
	public void setNumPrevisiones(int numPrevisiones) {
		this.numPrevisiones = numPrevisiones;
	}

	/**
	 * @param numPrevisiones
	 *            el numPrevisiones a establecer
	 */
	public void incNumPrevisiones() {
		this.numPrevisiones++;
	}

}
