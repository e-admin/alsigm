/*
 * Created on 20-feb-2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.isicres.usecase.book.xml;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author 79415840
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XMLSaveFdrErrors implements Keys {
	 /***************************************************************************
     * Attributes
     **************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLSaveFdrErrors.class);

    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public static Document createXMLErrors(UseCaseConf useCaseConf, List badCtrls) {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(XML_ERROR_TEXT);
        String msg = RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_ISICRESSRV_QRY_ABORT_CAUSE_INVALID_TEXT);
        
        root.addElement(XML_MESSAGE_TEXT).add(DocumentHelper.createCDATA(msg));
        
        for (Iterator it = badCtrls.iterator(); it.hasNext();) {
        	root.addElement(XML_CTRL_ID).addAttribute(XML_ID_TEXT, it.next().toString());
        }
        
        return document;
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
