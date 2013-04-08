package ieci.tecdoc.sgm.mensajes_cortos.helpers;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

/**
 * Fachada que encapsula la funcionalidad del API DOM.
 */
public class XmlFacade {
    
    /** Logger de la clase. */
    protected static final Logger logger = Logger.getLogger(XmlFacade.class);
    
    /** The parsed XML tree. */
    protected final Document _doc;

    
    /**
     * Loads the specified XML as the new document tree, discarding the previous
     * tree.
     */
    public XmlFacade(String xmlText) {
        try {
            // Parse the XML document
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(new StringReader(xmlText)));
            _doc = parser.getDocument();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error cargando XML: \n"
                    + e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * @return the value of the specified Node.
     */
    protected static String getValue(Node nd) throws Exception {
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
    
    /**
     * @return the value of the specified Node.
     */
    public static String getNodeValue(Node nd) {
    	
    	StringBuffer value = new StringBuffer();
    	
        if (nd != null) {
    		NodeList children = nd.getChildNodes();
    		for (int i = 0; i < children.getLength(); i++) {
    			Node child = children.item(i);
    			if (child.getNodeType() == Node.ELEMENT_NODE) {
    				value.append(toString((Element)child));
    			} else {
    				value.append(child.getNodeValue());
    			}
    		}
        }
        
        return value.toString().trim();
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
    public String get(String xpath) {
        try {
            return getValue(XPathAPI.selectSingleNode(_doc, xpath));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }

    public static String get(Node node, String xpath) {
        try {
            return getValue(XPathAPI.selectSingleNode(node, xpath));
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

    /**
     * @return a List of all values found at the specified path.
     */
    public List getList(String xpath) {
        try {
            List list = new Vector();
            NodeIterator i = XPathAPI.selectNodeIterator(_doc, xpath);
            for (Node nd = i.nextNode(); nd != null; nd = i.nextNode()) {
                list.add(getValue(nd));
            }
            return list;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error recuperando con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    
    /**
     * @return an ordered Map of keys to values rooted at the specified path,
     *         and defined by the specified relative key and value paths.
     */
    public Map getMap(String xpath, String keyPath, String valuePath) {
        Map map = new LinkedHashMap();
        Object[] keys = getList(xpath + "/" + keyPath).toArray();
        Object[] values = getList(xpath + "/" + valuePath).toArray();
        for (int i = 0, length = keys.length; i < length; i++) {
            String key = (String) keys[i];
            if (key != null)
                map.put(key, values[i]);
        }
        return map;
    }
    
    
    /**
     * Lazily creates nodes based on the specified path. The implementation of
     * this method determines the extent that this class can simulate read-write
     * XPath support.
     * 
     * @return a new node inserted into the doc at the specified path, or the
     *         node that already existed there.
     */
    protected Node getNode(String xpath) {
        try {
            // Find the closest existing ancestor
            Node nd = null;
            String validPath = xpath;
            for (int endOfValidPath = xpath.length(); endOfValidPath > 0; endOfValidPath = validPath
            .lastIndexOf('/')) {
                validPath = validPath.substring(0, endOfValidPath);
                nd = XPathAPI.selectSingleNode(_doc, validPath);
                if (nd != null)
                    break;
            }
            
            // Create the remainder of the path
            for (StringTokenizer tokens = new StringTokenizer(xpath
                    .substring(validPath.length()), "/"); tokens
                    .hasMoreTokens();) {
                // References to self can be ignored
                String ndName = tokens.nextToken();
                if (ndName.equals("."))
                    continue;
                
                // Find predicate (supports only single condition)
                String predicate = null;
                int predicatePos = ndName.indexOf('[');
                StringTokenizer predTokens = null;
                if (predicatePos > 0) {
                    predicate = ndName.substring(predicatePos + 1, ndName
                            .indexOf(']'));
                    predTokens = new StringTokenizer(predicate,
                    " \t\n\r\f=\'\"");
                    ndName = ndName.substring(0, predicatePos);
                }
                
                // Attribute
                Node newNode;
                if (ndName.indexOf('@') == 0) {
                    String attrName = ndName.substring(1);
                    newNode = _doc.createAttribute(attrName);
                    ((Element) nd).setAttributeNode((Attr) newNode);
                }
                
                // Element
                else {
                    newNode = _doc.createElement(ndName);
                    nd.appendChild(newNode);
                }
                
                // Update path to existing ancestor
                validPath += "/" + ndName;
                nd = newNode;
                
                // Process predicates
                if (predTokens != null) {
                    // Find the predicate name and value
                    String predNdName = "";
                    String predNdValue = "";
                    try {
                        while (predNdName.length() < 1) {
                            predNdName = predTokens.nextToken();
                        }
                        while (predNdValue.length() < 1) {
                            predNdValue = predTokens.nextToken();
                        }
                    } catch (NoSuchElementException e) {
                        throw new IllegalArgumentException(
                                "Esta implementación no soporta el formato:  "
                                + predicate);
                    }
                    
                    // Element
                    if (predNdName.indexOf('@') < 0) {
                        Element predicateElement = _doc
                        .createElement(predNdName);
                        Node predicateTextNode = _doc
                        .createTextNode(predNdValue);
                        predicateElement.appendChild(predicateTextNode);
                        nd.appendChild(predicateElement);
                    }
                    
                    // Attribute
                    else
                        ((Element) nd).setAttribute(predNdName.substring(1),
                                predNdValue);
                }
            }
            
            return nd;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Error recuperando/creando con XPath: " + xpath + "\n"
                    + e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    public String toString() {
    	
    	StringWriter stringWriter = new StringWriter();
    	
        try {
        	Source source = new DOMSource(_doc);
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
        } catch (Exception e) {
        	logger.warn("No se ha podido serializar el documento XML", e);
        }
        
        return stringWriter.toString();
    }

    public static String toString(Element element) {
    	
    	StringWriter stringWriter = new StringWriter();
    	
    	if (element != null) {
	        try {
	        	Source source = new DOMSource(element);
	            Result result = new StreamResult(stringWriter);
	            TransformerFactory factory = TransformerFactory.newInstance();
	            Transformer transformer = factory.newTransformer();
	            transformer.transform(source, result);
	        } catch (Exception e) {
	        	logger.warn("No se ha podido serializar el nodo", e);
	        }
    	}
        
        return stringWriter.toString();
    }

}

