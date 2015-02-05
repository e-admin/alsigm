package ieci.tdw.ispac.api.connector;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
*  Documento XML que describe las propiedades de un documento.
* 
*  < ?xml version="1.0" encoding=" UTF8"? > 
*  <doc_properties>
*    <property>
* 		<name>nombre</name>
* 		<value><![CDATA[valor de la propiedad]]></name>
* 	 </property>
*    <property>
* 		<name>nombre</name>
* 		<value><![CDATA[valor de la propiedad]]></name>
* 	 </property>
*    ...
*  </doc_properties>
**/

public class Properties
{
	protected HashMap mProperty = new HashMap();

	public Properties(String sProperties) 
	throws ISPACException
	{
		NodeList nodeList = null;
		Node property = null;

		Document document = XMLDocUtil.newDocument( sProperties);

		nodeList = XPathUtil.getNodeList( document, "//doc_properties/property");
		if (nodeList != null)
		{
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				property = nodeList.item( i);
				
				if (  XPathUtil.existNode( property, "name")
				   && XPathUtil.existNode( property, "value"))
				{
					String sName = XPathUtil.get( property, "name/text()");
					String sValue = XPathUtil.get( property, "value/text()");
					mProperty.put( sName, sValue);
				}
			}
		}
	}

	public HashMap getProperties() 
	{
		return mProperty;
	}
	
}