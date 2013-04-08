package com.ieci.tecdoc.isicres.desktopweb.utils;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author LMVICENTE
 * @creationDate 25-may-2004 9:26:33
 * @version
 * @since
 */
public class XMLDebug {

	private static Logger _logger = Logger.getLogger(XMLDebug.class);
    public static void showXML(Document doc) {
        try {
        	
            if (_logger.isDebugEnabled()) {
	            OutputFormat format = OutputFormat.createPrettyPrint();
	            XMLWriter writer = new XMLWriter(System.out, format);
	            writer.write(doc);
            }
        } catch (Exception e) {
            _logger.fatal("Error al escribir el fichero xml", e);
        }
    }

}