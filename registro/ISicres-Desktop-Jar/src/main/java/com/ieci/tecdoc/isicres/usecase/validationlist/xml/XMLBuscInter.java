package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-jun-2004 10:05:43
 * @version
 * @since
 */
public class XMLBuscInter extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLBuscInter.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLBuscInter(Document doc, int size,
			Locale locale, String caseSensitive) {
    	StringBuffer columnText1 = null;
    	String nombre = "";
    	String apellido1 = "";
    	String apellido2 = "";
    	Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_SICRESLIST_TEXT);

		addContext(1, size, size, size, 0, 0, RBUtil.getInstance(locale)
				.getProperty(I18N_VALIDATIONUSECASE_POPULATION), null, null,
				root, caseSensitive);
        addHead(locale, root);
        List nodes = doc.selectNodes(XPATH_PERSONAS_PERSONA);
        Element node = null;
        int index = 0;
        for (Iterator it = nodes.iterator(); it.hasNext();){
        	columnText1 = new StringBuffer();
        	node = (Element) it.next();
        	node.addElement("INDEX").addText(Integer.toString(index++));
        	nombre = ((Element)node.selectObject(XML_PERSONA_NOMBRE_TEXT)).getText();
        	apellido1 = ((Element)node.selectObject(XML_PERSONA_APELLIDO1_TEXT)).getText();
        	apellido2 = ((Element)node.selectObject(XML_PERSONA_APELLIDO2_TEXT)).getText();
        	if (apellido1 != null && !apellido1.equals("")){
        		columnText1.append(apellido1);
        	}
        	if (apellido2 != null && !apellido2.equals("")){
                if (apellido1 != null && !apellido1.equals("")) {
                    columnText1.append(" ");
                }
        		columnText1.append(apellido2);
        	}
        	if (nombre != null && !nombre.equals("")){
                if (apellido1 != null && !apellido1.equals("") && apellido2 != null && !apellido2.equals("")) {
                    columnText1.append(", ");
                }
        		columnText1.append(nombre);
        	}
            node.addElement(XML_COLUMNTEXT_TEXT+"2").add(DocumentHelper.createCDATA(columnText1.toString()));

        }
        root.appendContent(doc);

        return document;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private static void addHead(Locale locale, Element parent) {
        Element head = parent.addElement(XML_HEAD_TEXT);

        Element col0 = head.addElement(XML_COL_TEXT);
        col0.addElement(XML_ID_UPPER_TEXT).addText("0");
        col0.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_DOCUMENT)));

        Element col1 = head.addElement(XML_COL_TEXT);
        col1.addElement(XML_ID_UPPER_TEXT).addText("1");
        col1.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_NAME)));
    }

    public static void parseXML (Document doc)
    {
		List nodeList = doc.selectNodes(Keys.XPATH_PERSONAS_PERSONA);
		Element node = null;
        for (Iterator it=nodeList.iterator(); it.hasNext();) {
            node = (Element) it.next();
            String appellido1 = parseValues(((Element)node.selectObject(XML_PERSONA_APELLIDO1_TEXT)).getText());
            String appellido2 = parseValues(((Element)node.selectObject(XML_PERSONA_APELLIDO2_TEXT)).getText());
            String nombre = parseValues(((Element)node.selectObject(XML_PERSONA_NOMBRE_TEXT)).getText());
            ((Element)node.selectObject(XML_PERSONA_APELLIDO1_TEXT)).setText(appellido1);
            ((Element)node.selectObject(XML_PERSONA_APELLIDO2_TEXT)).setText(appellido2);
            ((Element)node.selectObject(XML_PERSONA_NOMBRE_TEXT)).setText(nombre);
        }
    }
    public static Document createXMLValidationBuscInter(Locale locale ) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_VALIDATION_TEXT);

        addNode(locale, root);

    	return document;
    }

    private static void addNode(Locale locale, Element parent) {
		parent.addElement(XML_ACTION_TEXT).addText("SearchInt");
		parent.addElement(XML_FLDID_TEXT).addText("9");
		parent.addElement(XML_ERROR_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale).getProperty(
				I18N_VALIDATION_ERROR_BUSCINTER)));

	}

    private static String parseValues (String value)
    {
    	String result = value;
       if (value == null) return "";

       char c;

       result = result.replaceAll("\"","'");
       result = result.replaceAll("\\n","");
       result = result.replaceAll("\\r","");
       return result;
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

