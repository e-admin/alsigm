package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un elemento de tipo etiqueta-dato.
 */
public class DefFmtElementoEtiquetaDato extends DefFmtElemento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Campo del elemento. */
	private DefFmtCampo campo = new DefFmtCampo();

	/**
	 * Constructor.
	 */
	public DefFmtElementoEtiquetaDato() {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO);
	}

	/**
	 * Constructor.
	 */
	public DefFmtElementoEtiquetaDato(String mostrarScroll) {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO, mostrarScroll);
	}

	/**
	 * Obtiene el Campo del elemento.
	 *
	 * @return Campo del elemento.
	 */
	public DefFmtCampo getCampo() {
		return campo;
	}

	/**
	 * Establece el campo del elemento.
	 *
	 * @param campo
	 *            Campo del elemento.
	 */
	public void setCampo(DefFmtCampo campo) {
		this.campo = campo;
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
		xml.append(tabs + "<" + DefFmtTags.TAG_ELEMENTO + " "
				+ DefFmtTags.ATTR_TIPO_ELEMENTO + "=\""
				+ getNombreTipoElemento() + "\" ");

		if(Constants.TRUE_STRING.equalsIgnoreCase(getScroll())){
			xml.append(DefFmtTags.ATTR_SCROLL_ELEMENTO + "=\"" + getScroll() + "\"");
		}

		xml.append(">");
		xml.append(Constants.NEWLINE);

		// Etiqueta
		xml.append(etiqueta.toXML(indent + 2));

		// Campo
		xml.append(campo.toXML(indent + 2));

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
