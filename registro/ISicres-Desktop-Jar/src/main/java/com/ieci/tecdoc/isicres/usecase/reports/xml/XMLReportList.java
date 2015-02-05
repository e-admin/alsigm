package com.ieci.tecdoc.isicres.usecase.reports.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrReport;
import com.ieci.tecdoc.isicres.desktopweb.Keys;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLReportList implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLReportList.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLReportList(List reportList) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);
        
        Element nodelist = root.addElement(XML_NODELIST_TEXT);
        
        if (reportList != null && !reportList.isEmpty()) {
            ScrReport scrReport = null;
            String des = null;
            for (Iterator it=reportList.iterator(); it.hasNext();) {
                scrReport = (ScrReport) it.next();
                Element node = nodelist.addElement(XML_NODE_UPPER_TEXT);
                node.addElement(XML_ID_TEXT).addText(scrReport.getId().toString());
                des = "";
                if (scrReport.getDescription() != null) {
                    des = scrReport.getDescription();
                }
                node.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(des));
            }
        } else {
            nodelist.addElement(XML_NODE_UPPER_TEXT);
        }

        return document;
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

