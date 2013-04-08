package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.UpdHisFdrResults;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;


/**
 * @author MABENITO
 * 
 */

public class XMLUpdHisReg implements Keys {

    /***************************************************************************
	 * Attributes
	 **************************************************************************/	
	private static Logger _logger = Logger.getLogger(XMLDistReg.class);
	
    private static SimpleDateFormat shortFormatter = null;	

    /***************************************************************************
	 * Constructors
	 **************************************************************************/

    /***************************************************************************
	 * Public methods
	 **************************************************************************/
	public static Document createXMLUpdHisReg(List list, Integer bookID, int folderId, 
			String num_reg, Locale locale) {
        shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));

        Document document = DocumentHelper.createDocument(); 
                
        Element root = document.addElement(XML_SICRESLIST_TEXT);

        addContext(list.size(), locale, root);  
        addHeadMinuta(locale, root);
        addBodyMinuta(locale, root);
        
        UpdHisFdrResults result = null;
        int i = 0;
        
        for (Iterator it = list.iterator(); it.hasNext();) {
            result = (UpdHisFdrResults) it.next();
            addMinuta(i++, bookID, result, locale, root);        	
        }
        
        return document;
	}

    /***************************************************************************
	 * Protected methods
	 **************************************************************************/

    /***************************************************************************
	 * Private methods
	 **************************************************************************/
		
    private static void addContext(int size, Locale locale, Element parent) {
        Element context = parent.addElement(XML_CONTEXT_TEXT);
        context.addElement(XML_INIT_TEXT).addText("1");
        context.addElement(XML_PASO_TEXT).addText(Integer.toString(size));
        context.addElement(XML_END_TEXT).addText(Integer.toString(size));
        context.addElement(XML_TOTAL_TEXT).addText(Integer.toString(size));
    }
    
    private static void addHeadMinuta(Locale locale, Element parent) {
        Element headMinuta = parent.addElement(XML_HEADMINUTA_TEXT);
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL1)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL2)));
        headMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_UPDATEHISTORY_HEADMINUTA_COL3)));
    }
    
    private static void addBodyMinuta(Locale locale, Element parent) {
        Element bodyMinuta = parent.addElement(XML_BODYMINUTA_TEXT);
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_UPDATEHISTORY_BODYMINUTA_COL4)));
        bodyMinuta.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_UPDATEHISTORY_BODYMINUTA_COL5)));
    }
    
    private static void addMinuta(int index,
            Integer bookID,
            UpdHisFdrResults result,
            Locale locale,
            Element parent) {
        Element minuta = parent.addElement(XML_MINUTA_TEXT).addAttribute(XML_ID_TEXT, Integer.toString(index));

        Element head = minuta.addElement(XML_HEAD_TEXT);
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getScrModifReg().getUsr()));
        

        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        head.addElement(XML_COL_TEXT).addText(formatoDeFecha.format(result.getScrModifReg().getModifDate()).toString());

        
        head.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getNameFld()));

        
        Element body = minuta.addElement(XML_BODY_TEXT);

        Element row = null;

        row = body.addElement(XML_ROW_UPPER_TEXT)
                  .addAttribute(XML_ID_TEXT, result.getScrModifReg().getId().toString())
                  .addAttribute(XML_IDARCH_TEXT, String.valueOf(result.getScrModifReg().getIdArch()));
        
        switch (result.getSustituto()) {
        case 6:{
        	//caso de VALIDATE_ESTADO_REGISTRO
			if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.COMPLETO"))) {
				result.setValue(RBUtil.getInstance(locale).getProperty("book.fld6.0"));
			} else if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.INCOMPLETO"))){
				result.setValue(RBUtil.getInstance(locale).getProperty("book.fld6.1"));
			} else if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.RESERVADO"))){
				result.setValue(RBUtil.getInstance(locale).getProperty("book.fld6.2"));
			} else if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.ANULADO"))){
				result.setValue(RBUtil.getInstance(locale).getProperty("book.fld6.4"));
			} else if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.CERRADO"))){
				result.setValue(RBUtil.getInstance(locale).getProperty("book.fld6.5"));
			} else {
				result.setValue(" ");
			}

			if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.COMPLETO"))) {
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("book.fld6.0"));
			} else if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.INCOMPLETO"))) {
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("book.fld6.1"));
			} else if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.RESERVADO"))) {
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("book.fld6.2"));
			} else if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.ANULADO"))) {
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("book.fld6.4"));
			} else if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld6.CERRADO"))){
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("book.fld6.5"));
			} else {
				result.setOldvalue(" ");
			}
			break;
        }
        case 7:{
        	//caso de VALIDATE_TYPE_REGISTER_TYPE
			if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld11.ENTRADA"))) {
				result.setValue(RBUtil.getInstance(locale).getProperty("bookusecase.node.inBook.name"));
			} else if (result.getValue().equals(RBUtil.getInstance(locale).getProperty("book.fld11.SALIDA"))) {
				result.setValue(RBUtil.getInstance(locale).getProperty("bookusecase.node.outBook.name"));
			} else {
				result.setValue(" ");
			}

			if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld11.ENTRADA"))) {
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("bookusecase.node.inBook.name"));
			} else if (result.getOldvalue().equals(RBUtil.getInstance(locale).getProperty("book.fld11.SALIDA"))){
				result.setOldvalue(RBUtil.getInstance(locale).getProperty("bookusecase.node.outBook.name"));
			} else {
				result.setOldvalue(" ");
			}        	

        	break;
        }
        }
        
        row.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getOldvalue()));
        row.addElement(XML_COL_TEXT).add(DocumentHelper.createCDATA(result.getValue()));
      
    }

    /***************************************************************************
	 * Inner classes
	 **************************************************************************/

    /***************************************************************************
	 * Test brench
	 **************************************************************************/    
}


	

