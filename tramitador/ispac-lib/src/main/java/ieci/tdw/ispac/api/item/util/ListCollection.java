/*
 * Created on 20-jul-2004
 *
 */
package ieci.tdw.ispac.api.item.util;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Implementaci&oacute;n de IItemCollection para cualquier lista est&aacute;ndar
 * del paquete de colecciones
 *
 */
public class ListCollection implements IItemCollection
{
	final List mList;
	Iterator mIterator;

	public ListCollection(List list)
	{
		mList=list;
		if (mList != null)
			mIterator = mList.iterator();
	}

	public ListCollection(Set set)
	{
		mList = new ArrayList();

		Iterator iterator = set.iterator();
		while (iterator.hasNext())
		{
			mList.add( iterator.next());
		}

		mIterator = mList.iterator();
	}

	public boolean next() throws ISPACException
	{
		if (mIterator == null)
			return false;
		return mIterator.hasNext();
	}

	public IItem value()
	throws ISPACException
	{
		return (IItem)mIterator.next();
	}

	public List toList()
	throws ISPACException
	{
		return mList;
	}

	public Map toMap(String hashPropertyKey) throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj = value();
			map.put(obj.get(hashPropertyKey),obj);
		}
		return map;
	}
	
	public Map toMapStringKey(String hashPropertyKey) throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj = value();
			map.put(obj.get(hashPropertyKey).toString(),obj);
		}
		return map;
	}

	public Map toMap()
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKey(),obj);
		}
		return map;
	}

	public Map toMapStringKey()
	throws ISPACException
	{
		LinkedHashMap map=new LinkedHashMap();

		while(next())
		{
			IItem obj=value();
			map.put(obj.getKey().toString(),obj);
		}
		return map;
	}	
	
	
	public String toXml()
	throws ISPACException
	{
		//TODO  Implementar el volcado a XML
		return null;
	}

	public Iterator iterator ()
	{
		return mList.iterator();
	}

	public void reset ()
	{
		mIterator = mList.iterator();
	}
}
