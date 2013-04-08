package descripcion.model.xml.card;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.db.DBUtils;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de edición mínima de edición.
 */
public class Edicion extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del campo. */
	private String id = null;

	/** Indica si el campo es editable. */
	private boolean editable = false;

	/**
	 * Constructor.
	 */
	public Edicion() {
		super();
	}

	/**
	 * Indica si el campo es editable.
	 * 
	 * @return Si el campo es editable.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Establece si el campo es editable.
	 * 
	 * @param editable
	 *            Si el campo es editable.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
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
		xml.append(tabs + "<" + TagsFicha.TAG_EDICION + ">");
		xml.append(Constants.NEWLINE);

		// Id
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_ID + ">");
		if (id != null)
			xml.append(id);
		xml.append("</" + TagsFicha.TAG_EDICION_ID + ">");
		xml.append(Constants.NEWLINE);

		// Editable
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_EDITABLE + ">");
		xml.append(DBUtils.getStringValue(editable));
		xml.append("</" + TagsFicha.TAG_EDICION_EDITABLE + ">");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_EDICION + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
