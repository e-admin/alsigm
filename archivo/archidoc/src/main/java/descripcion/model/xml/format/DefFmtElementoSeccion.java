package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un elemento de tipo sección.
 */
public class DefFmtElementoSeccion extends DefFmtContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Indica si la sección aparecerá desplegada. */
	private String desplegada = null;

	/**
	 * Constructor.
	 */
	public DefFmtElementoSeccion() {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_SECCION);
	}

	/**
	 * Indica si la sección aparecerá desplegada.
	 * 
	 * @return Si la sección aparecerá desplegada.
	 */
	public String getDesplegada() {
		return desplegada;
	}

	/**
	 * Establece si la sección aparecerá desplegada.
	 * 
	 * @param desplegada
	 *            Si la sección aparecerá desplegada.
	 */
	public void setDesplegada(String desplegada) {
		this.desplegada = desplegada;
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
				+ getNombreTipoElemento() + "\">");
		xml.append(Constants.NEWLINE);

		// Desplegada
		if (Constants.TRUE_STRING.equals(desplegada)) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_DESPLEGADA + ">");
			xml.append(desplegada);
			xml.append("</" + DefFmtTags.TAG_DESPLEGADA + ">");
			xml.append(Constants.NEWLINE);
		}

		// Etiqueta
		xml.append(etiqueta.toXML(indent + 2));

		// Inicio del tag Elementos
		xml.append(tabs + "  <" + DefFmtTags.TAG_ELEMENTOS_SECCION + ">");
		xml.append(Constants.NEWLINE);

		// Elementos
		for (int i = 0; i < getTotalElementos(); i++)
			xml.append(getElemento(i).toXML(indent + 4));

		// Fin del tag Elementos
		xml.append(tabs + "  </" + DefFmtTags.TAG_ELEMENTOS_SECCION + ">");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
