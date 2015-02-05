package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;

import java.util.HashMap;
import java.util.Iterator;

public class ActionBean extends ItemBean {

	private static final long serialVersionUID = 1L;
	
	private HashMap property = new HashMap();
	
	public final static String ID = "ID";
	public final static String TITLE = "TITLE";
	public final static String ACTION = "ACTION";

	public ActionBean() {	
		super();
	}

	public ActionBean( String sAction) 
	throws ISPACException {	
		super();
		setProperty( ACTION, sAction);
	}
	
	public ActionBean(String sTitle, String action) 
	throws ISPACException {
		super();
		setProperty( TITLE, sTitle);
		setProperty( ACTION, action);
	}

	public ActionBean( String sId, String sTitle, String action) 
	throws ISPACException {	
		super();
		setProperty( ID, sId);
		setProperty( TITLE, sTitle);
		setProperty( ACTION, action);
	}
	/*
	 * Guarda las propiedades del objeto IItem
	 */
	public void setItem( IItem item)
	throws ISPACException {
		
		Properties properties = item.getProperties();

		Iterator iterator = properties.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			String sName = property.getName();
			String sValue = item.getString( sName);
			setProperty( sName, sValue);
		}		
	}

	/**
	 * Obtiene el valor de una propiedad
	 */
	public Object getProperty(String key) 
	{
		if (property.get(key) != null)
			return property.get(key).toString();
		else
			return property.get(key);
	}
	/**
	 * Guarda el valor de una propiedad
	 */
	public void setProperty(String key, String value) 
	{
		property.put(key, value);
	}
}
