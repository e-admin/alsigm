package ieci.tdw.ispac.ispaclib.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

/**
 * A Facade that encapsulates the complexities of the XML DOM API, providing a
 * simple get/set interface for persisting information in XML using XPath.
 */
public class XmlFacade {
    
    /** Logger de la clase. */
    protected static final Logger logger = Logger.getLogger(XmlFacade.class);
    
    /** Codificación del XML por defecto. */
    protected static final String DEFAULT_ENCODING = "ISO-8859-1";

    /** The parsed XML tree. */
    protected final Document _doc;

    /** Codificación del XML. */
    private String encoding = DEFAULT_ENCODING;
    
    
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
     * Loads the specified XML as the new document tree, discarding the previous
     * tree.
     */
    public XmlFacade(String xmlText, String encoding) {
        this(xmlText);
        this.encoding = encoding;
    }
    
    /**
     * Loads the specified XML as the new document tree, discarding the previous
     * tree.
     */
    public XmlFacade(InputStream is) {
        try {
            // Parse the XML document
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(is));
            _doc = parser.getDocument();
        }
        catch (Exception e) {
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

    public static Properties getAttributes(Node node)
    {
        Properties atts = new Properties();
        
        if (node.hasAttributes())
        {
        	NamedNodeMap attributes = node.getAttributes();
        	for (int i = 0; i < attributes.getLength(); i++)
        	{
        		Node attNode = attributes.item(i);
                if (attNode != null) 
                {
                    atts.setProperty(attNode.getNodeName(), attNode.getNodeValue());
                }
        	}
        }
        
        return atts;
    }

    public static Properties getAttributes(Node node, String xpath)
    {
        Properties atts = new Properties();
        
        node = getSingleNode(node, xpath);
        if (node.hasAttributes())
        {
        	NamedNodeMap attributes = node.getAttributes();
        	for (int i = 0; i < attributes.getLength(); i++)
        	{
        		Node attNode = attributes.item(i);
                if (attNode != null) 
                {
                    atts.setProperty(attNode.getNodeName(), attNode.getNodeValue());
                }
        	}
        }
        
        return atts;
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
    
    //// Delete operations ////
    
    /**
     * Deletes the specified node.
     */
    protected void delete(Node nd) throws Exception {
        if (nd != null) {
            if (nd.getNodeType() == Node.ELEMENT_NODE) {
                nd.getParentNode().removeChild(nd);
            } else {
                Attr a = (Attr) nd;
                a.getOwnerElement().removeAttributeNode(a);
            }
        }
    }
    
    /**
     * Deletes the value at the specified path.
     */
    public void delete(String xpath) {
        try {
            delete(XPathAPI.selectSingleNode(_doc, xpath));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error borrando usando XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    
    //// Write operations ////
    
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
    
    /**
     * Sets the value of the specified node.
     */
    protected void setValue(Node nd, String value) throws Exception {
        // Node is an element - replace its contents
        if (nd.getNodeType() == Node.ELEMENT_NODE) {
            for (int i = 0; i < nd.getChildNodes().getLength(); i++) {
                nd.removeChild(nd.getChildNodes().item(i));
            }
            nd.appendChild(_doc.createTextNode(value));
        }
        
        // Node is a non-element - set its value directly
        else
            nd.setNodeValue(value);
    }
    
    /**
     * Sets the value at the specified path.
     */
    public void set(String xpath, boolean value) {
        set(xpath, Boolean.toString(value));
    }
    
    /**
     * Sets the value at the specified path.
     */
    public void set(String xpath, int value) {
        set(xpath, Integer.toString(value));
    }
    
    /**
     * Sets the value at the specified path.
     */
    public void set(String xpath, java.lang.Object value) {
        if (value == null)
            delete(xpath);
        else
            set(xpath, value.toString());
    }
    
    /**
     * Sets the value at the specified path.
     */
    public void set(String xpath, String value) {
        try {
            setValue(getNode(xpath), value);
            //fireXmlChanged();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al escribir con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    
    /**
     * Sets the values matching the specified path.
     */
    public void setList(String xpath, Collection c) {
        try {
            // Make sure there is at least one matching node
            if (c.size() > 0)
                getNode(xpath);
            
            // Find existing nodes matching XPath query
            Node prevNode = null;
            NodeIterator i = XPathAPI.selectNodeIterator(_doc, xpath);
            for (Iterator j = c.iterator(); j.hasNext();) {
                // Get the next value to be set
                Object v = j.next();
                String value = v != null ? v.toString() : null;
                
                // A node exists in the current list position - change it
                Node nd = i.nextNode();
                if (nd != null)
                    setValue(nd, value);
                
                // The list is longer than there are existing nodes
                else {
                    // Element
                    if (prevNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element el = _doc.createElement(prevNode.getNodeName());
                        nd = prevNode.getParentNode().appendChild(el);
                    }
                    
                    // Attribute
                    else {
                        // Copy the previous node's owner element / parent node
                        Element owner = ((Attr) prevNode).getOwnerElement();
                        Element copy = _doc.createElement(owner.getNodeName());
                        owner = (Element) owner.getParentNode().appendChild(
                                copy);
                        
                        // Add a copy of the previous node and set its value
                        nd = _doc.createAttribute(((Attr) prevNode).getName());
                        owner.setAttributeNode((Attr) nd);
                    }
                    
                    // Set the value of the new node
                    setValue(nd, value);
                }
                
                // Keep the current node for copying later
                prevNode = nd;
            }
            
            // Remove redundant nodes when existing list is longer
            List redundantNodes = new Vector();
            for (Node nd = i.nextNode(); nd != null; nd = i.nextNode()) {
                redundantNodes.add(nd);
            }
            for (Iterator r = redundantNodes.iterator(); r.hasNext();) {
                delete((Node) r.next());
            }
            
            // Clean up parent nodes that contain no other information
            String voidParentsPath = xpath.substring(0, xpath.lastIndexOf('/'))
            + "[not(@*|node())]";
            NodeIterator k = XPathAPI.selectNodeIterator(_doc, voidParentsPath);
            for (Node n = k.nextNode(); n != null; n = k.nextNode()) {
                delete(n);
            }
            //fireXmlChanged();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error escribiendo con XPath: "
                    + xpath + "\n" + e.getClass().getName() + ": "
                    + e.getMessage());
        }
    }
    
    /**
     * Sets a key-value pair list matching the specified paths to the contents
     * of the specified Map. If you want to maintain the order of the keys, use
     * an ordered Map implementation, such as TreeMap or LinkedHashMap.
     * 
     * @param xpath
     *            the absolute path to the element or elements that contain the
     *            key-value pairs.
     * @param keyPath
     *            the relative path from the element(s) specified by xpath to
     *            the element/attribute containing each key.
     * @param valuePath
     *            the relative path from the element(s) specified by xpath to
     *            the element/attribute containing each value.
     */
    public void setMap(String xpath, String keyPath, String valuePath, Map map) {
        try {
            // Delete existing nodes
            List redundantNodes = new Vector();
            NodeIterator n = XPathAPI.selectNodeIterator(_doc, xpath);
            for (Node nd = n.nextNode(); nd != null; nd = n.nextNode()) {
                redundantNodes.add(nd);
            }
            for (Iterator r = redundantNodes.iterator(); r.hasNext();) {
                delete((Node) r.next());
            }
            
            // Set map values
            if (valuePath == null || valuePath.equals(""))
                valuePath = ".";
            for (Iterator i = map.keySet().iterator(); i.hasNext();) {
                String key = (String) i.next();
                set(xpath + "[" + keyPath + " = '" + key + "']/" + valuePath,
                        map.get(key));
                //fireXmlChanged();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error escribiendo con XPath: "
                    + xpath + "{ " + keyPath + " => " + valuePath + " }\n"
                    + e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    public String toString() {
    	
    	StringBuffer xml = new StringBuffer();
    	
        try {
            OutputFormat format = new OutputFormat(_doc,encoding,false);
            format.setStandalone(false);
            format.setIndent(4);
            format.setLineWidth(0);
            StringWriter stringOut = new StringWriter();
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.serialize(_doc.getDocumentElement());
            xml.append(stringOut.toString());
        } catch (IOException e) {
        	logger.warn("No se ha podido serializar el documento XML", e);
        }
        
        return xml.toString();
    }

    public static String toString(Element element) {
    	
    	StringBuffer xml = new StringBuffer();
    	
    	if (element != null) {
	        try {
	            OutputFormat outputFormat = new OutputFormat(element.getOwnerDocument(), DEFAULT_ENCODING, false);
	            outputFormat.setOmitXMLDeclaration(true);
	            outputFormat.setStandalone(false);
	            outputFormat.setIndent(0);
	            outputFormat.setLineWidth(0);
	            outputFormat.setPreserveSpace(false);
	            
	            StringWriter stringWriter = new StringWriter();
	            XMLSerializer serial = new XMLSerializer(stringWriter, outputFormat);
	            serial.serialize(element);
	            xml.append(stringWriter.toString());
	        } catch (IOException e) {
	        	logger.warn("No se ha podido serializar el nodo", e);
	        }
    	}
        
        return xml.toString();
    }

}

