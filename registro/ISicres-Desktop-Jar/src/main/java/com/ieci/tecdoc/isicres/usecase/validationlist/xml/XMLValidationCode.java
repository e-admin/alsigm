/*
 * Created on 28-abr-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.isicres.usecase.validationlist.xml;


import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.ValidationResultCode;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;


/**
 * @author 79426599
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLValidationCode  extends XMLValidationListAbstract{
	private static Logger _logger = Logger.getLogger(XMLValidationCode.class);

    public static Document createXMLValidationCode(ValidationResultCode validationResultCode, Locale locale ) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_VALIDATION_TEXT);
        
        addNode(validationResultCode, locale, root);
   
    	return document;
    }
    
    private static void addNode(ValidationResultCode validationResultCode, Locale locale, Element parent) {
    	parent.addElement(XML_FLDID_TEXT).addText(validationResultCode.getFldid().toString());
        if (validationResultCode.getDescription() != null){
        	parent.addElement(XML_CODELOWER_TEXT).addText(validationResultCode.getCode());
        	parent.addElement(XML_DESCRIPTION_TEXT).addText(validationResultCode.getDescription());
	        if (validationResultCode.getFldIdAdd() != null){
	        	parent.addElement(XML_FLDIDADD_TEXT).addText(validationResultCode.getFldIdAdd().toString());
	        	parent.addElement(XML_CODELOWERADD_TEXT).addText(validationResultCode.getCodeAdd());
	        	parent.addElement(XML_DESCRIPTIONADD_TEXT).addText(validationResultCode.getDescriptionAdd());
	        }
        } else {
        	parent.addElement(XML_ERROR_TEXT).addText(RBUtil.getInstance(locale)
	                .getProperty(I18N_VALIDATIONUSECASE_VALIDATIONCODE_ERROR));
        }
    }
    
    
	
}
