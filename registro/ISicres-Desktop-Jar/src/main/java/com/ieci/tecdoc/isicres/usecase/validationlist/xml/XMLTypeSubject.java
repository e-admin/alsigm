package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrCact;
import com.ieci.tecdoc.common.invesicres.ScrCaeu;
import com.ieci.tecdoc.common.invesicres.ScrCagl;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeSubject extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeSubject.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLValidationList(ValidationResults results,
			int initRow, int enabled, Locale locale, List fieldsInfo,
			String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_TYPESUBJECT), "",
				"", fieldsInfo, locale, caseSensitive, 0, 0);

        Element root = doc.getRootElement();

        Element nodeList = root.addElement(XML_NODELIST_TEXT);

        if (results.getResults().isEmpty()) {
			addNode(Integer.MIN_VALUE, "", "", "", null, "", Integer.MIN_VALUE,
					"", nodeList, "");
		} else {
			ScrCa scr = null;
        	ScrCaeu scrEu = null;
        	ScrCagl scrGl = null;
        	ScrCact scrCt = null;
        	Boolean bool = null;
        	for (Iterator it = results.getResults().iterator(); it.hasNext();) {
				Object scrCaAux = it.next();
				if (scrCaAux instanceof ScrCa) {
					scr = (ScrCa) scrCaAux;
					if (scr.getEnabled().equals(new Integer(1))) {
						bool = Boolean.TRUE;
					} else {
						bool = Boolean.FALSE;
					}

					addNode(scr.getId().intValue(), scr.getMatter(), scr
							.getCode(), "", bool, "", Integer.MIN_VALUE, "",

					nodeList, "");
				} else if (scrCaAux instanceof ScrCaeu) {
					scrEu = (ScrCaeu) scrCaAux;
					if (scrEu.getEnabled().equals(new Integer(1))) {
						bool = Boolean.TRUE;
					} else {
						bool = Boolean.FALSE;
					}

					addNode(scrEu.getId().intValue(), scrEu.getMatter(), scrEu
							.getCode(), "", bool, "", Integer.MIN_VALUE, "",

					nodeList, "");
				} else if (scrCaAux instanceof ScrCagl) {
					scrGl = (ScrCagl) scrCaAux;
					if (scrGl.getEnabled().equals(new Integer(1))) {
						bool = Boolean.TRUE;
					} else {
						bool = Boolean.FALSE;
					}

					addNode(scrGl.getId().intValue(), scrGl.getMatter(), scrGl
							.getCode(), "", bool, "", Integer.MIN_VALUE, "",

					nodeList, "");
				} else if (scrCaAux instanceof ScrCact) {
					scrCt = (ScrCact) scrCaAux;
					if (scrCt.getEnabled().equals(new Integer(1))) {
						bool = Boolean.TRUE;
					} else {
						bool = Boolean.FALSE;
					}

					addNode(scrCt.getId().intValue(), scrCt.getMatter(), scrCt
							.getCode(), "", bool, "", Integer.MIN_VALUE, "",

					nodeList, "");
				}
			}
		}

        return doc;
    }

    /***************************************************************************
	 * Protected methods
	 **************************************************************************/

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

