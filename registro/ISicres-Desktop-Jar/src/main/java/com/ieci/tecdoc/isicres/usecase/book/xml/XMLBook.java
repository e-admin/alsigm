package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.idoc.decoder.form.FCtrlDef;
import com.ieci.tecdoc.idoc.decoder.form.FPageDef;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxXf;
import com.ieci.tecdoc.common.isicres.SessionInformation;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version @since
 */
public class XMLBook implements Keys {

    /***************************************************************************
     * Attributes
     **************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLBook.class);

	private static SimpleDateFormat longFormatter = null;

    private static SimpleDateFormat shortFormatter = null;

    private static final String ALMOHADILLA = "#";

    private static final String ALMOHADILLAS = "##";

    private static final String DOSAMPERSAN = "&&";

    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public static Document createXMLBook(boolean readOnly, int updatePer, int addPer, int updateProField,
            int updateRegisterDate, AxSf axsf, FieldFormat fieldformat, Integer bookID, int page, Locale locale, Map extendedValues,
            String origen, String destino, String additionalInfo, SessionInformation sessionInformation) {
        String data = axsf.getFormat().getData();
        FormFormat formFormat = new FormFormat(data);

        longFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_LONGFORMAT));
        shortFormatter = new SimpleDateFormat(RBUtil.getInstance(locale).getProperty(I18N_DATE_SHORTFORMAT));

        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_FRMDATA_TEXT);

        addParams(updatePer, addPer, additionalInfo, root);

        if (formFormat.getDlgDef() != null && formFormat.getDlgDef().getPagedefs() != null
                && !formFormat.getDlgDef().getPagedefs().isEmpty()) {

            TreeMap pages = formFormat.getDlgDef().getPagedefs();
            FPageDef pageDef = (FPageDef) pages.get(new Integer(page));
            TreeMap ctrls = pageDef.getCtrldefs();
            FCtrlDef ctrlDef = null;

            int width = XMLUtils.convertWidth(formFormat.getDlgDef().getR() - formFormat.getDlgDef().getL());
            int height = XMLUtils.convertWidth(formFormat.getDlgDef().getB() - formFormat.getDlgDef().getT());
            String title = "";
            try {
                title = axsf.getLocaleAttributePage(locale, pageDef.getTitle());
            } catch (Exception e) {
            }
            addPageData("", formFormat.getDlgDef().getPagedefs().size(), page, width, height, root);

            for (Iterator it = ctrls.values().iterator(); it.hasNext();) {
                ctrlDef = (FCtrlDef) it.next();
                if (ctrlDef.getName().startsWith(IDOC_EDIT)) {
                    createTypeEdit(readOnly, ctrlDef, axsf, fieldformat, root, locale, extendedValues, updateProField,
                            updateRegisterDate, origen, destino, sessionInformation.getCaseSensitive());
                } else if (ctrlDef.getName().startsWith(IDOC_STATIC)) {
                    String text = ctrlDef.getText();
                    try {
                        text = axsf.getLocaleAttributeNameForm(locale, text);
                    } catch (Exception e) {
                    }
                    addControlTypeStatic(ctrlDef.getClassId(), ctrlDef.getStyle(), ctrlDef.getFldId(), text,
                            ctrlDef.getL(), ctrlDef.getT(), ctrlDef.getR(), ctrlDef.getB(), ctrlDef.getFontName(),
                            ctrlDef.getFontSize(), ctrlDef.getFontEnh(), ctrlDef.getFontColor(), ctrlDef.getRole(), root);
                } else if (ctrlDef.getName().startsWith(IDOC_BUTTON)) {
                    String text = ctrlDef.getText();
                    try {
                        text = axsf.getLocaleAttributeNameForm(locale, text);
                    } catch (Exception e) {
                    }
                    addControlTypeButton(ctrlDef.getClassId(), ctrlDef.getStyle(), ctrlDef.getFldId(), text,
                            ctrlDef.getL(), ctrlDef.getT(), ctrlDef.getR(), ctrlDef.getB(), root);
                }
            }
        }

        return document;
    }

    /***************************************************************************
     * Protected methods
     **************************************************************************/

    /***************************************************************************
     * Private methods
     **************************************************************************/

