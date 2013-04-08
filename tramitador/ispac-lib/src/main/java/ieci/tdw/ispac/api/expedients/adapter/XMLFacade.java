package ieci.tdw.ispac.api.expedients.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;


/**
 * A Facade that encapsulates the complexities of the XML DOM API, providing a
 * simple get/set interface for persisting information in XML using XPath.
 */
public class XMLFacade 
{
    /** Logger de la clase. */
    protected static final Logger logger = Logger.getLogger(XMLFacade.class);
    
    /** Codificación del XML. */
    protected static final String ENCODING = "ISO-8859-1";

    /** The parsed XML tree. */
    protected final Document _doc;
    
    
    
    /**
     * Loads the specified XML as the new document tree, discarding the previous
     * tree.
     */
    public XMLFacade(String xmlText) 
    {
        try 
        {
            // Parse the XML document
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(new StringReader(xmlText)));
            _doc = parser.getDocument();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Error cargando XML: \n"
                    + e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * Loads the specified XML as the new document tree, discarding the previous
     * tree.
     */
    public XMLFacade(InputStream is) 
    {
        try 
        {
            // Parse the XML document
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(is));
            _doc = parser.getDocument();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Error cargando XML: \n"
                    + e.getClass().getName() + ": " + e.getMessage());
        }
    }


    /**
     * @return the value of the specified Node.
     */
    protected static String getValue(Node nd) throws Exception
    {
        if (nd == null)
            return null;
        
        // Element
        if (nd.getNodeType() == Node.ELEMENT_NODE) {
            String flattenedValue = "";
            nd.normalize();
            NodeIterator i = XPathAPI.selectNodeIterator(nd,
            "descendant::text()");
            for (Node t = i.nextNode(); t != null; t = i.nextNode()) {
                flattenedValue += t.getNodeValue();
            }
            return flattenedValue;
        }
        
        // Non-element
        else
            return nd.getNodeValue();
    }
    
    protected static String[] getValues(NodeIterator nodeIt) throws Exception {
    	List values = new ArrayList();
    	
    	if (nodeIt != null) {
    		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt.nextNode()) {
    			values.add(getValue(node));
    		}
    	}
    	
    	return (String[]) values.toArray(new String[values.size()]);
    }


    /**
     * @return the value of the specified Node.
     */
    public static String getNodeValue(Node nd) 
    {
        if ( (nd == null) || !nd.hasChildNodes() )
            return null;
        
        return nd.getFirstChild().getNodeValue();
    }
    
    
    public static String getAttributeValue(Node node, String attName)
    {
        String attValue = null;
        
        if (node.hasAttributes())
        {
            Node attNode = node.getAttributes().getNamedItem(attName);
            if (attNode != null)
                attValue = attNode.getNodeValue();
        }
        
        return attValue;
    }


    
    /**
     * @return the first value found at the specified path, or null if the path
     *         was not found.
     */
    public String get(String xpath) 
    {
        return get(_doc, xpath);
    }

    public String[] getValues(String xpath) 
    {
        return getValues(_doc, xpath);
    }



    /**
     * @return the first value found at the specified path, or null if the path
     *         was not found.
     */
    public static String get(Node node, String xpath)
    {
        try {
            return getValue(XPathAPI.selectSingleNode(node, xpath));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }

    public static String[] getValues(Node node, String xpath)
    {
        try {
            return getValues(XPathAPI.selectNodeIterator(node, xpath));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }


    /**
     * @return the first value found at the specified path, or null if the path
     *         was not found.
     */
    public Node getSingleNode(String xpath) 
    {
        return getSingleNode(_doc, xpath);
    }

    /**
     * @return the first value found at the specified path, or null if the path
     *         was not found.
     */
    public static Node getSingleNode(Node node, String xpath)
    {
        try {
            return XPathAPI.selectSingleNode(node, xpath);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    

    /**
     * @return a List of all values found at the specified path.
     */
    public NodeIterator getNodeIterator(String xpath) 
    {
        return getNodeIterator(_doc, xpath);
    }

    /**
     * @return a List of all values found at the specified path.
     */
    public static NodeIterator getNodeIterator(Node node, String xpath) 
    {
        try 
        {
            return XPathAPI.selectNodeIterator(node, xpath);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    
    
    public String toString() 
    {
        String xml = null;
        
        try 
        {
            OutputFormat format = new OutputFormat(_doc, ENCODING ,false);
            format.setStandalone(false);
            format.setIndent(4);
            format.setLineWidth(0);
            StringWriter stringOut = new StringWriter();
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.serialize(_doc.getDocumentElement());
            xml = stringOut.toString();
        }
        catch (IOException e)
        {
            logger.warn("No se ha podido serializar el documento XML", e);
        }
        
        return xml;
    }
    
}