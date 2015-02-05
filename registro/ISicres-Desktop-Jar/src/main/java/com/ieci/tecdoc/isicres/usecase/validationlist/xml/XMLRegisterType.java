package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLRegisterType extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLRegisterType.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLValidationList(Locale locale, String caseSensitive) {
		Document doc = createDocument(1, 2, 2, 2, 0, 0, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_REGISTERTYPE), "",
				"", null, null, caseSensitive);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);

        addNode(1, RBUtil.getInstance(locale).getProperty("book.fld11.1"), RBUtil.getInstance(locale)
                .getProperty("book.fld11.1"), "", null, "", Integer.MIN_VALUE, "", nodeList, "");
        addNode(2, RBUtil.getInstance(locale).getProperty("book.fld11.2"), RBUtil.getInstance(locale)
                .getProperty("book.fld11.2"), "", null, "", Integer.MIN_VALUE, "", nodeList, "");

        return doc;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

