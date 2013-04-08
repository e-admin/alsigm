/*
 * Created on 15-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.attribute;

import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacpublicador.business.util.xml.Context;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


/**
 * Contexto que almacena una serie de atributos.
 * Estructura del XML:
 * <?xml version='1.0' encoding='ISO-8859-1'?>
 * <attributes>
 * 	<attribute name='at01'>5</attribute>
 * 	<attribute name='at02'>20</attribute>
 *   ...
 * </attributes>
 * 
 * @author Ildefonso Noreña
 *
 */
public class AttributeContext extends Context{

    /**
     * nombre del nodo raíz del contexto de atributos
     */
    private final static String ATT_CTX_ROOT = "attributes";
    /**
     * nombre de cada uno de los nodos que contendrán información de atributos
     */
    public final static String NAME_NODE = "attribute";
    
    /**
     * en este mapa se almacenarán los nombres de atributos y los valores de los mismos, 
     */
    private Map attributes = null;
    /**
     * @param xml
     */
    public AttributeContext(String xml) {
        super(xml, ATT_CTX_ROOT);
    }
    
    
    public String getAttribute(String attribute){
        getProperties();
        return (attributes == null) ? null : (String)attributes.get(attribute);
    }

    
	public Map getProperties(){
	    if (facade == null)
	        return new LinkedHashMap();
	    if (attributes == null){
		    attributes = new LinkedHashMap();
		    NodeIterator it = facade.getNodeIterator(root+"/"+NAME_NODE);
		    Node node = it.nextNode();
		    if (node != null)
			    do{
			        attributes.put(
			        		getNodeAttribute(node, "name"),
			        		XmlFacade.getNodeValue(node));
			        node = it.nextNode();
			    }while(node != null);
	    }

	    return attributes; 
	}    
    
	private static String getNodeAttribute(Node node, String attribute)
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
	
    protected int getInt(String property) {
        // TODO Auto-generated method stub
        return 0;
    }

    protected Date getDate(String property) {
        // TODO Auto-generated method stub
        return null;
    }

    protected Timestamp getTimestamp(String property) {
        // TODO Auto-generated method stub
        return null;
    }

    protected String get(String property) {
        // TODO Auto-generated method stub
        return null;
    }	
}
