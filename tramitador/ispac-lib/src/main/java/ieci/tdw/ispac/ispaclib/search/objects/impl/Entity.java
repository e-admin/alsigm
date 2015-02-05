/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.search.objects.IEntity;
import ieci.tdw.ispac.ispaclib.search.objects.IField;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Entity extends GenericItem implements IEntity
{
	
	private String PROPERTY_TABLENAME = "tablename";
	private String PROPERTY_DESCRIPTION = "description";
	private String PROPERTY_BINDFIELD = "bindfield";
	private String PROPERTY_TYPE = "type";
	private String PROPERTY_IDENTIFIER = "identifier";
	/**
	 * Campos de la entidad
	 */
	private LinkedHashMap mFields = null;
	
	/**
	 * Numero de campos de la entidad
	 */
	private int mNumFields = 0;
	
	/**
	 * PropertyFmts de la entidad
	 */
	//private LinkedHashMap mPropertyFmts = null;
	
	/**
	 * Numero de PropertyFmt de la entidad
	 */
	//private int mNumPropertyFmts = 0;
	
	/**
	 * Constructor
	 * @param node contiene la informacion de la entidad
	 */
	public Entity (Node node)
	throws ISPACException
	{
		
		Properties properties = new Properties ();
		HashMap values = new HashMap ();
		NamedNodeMap attrs = node.getAttributes();
		int numAttrs = attrs.getLength();
		for (int i=0; i<numAttrs; i++)
		{
			Node attr = attrs.item(i);
			String name = attr.getNodeName ();
			String value = attr.getNodeValue();
			properties.add(new Property (i, name, Types.VARCHAR));
			values.put(name, value);
		}
		String columName = XPathUtil.get(node, PROPERTY_TABLENAME + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_TABLENAME, Types.VARCHAR));
		values.put(PROPERTY_TABLENAME, columName);
		String description = XPathUtil.get(node, PROPERTY_DESCRIPTION + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_DESCRIPTION, Types.VARCHAR));
		values.put(PROPERTY_DESCRIPTION, description);		
		String datatype = XPathUtil.get(node, PROPERTY_BINDFIELD + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_BINDFIELD, Types.VARCHAR));
		values.put(PROPERTY_BINDFIELD, datatype);		
		
		init(properties, PROPERTY_TABLENAME, values);	
		
		mFields = new LinkedHashMap ();
		NodeList nodeList = XPathUtil.getNodeList(node, "field");
		for (int i=0; i<nodeList.getLength();i++)
		{
			Field field = new Field (nodeList.item(i));
			mFields.put(field.getColumName(), field);
			mNumFields++;
		}
		
		// Los campos a consultar ya no se establecen por entidad
		// sino a nivel global en el xml del formulario de búsqueda
		/*
		mPropertyFmts = new LinkedHashMap ();
		nodeList = XPathUtil.getNodeList(node, "propertyfmt");
		for (int i=0; i<nodeList.getLength();i++)
		{
			PropertyFmt propertyFmt = new PropertyFmt (nodeList.item(i));
			mPropertyFmts.put(propertyFmt.getPropertyName(), propertyFmt);
			mNumPropertyFmts++;
		}
		*/			
	}
		
	public Entity(String tableName) {
		HashMap values = new HashMap ();
		Properties properties = new Properties ();
		properties.add(new Property(properties.size(), PROPERTY_TABLENAME, Types.VARCHAR));
		values.put(PROPERTY_TABLENAME, tableName);
		properties.add(new Property(properties.size(), PROPERTY_BINDFIELD, Types.VARCHAR));
		values.put(PROPERTY_BINDFIELD, "NUMEXP");		
		properties.add(new Property(properties.size(), PROPERTY_TYPE, Types.VARCHAR));
		values.put(PROPERTY_TYPE, "");		
				
		init(properties, PROPERTY_TABLENAME, values);
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription()
	throws ISPACException
	{
		return getString (PROPERTY_DESCRIPTION);
	}
	
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description)
	throws ISPACException
	{
		this.set(PROPERTY_DESCRIPTION, description);
	}
	
	/**
	 * @return Returns the bindField.
	 */
	public String getBindField()
	throws ISPACException
	{
		return getString (PROPERTY_BINDFIELD);
	}
	
	/**
	 * @param numExpField The numExpField to set.
	 */
	public void setBindField(String bindField)
	throws ISPACException
	{
		this.set(PROPERTY_BINDFIELD, bindField);
	}

	
	/**
	 * @return Returns the type.
	 */
	public String getType()
	throws ISPACException
	{
		return getString (PROPERTY_TYPE);
	}

	/**
	 * @return Returns the identifier.
	 */
	public String getIdentifier()
	throws ISPACException
	{
		return getString (PROPERTY_IDENTIFIER);
	}	
	
	/**
	 * @param type The type to set.
	 */
	public void setType(String type)
	throws ISPACException
	{
		this.set(PROPERTY_TYPE, type);
	}
		
	
	/**
	 * @return Returns the table.
	 */
	public String getTable()
	throws ISPACException
	{
		return getString (PROPERTY_TABLENAME);
	}
	
	/**
	 * @param table The table to set.
	 */
	public void setTable(String table)
	throws ISPACException
	{
		this.set (PROPERTY_TABLENAME, table);
	}
	
	/**
	 * Devuelve el numero de campos de la entidad
	 * @return numero de campos 
	 */
	public int getNumFields ()
	{
		return mNumFields;
	}
	
	/**
	 * Devuelve el numero de propertyfmt de la entidad
	 * @return numero de propertyfmt
	 */
	/*
	public int getNumPropertyFmts ()
	{
		return mNumPropertyFmts;
	}
	*/
	
	/**
	 * Devuelve el campo de indice 'i'
	 * @param i indice dentro del conjunto de campos. Comienza en 0
	 * @return la entidad i-esima
	 */
	public IField getField (int i)
	throws ISPACException
	{
		if (i>= mNumFields)
			throw new ISPACException ("Error en Entity::getField (int i): No existe el campo i-esimo (i=" + i + ")");
		Collection collection = mFields.values();
		Object[] array = collection.toArray();
		return (Field) array[i];
	}
	
	/**
	 * Devuelve el propertyfmt de indice 'i'
	 * @param i indice dentro del conjunto de propertyfmt. Comienza en 0
	 * @return la entidad i-esima
	 */
	/*
	public IPropertyFmt getPropertyFmt (int i)
	throws ISPACException
	{
		if (i>= mNumPropertyFmts)
			throw new ISPACException ("Error en Entity::getPropertyFmt (int i): No existe el propertyfmt i-esimo (i=" + i + ")");
		Collection collection = mPropertyFmts.values();
		Object[] array = collection.toArray();
		return (PropertyFmt) array[i];
	}
	*/	
	
	/**
	 * Establece un valor a una de los campos de la entidad
	 * @param name nombre del campo
	 * @param value valor del campo
	 */
	public void setFieldValue (String name, String value)
	throws ISPACException
	{
		Field field = (Field)mFields.get(name);
		if (field == null)
			throw new ISPACException ("Error en Entity::setFieldValue ("
					+ name + "," + value + "): no existe el campo " + name + " en la entidad " + getTable());
		field.setValue(value);
	}
	
	
	
	public void setFieldValue (String name, List value)
	throws ISPACException
	{
		Field field = (Field)mFields.get(name);
		if (field == null)
			throw new ISPACException ("Error en Entity::setFieldValue ("
					+ name + "," + value + "): no existe el campo " + name + " en la entidad " + getTable());
		field.setValue(value);
	}

	/**
	 * Establece el operador q se utilizara en la query para un determinado campo
	 * @param name nombre del campo
	 * @param value operador
	 */
	public void setFieldOperator (String name, String operator)
	throws ISPACException
	{
		Field field = (Field)mFields.get(name);
		if (field == null)
			throw new ISPACException ("Error en Entity::setFieldOperator ("
					+ name + "," + operator + "): no existe el campo " + name + " en la entidad " + getTable());
		field.setOperator(operator);
	}
	
}
