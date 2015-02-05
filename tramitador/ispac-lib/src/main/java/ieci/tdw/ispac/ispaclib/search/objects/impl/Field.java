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
import ieci.tdw.ispac.ispaclib.search.objects.IField;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class Field extends GenericItem implements IField
{
	
	private String PROPERTY_COLUMNAME = "columnname";
	private String PROPERTY_DESCRIPTION = "description";
	private String PROPERTY_DATATYPE = "datatype";
	private String PROPERTY_OPERATOR = "operator";	
	private String PROPERTY_VALUE = "value";
	private String PROPERTY_BINDING="binding";
		
	/**
	 * Tipo de control: text, textarea
	 */
	private IControlType mControlType = null;
	
	/**
	 * Atributos del campo: se refiere a atributos del nodo xml
	 */
	//private LinkedHashMap mAttributes = null;
	
	/**
	 * Constructor
	 * @param node nodo xml con la informacion del campo
	 */
	public Field (Node node)
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
		String columName = XPathUtil.get(node, PROPERTY_COLUMNAME + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_COLUMNAME, Types.VARCHAR));
		values.put(PROPERTY_COLUMNAME, columName);
		String description = XPathUtil.get(node, PROPERTY_DESCRIPTION + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_DESCRIPTION, Types.VARCHAR));
		values.put(PROPERTY_DESCRIPTION, description);		
		String datatype = XPathUtil.get(node, PROPERTY_DATATYPE + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_DATATYPE, Types.VARCHAR));
		values.put(PROPERTY_DATATYPE, datatype);		

		properties.add(new Property(properties.size(), PROPERTY_VALUE, Types.VARCHAR));
		values.put(PROPERTY_VALUE, null);		
		properties.add(new Property(properties.size(), PROPERTY_OPERATOR, Types.VARCHAR));
		values.put(PROPERTY_OPERATOR, null);
		
		String binding = XPathUtil.get(node, PROPERTY_BINDING + "/text()");
		properties.add(new Property(properties.size(), PROPERTY_BINDING, Types.VARCHAR));
		values.put(PROPERTY_BINDING, binding);		
		
	
		init(properties, PROPERTY_COLUMNAME, values);	
		
		mControlType = new ControlType (XPathUtil.getNodeList(node, "controltype").item(0));				
	}
		
	/** Devuelve nombre de la columna
	 * @return nombre de la columna
	 */
	public String getColumName()
	throws ISPACException
	{
		return getString (PROPERTY_COLUMNAME);
	}
	
	/** Establece el nombre de la columna
	 * @param columname nombre de la columna
	 */
	public void setColumName(String columName)
	throws ISPACException
	{
		set (PROPERTY_COLUMNAME, columName);
	}
	
	/**
	 * Devuelve el tipo de control
	 * @return tipo de control
	 */
	public IControlType getControlType()
	{
		return mControlType;
	}
	
	/**
	 * Devuelve el tipo de dato
	 * @return tipo de dato
	 */
	public String getDatatype()
	throws ISPACException
	{
		return getString(PROPERTY_DATATYPE);
	}
	
	/**
	 * Establece tipo de dato
	 * @param datatype tipo de dato
	 */
	public void setDatatype(String datatype)
	throws ISPACException
	{
		set (PROPERTY_DATATYPE, datatype);
	}
	
	/**
	 * Devuelve descripcion del campo
	 * @return Returns descripcion del campo
	 */
	public String getDescription()
	throws ISPACException
	{
		return getString(PROPERTY_DESCRIPTION);
	}
	
	/**
	 * Establece descripcion del campo
	 * @param description descripcion del campo
	 */
	public void setDescription(String description)
	throws ISPACException
	{
		set (PROPERTY_DESCRIPTION, description);
	}
	
	/**
	 * Devuelve el operador con el que se hara la query
	 * @return Returns operador
	 */
	public String getOperator()
	throws ISPACException
	{
		return getString(PROPERTY_OPERATOR);
	}
	
	/**
	 * Establece el operador con el q se hara la query
	 * @param operator operador con el q se hara la query
	 */
	public void setOperator(String operator)
	throws ISPACException
	{
		set (PROPERTY_OPERATOR, operator);
	}
	
	/**
	 * 
	 * @param binding consulta a realizar con cada uno de los valores seleccionado de este campo
	 * @throws ISPACException
	 */
	public void setBinding(String binding)throws ISPACException{
		
		set(PROPERTY_BINDING, binding);
	}
	
	/**
	 * 
	 * @return El valor de la propiedad binding
	 * @throws ISPACException
	 */
	public String getBinding()throws ISPACException{
		return getString(PROPERTY_BINDING);
	}
	
	/**
	 * Devuelve el valor de campo
	 * @return valor del campo
	 */
	public Object getValue()
	throws ISPACException
	{
		return getString(PROPERTY_VALUE);
	}
	
	/**
	 * Establece el valor del campo
	 * @param value valor del campo
	 */
	public void setValue(String value)
	throws ISPACException
	{
		set (PROPERTY_VALUE, value);
	}
	
	
	public void setValue(List value)
	throws ISPACException
	{
		set (PROPERTY_VALUE, value);
	}

	/**
	 * Dice si el campo tiene valor o no
	 * @return boolean
	 */
	public boolean hasValue ()
	throws ISPACException	
	{
		return (getValue() != null);
	}
}
