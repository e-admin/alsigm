package descripcion.model.xml.definition;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que especifica el formato de una fecha.
 */
public class DefFormatoFecha extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de formato. */
	private String tipo = null;

	/** Separador de la fecha. */
	private String separador = null;

	/**
	 * Constructor.
	 */
	public DefFormatoFecha() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param tipo
	 *            Tipo de formato.
	 * @param separador
	 *            Separador de la fecha.
	 */
	public DefFormatoFecha(String tipo, String separador) {
		super();
		setTipo(tipo);
		setSeparador(separador);
	}

	/**
	 * Obtiene el separador de la fecha.
	 * 
	 * @return Separador de la fecha.
	 */
	public String getSeparador() {
		return separador;
	}

	/**
	 * Establece el Separador de la fecha.
	 * 
	 * @param separador
	 *            Separador de la fecha.
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

	/**
	 * Obtiene el tipo de formato.
	 * 
	 * @return Tipo de formato.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de formato.
	 * 
	 * @param tipo
	 *            Tipo de formato.
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

		xml.append(tabs + "<" + DefTags.TAG_FORMATO + " "
				+ DefTags.ATTR_TIPO_FORMATO + "=\"");
		xml.append(tipo != null ? tipo : "");
		xml.append("\" " + DefTags.ATTR_SEP_FORMATO + "=\"");
		xml.append(separador != null ? separador : "");
		xml.append("\" />");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
