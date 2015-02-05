package ieci.tdw.ispac.api.expedients.adapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Clase que contiene la información de mapeo de las tablas
 * para los datos específicos de un procedimiento.
 */
public class Procedure extends XMLFacade
{

    /** Lista de tablas. */
    private HashMap tables = null;

    /**
     * Constructor.
     * @param xml Cadena que contiene el documento XML con los mapeos.
     */
    public Procedure(String xml)
    {
        super(xml);
        
        // Iniciar la lista de tablas
        tables = new LinkedHashMap();

        // Leer el documento XML
        loadInfo();
    }

    /**
     * Constructor.
     * @param is InputStream que contiene el documento XML con los mapeos.
     */
    public Procedure(InputStream is)
    {
        super(is);
        
        // Iniciar la lista de tablas
        tables = new LinkedHashMap();

        // Leer el documento XML
        loadInfo();
    }

    /**
     * Devuelve los nombres de las tablas del procedimiento.
     * @return Tablas del procedimiento.
     */
    public Iterator getTableNames()
    {
        return tables.keySet().iterator();
    }
    
    
    /**
     * Devuelve la información de una tabla a partir de su nombre.
     * @param name Nombre de la tabla.
     * @return Tabla del procedimiento.
     */
    public Table getTable(String name)
    {
        return (Table) tables.get(name);
    }

    /**
     * Carga la información de mapeo de las tablas.
     */
    private void loadInfo()
    {
        Table table = null;
        String name;

        logger.info("Cargando la informaci\363n de mapeo de las tablas");
        
        // Parsear la configuración de las tablas
        NodeIterator nodeIt = getNodeIterator(Constants.XPATH_TABLE);
        for (Node node = nodeIt.nextNode(); node != null; node = nodeIt.nextNode())
        {
            name = getAttributeValue(node, Constants.ATTR_NAME);
            if (name != null)
            {
                logger.info("Cargando la informaci\363n de la mapeo de la tabla " + name);
                
                // Crear el mapeo de la tabla
                table = new Table(name);
                table.setIterator(getAttributeValue(node, Constants.ATTR_ITERATOR));
                table.setProperties(getProperties(node));
                table.setMappings(getMappings(node));
                
                tables.put(name, table);
                
                if (logger.isDebugEnabled())
                    logger.debug("Tabla " + name + ":\r\n" + table.toString());
            }
        }
    }
     
    /**
     * Carga las propiedades para el mapeo de una tabla.
     * @param node Nodo XML.
     * @return Propiedades para el mapeo.
     */
    private Properties getProperties(Node node)
    {
        Properties props = new Properties();
        String name, value;
        
        NodeIterator it = getNodeIterator(node, Constants.XPATH_PROPERTY);
        for (Node nd = it.nextNode(); nd != null; nd = it.nextNode())
        {
            name = getAttributeValue(nd, Constants.ATTR_NAME);
            if (name != null)
            {
                value = getNodeValue(nd);
                props.setProperty(name, value != null ? value : "");
            }
        }
        
        return props;
    }

    /**
     * Carga los mapeos de campos y entidades de una tabla.
     * @param node Nodo XML.
     * @return Mapeos de una tabla.
     */
    private Mappings getMappings(Node node)
    {
        Mappings mappings = new Mappings();
        mappings.setNode(getSingleNode(node, Constants.XPATH_MAPPINGS));
        return mappings;
    }
    
}