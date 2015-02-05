/**
 * 
 */
package com.ieci.tecdoc.isicres.usecase.book.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.validationlist.xml.XMLValidationListAbstract;

/**
 * @author 66575267
 * 
 * @date 04/06/2009
 * 
 */
public class XMLAsocRegsValidate extends XMLValidationListAbstract implements
		Keys {

	public static Document createXMLAsocRegsValidate(String code, String asocRegsSelected) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement(XML_VALIDATION_TEXT);		
		root.addElement(XML_CODELOWER_TEXT).addText(code);
		root.addElement(XML_REGISTER_TEXT).addText(asocRegsSelected);
		
		return doc;
		
	}
	
}
