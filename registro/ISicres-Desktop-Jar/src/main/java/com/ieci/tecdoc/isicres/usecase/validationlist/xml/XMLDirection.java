package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

//import java.util.Iterator;
//import java.util.List;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrAddrtel;
import com.ieci.tecdoc.common.invesicres.ScrDom;
import com.ieci.tecdoc.common.invesicres.ScrTypeaddress;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author jcebrien
 * @creationDate 7-jul-2004 11:23:43
 * @version
 * @since
 */
public class XMLDirection extends XMLValidationListAbstract implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLDirection.class);
    private static final String BLANCO = " ";

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLDirection(Document doc, int size, Locale locale, boolean onlyDirection, int typeAddress) {
    	
        Document document = DocumentHelper.createDocument();
        Element root = null;
        Element body = null;
        Element telem = null; 
        Element bodytel = null;
        List nodeList = null;
        List telemList = null;
        if (!onlyDirection){
	        root = document.addElement(XML_PERSONA_TEXT);
	        String firstName = null;
	        String secondName = null;
	        String personType = null;
	
	        addPersonData(doc.selectSingleNode(XPATH_PERSONA_ID).getText(),
	        		doc.selectSingleNode(XPATH_PERSONA_TIPO).getText(),
	        		doc.selectSingleNode(XPATH_PERSONA_TIPODOC).getText(),
	        		doc.selectSingleNode(XPATH_PERSONA_NIF).getText(),
	                parseValues(doc.selectSingleNode(XPATH_PERSONA_APELLIDO1).getText()),
	                parseValues(doc.selectSingleNode(XPATH_PERSONA_APELLIDO2).getText()),
	                parseValues(doc.selectSingleNode(XPATH_PERSONA_NOMBRE).getText()),
	                root,
					locale);
        
	        body = root.addElement(XML_DOMICILIOS_TEXT);
	        nodeList = doc.selectNodes(XPATH_PERSONA_DOMICILIO);
	        
	        bodytel = root.addElement(XML_TELEMATICAS_TEXT);
	        telemList = doc.selectNodes(XPATH_PERSONA_TELEMATICA);
        } else {
        	if (typeAddress == 0){
        		root = document.addElement(XML_DOMICILIOS_TEXT);
        		nodeList = doc.selectNodes(XPATH_DOMICILIOS_DOMICILIO);
        	}else{
        		root = document.addElement(XML_TELEMATICAS_TEXT);
	        	telemList = doc.selectNodes(XPATH_TELEMATICAS_TELEMATICA);
        	}
        }
        if(nodeList != null ){
        if (!nodeList.isEmpty()) {
            Element node = null;
            ScrDom dom = null;
            for (Iterator it=nodeList.iterator(); it.hasNext();) {
                node = (Element) it.next();
                dom = new ScrDom();
                dom.setAddress(((Element)node.selectObject(XML_DIRECCION_TEXT)).getText());
                dom.setCity(((Element)node.selectObject(XML_POBLACION_TEXT)).getText());
                dom.setCountry(((Element)node.selectObject(XML_PROVINCIA_TEXT)).getText());
                dom.setId(new Integer(((Element)node.selectObject(XML_ID_TEXT)).getText()));
                dom.setPreference(new Integer(((Element)node.selectObject(XML_PREFERENCIA_TEXT)).getText()).intValue());
                dom.setZip(((Element)node.selectObject(XML_CODPOSTAL_TEXT)).getText());
                if (!onlyDirection){
                	addDom(dom, body);
                } else {
                	addDom(dom, root);
                }
            }
        }
        }
        if(telemList != null){
        if (!telemList.isEmpty()) {
            Element node = null;
            ScrAddrtel dirtel = null;
            ScrTypeaddress tipo = null;
            for (Iterator it=telemList.iterator(); it.hasNext();) {
                node = (Element) it.next();
                dirtel = new ScrAddrtel();
                dirtel.setAddress(((Element)node.selectObject(XML_DIRECCIONTEL_TEXT)).getText());
                tipo = new ScrTypeaddress();
                tipo.setId(new Integer(((Element)node.selectObject(XML_TIPOTEL_TEXT)).getText()));
                dirtel.setScrTypeaddress(tipo);
                dirtel.setId(new Integer(((Element)node.selectObject(XML_IDTEL_TEXT)).getText()).intValue());
                dirtel.setPreference(new Integer(((Element)node.selectObject(XML_PREFERENCIATEL_TEXT)).getText()).intValue());
                if (!onlyDirection){
                	addDirtel(dirtel, bodytel);
                } else {
                	addDirtel(dirtel, root);
                }
            }
        }
        }
        return document;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/
    
    private static void addDom(ScrDom dom, Element parent) {
    	
        Element row = parent.addElement(XML_DOMICILIO_TEXT);
        if(dom.getAddress()!=null){
        	row.addElement(XML_DIRECCION_TEXT).add(DocumentHelper.createCDATA(dom.getAddress()));
        } else {
        	row.addElement(XML_DIRECCION_TEXT).add(DocumentHelper.createCDATA(BLANCO));
        }
        if(dom.getZip()!=null){
            row.addElement(XML_CODPOSTAL_TEXT).add(DocumentHelper.createCDATA(dom.getZip()));
        } else {
            row.addElement(XML_CODPOSTAL_TEXT).add(DocumentHelper.createCDATA(BLANCO));
        }
        if(dom.getCity()!=null){
        	row.addElement(XML_POBLACION_TEXT).add(DocumentHelper.createCDATA(dom.getCity()));
        } else {
        	row.addElement(XML_POBLACION_TEXT).add(DocumentHelper.createCDATA(BLANCO));
        }
        if(dom.getCountry()!=null){
            row.addElement(XML_PROVINCIA_TEXT).add(DocumentHelper.createCDATA(dom.getCountry()));
        } else {
            row.addElement(XML_PROVINCIA_TEXT).add(DocumentHelper.createCDATA(BLANCO));
        }
       
        row.addElement(XML_ID_TEXT).addText(dom.getId().toString());
        row.addElement(XML_PREFERENCIA_TEXT).addText(Integer.toString(dom.getPreference()));
    }
    
    private static void addDirtel(ScrAddrtel dirtel, Element parent) {
    	
        Element row = parent.addElement(XML_TELEMATICA_TEXT);
        if(dirtel.getAddress()!=null){
        	row.addElement(XML_DIRECCIONTEL_TEXT).add(DocumentHelper.createCDATA(dirtel.getAddress()));
        } else {
        	row.addElement(XML_DIRECCIONTEL_TEXT).add(DocumentHelper.createCDATA(BLANCO));
        }
       
        row.addElement(XML_TIPOTEL_TEXT).add(DocumentHelper.createCDATA(dirtel.getScrTypeaddress().getId().toString()));
        row.addElement(XML_IDTEL_TEXT).addText(new Integer(dirtel.getId()).toString());
        row.addElement(XML_PREFERENCIATEL_TEXT).addText(Integer.toString(dirtel.getPreference()));
    }
    
    private static void addHead(Locale locale, Element parent) {
    	
        Element head = parent.addElement(XML_HEAD_TEXT);

        Element col0 = head.addElement(XML_COL_TEXT);
        col0.addElement(XML_ID_UPPER_TEXT).addText("0");
        col0.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_DIRECTION)));

        Element col1 = head.addElement(XML_COL_TEXT);
        col1.addElement(XML_ID_UPPER_TEXT).addText("1");
        col1.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_CODPOSTAL)));

        Element col2 = head.addElement(XML_COL_TEXT);
        col2.addElement(XML_ID_UPPER_TEXT).addText("2");
        col2.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_CITY)));

        Element col3 = head.addElement(XML_COL_TEXT);
        col3.addElement(XML_ID_UPPER_TEXT).addText("3");
        col3.addElement(XML_NAME_UPPER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_VALIDATIONUSECASE_PROVI)));
    }

    private static void addPersonData(String id,
            String personType,
            String typeDoc,
            String cifNif,
            String firstName,
            String secondName,
            String surName,
            Element parent,
			Locale locale) {
    	
        parent.addElement(XML_ID_TEXT).addText(id);
		parent.addElement(XML_TIPO_TEXT).addText(personType);
        parent.addElement(XML_TIPODOC_TEXT).addText(typeDoc);
        parent.addElement(XML_NIF_TEXT).add(DocumentHelper.createCDATA(cifNif));
        parent.addElement(XML_PERSONA_APELLIDO1_TEXT).add(DocumentHelper.createCDATA(firstName));
        parent.addElement(XML_PERSONA_APELLIDO2_TEXT).add(DocumentHelper.createCDATA(secondName));
        parent.addElement(XML_PERSONA_NOMBRE_TEXT).add(DocumentHelper.createCDATA(surName));
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

