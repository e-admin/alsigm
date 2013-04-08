package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.utils.RBExternUtil;

public class XMLTypeAddress extends XMLValidationListAbstract {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeAddress.class);

	private static String ROOT_PROPERTY = "ADDRESS.TYPE_DOC.";
	
	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Document createXMLTypeAddress(Document doc, Locale locale) {
		List nodeList = doc.selectNodes(XPATH_TYPEADDRESSES_TYPEADDRESS);

		if ((nodeList != null) && (!nodeList.isEmpty())) {
			for (Iterator iterator = nodeList.iterator(); iterator.hasNext();) {
				Element node = (Element) iterator.next();

				String codigoText = ((Element) node
						.selectObject(XML_TYPEADDRESS_CODIGO)).getText();

				if (codigoText != null) {
					String descripcionText = null;
					try {
						descripcionText = RBExternUtil.getInstance(locale)
								.getProperty(ROOT_PROPERTY + codigoText);
						
						if ((descripcionText != null)
								&& (!descripcionText.equals(""))) {
							Element descriptionElement = (Element) node
									.selectObject(XML_TYPEADDRESS_DESCRIPCION);
							node.remove(descriptionElement);
							node.addElement(XML_TYPEADDRESS_DESCRIPCION).add(
									DocumentHelper.createCDATA(descripcionText));
						}
					} catch (Exception e) {
						_logger.error(
								"Imposible obtener la descripcion del tipo de direccion telematica con codigo: "
										+ codigoText, e);
					}					
				}
			}
		}

		return doc;
	}

}
