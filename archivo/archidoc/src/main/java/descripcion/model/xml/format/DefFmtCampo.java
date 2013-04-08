package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información del campo de un elemento de tipo
 * etiqueta-dato.
 */
public class DefFmtCampo extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del campo. */
	private String id = null;

	/** Valor de la selección por defecto. */
	private String valorSeleccionPorDefecto = null;

	/** Si hay que mostrar el tipo de número. */
	private boolean mostrarTipoNumero = true;

	/** Si hay que mostrar la unidad del número. */
	private boolean mostrarUnidadNumero = true;

	/** Estilo del campo. */
	private String estilo = null;

	/** Multilinea */
	private boolean multilinea = false;

	/**
	 * Constructor.
	 */
	public DefFmtCampo() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            Identificador del campo.
	 * @param estilo
	 *            Estilo del campo.
	 */
	public DefFmtCampo(String id, String estilo) {
		setId(id);
		setEstilo(estilo);
	}

	/**
	 * Obtiene el identificador del campo.
	 * 
	 * @return Identificador del campo.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del campo.
	 * 
	 * @param id
	 *            Identificador del campo.
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Obtiene el valor de la selección por defecto.
	 * 
	 * @return Valor de la selección por defecto.
	 */
	public String getValorSeleccionPorDefecto() {
		return valorSeleccionPorDefecto;
	}

	/**
	 * Establece el valor de la selección por defecto.
	 * 
	 * @param valorSeleccionPorDefecto
	 *            Valor de la selección por defecto.
	 */
	public void setValorSeleccionPorDefecto(String valorSeleccionPorDefecto) {
		this.valorSeleccionPorDefecto = valorSeleccionPorDefecto;
	}

	/**
	 * Indica si hay que mostrar la unidad del número.
	 * 
	 * @return Si hay que mostrar la unidad del número.
	 */
	public boolean isMostrarTipoNumero() {
		return mostrarTipoNumero;
	}

	/**
	 * Establece si hay que mostrar la unidad del número.
	 * 
	 * @param mostrarTipoNumero
	 *            Si hay que mostrar la unidad del número.
	 */
	public void setMostrarTipoNumero(boolean mostrarTipoNumero) {
		this.mostrarTipoNumero = mostrarTipoNumero;
	}

	/**
	 * Indica si hay que mostrar el tipo de número.
	 * 
	 * @return Si hay que mostrar el tipo de número.
	 */
	public boolean isMostrarUnidadNumero() {
		return mostrarUnidadNumero;
	}

	/**
	 * Establece si hay que mostrar el tipo de número.
	 * 
	 * @param mostrarUnidadNumero
	 *            Si hay que mostrar el tipo de número.
	 */
	public void setMostrarUnidadNumero(boolean mostrarUnidadNumero) {
		this.mostrarUnidadNumero = mostrarUnidadNumero;
	}

	public boolean isMultilinea() {
		return multilinea;
	}

	public void setMultilinea(boolean multilinea) {
		this.multilinea = multilinea;
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
		xml.append(tabs + "<" + DefFmtTags.TAG_CAMPO + " "
				+ DefFmtTags.ATTR_ID_CAMPO + "=\"");
		xml.append(id != null ? id : "");
		xml.append("\">");
		xml.append(Constants.NEWLINE);

		// ValorSeleccionPorDefecto
		if (valorSeleccionPorDefecto != null) {
			xml.append(tabs + "  <"
					+ DefFmtTags.TAG_VALOR_SELECCION_POR_DEFECTO + ">");
			xml.append(valorSeleccionPorDefecto);
			xml.append("</" + DefFmtTags.TAG_VALOR_SELECCION_POR_DEFECTO + ">");
			xml.append(Constants.NEWLINE);
		}

		// mostrarTipoNumero
		if (!mostrarTipoNumero) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_MOSTRAR_TIPO_NUMERO + ">");
			xml.append(Constants.FALSE_STRING);
			xml.append("</" + DefFmtTags.TAG_MOSTRAR_TIPO_NUMERO + ">");
			xml.append(Constants.NEWLINE);
		}

		// mostrarUnidadNumero
		if (!mostrarUnidadNumero) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_MOSTRAR_UNIDAD_NUMERO
					+ ">");
			xml.append(Constants.FALSE_STRING);
			xml.append("</" + DefFmtTags.TAG_MOSTRAR_UNIDAD_NUMERO + ">");
			xml.append(Constants.NEWLINE);
		}

		// Estilo
		xml.append(tabs + "  <" + DefFmtTags.TAG_ESTILO_CAMPO + ">");
		xml.append(estilo != null ? estilo : "");
		xml.append("</" + DefFmtTags.TAG_ESTILO_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		// Multilinea
		if (multilinea) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_MULTILINEA + ">");
			xml.append(Constants.TRUE_STRING);
			xml.append("</" + DefFmtTags.TAG_MULTILINEA + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_CAMPO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
