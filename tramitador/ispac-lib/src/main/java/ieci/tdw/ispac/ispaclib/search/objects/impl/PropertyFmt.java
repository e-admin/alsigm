/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.search.objects.IPropertyFmt;

import java.sql.Types;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PropertyFmt extends GenericItem implements IPropertyFmt
{
		
	private String PROPERTY_NAME = "property"; 
	
	/**
	 * Contructor
	 * @param node informacion del PropertyFmt
	 */
	public PropertyFmt (Node node)
	{
		String key = null; 
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
			if (name.equals("property"))
				key = value;					
		}
		init(properties, key, values);
	}
		
	/**
	 * Devuelve el nombre de la propiedad
	 * @return nombre de la propiedad
	 */
	public String getPropertyName()
	throws ISPACException
	{
		return getString (PROPERTY_NAME);
	}
	
	/**
	 * Establece el nombre de la propiedad
	 * @param propertyName nombre de la propiedad
	 */
	public void setPropertyName(String propertyName)
	throws ISPACException
	{
		set (PROPERTY_NAME, propertyName);
	}
}
