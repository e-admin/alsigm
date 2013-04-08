package ieci.tdw.ispac.api.item;

import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Properties extends LinkedHashSet
{

	public Properties()
	{
		super();
	}

	public Properties(Properties properties)
	{
		super();
		if (properties != null)
			addProperties(properties);
	}

	public void addProperties(Properties properties, Map proptranslate)
	{
		Iterator it=properties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			if (proptranslate.containsKey(prop.getName()))
				prop.translate((String)proptranslate.get(prop.getName()));

			add(prop);
		}
	}

	public void addProperties(Properties properties,String prefix)
	{
		Iterator it=properties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			prop.translate(prefix+prop.getName());
			add(prop);
		}
	}

	public void addProperties(Properties properties)
	{
		Iterator it=properties.iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			add(prop);
		}
	}

	public void addProperties(Property[] properties)
	{
		for (int i = 0; i < properties.length; i++)
		{
			add(properties[i]);
		}
	}

	public void setReference(int reference)
	{
		Iterator it=iterator();
		while (it.hasNext())
		{
			Property prop = (Property) it.next();
			prop.setReference(reference);
		}
	}

	public int getReference ()
	{
		int reference = -1;
		Iterator it=iterator();
		if (it.hasNext())
		{
			Property prop = (Property) it.next();
			reference = prop.getReference();
		}
		return reference;

	}

	public int getMaxPropertyOrdinal()
	{
		int nMaxOrdinal=0;

		Iterator iterator = this.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			if (nMaxOrdinal<property.getOrdinal())
				nMaxOrdinal=property.getOrdinal();
		}
		return nMaxOrdinal;
	}

	public String toXML()
	{
		StringBuffer sXml = new StringBuffer();

		Iterator iterator = this.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			sXml.append(property.toXML());
		}

		return XmlTag.newTag("properties", sXml.toString());
	}

	public Map toMap()
	{
		LinkedHashMap map=new LinkedHashMap();
		Iterator iterator = this.iterator();

		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			map.put(property.getName(),property);
		}

		return map;
	}

	public Property[] getPropertyArray()
	{
		Property[] properties = new Property[this.size()];

		Iterator iterator = this.iterator();

		for (int i = 0; iterator.hasNext(); i++)
		{
			properties[i] = (Property) iterator.next();
		}

		return properties;
	}
}