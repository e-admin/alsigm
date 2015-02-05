package descripcion.model.xml.definition;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que define un evento.
 */
public class DefEvento extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de evento. */
	private String tipo = null;

	/** Nombre cualificado de la clase que implementa el evento. */
	private String clase = null;

	/**
	 * Constructor.
	 */
	public DefEvento() {
		super();
	}

	/**
	 * Constructor.
    *
	 * @param tipo
	 *            Tipo de evento.
	 * @param clase
	 *            Nombre cualificado de la clase que implementa el evento.
	 */
	public DefEvento(String tipo, String clase) {
		super();
		setTipo(tipo);
		setClase(clase);
	}

	/**
	 * Obtiene el nombre cualificado de la clase que implementa el evento.
    *
	 * @return Nombre cualificado de la clase.
	 */
	public String getClase() {
		return clase;
	}

	/**
	 * Establece el nombre cualificado de la clase que implementa el evento.
    *
	 * @param clase
	 *            Nombre cualificado de la clase.
	 */
	public void setClase(String clase) {
		this.clase = StringUtils.trimToEmpty(clase);
	}

	/**
	 * Obtiene el tipo de evento.
    *
	 * @return Tipo de evento.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de evento.
    *
	 * @param tipo
	 *            Tipo de evento.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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

		xml.append(tabs + "<" + DefTags.TAG_EVENTO + ">");
		xml.append(Constants.NEWLINE);

		// Tipo
		xml.append(tabs + "  <" + DefTags.TAG_TIPO_EVENTO + ">");
		xml.append(tipo != null ? tipo : "");
		xml.append("</" + DefTags.TAG_TIPO_EVENTO + ">");
		xml.append(Constants.NEWLINE);

		// Clase
		xml.append(tabs + "  <" + DefTags.TAG_CLASE_EVENTO + ">");
		xml.append(clase != null ? clase : "");
		xml.append("</" + DefTags.TAG_CLASE_EVENTO + ">");
		xml.append(Constants.NEWLINE);

		xml.append(tabs + "</" + DefTags.TAG_EVENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