    private static void createTypeEdit(boolean readOnly, FCtrlDef ctrlDef, AxSf axsf, FieldFormat fieldformat, Element parent,
            Locale locale, Map extendedValues, int updateProField, int updateRegisterDate, String origen,
            String destino, String caseSensitive) {
        String fldName = XML_FLD_TEXT + ctrlDef.getFldId();
        String text = "";
        int len = 0;

        if (axsf.getLenFields().containsKey(new Integer(ctrlDef.getFldId()))){
        	len = ((Integer) axsf.getLenFields().get(new Integer(ctrlDef.getFldId()))).intValue();
        }

        if (fldName.equals(AxSf.FLD5_FIELD)) {
            text = getRegOff(axsf.getFld5(), ctrlDef.getRole(), axsf.getFld5Name());
        } else if (fldName.equals(AxSf.FLD7_FIELD)) {
            text = getAdminUnit(axsf.getFld7(), ctrlDef.getRole(), origen);
        } else if (fldName.equals(AxSf.FLD8_FIELD)) {
            text = getAdminUnit(axsf.getFld8(), ctrlDef.getRole(), destino);
        } else if (fldName.equals(AxSf.FLD13_FIELD) && axsf instanceof AxSfIn) {
            text = getRegAdmin(((AxSfIn) axsf).getFld13(), ctrlDef.getRole(), ((AxSfIn) axsf).getFld13Name());
        } else if (fldName.equals(AxSf.FLD16_FIELD) && axsf instanceof AxSfIn) {
            text = getSubjType(((AxSfIn) axsf).getFld16(), ctrlDef.getRole(), ((AxSfIn) axsf).getFld16Name());
        } else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfOut) {
            text = getSubjType(((AxSfOut) axsf).getFld12(), ctrlDef.getRole(), ((AxSfOut) axsf).getFld12Name());
        } else if (fldName.equals(AxSf.FLD12_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAttributeValue(fldName) != null) {
                text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
            }
        } else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAttributeValue(fldName) != null) {
                text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
            }
        } else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAttributeValue(fldName) != null) {
                text = longFormatter.format((Date) axsf.getAttributeValue(fldName));
            }
        } else if (fldName.equals(AxSf.FLD2_FIELD) && axsf instanceof AxSfOut) {
            if (axsf.getAttributeValue(fldName) != null) {
                text = longFormatter.format((Date) axsf.getAttributeValue(fldName));
            }
        } else if (fldName.equals(AxSf.FLD4_FIELD) && axsf instanceof AxSfOut) {
            if (axsf.getAttributeValue(fldName) != null) {
                text = shortFormatter.format((Date) axsf.getAttributeValue(fldName));
            }
        } else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAttributeValue(fldName) != null) {
                int value = 0;
                try {
                    value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                } catch (ClassCastException e) {
                    value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                }
                text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
            }
        } else if (fldName.equals(AxSf.FLD6_FIELD) && axsf instanceof AxSfOut) {
            if (axsf.getAttributeValue(fldName) != null) {
                int value = 0;
                try {
                    value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                } catch (ClassCastException e) {
                    value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                }
                text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
            }
        } else if (fldName.equals(AxSf.FLD11_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAttributeValue(fldName) != null) {
                int value = 0;
                try {
                    value = ((BigDecimal) axsf.getAttributeValue(fldName)).intValue();
                } catch (ClassCastException e) {
                    value = ((Integer) axsf.getAttributeValue(fldName)).intValue();
                }
                text = RBUtil.getInstance(locale).getProperty("book." + fldName + "." + value, text);
            }
        } else if (fldName.equals(AxSf.FLD14_FIELD) && axsf instanceof AxSfOut) {
            if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
                text = axsf.getAxxf().getText();
            }
        } else if (fldName.equals(AxSf.FLD18_FIELD) && axsf instanceof AxSfIn) {
            if (axsf.getAxxf() != null && axsf.getAxxf().getText() != null) {
                text = axsf.getAxxf().getText();
            }
        } else if (axsf.getExtendedFields().containsKey(new Integer(ctrlDef.getFldId()))) {
            AxXf extendedField = (AxXf) axsf.getExtendedFields().get(new Integer(ctrlDef.getFldId()));
            if (extendedField != null && extendedField.getText() != null) {
                text = extendedField.getText();
            }
        } else if (axsf instanceof AxSfIn
                && ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER
                && (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4)) {
            if (extendedValues.containsKey(new Integer(ctrlDef.getFldId()))) {
                Map aux = (Map) extendedValues.get(new Integer(ctrlDef.getFldId()));
                if (aux != null && !aux.isEmpty()) {
                    if (aux.containsKey(axsf.getAttributeValueAsString(fldName))) {
                        text = (String) aux.get(axsf.getAttributeValueAsString(fldName));
                    } else {
                        text = "";
                    }
                } else {
                    text = "";
                }
            }
        } else if (axsf instanceof AxSfOut
                && ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER
                && (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4)) {
            if (extendedValues.containsKey(new Integer(ctrlDef.getFldId()))) {
                Map aux = (Map) extendedValues.get(new Integer(ctrlDef.getFldId()));
                if (aux != null && !aux.isEmpty()) {
                    if (aux.containsKey(axsf.getAttributeValueAsString(fldName))) {
                        text = (String) aux.get(axsf.getAttributeValueAsString(fldName));
                    } else {
                        text = "";
                    }
                } else {
                    text = "";
                }
            }
        } else {
            if (axsf.getAttributeValue(fldName) != null) {
                text = axsf.getAttributeValue(fldName).toString();
            }

            if (_logger.isDebugEnabled()) {
	            _logger.debug("ctrlDef => " + ctrlDef);
	            _logger.debug("axsf.getAttributeClass(fldName) => " + axsf.getAttributeClass(fldName));
	            _logger.debug("axsf.getAttributeValue(fldName) => " + axsf.getAttributeValue(fldName));
	            if (axsf.getAttributeValue(fldName) != null) {
	            	_logger.debug("axsf.getAttributeValue(fldName) => " + axsf.getAttributeValue(fldName).getClass().getName());
	            }
            }

            if (axsf.getAttributeValue(fldName) != null && axsf.getAttributeClass(fldName) != null) {
                if (axsf.getAttributeClass(fldName).equals(Date.class)) {
					text = XMLUtils.getDateWithFormat(longFormatter,
							shortFormatter,
							(Date) axsf.getAttributeValue(fldName), axsf,
							fieldformat, ctrlDef.getFldId());
                }
            } else if (axsf.getAttributeClass(fldName) == null) {
                if (axsf.getAttributeValue(fldName) instanceof Date) {
					text = XMLUtils.getDateWithFormat(longFormatter,
							shortFormatter,
							(Date) axsf.getAttributeValue(fldName), axsf,
							fieldformat, ctrlDef.getFldId());
                }
                if (axsf.getAttributeValue(fldName) instanceof java.sql.Date) {
					text = XMLUtils.getDateWithFormat(
							longFormatter,
							shortFormatter,
							new Date(((java.sql.Date) axsf
									.getAttributeValue(fldName)).getTime()),
							axsf, fieldformat, ctrlDef.getFldId());
                }
                if (axsf.getAttributeValue(fldName) instanceof Timestamp) {
					text = XMLUtils.getDateWithFormat(
							longFormatter,
							shortFormatter,
							new Date(((Timestamp) axsf
									.getAttributeValue(fldName)).getTime()),
							axsf, fieldformat, ctrlDef.getFldId());
                }
            }
        }
        int dataType = XMLUtils.getDataType(axsf, fieldformat, ctrlDef.getFldId());

        if (readOnly) {
        	int tblVal = 0;

        	if(ctrlDef.getFldId() == 9){
        		tblVal = getValidation(axsf, ctrlDef.getFldId(), extendedValues.keySet());
        	}

            addControlTypeEdit(ctrlDef.getClassId(), ctrlDef.getStyle(), ctrlDef.getId(), ctrlDef.getFldId(),
                    XML_TRUE_VALUE, XML_FALSE_VALUE, tblVal, 0, XML_FALSE_VALUE, text, ctrlDef.getL(), ctrlDef.getT(),
                    ctrlDef.getR(), ctrlDef.getB(), ctrlDef.getFontName(), ctrlDef.getFontSize(),
                    ctrlDef.getFontEnh(), ctrlDef.getFontColor(), ctrlDef.getRole(), null, null, parent, len, dataType, caseSensitive);
        } else {
            if (text == null) {
                text = "";
            }
            addTypeEditNotReadOnly(readOnly, ctrlDef, axsf, dataType, parent, locale, extendedValues, updateProField,
                    updateRegisterDate, text, origen, destino, len, caseSensitive);
        }
    }

    private static void addTypeEditNotReadOnly(boolean readOnly, FCtrlDef ctrlDef, AxSf axsf, int dataType, Element parent,
            Locale locale, Map extendedValues, int updateProField, int updateRegisterDate, String text,
            String origen, String destino, int len, String caseSensitive) {
        readOnly = getReadOnly(ctrlDef, axsf, updateProField, updateRegisterDate, text);
        String readOnlyString = XML_FALSE_VALUE;
        if (readOnly) {
            readOnlyString = XML_TRUE_VALUE;
        }

        int tblVal = getValidation(axsf, ctrlDef.getFldId(), extendedValues.keySet());
        int tblFldId = tblVal;
        if (tblVal != 0) {
            tblFldId = ctrlDef.getFldId();
        }

        String isSust = XML_FALSE_VALUE;
        if (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4) {
            isSust = XML_TRUE_VALUE;
        }

        String cadena = null;
        if (tblVal == 6 && isSust.equals(XML_FALSE_VALUE)) {
            if (ctrlDef.getFldId() == 9) {
                cadena = text;
                text = "";
            }
        }

        String isResum = null;
        if (ctrlDef.getFldId() == 17 && axsf instanceof AxSfIn) {
            isResum = XML_TRUE_VALUE;
        } else if (ctrlDef.getFldId() == 13 && axsf instanceof AxSfOut) {
            isResum = XML_TRUE_VALUE;
        }

        addControlTypeEdit(ctrlDef.getClassId(), ctrlDef.getStyle(), ctrlDef.getId(), ctrlDef.getFldId(),
                readOnlyString, XML_FALSE_VALUE, tblVal, tblFldId, isSust, text, ctrlDef.getL(), ctrlDef
                        .getT(), ctrlDef.getR(), ctrlDef.getB(), ctrlDef.getFontName(), ctrlDef.getFontSize(),
                        ctrlDef.getFontEnh(), ctrlDef.getFontColor(), ctrlDef.getRole(), cadena, isResum, parent, len, dataType, caseSensitive);
    }

    private static boolean getReadOnly(FCtrlDef ctrlDef, AxSf axsf, int updateProField,
            int updateRegisterDate, String text) {
        if (updateProField == 0) {
            // Sino tiene el permiso, miramos si esta vacio el text para darselo
            if (text.equals("")) {
                updateProField = 1;
            }
        }

        if (ctrlDef.getClassId() == 129) {
            if ((ctrlDef.getStyle() & 2048) == 2048) {
                return true;
            } else if ((ctrlDef.getStyle() & 134217728) == 134217728) {
                return true;
            }
        }

        if (ctrlDef.getRole() == 10 || ctrlDef.getRole() == 4) {
            return true;
        } else if (axsf instanceof AxSfIn) {
            // Libro de entrada
            switch (ctrlDef.getFldId()) {
            case 2:
                return !(updateRegisterDate == 1);
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return false;
            case 7:
            case 8:
            case 9:
            case 16:
            case 17: {
                if (updateProField == 0) {
                    // Sino tiene el permiso, miramos si esta vacio el text para
                    // darselo
                    return !(text.equals(""));
                } else {
                    return false;
                }
            }
            default: {
                if (ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
                    return false;
                } else {
                    return true;
                }
            }
            }
        } else if (axsf instanceof AxSfOut) {
            // Libro de salida
            switch (ctrlDef.getFldId()) {
            case 2:
                return !(updateRegisterDate == 1);
            case 10:
            case 11:
                return false;
            case 7:
            case 8:
            case 9:
            case 12:
            case 13: {
                if (updateProField == 0) {
                    // Sino tiene el permiso, miramos si esta vacio el text para
                    // darselo
                    return !(text.equals(""));
                } else {
                    return false;
                }
            }
            default: {
                if (ctrlDef.getFldId() > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
                    return false;
                } else {
                    return true;
                }
            }
            }
        } else {
            return true;
        }
    }

    private static String getRegOff(ScrOfic ofic, int role, String fldName) {
        String text = "";
        if (ofic != null) {
            if (role == 10 || role == 4) {
                text = fldName;
            } else {
                text = ofic.getCode();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static String getAdminUnit(ScrOrg org, int role, String name) {
        String text = "";
        if (org != null) {
            if (role == 10 || role == 4) {
                text = name;
            } else {
                text = org.getCode();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static String getRegAdmin(ScrOrg org, int role, String fldName) {
        String text = "";
        if (org != null) {
            if (role == 10 || role == 4) {
            	text = fldName;
            } else {
                text = org.getCode();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static String getSubjType(ScrCa ca, int role, String fldName) {
        String text = "";
        if (ca != null) {
            if (role == 10 || role == 4) {
            	text = fldName;
            } else {
                text = ca.getCode();
            }
            if (text == null) {
                text = "";
            }
        }
        return text;
    }

    private static void addParams(int canUpdatePer, int canAddPer, String addInfo, Element parent) {
        Element node = parent.addElement(XML_PARAMS_TEXT);
        node.addElement(XML_CANUPDATEPER_TEXT).addText(Integer.toString(canUpdatePer));
        node.addElement(XML_CANADDPER_TEXT).addText(Integer.toString(canAddPer));
        node.addElement(XML_ADDINFO_TEXT).add(DocumentHelper.createCDATA(addInfo));
    }

    private static void addPageData(String title, int pages, int page, int width, int height, Element parent) {
        Element node = parent.addElement(XML_PAGEDATA_TEXT);
        node.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(title));
        node.addElement(XML_PAGES_TEXT).addText(Integer.toString(pages));
        node.addElement(XML_PAGE_TEXT).addText(Integer.toString(page));
        node.addElement(XML_WIDTH_TEXT).addText(Integer.toString(width));
        node.addElement(XML_HEIGHT_TEXT).addText(Integer.toString(height));
    }

    private static void addControlTypeEdit(int classType, int style, int id, int fldid, String readOnly,
            String obli, int tblval, int tblfldid, String issust, String text, int left, int top, int width,
            int height, String fontName, int fontSize, int fontEnh, int fontColor,
            int role, String cadena, String isResum, Element parent, int len, int dataType, String caseSensitive) {
        if (text == null) {
            text = "";
        }
        Element node = parent.addElement(XML_CONTROL_TEXT);
        node.addElement(XML_CLASS_TEXT).addText(getClassEditType(classType, style));
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_FLDID_TEXT).addText(Integer.toString(fldid));
        node.addElement(XML_READONLY_TEXT).addText(readOnly);
        node.addElement(XML_OBLI_TEXT).addText(obli);
        node.addElement(XML_TBLVAL_TEXT).addText(Integer.toString(tblval));
        node.addElement(XML_ISVISIBLE_TEXT).addText(getVisible(style));

        String formText = "";
        if (cadena != null) {
            node.addElement(XML_CADENA_TEXT).add(DocumentHelper.createCDATA(cadena));
        } else if (fldid == 9 && tblval == 0) {
            StringTokenizer doblesAmpersan = new StringTokenizer(text, DOSAMPERSAN);
            while (doblesAmpersan.hasMoreTokens()) {
                StringTokenizer almohadilla = new StringTokenizer(doblesAmpersan.nextToken(), ALMOHADILLA);
                while (almohadilla.hasMoreTokens()) {
                    almohadilla.nextToken();
                    if (formText == "") {
                        formText = almohadilla.nextToken();
                    } else if (getClassEditType(classType, style).equals(XML_TEXTAREA_VALUE)) {
                        formText = formText + "%0D" + almohadilla.nextToken();
                    }
                    break;
                }
            }
            try {
                formText = URLDecoder.decode(formText, "UTF-8");
            } catch (Exception e) {
            }
            text = formText;
        }
        if (isResum != null) {
            node.addElement(XML_ISRESUM_TEXT).addText(isResum);
        }

        node.addElement(XML_TBLFLDID_TEXT).addText(Integer.toString(tblfldid));
        node.addElement(XML_ISSUST_TEXT).addText(issust);
        if(len != 0){
        	node.addElement(XML_MAXLENGTH_TEXT).addText(Integer.toString(len));
        }
        node.addElement(XML_DATATYPE_TEXT).addText(Integer.toString(dataType));
        node.addElement(XML_CASESENSITIVE_TEXT).addText(caseSensitive);

        if((fldid == 9) && (tblval != 0)){
        	node.addElement(XML_CADENA_TEXT).add(DocumentHelper.createCDATA(text));
        }else{
 	       node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
        }

        XMLUtils.addStyleNode(node, left, top, width, height, fontName, fontSize, fontEnh, fontColor, style, role);
        XMLUtils.addStyleValNode(node, left, width, top);
    }

    /**
     * Devuelve si el control es visible o no. El Bit 29 del valor de style indica
     * si es visible (1) o no (0).
     * @param style Valor de estilo del campo
     * @return Devuelve "1" si es visible o "0" si no lo es.
     */
    private static String getVisible(int style) {
    	if (((style >>> 28) & 1) == 1) {
    		return "1";
    	} else {
    		return "0";
    	}
    }   

    private static void addControlTypeButton(int classType, int style, int id, String text, int left,
            int top, int width, int height, Element parent) {
        Element node = parent.addElement(XML_CONTROL_TEXT);

        node.addElement(XML_CLASS_TEXT).addText(getClassEditType(classType, style));
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
        XMLUtils.addStyleNode(node, left, top, width, height);
    }

    private static void addControlTypeStatic(int classType, int style, int id, String text, int left,
            int top, int width, int height, String fontName, int fontSize, int fontEnh, int fontColor,
            int role, Element parent) {
        Element node = parent.addElement(XML_CONTROL_TEXT);

        node.addElement(XML_CLASS_TEXT).addText(getClassEditType(classType, style));
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_TEXT_TEXT).add(DocumentHelper.createCDATA(text));
        node.addElement(XML_ISVISIBLE_TEXT).addText(getVisible(style));
        XMLUtils.addStyleNode(node, left, top, width, height, fontName, fontSize, fontEnh, fontColor, style, role);
    }

    private static String getClassEditType(int classType, int style) {
        switch (classType) {
        case 128:
            return XML_BUTTON_VALUE;
        case 130:
            return XML_STATIC_VALUE;
        case 131:
            return XML_LBOX_VALUE;
        case 132:
            return XML_SBAR_VALUE;
        case 133:
            return XML_CBOX_VALUE;
        case 129:
            if ((style & 4) == 4) {
                return XML_TEXTAREA_VALUE;
            } else {
                return XML_EDIT_VALUE;
            }
        default: {
            return "";
        }
        }
    }

    private static int getValidation(AxSf axsf, int fldId, Collection validationFiels) {
        int validation = 0;

        if (axsf instanceof AxSfIn) {
            if (fldId == 5) {
                validation = 4;
            } else if (fldId == 7 || fldId == 8) {
                validation = 1;
            } else if (fldId == 9) {
                validation = 6;
            } else if (fldId == 11) {
                validation = 7;
            } else if (fldId == 13) {
                validation = 5;
            } else if (fldId == 14) {
                validation = 2;
            } else if (fldId == 16) {
                validation = 3;
            } else if (fldId > com.ieci.tecdoc.common.isicres.Keys.EREG_FDR_MATTER) {
                if (validationFiels.contains(new Integer(fldId))) {
                    validation = 1000;
                }
            }
        } else if (axsf instanceof AxSfOut) {
            if (fldId == 5) {
                validation = 4;
            } else if (fldId == 7 || fldId == 8) {
                validation = 1;
            } else if (fldId == 9) {
                validation = 6;
            } else if (fldId == 10) {
                validation = 2;
            } else if (fldId == 12) {
                validation = 3;
            } else if (fldId > com.ieci.tecdoc.common.isicres.Keys.SREG_FDR_MATTER) {
                if (validationFiels.contains(new Integer(fldId))) {
                    validation = 1000;
                }
            }
        }

        return validation;
    }

    /***************************************************************************
     * Inner classes
     **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}

