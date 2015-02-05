package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.card.TagsFicha;

/**
 * Clase que almacena la información de un elemento de tipo cabecera.
 */
public class DefFmtElementoCabecera extends DefFmtContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public DefFmtElementoCabecera() {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_CABECERA);
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
		xml.append(tabs + "<" + TagsFicha.TAG_ELEMENTO + " "
				+ TagsFicha.ATTR_TIPO_ELEMENTO + "=\""
				+ getNombreTipoElemento() + "\">");
		xml.append(Constants.NEWLINE);

		// Etiqueta
		xml.append(etiqueta.toXML(indent + 2));

		// Elementos
		if (getTotalElementos() > 0) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_ELEMENTOS_CABECERA + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < getTotalElementos(); i++)
				xml.append(getElemento(i).toXML(indent + 4));

			xml.append(tabs + "  </" + DefFmtTags.TAG_ELEMENTOS_CABECERA + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
