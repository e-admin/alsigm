package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.ValidationResults;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 *
 * @author 66575267
 *
 * @date 04/06/2009
 *
 */
public class XMLRegisterBook extends XMLValidationListAbstract {

	public static Document createXMLValidationList(ValidationResults results,
			int initRow, Locale locale, List fieldsInfo, String caseSensitive) {
		Document doc = createDocument(initRow, results, RBUtil.getInstance(
				locale).getProperty(I18N_VALIDATIONUSECASE_REGISTERBOOK), "",
				"", fieldsInfo, locale, caseSensitive, 0, 0);

		Element root = doc.getRootElement();

		Element nodeList = root.addElement(XML_NODELIST_TEXT);

		if (results.getResults().isEmpty()) {
			addNode(Integer.MIN_VALUE, "", "", "", null, "", Integer.MIN_VALUE,
					"", nodeList, "");
		} else {

			String inRegisterLabel = RBUtil.getInstance(locale).getProperty(
					"book.fld11.1");
			String outRegisterLabel = RBUtil.getInstance(locale).getProperty(
					"book.fld11.2");

			// for (Iterator iterator = results.getResults().iterator();
			// iterator
			// .hasNext();) {
			// ScrRegstate book = (ScrRegstate) iterator.next();
			// String name = book.getIdocarchhdr().getName();
			//
			// if (book.getIdocarchhdr().getType() == 1) {
			// name = name + " (" + inRegisterLabel + ")";
			// } else if (book.getIdocarchhdr().getType() == 2) {
			// name = name + " (" + outRegisterLabel + ")";
			// }
			//
			// addNode(book.getIdocarchhdr().getId().intValue(), name, book.
			// getIdocarchhdr().getId().toString(), "", Boolean.TRUE, "",
			// Integer.MIN_VALUE, "", nodeList, "");
			//
			//			}

			for (Iterator iterator = results.getResults().iterator(); iterator.hasNext();) {
				ScrRegStateByLanguage scrRegStateByLanguage = (ScrRegStateByLanguage) iterator.next();
				String name = scrRegStateByLanguage.getIdocarchhdrName();

				if (scrRegStateByLanguage.getType() == 1){
					name = name + " (" + inRegisterLabel + ")";
				} else if (scrRegStateByLanguage.getType() == 2){
					name = name + " (" + outRegisterLabel + ")";
				}

				addNode(scrRegStateByLanguage.getIdocarchhdrId().intValue(),
						name, scrRegStateByLanguage.getIdocarchhdrId()
								.toString(), "", Boolean.TRUE, "",
						Integer.MIN_VALUE, "", nodeList, "");
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
