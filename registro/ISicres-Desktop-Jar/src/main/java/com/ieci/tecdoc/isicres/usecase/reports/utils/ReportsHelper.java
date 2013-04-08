package com.ieci.tecdoc.isicres.usecase.reports.utils;
/**
 * Clase de ayuda para la generación de los informes
 * @author Iecisa
 * @version $Revision$
 *
 */
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

public class ReportsHelper {


	private static final String BARRA_COMILLAS = "\"";

	private static final String EMPTY = "";

	//Parámetros definidos con valor por defecto
	/**
	 * Parámetro que contiene la ordenación por defecto del informe
	 */
	public static final String PARAM_ORDERBY_FIELDS = "ORDERBY_FIELDS";

	//Parámetros Adicionales
	/**
	 * Parámetro que contiene la consulta sql que ejecuta el informe
	 */
	public static final String PARAM_QUERYSTRING = "QUERYSTRING";

	/**
	 * Parámetro que contiene un texto personalizado para mostrar la ordenación del informe
	 * Ej. Ordenado por Número de Registro y Fecha
	 */
	public static final String PARAM_ORDERBY_LABEL	= "ORDERBY_LABEL";


	public static final String DEFAULTEXPRESION_TAG = "defaultValueExpression";

	public static final String NAME_TAG = "name";

	//Variable que indica el nodo: /jasperReport/queryString/
	public static final String QUERYSTRING_NODE = "/*[local-name()='jasperReport']/*[local-name()='queryString']";

	//Variable que indica el nodo: /jasperReport/parameter/
	public static final String PARAMETER_NODE = "/*[local-name()='jasperReport']/*[local-name()='parameter']";

	public static final String INICIO_CDATA_TEXT = "<![CDATA[";
	public static final String FIN_CDATA_TEXT ="]]>";

	/**
	 * Documento que contiene el XML del informe
	 */
	private Document xmlDocument = null;


	/**
	 * Mapa de parametros definidos en el informe:</b
	 * Clave: {@link String} Cadena que contiene el identificador del parametro por su nombre<br/>
	 * Valor: {@link Element} Elemento que define el parametro<br>
	 */
	private Map mapParametros = null;

	/**
	 * Constructor que recibe un Document
	 * @param xmlDocument Document que contine el XML del informe
	 */
	public ReportsHelper(Document xmlDocument){
		this.xmlDocument = xmlDocument;
	}

	/**
	 * @return el xmlDocument
	 */
	public Document getXmlDocument() {
		return xmlDocument;
	}

	/**
	 * @param xmlDocument el xmlDocument a fijar
	 */
	public void setXmlDocument(Document xmlDocument) {
		this.xmlDocument = xmlDocument;
	}



	/**
	 * Obtiene los parámetros definidos en el informe
	 * @return Map de parametros
	 */
	private Map getParameters() {
		if (mapParametros == null) {
			mapParametros = new LinkedHashMap();

			if (xmlDocument != null) {

				List listaParametros = xmlDocument.getRootElement()
						.selectNodes(PARAMETER_NODE);

				if (listaParametros != null) {
					for (Iterator iterator = listaParametros.iterator(); iterator
							.hasNext();) {
						Element parametro = (Element) iterator.next();

						if (parametro != null) {
							String paramName = parametro.attributeValue(NAME_TAG);

							if (paramName != null) {
								mapParametros.put(paramName, parametro);
							}
						}
					}
				}
			}
		}
		return mapParametros;
	}

	private String deleteComillas(String valor){
		if(valor != null){

			if(valor.indexOf(BARRA_COMILLAS) != -1 ){
				return valor.replaceAll(BARRA_COMILLAS, EMPTY);
			}
		}
		return valor;
	}

	private String addComillas(String valor){
		if(valor != null){
			if(valor.indexOf(BARRA_COMILLAS) == -1 ){
				return BARRA_COMILLAS + valor + BARRA_COMILLAS;
			}
		}
		return valor;
	}

	/**
	 * Obtiene el nodo que contiene la queryString (La sentencia SQL del informe)
	 * @return Elemento - Elemento queryString
	 */
	private Element getQueryStringNode() {

		Node node = xmlDocument.getRootElement().selectSingleNode(
				QUERYSTRING_NODE);

		return (Element) node;
	}

