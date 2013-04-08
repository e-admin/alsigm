package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de un título de etiqueta.
 */
public class DefFmtTitulo extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Título predeterminado. */
	private String predeterminado = null;

	/**
	 * Constructor.
	 */
	public DefFmtTitulo() {
	}

	/**
	 * Obtiene el título predeterminado.
	 * 
	 * @return Título predeterminado.
	 */
	public String getPredeterminado() {
		return predeterminado;
	}

	/**
	 * Establece el título predeterminado.
	 * 
	 * @param predeterminado
	 *            Título predeterminado.
	 */
	public void setPredeterminado(String predeterminado) {
		this.predeterminado = predeterminado;
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
		xml.append(tabs + "<" + DefFmtTags.TAG_TITULO + " "
				+ DefFmtTags.ATTR_TITULO_PREDETERMINADO + "=\"");
		xml.append(predeterminado != null ? predeterminado : "");
		xml.append("\"></" + DefFmtTags.TAG_TITULO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
