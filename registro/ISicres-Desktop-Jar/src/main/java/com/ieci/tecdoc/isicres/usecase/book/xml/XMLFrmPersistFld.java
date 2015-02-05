package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;

//import com.ieci.tecdoc.isicres.ejb.common.AxSf;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLFrmPersistFld implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLFrmPersistFld.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLFrmPersistFld(Idocarchdet idocarchdet,
            Integer bookID,
            AxSf axsf,
            boolean includeDate,
            Locale locale) {
        FieldFormat fieldFormat = new FieldFormat(idocarchdet.getDetval());

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_CONFREG_TEXT);

        FFldDef fldDef = null;
        int fldid = 0;
        String text = null;
        for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it.hasNext();) {
            text = "";
            fldDef = (FFldDef) it.next();
            fldid = Integer.parseInt(fldDef.getColname().substring(XML_FLD_TEXT.length(), fldDef.getColname().length()));
            switch (fldid) {
            case 2:
                {
                    if (includeDate) {
                        try {
                            text = axsf.getLocaleAttributeName(bookID,
                                    Integer.toString(fldid),
                                    locale,
                                    fldDef.getName());
                        } catch (Exception e) {
                        }
                        addFld(Integer.toString(fldid), text, root);
                    }
                    break;
                }
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                {
                    try {
                        text = axsf.getLocaleAttributeName(bookID, Integer.toString(fldid), locale, fldDef.getName());
                    } catch (Exception e) {
                    }
                    addFld(Integer.toString(fldid), text, root);
                    break;
                }
            default:
                {
                    if (axsf instanceof AxSfIn) {
                        if (fldid == 14
                                || fldid == 15
                                || fldid == 16
                                || fldid == 17
                                || (fldid > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER + 1 && fldDef.getType() != 2)) {
                            try {
                                text = axsf.getLocaleAttributeName(bookID,
                                        Integer.toString(fldid),
                                        locale,
                                        fldDef.getName());
                            } catch (Exception e) {
                            }
                            addFld(Integer.toString(fldid), text, root);
                        }
                    } else {
                        if (fldid > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER + 1
                                && fldDef.getType() != 2) {
                            try {
                                text = axsf.getLocaleAttributeName(bookID,
                                        Integer.toString(fldid),
                                        locale,
                                        fldDef.getName());
                            } catch (Exception e) {
                            }
                            addFld(Integer.toString(fldid), text, root);
                        }
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

    private static void addFld(String id, String text, Element parent) {
        Element fld = parent.addElement(XML_FLD_UPPERF_TEXT);
        fld.addElement(XML_ID_TEXT).addText(id);
        fld.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

