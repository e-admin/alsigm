package descripcion.model.xml.format;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que modeliza la definición del formato de una ficha ISAD(G).
 */
public class DefFmtFicha extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Versión de la definición por defecto. */
	public static final String DEFAULT_VERSION = "01.00";

	/** Versión de la definición. */
	private String version = DEFAULT_VERSION;

	/** Identificador del formato */
	private String id = null;

	/** Tipo de acceso */
	private int tipoAcceso = 0;

	/** Elementos de la definición. */
	private List elementos = new LinkedList();

	/** Si la definición es editable. */
	private boolean editable = false;

	/** Si hay que mostrar botón de generación de automáticos. */
	private boolean automaticos = false;

	private Map idsElementosTabla = new HashMap();
	private Map idsElementosCampoDato = new HashMap();

	/**
	 * Constructor.
	 */
	public DefFmtFicha() {
	}

	/**
	 * Obtiene la versión de la definición.
	 * 
	 * @return Versión de la definición.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Establece la versión de la definición.
	 * 
	 * @param version
	 *            Versión de la definición.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Devuelve el identificador del formato
	 * 
	 * @return identificador del formato
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del formato
	 * 
	 * @param id
	 *            identificador del formato
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el tipo de acceso a la ficha ({@link gcontrol.model.TipoAcceso}
	 * ).
	 * 
	 * @return tipo de acceso a la ficha ({@link gcontrol.model.TipoAcceso}).
	 */
	public int getTipoAcceso() {
		return tipoAcceso;
	}

	/**
	 * Establece el tipo de acceso a la ficha
	 * 
	 * @param tipoAcceso
	 *            tipo de acceso a la ficha
	 */
	public void setTipoAcceso(int tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	/**
	 * Añade un elemento a la lista.
	 * 
	 * @param elemento
	 *            Elemento.
	 */
	public void addElemento(DefFmtElementoSeccion elemento) {
		idsElementosCampoDato.putAll(elemento.getIdsElementosCampoDato());
		idsElementosTabla.putAll(elemento.getIdsElementosTabla());
		elementos.add(elemento);
	}

	/**
	 * Obtiene un elemento de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del elemento dentro de la lista.
	 * @return Elemento.
	 */
	public DefFmtElementoSeccion getElemento(int index) {
		return (DefFmtElementoSeccion) elementos.get(index);
	}

	/**
	 * Obtiene la lista de elementos.
	 * 
	 * @return Lista de elementos.
	 */
	public DefFmtElementoSeccion[] getElementos() {
		return (DefFmtElementoSeccion[]) elementos
				.toArray(new DefFmtElementoSeccion[elementos.size()]);
	}

	/**
	 * Obtiene el número de elementos de la lista.
	 * 
	 * @return Número de elementos.
	 */
	public int getTotalElementos() {
		return elementos.size();
	}

	/**
	 * Elimina el elemento de la posición determinada.
	 * 
	 * @param index
	 *            Posición del elemento a eliminar.
	 */
	public void removeElemento(int index) {
		elementos.remove(index);
	}

	/**
	 * Elimina el elemento de la lista.
	 * 
	 * @param elemento
	 *            Elemento a eliminar.
	 */
	public void removeElemento(DefFmtElementoSeccion elemento) {
		elementos.remove(elemento);
	}

	/**
	 * Elimina todos los elementos de la lista.
	 */
	public void clearElementos() {
		elementos.clear();
	}

	/**
	 * Indica si la definición de la ficha es editable.
	 * 
	 * @return Si es editable.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Establece si la definición de la ficha es editable.
	 * 
	 * @param editable
	 *            Si es editable.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Si hay que mostrar botón de generación de automáticos.
	 * 
	 * @return true si hay que mostrar botón de generación de automáticos.
	 */
	public boolean isAutomaticos() {
		return automaticos;
	}

	/**
	 * Establece si hay que mostrar botón de generación de automáticos.
	 * 
	 * @param automaticos
	 *            Si hay que mostrar botón de generación de automáticos.
	 */
	public void setAutomaticos(boolean automaticos) {
		this.automaticos = automaticos;
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

		// XML
		xml.append(tabs + "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xml.append(Constants.NEWLINE);

		// Definición_FmtFicha
		xml.append(tabs + "<" + DefFmtTags.TAG_DEFINITION + " "
				+ DefFmtTags.ATTR_VERSION + "=\"");
		xml.append(version != null ? version : DEFAULT_VERSION);
		xml.append("\">");
		xml.append(Constants.NEWLINE);

		// Editable
		xml.append(tabs + "  <" + DefFmtTags.TAG_EDITABLE + ">");
		xml.append(editable ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefFmtTags.TAG_EDITABLE + ">");
		xml.append(Constants.NEWLINE);

		// Automáticos
		xml.append(tabs + "  <" + DefFmtTags.TAG_AUTOMATICOS + ">");
		xml.append(automaticos ? Constants.TRUE_STRING : Constants.FALSE_STRING);
		xml.append("</" + DefFmtTags.TAG_AUTOMATICOS + ">");
		xml.append(Constants.NEWLINE);

		// Elementos
		if (elementos.size() > 0) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_ELEMENTOS + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < elementos.size(); i++)
				xml.append(getElemento(i).toXML(indent + 4));

			xml.append(tabs + "  </" + DefFmtTags.TAG_ELEMENTOS + ">");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "</" + DefFmtTags.TAG_DEFINITION + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	/**
	 * @return el idsElementosCampoDato
	 */
	public Map getIdsElementosCampoDato() {
		return idsElementosCampoDato;
	}

	/**
	 * @return el idsElementosTabla
	 */
	public Map getIdsElementosTabla() {
		return idsElementosTabla;
	}

}
