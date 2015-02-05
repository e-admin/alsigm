package ieci.tdw.ispac.ispacweb.header;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.item.GenericItem;

import java.sql.Types;
import java.util.HashMap;


public class ContextHeader extends GenericItem{

	private String PROPERTY_LINK = "LINK";
	private String PROPERTY_QUERYSTRING = "QUERYSTRING";
	private String PROPERTY_NAME = "NAME";
	private String PROPERTY_CURRENTSTATE = "CURRENTSTATE";

	public ContextHeader()
	{
		super();
	}
	
	public ContextHeader(String nombre, String link, String queryString) {
		Properties properties = new Properties ();
		HashMap values = new HashMap ();
		properties.add(new Property(1, PROPERTY_NAME, Types.VARCHAR));
		values.put(PROPERTY_NAME, nombre);
		properties.add(new Property(2, PROPERTY_LINK, Types.VARCHAR));
		values.put(PROPERTY_LINK, link);
		properties.add(new Property(3, PROPERTY_QUERYSTRING, Types.VARCHAR));
		values.put(PROPERTY_QUERYSTRING, queryString);
		properties.add(new Property(4, PROPERTY_CURRENTSTATE, Types.NUMERIC));
		
		init(properties,"contextHeader",values);
	}

	/**
	 * @return Returns the link.
	 */
	public String getLink()
	throws ISPACException
	{
		return getString (PROPERTY_LINK);
	}
	
	/**
	 * @param description The link to set.
	 */
	public void setLink(String link)
	throws ISPACException
	{
		this.set(PROPERTY_LINK, link);
	}
	/**
	 * @return Returns the queryString.
	 */
	public String getQueryString()
	throws ISPACException
	{
		return getString (PROPERTY_QUERYSTRING);
	}
	
	/**
	 * @param description The queryString to set.
	 */
	public void setQueryString(String queryString)
	throws ISPACException
	{
		this.set(PROPERTY_QUERYSTRING, queryString);
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()
	throws ISPACException
	{
		return getString (PROPERTY_NAME);
	}
	
	/**
	 * @param description The name to set.
	 */
	public void setName(String name)
	throws ISPACException
	{
		this.set(PROPERTY_NAME, name);
	}
}
