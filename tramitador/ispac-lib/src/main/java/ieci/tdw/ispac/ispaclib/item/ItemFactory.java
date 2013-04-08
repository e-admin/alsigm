package ieci.tdw.ispac.ispaclib.item;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.sql.Types;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author JUANIN
 *
 */
public class ItemFactory
{
	final Properties mdefaultprop;
	
	public ItemFactory()
	{
		mdefaultprop=null;
	}
	
	public ItemFactory(Properties defaultprop)
	{
		mdefaultprop=defaultprop;
	}
	
	public Property newXmlProperty(int ordinal,Node node, XmlItemInfo iteminfo)
	throws ISPACException
	{
		String name=XPathUtil.get(node,iteminfo.getPropNameXPath());
		return new Property(ordinal,name,Types.VARCHAR);
	}
	
	private IItem newXmlItem(Node node, XmlItemInfo iteminfo)
	throws ISPACException
	{
		Properties properties=new Properties();
		int ordinal=properties.getMaxPropertyOrdinal();
		
		NodeList nodelist=XPathUtil.getNodeList(node,iteminfo.getPropertyXPath());
		
		for (int i=0;i<nodelist.getLength();i++)
		{
			properties.add(newXmlProperty(++ordinal,nodelist.item(i),iteminfo));
		}
		
		
		//Se unen ambos grupos de propiedades y se obtienen valores sólo para las propiedades
		// especificadas en el XML.
		Properties itemprop=new Properties(properties);
		itemprop.addProperties(mdefaultprop);
		GenericItem item=new GenericItem(itemprop,iteminfo.getPropKey());
		
		// properties contiene las propiedades definidas en el XML.
		Object proparray[]=properties.toArray();
		for (int i=0;i<nodelist.getLength();i++)
		{	
			Property prop=(Property)proparray[i];
			String value=XPathUtil.get(nodelist.item(i),iteminfo.getValueXPath());
			item.set(prop.getName(),value);
		}

		return item;
	}
	
	public IItem newXmlItem(Document doc, XmlItemInfo iteminfo)
	throws ISPACException
	{		
		NodeList nodelist=XPathUtil.getNodeList(doc,iteminfo.getItemsXPath());
		if (nodelist.getLength()<=0)
			throw new ISPACException("No se ha encontrado el item especificado");
		
		return newXmlItem(nodelist.item(0),iteminfo);
	}
	
	public IItemCollection newXmlCollectionItem(Document doc, XmlItemInfo iteminfo)
		throws ISPACException
	{		
		NodeList nodelist=XPathUtil.getNodeList(doc,iteminfo.getItemsXPath());
		ArrayList list=new ArrayList();
		for (int i=0;i<nodelist.getLength();i++)
		{
			IItem item=newXmlItem(nodelist.item(i),iteminfo);
			list.add(item);
		}
		return new ListCollection(list);
	}
}
