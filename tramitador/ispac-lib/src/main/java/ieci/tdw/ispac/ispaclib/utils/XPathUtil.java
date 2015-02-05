package ieci.tdw.ispac.ispaclib.utils;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.Date;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author juanin
 *
 */
public class XPathUtil
{

	public static int getInt(Document doc,String xpath) throws ISPACException
	{
		return TypeConverter.parseInt(get(doc,xpath));
	}

	public static Date getDate(Document doc,String xpath) throws ISPACException
	{
//		return TypeConverter.toDate(get(doc,xpath));	
		String value=get(doc,xpath);
		if (value ==null)
			throw new ISPACException("XPathUtil.getInt(): Imposible convertir el nodo '"+
					xpath+"' a una fecha ");
		return TypeConverter.toDate(value);
	}
	
	public static String get(Document doc,String xpath) throws ISPACException
	{
		Node node=selectNode(doc,xpath);
		if (node==null)
			return null;
		
		return node.getNodeValue();
	}

	public static String get(Node elem,String xpath) throws ISPACException
	{
		Node node=selectNode(elem,xpath);
		
		if (node==null)
			return null;
		
		return node.getNodeValue();
	}
	
	public static boolean existNode(Node elem,String xpath) throws ISPACException
	{
		Node node=selectNode(elem,xpath);
		return (node!=null);
	}
	
	public static Node selectNode(Document doc,String xpath) throws ISPACException
	{
		Node node = null;
		try
		{
			node = XPathAPI.selectSingleNode(doc.getDocumentElement(),xpath);
		} catch (TransformerException e)
		{
			throw new ISPACException(e);
		}
		return node;
	}
	
	public static NodeList getNodeList(Document doc,String xpath) throws ISPACException
	{
		NodeList nodelist = null;
		try
		{
			nodelist = XPathAPI.selectNodeList(doc.getDocumentElement(),xpath);
		} catch (TransformerException e)
		{
			throw new ISPACException(e);
		}
		return nodelist;
	}
	
	public static NodeList getNodeList(Node node,String xpath) throws ISPACException
	{
		NodeList nodelist = null;
		try
		{
			nodelist = XPathAPI.selectNodeList(node,xpath);
		} catch (TransformerException e)
		{
			throw new ISPACException(e);
		}
		return nodelist;
	}
	
	public static Node selectNode(Node node, String xpath) throws ISPACException
	{
		Node nod = null;
		try
		{			
			nod = XPathAPI.selectSingleNode(node, xpath);
		} catch (TransformerException e)
		{
			throw new ISPACException(e);
		}
		return nod;
	}	
}