	/**
	 * Se compone la consulta con el order que se indique
	 * @param query
	 * @param orderByFields
	 * @return String - Sentencia SQL a ejecutar
	 */
	public static String getComposeQuery(String query, String orderByFields){
		return query + orderByFields;
	}

	/**
	 * Obtiene el nodo de un parámetro por su nombre de parámetro
	 *
	 * @param paramName
	 * @return Element
	 */
	private Element getParameterByNameNode(String paramName) {
		return (Element) getParameters().get(paramName);
	}

	public Element getParamDefaultNode(String paramName) {
		Node defaultValueNode = null;

		Element element = getParameterByNameNode(paramName);
		if (element != null) {
			defaultValueNode = element
					.selectSingleNode(DEFAULTEXPRESION_TAG);
		}

		return (Element)defaultValueNode;
	}


	/**
	 * Obtiene el valor por defecto de un parámetro
	 * @param paramName Nombre del parámetro
	 * @return
	 */
	public String getDefaultParamValue(String paramName) {
		String valorDefecto = null;
		Node element = getParamDefaultNode(paramName);
		if (element != null) {
			valorDefecto = element.getText();
		}

		return deleteComillas(valorDefecto);
	}


	/**
	 * Obtiene los campos de ordenación por defecto del informe
	 * @return el orderByDefaultValue
	 */
	public String getOrderByDefaultValue() {
		return getDefaultParamValue(PARAM_ORDERBY_FIELDS);
	}

	/**
	 * Obtiene el texto de un elemento
	 *
	 * @param elemento
	 * @return String
	 */
	private String getTextByElement(Node elemento) {
		if (elemento != null) {
			return elemento.getText();
		}

		return null;
	}


	/**
	 * Establece el valor en <code>/jasperReport/parameter</code> cuyo parametro
	 * coincide con el nombre
	 *
	 * @param paramName
	 * @param paramValue
	 */
	public void setDefaultParameterValue(String paramName, String paramValue) {

		Element parametro = getParameterByNameNode(paramName);

		if (parametro != null) {
			if(paramValue == null){
				paramValue = EMPTY;
			}

			paramValue = addComillas(paramValue);

			Element defaultValueNode = (Element)getParamDefaultNode(paramName);

			if(defaultValueNode != null){
				defaultValueNode.clearContent();
				defaultValueNode.addCDATA(paramValue);
			}
			else{
				parametro.addElement(DEFAULTEXPRESION_TAG).addCDATA(paramValue);
			}
		}
	}


	/**
	 * Establece la consulta que debe ejecutar el informe <code>/jasperReport/queryString</code>
	 * @param sqlClause Consulta
	 * @param orderByClause Clausula OrderBy
	 *
	 * @param parametrosAdicionales Map con los parámetros adicionales.
	 */
   public void setQueryStringReport(String query, String orderByFields, Map<String,String> parametrosAdicionales){
	   Element queryStringElement = getQueryStringNode();

	   String queryString = getComposeQuery(query, orderByFields);

		if (queryStringElement != null) {

			queryStringElement.clearContent();
			queryStringElement.addCDATA(queryString);
		}

		//Parametros Adicionales
		setDefaultParameterValue(PARAM_ORDERBY_FIELDS, orderByFields);
		setDefaultParameterValue(PARAM_QUERYSTRING, queryString);

		if(parametrosAdicionales != null){
			for (Iterator<String> iterator = parametrosAdicionales.keySet().iterator(); iterator.hasNext();) {
				String paramName = iterator.next();
				String paramValue = parametrosAdicionales.get(paramName);
				setDefaultParameterValue(paramName, paramValue);
			}
		}

		xmlDocument.normalize();
   }

   /**
    * Establece el valor de la etiqueta <code>/jasperReport/queryString</code>
    * @param query
    * @param orderByFields
    */
   public void setQueryStringReport(String query, String orderByFields){
	   setQueryStringReport(query, orderByFields, null);
   }

   /**
    * Obtiene el valor de la etiqueta <code>/jasperReport/queryString</code>
    */
   public String getQueryStringReport(){
	   Element queryStringElement = getQueryStringNode();

		if (queryStringElement != null) {
			return queryStringElement.getTextTrim();
		}
		return null;
   }
}
