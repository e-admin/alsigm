package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrTt;
import com.ieci.tecdoc.common.invesicres.ScrTtct;
import com.ieci.tecdoc.common.invesicres.ScrTteu;
import com.ieci.tecdoc.common.invesicres.ScrTtgl;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeTransport extends XMLValidationListAbstract {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeTransport.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Document createXMLValidationList(ValidationResults results,
			int initRow, Locale locale, List fieldsInfo, String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_TYPETRANSPORT), "",
				"", fieldsInfo, locale, caseSensitive, 0, 0);

		Element root = doc.getRootElement();

		Element nodeList = root.addElement(XML_NODELIST_TEXT);

		ScrTt scr = null;
		ScrTteu scr1 = null;
		ScrTtgl scr2 = null;
		ScrTtct scr3 = null;
		Object scrAux = null;

		if(results.getResults().isEmpty()){
			addNode(Integer.MIN_VALUE, "", "", "", null, "", Integer.MIN_VALUE,
					"", nodeList, "");
		}else{
			for (Iterator it = results.getResults().iterator(); it.hasNext();) {
				scrAux = it.next();
				if (scrAux instanceof ScrTt) {
					scr = (ScrTt) scrAux;
					addNode(scr.getId().intValue(), scr.getTransport(), scr
							.getTransport(), "", null, "", Integer.MIN_VALUE, "",

					nodeList, "");
				} else if (scrAux instanceof ScrTteu) {
					scr1 = (ScrTteu) scrAux;
					addNode(scr1.getId().intValue(), scr1.getTransport(), scr1
							.getTransport(), "", null, "", Integer.MIN_VALUE, "",

					nodeList, "");

				} else if (scrAux instanceof ScrTtgl) {
					scr2 = (ScrTtgl) scrAux;
					addNode(scr2.getId().intValue(), scr2.getTransport(), scr2
							.getTransport(), "", null, "", Integer.MIN_VALUE, "",

					nodeList, "");

				} else if (scrAux instanceof ScrTtct) {
					scr3 = (ScrTtct) scrAux;
					addNode(scr3.getId().intValue(), scr3.getTransport(), scr3
							.getTransport(), "", null, "", Integer.MIN_VALUE, "",

					nodeList, "");

				}
			}
		}

		return doc;
	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
