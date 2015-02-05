package ieci.tdw.ispac.api.expedients.adapter;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Clase que contiene la información de mapeos de campos y entidades
 * entre las tablas y el mensaje XML.
 */
public class Mappings
{

    /** Logger de la clase. */
    private static final Logger logger = Logger.getLogger(Mappings.class);    
    
    /** Nodo XML con la información de los mapeos. */
    private Node node = null;
    
    /**
     * Constructor.
     */
    public Mappings()
    {
        
    }

    public Node getNode()
    {
        return node;
    }

    public void setNode(Node node)
    {
        this.node = node;
    }
        
    /**
     * Representación de la instancia en formato XML.
     */
    public String toString()
    {
        StringWriter sw = new StringWriter();
        
        try 
        {
            OutputFormat of = new OutputFormat(Method.XML, "ISO-8859-1" ,false);
            of.setOmitComments(true);
            of.setOmitDocumentType(true);
            of.setOmitXMLDeclaration(true);
            of.setIndent(2);
            of.setLineWidth(0);

            XMLSerializer serial = new XMLSerializer(sw, of);
            serial.serialize((Element)node);
        }
        catch (IOException e)
        {
            logger.warn("No se ha podido serializar el documento XML", e);
        }
        
        return sw.toString();
    }
    
}