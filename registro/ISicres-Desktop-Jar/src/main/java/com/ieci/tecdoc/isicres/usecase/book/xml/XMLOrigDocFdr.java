package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrRegorigdoc;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.SaveOrigDocDataDocInput;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version
 * @since
 */
public class XMLOrigDocFdr implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLOrigDocFdr.class);
    private static SimpleDateFormat shortFormatter = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLOrigDocFdr(List list, int type, int canAdd, int canDel, AxSf axsf, Locale locale, boolean bad) {
        shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_ORIGDOCS_TEXT);

        addLibro(type, canAdd, canDel, root);

        int fld8Type = 0;
        if (axsf.getFld8() != null && axsf.getFld8().getScrTypeadm() != null) {
            fld8Type = axsf.getFld8().getScrTypeadm().getId().intValue();
            if (fld8Type != 1) {
                fld8Type = 0;
            }
        }

        String id = "0";
        if (axsf.getFld8() != null) {
            id = axsf.getFld8().getId().toString();
        }
        addRegistro(id, fld8Type, root);
        addCols(locale, root);

        Element docs = root.addElement(XML_DOCS_TEXT);
        if (bad){
        	SaveOrigDocDataDocInput saveOrigDocDataDocInput = null;
	        for (Iterator it = list.iterator(); it.hasNext();) {
	        	saveOrigDocDataDocInput = (SaveOrigDocDataDocInput) it.next();
	            addDocsBad(saveOrigDocDataDocInput, locale, docs);
	        }
        } else {
	        ScrRegorigdoc scr = null;
	        for (Iterator it = list.iterator(); it.hasNext();) {
	            scr = (ScrRegorigdoc) it.next();
	            addDocs(scr, locale, docs);
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

    private static void addLibro(int type, int canAdd, int canDel, Element parent) {
        Element context = parent.addElement(XML_LIBRO_TEXT)
                .addAttribute(XML_TIPO_TEXT, Integer.toString(type))
                .addAttribute(XML_CANADD_TEXT, Integer.toString(canAdd))
                .addAttribute(XML_CANDEL_TEXT, Integer.toString(canDel));
    }

    private static void addRegistro(String id, int propio, Element parent) {
        Element destino = parent.addElement(XML_REGISTRO_TEXT);
        destino.addElement(XML_DESTINO_TEXT).addAttribute(XML_ID_TEXT, id).addAttribute(XML_PROPIO_TEXT,
                Integer.toString(propio));
    }

    private static void addCols(Locale locale, Element parent) {
        Element bodyMinuta = parent.addElement(XML_COLS_TEXT);
        bodyMinuta.addElement(XML_COL_LOWER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSE_ORIGINDOCS_COL1)));
        bodyMinuta.addElement(XML_COL_LOWER_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSE_ORIGINDOCS_COL2)));
    }

    private static void addDocs(ScrRegorigdoc scr, Locale locale, Element parent) {
        Element proc = parent.addElement(XML_PROC_TEXT);
        if (scr.getScrTypeproc() != null) {
            proc.addAttribute(XML_IDPROC_TEXT, scr.getScrTypeproc().getId().toString());
        } else {
            proc.addAttribute(XML_IDPROC_TEXT, "0");
        }
        proc.add(DocumentHelper.createCDATA(scr.getSummary()));

    }

    private static void addDocsBad(SaveOrigDocDataDocInput saveOrigDocDataDocInput, Locale locale, Element parent) {
        Element proc = parent.addElement(XML_PROC_TEXT);
        proc.addAttribute(XML_IDPROC_TEXT, saveOrigDocDataDocInput.getProcId().toString());
        proc.add(DocumentHelper.createCDATA(saveOrigDocDataDocInput.getProcName()));

    }
    
    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

