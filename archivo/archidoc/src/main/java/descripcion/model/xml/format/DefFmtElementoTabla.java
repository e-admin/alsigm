package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import descripcion.model.xml.card.TagsFicha;

/**
 * Clase que almacena la información de un elemento de tipo tabla.
 */
public class DefFmtElementoTabla extends DefFmtContenedorElementos {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la tabla. */
	private String idCampoTbl = null;

	/** Indica si hay que mostrar las cabeceras. */
	private boolean mostrarCabeceras = true;

	/**
	 * Constructor.
	 */
	public DefFmtElementoTabla() {
		super(DefFmtTiposElemento.TIPO_ELEMENTO_TABLA);
	}

	/**
	 * Obtiene el identificador de la tabla.
	 *
	 * @return Identificador de la tabla.
	 */
	public String getIdCampoTbl() {
		return idCampoTbl;
	}

	/**
	 * Establece el identificador de la tabla.
	 *
	 * @param idCampoTbl
	 *            Identificador de la tabla.
	 */
	public void setIdCampoTbl(String idCampoTbl) {
		this.idCampoTbl = idCampoTbl;
	}

	/**
	 * Indica si hay que mostrar las cabeceras.
	 *
	 * @return Si hay que mostrar las cabeceras.
	 */
	public boolean isMostrarCabeceras() {
		return mostrarCabeceras;
	}

	/**
	 * Establece si hay que mostrar las cabeceras.
	 *
	 * @param cabeceras
	 *            Indica si hay que mostrar las cabeceras.
	 */
	public void setMostrarCabeceras(boolean cabeceras) {
		this.mostrarCabeceras = cabeceras;
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
				+ getNombreTipoElemento() + "\" ");

		if(Constants.TRUE_STRING.equalsIgnoreCase(getScroll())){
			xml.append(TagsFicha.ATTR_SCROLL_ELEMENTO + "=\"" + getScroll() + "\"");
		}
		xml.append(">");

		xml.append(Constants.NEWLINE);

		// Id_CampoTbl
		xml.append(tabs + "  <" + DefFmtTags.TAG_ID_CAMPO_TBL + ">");
		xml.append(idCampoTbl != null ? idCampoTbl : "");
		xml.append("</" + DefFmtTags.TAG_ID_CAMPO_TBL + ">");
		xml.append(Constants.NEWLINE);

		// Etiqueta
		xml.append(etiqueta.toXML(indent + 2));

		// Mostrar_Cabeceras
		if (!mostrarCabeceras) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.FALSE_STRING);
			xml.append("</" + DefFmtTags.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.NEWLINE);
		}

		// Elementos
		if (getTotalElementos() > 0) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_ELEMENTOS_TABLA + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < getTotalElementos(); i++)
				xml.append(getElemento(i).toXML(indent + 4));

			xml.append(tabs + "  </" + DefFmtTags.TAG_ELEMENTOS_TABLA + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
