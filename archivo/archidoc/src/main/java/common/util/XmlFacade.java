package common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import common.exceptions.ConfigException;

import xml.config.ConfiguracionArchivoManager;

/**
 * Utilidad que encapsula la complejidad del API XML DOM, proporcionando una
 * interfaz simple para manejar documentos XML utilizando XPath.
 */
public class XmlFacade {
	Logger logger = Logger.getLogger(this.getClass());

	/** Documento XML. */
	protected Document document;

	/**
	 * Constructor.
	 *
	 * @param xml
	 *            Cadena que contiene el documento XML.
	 */
	public XmlFacade(String xml) {
		try {
			DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(xml
					.trim())));
		} catch (Exception e) {
			throw new IllegalArgumentException("Error cargando XML: "
					+ e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public XmlFacade(String xml, String xsd) throws IllegalArgumentException {

		// Reader xsdReader;
		InputSource isXml = new InputSource(new StringReader(xml.trim()));

		InputStream inputStreamXsd;
		try {
			inputStreamXsd = ConfiguracionArchivoManager
					.getInstance().getFichero(xsd);
		} catch (FactoryConfigurationError e) {
			throw new IllegalArgumentException(
					"Error cargando XML FactoryConfigurationException: "
							+ e.getClass().getName() + ": " + e.getMessage());

		} catch (ConfigException e) {
			logger.error(e);
			throw new IllegalArgumentException(
					"Error cargando XML FactoryConfigurationException: "
							+ e.getClass().getName() + ": " + e.getMessage());


		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new IllegalArgumentException(
					"Error cargando XML FactoryConfigurationException: "
							+ e.getClass().getName() + ": " + e.getMessage());
		}

		// XmlFacade.class.getClassLoader().getResourceAsStream("/xsd/" +
		// xsd);

		InputSource isXsd = new InputSource(inputStreamXsd);

		new XmlFacade(isXml, isXsd);
	}


	public XmlFacade(InputSource isXml, InputSource isXsd) throws IllegalArgumentException {
		boolean NAME_SPACE_AWARE = true;
		boolean VALIDATING = true;
		String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
		String SCHEMA_LANGUAGE_VAL = "http://www.w3.org/2001/XMLSchema";
		String SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();

			factory.setNamespaceAware(NAME_SPACE_AWARE);
			factory.setValidating(VALIDATING);

			SAXParser parser = factory.newSAXParser();

			parser.setProperty(SCHEMA_LANGUAGE, SCHEMA_LANGUAGE_VAL);
			parser.setProperty(SCHEMA_SOURCE, isXsd);

			DefaultHandler handler = new XmlDefaultHandler();
			parser.parse(isXml, handler);
		} catch (FactoryConfigurationError e) {
			throw new IllegalArgumentException(
					"Error cargando XML FactoryConfigurationException: "
							+ e.getClass().getName() + ": " + e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new IllegalArgumentException(
					"Error cargando XML ParserConfigurationException: "
							+ e.getClass().getName() + ": " + e.getMessage());
		} catch (SAXException e) {
			throw new IllegalArgumentException(
					"Error cargando XML SAXException: "
							+ e.getClass().getName() + ": " + e.getMessage());
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Error cargando XML IOException: " + e.getClass().getName()
							+ ": " + e.getMessage());
		}
	}

	/**
	 * Constructor.
	 *
	 * @param is
	 *            Flujo de entrada del fichero XML.
	 */
	public XmlFacade(InputStream is) {
		try {
			DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(is);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error cargando XML: "
					+ e.getClass().getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Obtiene el valor XPath determinado.
	 *
	 * @param xpath
	 *            Ruta XPath.
	 * @return El primer valor encontrado en la ruta XPath.
	 */
	public String get(String xpath) {
		String value = null;

		try {
			Node nd = XPathAPI.selectSingleNode(document, xpath);
			if (nd != null)
				value = nd.getNodeValue();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return value;
	}

	/**
	 * Obtiene el valor XPath determinado.
	 *
	 * @param node
	 *            Nodo del árbol XML.
	 * @param xpath
	 *            Ruta XPath.
	 * @return El primer valor encontrado en la ruta XPath.
	 */
	public static String get(Node node, String xpath) {
		String value = null;

		try {
			Node nd = XPathAPI.selectSingleNode(node, xpath);
			if (nd != null)
				value = nd.getNodeValue();
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return value;
	}

	/**
	 * Obtiene el nodo del XPath determinado.
	 *
	 * @param xpath
	 *            Ruta XPath.
	 * @return Nodo.
	 */
	public Node getNode(String xpath) {
		Node nd = null;

		try {
			nd = XPathAPI.selectSingleNode(document, xpath);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return nd;
	}

	/**
	 * Obtiene el nodo del XPath determinado.
	 *
	 * @param node
	 *            Nodo del árbol XML.
	 * @param xpath
	 *            Ruta XPath.
	 * @return Nodo.
	 */
	public static Node getNode(Node node, String xpath) {
		Node nd = null;

		try {
			nd = XPathAPI.selectSingleNode(node, xpath);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return nd;
	}

	/**
	 * Obtiene los nodos del XPath determinado.
	 *
	 * @param xpath
	 *            Ruta XPath.
	 * @return Lista de nodos.
	 */
	public NodeIterator getNodeIterator(String xpath) {
		NodeIterator it = null;

		try {
			it = XPathAPI.selectNodeIterator(document, xpath);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return it;
	}

	/**
	 * Obtiene los nodos del XPath determinado.
	 *
	 * @param node
	 *            Nodo del árbol XML.
	 * @param xpath
	 *            Ruta XPath.
	 * @return Lista de nodos.
	 */
	public static NodeIterator getNodeIterator(Node node, String xpath) {
		NodeIterator it = null;

		try {
			it = XPathAPI.selectNodeIterator(node, xpath);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error recuperando con XPath: "
					+ xpath + " - " + e.getClass().getName() + ": "
					+ e.getMessage());
		}

		return it;
	}

	/**
	 * Obtiene el documento XML.
	 *
	 * @return Documento XML.
	 */
	public Document getDocument() {
		return document;
	}

	public class XmlDefaultHandler extends DefaultHandler {
		/**
		 * @see org.xml.sax.ErrorHandler#error(SAXParseException)
		 */
		public void error(SAXParseException spe) throws SAXException {
			throw spe;
		}

		/**
		 * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
		 */
		public void fatalError(SAXParseException spe) throws SAXException {
			throw spe;
		}
	}

}
