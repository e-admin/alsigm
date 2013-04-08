package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un elemento de tipo cabecera.
 */
public class ElementoCabecera extends ContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ElementoCabecera() {
		super(TiposElemento.TIPO_ELEMENTO_CABECERA);
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
		xml.append(getEtiqueta().toXML(indent + 2));

		// Inicio del tag Elementos_Cabecera
		xml.append(tabs + "  <" + TagsFicha.TAG_ELEMENTOS_CABECERA + ">");
		xml.append(Constants.NEWLINE);

		// Elementos
		for (int i = 0; i < getTotalElementos(); i++)
			xml.append(getElemento(i).toXML(indent + 4));

		// Cierre del tag Elementos_Cabecera
		xml.append(tabs + "  </" + TagsFicha.TAG_ELEMENTOS_CABECERA + ">");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
