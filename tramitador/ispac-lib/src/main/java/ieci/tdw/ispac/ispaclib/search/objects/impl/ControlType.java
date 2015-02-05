/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.search.objects.IControlType;

import java.sql.Types;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class ControlType extends GenericItem implements IControlType
{
	
	String PROPERTY_TYPE = "type";
		
	/**
	 * Constructor
	 * @param node nodo xml de tipo controltype con los atributos
	 * que lo definen. Estos son almacenados en un map
	 */
	public ControlType (Node node)
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
		String type = node.getFirstChild().getNodeValue();
		properties.add(new Property(properties.size(), PROPERTY_TYPE, Types.VARCHAR));
		values.put(PROPERTY_TYPE, type);
		init(properties, PROPERTY_TYPE, values);		
	}

	/**
	 * Devuelve el tipo de control
	 * @return
	 */
	public String getControlType () 
	throws ISPACException	
	{
		return getString(PROPERTY_TYPE);
	}
	
	/**
	 * Establece el tipo de control
	 * @param type tipo de control
	 */
	public void setTypeControl (String type)
	throws ISPACException
	{
		set(PROPERTY_TYPE, type);
	}
	
}
