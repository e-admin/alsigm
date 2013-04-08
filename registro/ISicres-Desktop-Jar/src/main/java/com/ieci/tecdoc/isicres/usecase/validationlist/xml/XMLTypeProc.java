package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrTypeproc;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeProc extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeProc.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLValidationList(ValidationResults results,
			int initRow, Locale locale, List fieldsInfo, String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_TYPEPROC), "", "",
				fieldsInfo, locale, caseSensitive, 0, 0);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);

        ScrTypeproc scr = null;
        Boolean bool = null;
        for (Iterator it = results.getResults().iterator(); it.hasNext();) {
            scr = (ScrTypeproc) it.next();
            if (scr.getEnabled() == 1) {
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            addNode(scr.getId().intValue(), scr.getName(), scr.getCode(), "", bool, "", Integer.MIN_VALUE, "", nodeList, "");
        }

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

