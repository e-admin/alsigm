package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un área de la ficha ISAD(G).
 */
public class Area extends ContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del área. */
	private String id = null;

	/** Indica si el área aparecerá desplegada. */
	private String desplegada = null;

	/**
	 * Constructor.
	 */
	public Area() {
		super(TiposElemento.TIPO_ELEMENTO_AREA);
	}

	/**
	 * Obtiene el identificador del área.
	 * 
	 * @return Identificador del área.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del área.
	 * 
	 * @param id
	 *            Identificador del área.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Indica si el área aparecerá desplegada.
	 * 
	 * @return Si el área aparecerá desplegada.
	 */
	public String getDesplegada() {
		return desplegada;
	}

	/**
	 * Establece si el área aparecerá desplegada.
	 * 
	 * @param desplegada
	 *            Si el área aparecerá desplegada.
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

		// Área
		xml.append(tabs + "<" + TagsFicha.TAG_AREA + " "
				+ TagsFicha.ATTR_ID_AREA + "=\"");
		xml.append(id != null ? id : "");
		xml.append("\"");
		if (Constants.TRUE_STRING.equals(desplegada)) {
			xml.append(" " + TagsFicha.ATTR_AREA_DESPLEGADA + "=\"");
			xml.append(desplegada);
			xml.append("\"");
		}
		xml.append(">");
		xml.append(Constants.NEWLINE);

		// Etiqueta
		xml.append(getEtiqueta().toXML(indent + 2));

		// Elementos
		if (elementos.size() > 0) {
			xml.append(tabs + "  <" + TagsFicha.TAG_ELEMENTOS + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < getTotalElementos(); i++)
				xml.append(getElemento(i).toXML(indent + 4));

			xml.append(tabs + "  </" + TagsFicha.TAG_ELEMENTOS + ">");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "</" + TagsFicha.TAG_AREA + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
