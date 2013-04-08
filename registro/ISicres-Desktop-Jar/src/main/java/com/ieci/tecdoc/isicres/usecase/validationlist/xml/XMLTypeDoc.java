package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.utils.RBExternUtil;

/**
 * 
 * @author 66575267
 * 
 * @date 25/06/2009
 * 
 */
public class XMLTypeDoc extends XMLValidationListAbstract {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeDoc.class);

	private static String ROOT_PROPERTY = "PERSON.TYPE_DOC.";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Document createXMLTypeDoc(Document doc, Locale locale) {
		List nodeList = doc.selectNodes(XPATH_TYPEDOCS_TYPEDOC);

		if ((nodeList != null) && (!nodeList.isEmpty())) {
			for (Iterator iterator = nodeList.iterator(); iterator.hasNext();) {
				Element node = (Element) iterator.next();

				String codigoText = ((Element) node
						.selectObject(XML_TYPEDOC_CODIGO)).getText();

				if (codigoText != null) {
					String descripcionText = null;
					try {
						descripcionText = RBExternUtil.getInstance(locale)
								.getProperty(ROOT_PROPERTY + codigoText);
						
						if ((descripcionText != null)
								&& (!descripcionText.equals(""))) {
							Element descriptionElement = (Element) node
									.selectObject(XML_TYPEDOC_DESCRIPCION);
							node.remove(descriptionElement);
							node.addElement(XML_TYPEDOC_DESCRIPCION).add(
									DocumentHelper.createCDATA(descripcionText));
						}
					} catch (Exception e) {
						_logger.error(
								"Imposible obtener la descripcion del tipo de documento con codigo: "
										+ codigoText, e);
					}					
				}
			}
		}

		return doc;
	}

}
