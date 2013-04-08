/*
 * Created on 09-jun-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispacpublicador.business.util.xml;

import ieci.tdw.ispac.ispacpublicador.business.exceptions.PublisherException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author juanin
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class XMLDocUtil
{

	/**
	 *  
	 */
	public XMLDocUtil()
	{
	}

	/**
	 * Construye un documento XML vacío
	 * 
	 * @return <code>true</code> si todo va bien
	 */
	public static Document newDocument() throws PublisherException
	{
		try
		{
			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			return docBuilder.newDocument();

		} catch (ParserConfigurationException e)
		{
			throw new PublisherException("XMLDocUtil::newDocument error", e);
		}
	}
	
	public static Document newDocument(InputStream in) throws PublisherException
	{
		return newDocument(new StreamSource(in));
	}
	
	public static Document newDocument(StreamSource ss) throws PublisherException
	{
		Document doc = newDocument();
		try
		{
			//TransformerFactory tFactory = TransformerFactory.newInstance();
		    TransformerFactory tFactory= new TransformerFactoryImpl();
			Transformer transformer = tFactory.newTransformer();

			transformer.transform(ss, new DOMResult(doc));
			doc.normalize();
			return doc;
		} catch (TransformerException e)
		{
			throw new PublisherException(e);
		}
	}

	/**
	 * Construye el documento XML a partir del string que se pasa
	 * 
	 * @param sXml string que representa el documentop XML que se quiere cargar
	 * @return <code>true</code> si todo va bien
	 */
	public static Document newDocument(String sXml) throws PublisherException
	{
		if (sXml == null)
			return newDocument();

		return newDocument(new StreamSource(new StringReader(sXml)));
	}

	/**
	 * Construye el documento XML buscando el recurso adecuado ya sea en el
	 * paquete de la clase suministrada como en el resto del sistema.
	 * 
	 * @param resourceclass clase en cuyo paquete reside el archivo xml. Si es
	 *            null se busca también en todo el sistema
	 * @param fileNameXML string con el nombre del archivo xml
	 * @return <code>true</code> si todo va bien
	 */
	public static Document newDocumentResource(Class resourceclass, String fileNameXML)
			throws PublisherException
	{
		try
		{
			if (fileNameXML == null)
				throw new PublisherException("No se ha proporcionado el documento XML");

			InputStream istream = null;
			// Obtiene la carpeta donde está el fichero de configuración
			if (resourceclass != null)
				istream = resourceclass.getClassLoader().getResourceAsStream(fileNameXML);

			// Lo busca en todo el classpath
			if (istream == null)
				istream = ClassLoader.getSystemResourceAsStream(fileNameXML);

			if (istream == null)
				throw new PublisherException("No existe el documento XML");

			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(istream);
			doc.normalize();
			return doc;
		} catch (Exception e)
		{
			throw new PublisherException(e);
		}
	}

	/**
	 * Obtiene un string con la representación del documento XML
	 * 
	 * @return Un string formateado con la representación del documento, o
	 *         <code>null</code> si hay algún error.
	 */
	public static String toString(Document doc) throws PublisherException
	{
		return XMLDocUtil.toString((Node) doc);
	}

	/**
	 * Obtiene un string con la representación del nodo XML
	 * 
	 * @return Un string formateado con la representación del nodo, o
	 *         <code>null</code> si hay algún error.
	 */
	public static String toString(Node node) throws PublisherException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try
		{
			XMLDocUtil.serialize(os, node);
			return os.toString("utf-8");

		} catch (UnsupportedEncodingException e)
		{
			throw new PublisherException(e);
		}
	}

	/**
	 * Serializa un nodo xml en un outputstream
	 * 
	 * @param out OutputStream donde es serializado el nodo xml
	 */
	public static void serialize(OutputStream out, Node node) throws PublisherException
	{
		try
		{
			//TransformerFactory tFactory = TransformerFactory.newInstance();
		    TransformerFactory tFactory= new TransformerFactoryImpl();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");

			DOMSource domSource = new DOMSource(node);
			StreamResult result = new StreamResult(out);
			transformer.transform(domSource, result);
		   
		} catch (TransformerException e)
		{
			throw new PublisherException(e);
		}
	}

	
	public static String getNodeValue(Node node)
	{
		int type;
		
		NodeList nodeList = node.getChildNodes();
		String strValue = new String();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
		  node = nodeList.item(i);
		  type = node.getNodeType();
		  if (type == Node.CDATA_SECTION_NODE || type == Node.TEXT_NODE)
			strValue += node.getNodeValue();
		}
		return strValue;
	}

	public static String getNodeAttribute(Node node, String attribute)
	{
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++)
		{
			Node attr = attrs.item(i);
			String name = attr.getNodeName();
			String value = attr.getNodeValue();
			if (name.equals(attribute))
			{
				return value;
			}
		}
		return null;
	}
}