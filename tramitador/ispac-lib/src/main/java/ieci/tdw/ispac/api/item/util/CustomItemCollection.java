/*
 * Created on 27-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.api.item.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Implementaci&oacute;n de IItemCollection para cualquier clase est&aacute;ndar de tipo Collection
 */
public class CustomItemCollection implements IItemCollection
{
	final Collection mcollection;
	Iterator mit;

	public CustomItemCollection(Collection collection)
	{
		mcollection=collection;
		mit=mcollection.iterator();
	}

	public boolean next() throws ISPACException
	{
		return mit.hasNext();
	}

	public IItem value() throws ISPACException
	{
		return (IItem)mit.next();
	}

	public Map toMap() throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKey(),obj);
		}
		return map;
	}

	public Map toMap(String hashPropertyKey) throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.get(hashPropertyKey),obj);
		}
		return map;
	}
	
	public Map toMapStringKey(String hashPropertyKey) throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.get(hashPropertyKey).toString(),obj);
		}
		return map;
	}
	
	public Map toMapStringKey() throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKey().toString(),obj);
		}
		return map;
	}
	
	
	public List toList() throws ISPACException
	{
		ArrayList list=new ArrayList();
		while(next())
		{
			list.add(value());
		}
		return list;
	}

	public String toXml() throws ISPACException
	{
		//TODO  Implementar el volcado a XML
		return null;
	}

	public Iterator iterator ()
	{
		return mcollection.iterator();
	}

	public void reset ()
	{
		mit = mcollection.iterator();
	}
}
