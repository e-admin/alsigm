package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.card.TagsFicha;

/**
 * Clase que almacena la información de un elemento de tipo hipervínculo.
 */
public class DefFmtElementoHipervinculo extends DefFmtElemento {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Vínculo. */
	private DefFmtVinculo vinculo;

	/**
	 * Constructor.
	 */
	public DefFmtElementoHipervinculo() {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_HIPERVINCULO);
		vinculo = new DefFmtVinculo();
	}

	/**
	 * Obtiene el vínculo.
	 * 
	 * @return Vínculo.
	 */
	public DefFmtVinculo getVinculo() {
		return vinculo;
	}

	/**
	 * Establece el vínculo.
	 * 
	 * @param url
	 *            Vínculo.
	 */
	public void setVinculo(DefFmtVinculo vinculo) {
		this.vinculo = vinculo;
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

		// Vínculo
		xml.append(vinculo.toXML(indent + 2));

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
