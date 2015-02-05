package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLValidationListAbstract;

/**
 * 
 * @author 66575267
 * 
 * @date 01/06/2009
 * 
 */
public class XMLAsocRegsSearch extends XMLValidationListAbstract implements
		Keys {

	public static Document getXMLAsocRegsSearchList(Locale locale,
			String dataBaseType, String caseSensitive) {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement(XML_SICRESLIST_TEXT);

		addSearchTitle(root, locale);
		addAsocRegFields(root, locale, dataBaseType, caseSensitive);

		return document;
	}

	private static void addSearchTitle(Element parent, Locale locale) {

		Element title = parent.addElement(XML_TITLE_TEXT);
		title.addText(RBUtil.getInstance(locale).getProperty(
				BOOKUSECASE_ASOCREGSSEARCH_TITLE));

	}

	public static Document getXMLAsocRegsClausuleWhere(Object regWhere,
			Locale locale) {

		Document document = DocumentHelper.createDocument();

		Element root = document.addElement(XML_VALIDATION_TEXT);
		if (regWhere instanceof List) {
			root
					.addElement(XML_ERROR_TEXT)
					.add(
							DocumentHelper
									.createCDATA(RBUtil
											.getInstance(locale)
											.getProperty(
													I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR)));
			root.addElement(XML_FLDNAME_TEXT).add(
					DocumentHelper.createCDATA((String) ((List) regWhere)
							.get(0)));
		} else {
			root.addElement(XML_REGWHERE_TEXT).add(
					DocumentHelper.createCDATA((String) regWhere));
		}

		return document;
	}

	private static void addAsocRegFields(Element parent, Locale locale,
			String dataBaseType, String caseSensitive) {

		AsocRegsSearchFields asocRegsSearchFields = new AsocRegsSearchFields(
				locale, dataBaseType);
		Element xmlAsocRegsFields = parent.addElement(XML_REGFIELDS_TEXT);
		// xmlAsocRegsFields.addElement(XML_NAME_UPPER_TEXT).add(
		// DocumentHelper.createCDATA(RBUtil.getInstance(locale)
		// .getProperty(BOOKUSECASE_DISTRIBUTIONSEARCH_BY_FLD)));

		List fieldSearch = asocRegsSearchFields.getResult();
		for (int i = 0; i < fieldSearch.size(); i++) {
			AsocRegsFields asocRegsFields = (AsocRegsFields) fieldSearch.get(i);

			Element xmlFields = xmlAsocRegsFields.addElement(XML_FIELD_TEXT);
			xmlFields.addAttribute(XML_ID_TEXT, XML_REGFIELD_TEXT);
			xmlFields.addAttribute(XML_DATATYPE_TEXT, asocRegsFields
					.getFieldType().toString());
			xmlFields.addAttribute(XML_CASESENSITIVE_TEXT, caseSensitive);
			int validation = asocRegsFields.getFieldValidation().intValue();
			if (validation != 0) {
				xmlFields.addAttribute(XML_VALIDATION_TEXT, "1");
				if (validation == 10) {
					xmlFields.addAttribute(XML_FLDID_TEXT, "0");
					xmlFields.addAttribute(XML_TVALID_TEXT, "10");
				}

			}
			xmlFields.addElement(XML_FIELDLABEL_TEXT).add(
					DocumentHelper.createCDATA(asocRegsFields.getFieldLabel()));
			xmlFields.addElement(XML_FIELDNAME_TEXT).add(
					DocumentHelper.createCDATA(asocRegsFields.getFieldName()));

			Element fieldOperator = xmlFields.addElement(XML_OPERATORS_TEXT);
			Map operators = asocRegsFields.getOperators();
			for (Iterator it = operators.keySet().iterator(); it.hasNext();) {
				String operator = (String) it.next();
				operator = operator.substring(1, operator.length());
				fieldOperator.addElement(XML_OPERATOR_TEXT).add(
						DocumentHelper.createCDATA(operator));
			}

		}
	}
}
