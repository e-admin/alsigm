package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de una etiqueta.
 */
public class Etiqueta extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Título de la etiqueta. */
	private String titulo = null;

	/** Estilo de la etiqueta. */
	private String estilo = null;

	/**
	 * Constructor.
	 */
	public Etiqueta() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param titulo
	 *            Título de la etiqueta.
	 * @param estilo
	 *            Estilo de la etiqueta.
	 */
	public Etiqueta(String titulo, String estilo) {
		this();
		setTitulo(titulo);
		setEstilo(estilo);
	}

	/**
	 * Obtiene el Estilo de la etiqueta.
	 * 
	 * @return Estilo de la etiqueta.
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * Establece el estilo de la etiqueta.
	 * 
	 * @param estilo
	 *            Estilo de la etiqueta.
	 */
	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	/**
	 * Obtiene el título de la etiqueta.
	 * 
	 * @return Título de la etiqueta.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece el título de la etiqueta.
	 * 
	 * @param titulo
	 *            Título de la etiqueta.
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

		// Tag de inicio
		xml.append(tabs + "<" + TagsFicha.TAG_ETIQUETA + ">");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(tabs + "  <" + TagsFicha.TAG_TITULO + ">");
		xml.append(XmlElement.getCDataContent(titulo));
		xml.append("</" + TagsFicha.TAG_TITULO + ">");
		xml.append(Constants.NEWLINE);

		// Estilo
		xml.append(tabs + "  <" + TagsFicha.TAG_ESTILO + ">");
		xml.append(estilo != null ? estilo : "");
		xml.append("</" + TagsFicha.TAG_ESTILO + ">");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_ETIQUETA + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
