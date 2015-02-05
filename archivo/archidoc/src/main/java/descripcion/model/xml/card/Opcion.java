package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de una opción de un campo.
 */
public class Opcion extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Etiqueta de la opción. */
	private String titulo = null;

	/** Indica si es el valor por defecto. */
	private boolean valorPorDefecto = false;

	/**
	 * Constructor.
	 */
	public Opcion() {
		this(null, false);
	}

	/**
	 * Constructor.
	 */
	public Opcion(String titulo) {
		this(titulo, false);
	}

	/**
	 * Constructor.
	 */
	public Opcion(String titulo, boolean esValorPorDefecto) {
		super();

		setTitulo(titulo);
		setValorPorDefecto(esValorPorDefecto);
	}

	/**
	 * Indica si es el valor por defecto.
	 * 
	 * @return Si es el valor por defecto.
	 */
	public boolean isValorPorDefecto() {
		return valorPorDefecto;
	}

	/**
	 * Establece si es el valor por defecto.
	 * 
	 * @param valorPorDefecto
	 *            Si es el valor por defecto.
	 */
	public void setValorPorDefecto(boolean valorPorDefecto) {
		this.valorPorDefecto = valorPorDefecto;
	}

	/**
	 * Obtiene el título de la opción.
	 * 
	 * @return Título de la opción.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el título de la opción.
	 * 
	 * @param titulo
	 *            Título de la opción.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Opción
		xml.append(tabs + "<" + TagsFicha.TAG_OPCION);

		// Título
		xml.append(" " + TagsFicha.TAG_TITULO_OPCION + "=\"");
		if (titulo != null)
			xml.append(titulo);

		// Valor por defecto
		if (valorPorDefecto)
			xml.append("\" " + TagsFicha.TAG_VALOR_POR_DEFECTO + "=\"true\" />");
		else
			xml.append("\"/>");

		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
