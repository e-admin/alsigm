package ieci.tdw.ispac.ispaclib.XMLUtil;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
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
	public static Document newDocument() throws ISPACException
	{
		try
		{
			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			return docBuilder.newDocument();

		} catch (ParserConfigurationException e)
		{
			throw new ISPACException("XMLDocUtil::newDocument error", e);
		}
	}



    //Usando esta forma de parsear el XML, cuando el contenido de un nodo esta entre un
    //CDATA y contiene saltos de linea, al obtener el valor solo retornaba la primera linea
	/*
	public static Document newDocument(StreamSource ss) throws ISPACException
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
			throw new ISPACException(e);
		}
	}


    public static Document newDocument(String sXml) throws ISPACException
    {
        if (sXml == null)
            return newDocument();

        return newDocument(new StreamSource(new StringReader(sXml)));
    }
    public static Document newDocument(InputStream in) throws ISPACException
    {
        return newDocument(new StreamSource(in));
    }

*/
    /**
     * Construye el documento XML a partir del string que se pasa
     *
     * @param sXml string que representa el documentop XML que se quiere cargar
     * @return <code>true</code> si todo va bien
     */
    public static Document newDocument(String sXml) throws ISPACException
    {
        if (sXml == null)
            return newDocument();

        //return newDocument(new StreamSource(new StringReader(sXml)));
        return newDocument(new ByteArrayInputStream(sXml.getBytes()));
    }
    
    public static Document newDocument(StreamSource ss) throws ISPACException{
        return newDocument(ss.getInputStream());
    }
    
    public static Document newDocument(InputStream in) throws ISPACException
    {
        //return newDocument(new StreamSource(in));
        Document doc = null;
        try {
            DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
            // Create the builder and parse the fileww
            doc = factory.newDocumentBuilder().parse(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ISPACException(e);
        }
        if (doc != null)
            return doc;
        return newDocument();
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
			throws ISPACException
	{
		try
		{
			if (fileNameXML == null)
				throw new ISPACException("No se ha proporcionado el documento XML");

			InputStream istream = null;
			// Obtiene la carpeta donde está el fichero de configuración
			if (resourceclass != null)
				istream = resourceclass.getClassLoader().getResourceAsStream(fileNameXML);

			// Lo busca en todo el classpath
			if (istream == null)
				istream = ClassLoader.getSystemResourceAsStream(fileNameXML);

			if (istream == null)
				throw new ISPACException("No existe el documento XML");

			//DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(istream);
			doc.normalize();
			return doc;
		} catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}

	/**
	 * Obtiene un string con la representación del documento XML
	 *
	 * @return Un string formateado con la representación del documento, o
	 *         <code>null</code> si hay algún error.
	 */
	public static String toString(Document doc) throws ISPACException
	{
		return XMLDocUtil.toString((Node) doc);
	}

	/**
	 * Obtiene un string con la representación del nodo XML
	 *
	 * @return Un string formateado con la representación del nodo, o
	 *         <code>null</code> si hay algún error.
	 */
	public static String toString(Node node) throws ISPACException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
			//Modificamos la codificacion a ISO-8859-1. 
		    //Origen: Error al validar usuarios contra Active Directory cuando en el CN existen caracteres especiales
		    XMLDocUtil.serialize(os, node);
			//return os.toString("utf-8");
			return os.toString();
			


	}

	/**
	 * Serializa un nodo xml en un outputstream
	 *
	 * @param out OutputStream donde es serializado el nodo xml
	 */
	public static void serialize(OutputStream out, Node node) throws ISPACException
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
			throw new ISPACException(e);
		}
	}


	public static String getNodeValue(Node node)
	{
		int type;

		NodeList nodeList = node.getChildNodes();
		StringBuffer strValue = new StringBuffer();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
		  node = nodeList.item(i);
		  type = node.getNodeType();
		  if (type == Node.CDATA_SECTION_NODE || type == Node.TEXT_NODE)
			strValue.append(node.getNodeValue());
		}
		return strValue.toString();
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