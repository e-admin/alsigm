/**
 * 
 */
package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ieci.tecdoc.common.utils.adapter.PersonKeys;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLValidationListAbstract;

/**
 * @author 66575267
 * 
 * @date 04/06/2009
 * 
 */
public class XMLAsocRegsResults extends XMLValidationListAbstract implements
		Keys {

	public static Document createXMLAsocRegsResult(List listResults,
			int inicio, int rango) {
		if (listResults != null && listResults.size() > 0) {
			return createXMLRegsInfo(listResults, inicio, listResults.size(),
					rango);
		} else {
			return createXMLEmpty(inicio, rango);
		}
	}

	public static Document createXMLValidateAsocRegsResult(Document docIni,
			String action, int initRow, int paso, Locale locale,
			String registerBook, String regWhere) {
		Document doc = null;
		Integer totalSize = null;
		Integer maxResult = new Integer(100000);

		if (docIni == null) {
			doc = DocumentHelper.createDocument();
			Element root = doc.addElement(XML_VALIDATION_TEXT);
			addNodeActionForm(action, "", locale, root);
		} else {
			Element element = docIni.getRootElement();
			totalSize = Integer.valueOf(element.attribute(
					XML_TOTAL_ALL_LOWER_TEXT).getText());

			doc = createDocumentActionForm(action, initRow, maxResult
					.intValue(), totalSize, "", "", null, null, null, null);

			Element root = doc.getRootElement();

			List list = docIni.selectNodes(XPATH_ASOCREGS_REGISTO);
			if ((list != null) && (list.size() > 0)) {
				Element nodeList = root.addElement(XML_LIST_TEXT);

				for (Iterator it = list.iterator(); it.hasNext();) {
					Node object = (Node) it.next();
					addRegistroActionFormList(object, nodeList);
				}
			}

			root.addElement(XML_REGISTERBOOK_TEXT).addText(registerBook);
			root.addElement(XML_REGSWHERE_TEXT).addText(regWhere);
		}

		return doc;
	}

	private static Document createXMLEmpty(int firstRow, int rDefault) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_ASOCREGS_TEXT);
		root.addAttribute(XML_INICIO_TEXT, Integer.toString(firstRow));
		root.addAttribute(XML_FIN_TEXT, Integer.toString(firstRow + rDefault));
		root.addAttribute(XML_TOTAL_ALL_LOWER_TEXT, Integer.toString(0));
		root
				.addAttribute(PersonKeys.XML_RANGO_TEXT, Integer
						.toString(rDefault));

		return document;
	}

	private static Document createXMLRegsInfo(List listRegs, int inicio,
			int total, int rango) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(XML_ASOCREGS_TEXT);
		root.addAttribute(XML_INICIO_TEXT, Integer.toString(inicio));
		int fin = getFin(total, inicio, rango);
		root.addAttribute(XML_FIN_TEXT, Integer.toString(fin));
		root.addAttribute(XML_TOTAL_ALL_LOWER_TEXT, Integer.toString(total));
		root.addAttribute(XML_RANGO_TEXT, Integer.toString(rango));

		Element xmlReg = null;
		for (Iterator iterator = listRegs.iterator(); iterator.hasNext();) {
			AsocRegsResults registro = (AsocRegsResults) iterator.next();
			xmlReg = root.addElement(XML_REGISTRO_TEXT);

			xmlReg.addElement(XML_BOOKID_TEXT).addText(
					registro.getBookId().toString());
			xmlReg.addElement(XML_FOLDERID_TEXT).addText(
					registro.getFolderId().toString());
			xmlReg.addElement(XML_FOLDERNUMBER_TEXT).addText(
					registro.getFolderNumber());
			xmlReg.addElement(XML_FOLDERDATE_TEXT).addText(
					registro.getFolderDate());
			if ((registro.getSummary() != null)
					&& (registro.getSummary().length() > 0)) {
				xmlReg.addElement(XML_SUMMARY_TEXT).addText(
						registro.getSummary());
			}

			// else {
			// xmlReg.addElement(XML_SUMMARY_TEXT).addText("");
			// }
		}

		return document;
	}

	private static int getFin(int total, int inicio, int rango) {
		int result = 0;
		if (inicio == 0) {
			if (total >= rango) {
				result = rango - 1;
			} else {
				result = total - 1;
			}
		} else {
			if (total >= inicio + rango) {
				result = inicio + rango - 1;
			} else {
				result = total - 1;
			}
		}
		return result;
	}

	private static void addNodeActionForm(String action, String value,
			Locale locale, Element parent) {
		parent.addElement(XML_ACTION_TEXT).addText(action);
		parent.addElement(XML_FLDID_TEXT).addText(value);
		parent.addElement(XML_ERROR_TEXT).addText(
				RBUtil.getInstance(locale).getProperty(
						I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR));
	}

	private static void addRegistroActionFormList(Node node, Element parent) {
		node.setParent(null);
		parent.add(node);
	}
}
