package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOficct;
import com.ieci.tecdoc.common.invesicres.ScrOficeu;
import com.ieci.tecdoc.common.invesicres.ScrOficgl;
import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLTypeOffic extends XMLValidationListAbstract {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLTypeOffic.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static Document createXMLValidationList(ValidationResults results,
			int initRow, int enabled, Locale locale, List fieldsInfo,
			String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_TYPEOFFIC), "", "",
				fieldsInfo, locale, caseSensitive, 0, 0);

		Element root = doc.getRootElement();

		Element nodeList = root.addElement(XML_NODELIST_TEXT);

		ScrOfic scr = null;
		ScrOficeu scr1 = null;
		ScrOficgl scr2 = null;
		ScrOficct scr3 = null;
		Object scrAux = null;
		Boolean bool = null;
		for (Iterator it = results.getResults().iterator(); it.hasNext();) {
			scrAux = it.next();
			if (scrAux instanceof ScrOfic) {
				scr = (ScrOfic) scrAux;
				if (scr.getDisableDate() == null) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scr.getId().intValue(), scr.getName(), scr.getCode(),
						scr.getAcron(), bool, scr.getStamp(),
						Integer.MIN_VALUE, "",

						nodeList, "");
			} else if (scrAux instanceof ScrOficeu) {
				scr1 = (ScrOficeu) scrAux;
				if (scr1.getDisableDate() == null) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scr1.getId().intValue(), scr1.getName(),
						scr1.getCode(), scr1.getAcron(), bool, scr1.getStamp(),
						Integer.MIN_VALUE, "",

						nodeList, "");

			} else if (scrAux instanceof ScrOficgl) {
				scr2 = (ScrOficgl) scrAux;
				if (scr2.getDisableDate() == null) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scr2.getId().intValue(), scr2.getName(),
						scr2.getCode(), scr2.getAcron(), bool, scr2.getStamp(),
						Integer.MIN_VALUE, "",

						nodeList, "");

			} else if (scrAux instanceof ScrOficct) {
				scr3 = (ScrOficct) scrAux;
				if (scr3.getDisableDate() == null) {
					bool = Boolean.TRUE;
				} else {
					bool = Boolean.FALSE;
				}
				addNode(scr3.getId().intValue(), scr3.getName(),
						scr3.getCode(), scr3.getAcron(), bool, scr3.getStamp(),
						Integer.MIN_VALUE, "",

						nodeList, "");

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
