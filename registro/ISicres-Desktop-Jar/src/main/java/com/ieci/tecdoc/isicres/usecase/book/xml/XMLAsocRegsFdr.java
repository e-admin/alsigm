package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesdoc.Idocarchhdr;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLAsocRegsFdr implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLAsocRegsFdr.class);
    private static SimpleDateFormat longFormatter = null;
    private static final String GUION = " - ";
    
    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLAsocRegsFdr(Map idocs, Map axsfs, Locale locale) {
        longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
        
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_ASOCREGS_TEXT);

        AxPK axpk = null;
        AxSf axsf = null;
        Idocarchhdr idoc = null;
         
        for (Iterator it = axsfs.keySet().iterator(); it.hasNext();) {
            axpk = (AxPK) it.next();
            idoc = (Idocarchhdr) idocs.get(new Integer(axpk.getType()));
            axsf = (AxSf) axsfs.get(axpk);

            addRegister(idoc, axsf, locale, root);
        }

        return document;
    }
    
    public static Document createXMLAsocRegsFdr(Map idocs, Map axsfs, Map axsfPrim, Locale locale) {
        longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
        
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_ASOCREGS_TEXT);

        for (Iterator it = axsfPrim.keySet().iterator(); it.hasNext();) {
        	AxPK axpk = (AxPK) it.next();
            Idocarchhdr idoc = (Idocarchhdr) idocs.get(new Integer(axpk.getType()));
            AxSf axsf = (AxSf) axsfPrim.get(axpk);
            
            addRegisterParent(idoc, axsf, locale, root);
        }
         
        for (Iterator it = axsfs.keySet().iterator(); it.hasNext();) {
        	AxPK axpk = (AxPK) it.next();
            Idocarchhdr idoc = (Idocarchhdr) idocs.get(new Integer(axpk.getType()));
            AxSf axsf = (AxSf) axsfs.get(axpk);

            addRegister(idoc, axsf, locale, root);
        }

        return document;
    }
    
    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private static void addRegisterParent(Idocarchhdr idoc, AxSf axsf, Locale locale, Element parent) {
        Element register = parent.addElement(XML_REGISTER_PARENT_TEXT);
        
        Element book = register.addElement(XML_BOOK_TEXT);
        book.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(idoc.getName()));

        addField("0", idoc.getId().toString(), locale, register);
        addField("0", axsf.getAttributeValueAsString("fdrid"), locale, register);
        addField("1", axsf.getAttributeValueAsString("fld1"), locale, register);
        addField("2", longFormatter.format((Date) axsf.getAttributeValue("fld2")), locale, register);

        int value = 0;
        if (axsf.getAttributeValue("fld6") instanceof BigDecimal) {
            value = ((BigDecimal) axsf.getAttributeValue("fld6")).intValue();
        } else if (axsf.getAttributeValue("fld6") instanceof Integer) {
            value = ((Integer) axsf.getAttributeValue("fld6")).intValue();
        }
        String text = RBUtil.getInstance(locale).getProperty("book." + "fld6" + "." + value, "");
        addField("6", text, locale, register);

        text = "";
        if (axsf.getFld5() != null) {
            text = axsf.getFld5().getCode() + GUION + axsf.getFld5().getName();
        }
        addField("5", text, locale, register);

        text = "";
        if (axsf.getFld7() != null) {
            text = axsf.getFld7().getCode() + GUION + axsf.getFld7().getName();
        }
        addField("7", text, locale, register);

        text = "";
        if (axsf.getFld8() != null) {
            text = axsf.getFld8().getCode() + GUION + axsf.getFld8().getName();
        }
        addField("8", text, locale, register);


        if (axsf instanceof AxSfIn) {
            text = RBUtil.getInstance(locale).getProperty("asocregfdr.9.in", "");
        } else {
            text = RBUtil.getInstance(locale).getProperty("asocregfdr.9.out", "");
        }
        addField("9", text, axsf.getAttributeValueAsString("fld9"), locale, register);

        text = "";
        ScrCa scr = null;
        if (axsf instanceof AxSfIn) {
            scr = ((AxSfIn) axsf).getFld16();
        } else {
            scr = ((AxSfOut) axsf).getFld12();
        }
        if (scr != null) {
            text = scr.getCode() + GUION + scr.getMatter();
        }
        addField("16", text, locale, register);

        if (axsf instanceof AxSfIn) {
            text = axsf.getAttributeValueAsString("fld17");
        } else {
            text = axsf.getAttributeValueAsString("fld13");
        }
        addField("17", text, locale, register);
    }
    
    private static void addRegister(Idocarchhdr idoc, AxSf axsf, Locale locale, Element parent) {
        Element register = parent.addElement(XML_REGISTER_TEXT);

        Element book = register.addElement(XML_BOOK_TEXT);
        book.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(idoc.getName()));

        addField("0", idoc.getId().toString(), locale, register);
        addField("0", axsf.getAttributeValueAsString("fdrid"), locale, register);
        addField("1", axsf.getAttributeValueAsString("fld1"), locale, register);
        addField("2", longFormatter.format((Date) axsf.getAttributeValue("fld2")), locale, register);

        int value = 0;
        if (axsf.getAttributeValue("fld6") instanceof BigDecimal) {
            value = ((BigDecimal) axsf.getAttributeValue("fld6")).intValue();
        } else if (axsf.getAttributeValue("fld6") instanceof Integer) {
            value = ((Integer) axsf.getAttributeValue("fld6")).intValue();
        }
        String text = RBUtil.getInstance(locale).getProperty("book." + "fld6" + "." + value, "");
        addField("6", text, locale, register);

        text = "";
        if (axsf.getFld5() != null) {
            text = axsf.getFld5().getCode() + GUION + axsf.getFld5().getName();
        }
        addField("5", text, locale, register);

        text = "";
        if (axsf.getFld7() != null) {
            text = axsf.getFld7().getCode() + GUION + axsf.getFld7().getName();
        }
        addField("7", text, locale, register);

        text = "";
        if (axsf.getFld8() != null) {
            text = axsf.getFld8().getCode() + GUION + axsf.getFld8().getName();
        }
        addField("8", text, locale, register);


        if (axsf instanceof AxSfIn) {
            text = RBUtil.getInstance(locale).getProperty("asocregfdr.9.in", "");
        } else {
            text = RBUtil.getInstance(locale).getProperty("asocregfdr.9.out", "");
        }
        addField("9", text, axsf.getAttributeValueAsString("fld9"), locale, register);

        text = "";
        ScrCa scr = null;
        if (axsf instanceof AxSfIn) {
            scr = ((AxSfIn) axsf).getFld16();
        } else {
            scr = ((AxSfOut) axsf).getFld12();
        }
        if (scr != null) {
            text = scr.getCode() + GUION + scr.getMatter();
        }
        addField("16", text, locale, register);

        if (axsf instanceof AxSfIn) {
            text = axsf.getAttributeValueAsString("fld17");
        } else {
            text = axsf.getAttributeValueAsString("fld13");
        }
        addField("17", text, locale, register);
    }

    private static void addField(String id, String value, Locale locale, Element parent) {
        addField(id, RBUtil.getInstance(locale).getProperty("asocregfdr." + id), value, locale, parent);
    }

    private static void addField(String id, String label, String value, Locale locale, Element parent) {
        if (value == null) {
            value = "";
        }
        Element field = parent.addElement(XML_FIELD_TEXT).addAttribute(XML_ID_TEXT, id);
        field.addElement(XML_LABEL_TEXT).add(DocumentHelper.createCDATA(label));
        field.addElement(XML_VALUE_TEXT).add(DocumentHelper.createCDATA(value));
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

