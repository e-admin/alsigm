package ieci.tecdoc.sgm.registro.form.loaders;

import ieci.tecdoc.sgm.registro.form.loaders.vo.PropertyValue;

import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class SelectOptionsLoaderBase {

	private static final Logger logger = Logger
			.getLogger(SelectOptionsLoaderBase.class);

	public static final String TAG_SELECT = "select";
	public static final String TAG_OPTION = "option";
	public static final String TAG_TEXT = "text";
	public static final String TAG_VALUE = "value";

	protected String entidad;

	protected String language;

	public SelectOptionsLoaderBase(String entidad, String language) {
		this.entidad = entidad;
		this.language = language;
	}

	/**
	 * Método a implementar para cargar los datos de propiedad-valor para las
	 * opciones de selección.
	 *
	 * @return Lista de propiedad-valor para las opciones de selección.
	 */
	public abstract List<PropertyValue> loadOptions();

	/**
	 * Método a implementar para cargar los datos de propiedad-valor para las
	 * opciones de selección a partir de una tabla u objeto significativo que
	 * filtra dicha carga.
	 *
	 * @param tableName
	 *            Nombre de tabla u objeto para el que se realiza la carga de
	 *            datos.
	 * @return Lista de propiedad-valor para las opciones de selección.
	 */
	public abstract List<PropertyValue> loadOptions(String tableName);

	/**
	 * Crea las opciones de selección.
	 *
	 * @return Estructura XML de opciones de selección con el siguiente formato:
	 *         <select> <option> <text>...</text> <value>...</value> </option>
	 *         <option> ... </select>
	 */
	public Node createOptions() {

		List<PropertyValue> options = loadOptions();
		return createSelectNode(options);
	}

	/**
	 * Crea las opciones de selección a partir de una tabla u objeto
	 * significativo que filtra la obtención de las opciones de selección.
	 *
	 * @param tableName
	 *            Nombre de tabla u objeto para el que se crean las opciones de
	 *            selección.
	 * @return Estructura XML de opciones de selección con el siguiente formato:
	 *         <select> <option> <text>...</text> <value>...</value> </option>
	 *         <option> ... </select>
	 */
	public Node createOptions(String tableName) {

		List<PropertyValue> options = loadOptions(tableName);
		return createSelectNode(options);
	}

	/**
	 * Crea las opciones de selección a partir de los datos de propiedad-valor
	 * cargados.
	 *
	 * @param options
	 *            Lista de propiedad-valor para las opciones de selección.
	 * @return Estructura XML de opciones de selección con el siguiente formato:
	 *         <select> <option> <text>...</text> <value>...</value> </option>
	 *         <option> ... </select>
	 */
	protected Node createSelectNode(List<PropertyValue> options) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();

			Element root = (Element) document.createElement(TAG_SELECT);
			document.appendChild(root);

			if ((options != null) && (!options.isEmpty())) {

				Iterator<PropertyValue> it = options.iterator();
				while (it.hasNext()) {
					root.appendChild(createOption(document, it.next()));
				}
			}

			return root;

		} catch (Exception e) {
			logger.error("Error al crear las opciones de seleccion", e);
		}

		return null;
	}

	/**
	 * Crea la opción de selección a partir de los datos de propiedad-valor.
	 *
	 * @param propertyValue
	 *            Propiedad y valor para la opción de selección.
	 * @return Estructura XML de opción de selección con el siguiente formato:
	 *         <option> <text>...</text> <value>...</value> </option>
	 */
	protected Element createOption(Document document,
			PropertyValue propertyValue) {

		Element option = (Element) document.createElement(TAG_OPTION);

		Element text = (Element) document.createElement(TAG_TEXT);
		text.setTextContent(propertyValue.getName());

		Element value = (Element) document.createElement(TAG_VALUE);
		value.setTextContent(propertyValue.getValue());

		option.appendChild(text);
		option.appendChild(value);

		return option;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
